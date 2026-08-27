package com.lnatit.ccw.item.component;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.Tier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;
import java.util.function.Function;

public class MutableContents extends ItemStacksResourceHandler implements IContents
{
    public static final StreamCodec<RegistryFriendlyByteBuf, MutableContents> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_LIST_STREAM_CODEC,
            IContents::stacks,
            Type.STREAM_CODEC,
            IContents::type,
            Tier.STREAM_CODEC,
            m -> m.tier,
            MutableContents::new
    );

    private final Tier tier;
    private final Type type;

    public MutableContents(GummyContents contents, Tier tier) {
        this(contents.stacks(), contents.type(), tier);
    }

    private MutableContents(List<ItemStack> stacks, Type type, Tier tier) {
        super(type.size);
        this.type = type;
        this.tier = tier;
        for (int i = 0; i < type.size; i++) {
            ItemStack stack = stacks.get(i);
            super.set(i, ItemResource.of(stack), stack.getCount());
        }
    }

    public int activeSize() {
        return Math.min(this.slotCount(), this.type().tierMarch * (this.tier.ordinal() + 1));
    }

    /**
     * @return a modifiable list of the active slots
     */
    public List<ItemStack> activeSlots() {
        List<ItemStack> snapshot = this.copyToList();
        int length = this.activeSize();
        return snapshot.subList(0, length);
    }

    private void feed(int slot, ItemResource resource, Transaction transaction) {
        ItemStack template = resource.toStack();

        // The original stack is only used as a type template; refill starts from an empty target slot.
        int targetSize = Math.min(this.getCapacity(slot, resource), template.getMaxStackSize());
        if (targetSize <= 0) {
            this.set(slot, ItemResource.EMPTY, 0);
            return;
        }

        int pulled = 0;
        try (Transaction child = Transaction.open(transaction)) {
            this.extract(slot, resource, this.getAmountAsInt(slot), child);

            for (int i = this.activeSize(); i < this.slotCount() && pulled < targetSize; i++) {
                ItemResource source = this.getResource(i);
                if (!source.isEmpty() && source.toStack().is(template.getItem())
                    && ItemStack.isSameItemSameComponents(source.toStack(), template)) {
                    pulled += this.extract(i, source, targetSize - pulled, child);
                }
            }

            if (pulled > 0) {
                this.insert(slot, resource, pulled, child);
            }
            child.commit();
        }
    }

    public boolean apply(Function<ItemStack, ItemStack> consumer) {
        boolean changed = false;
        List<ItemStack> active = this.activeSlots();

        try (Transaction transaction = Transaction.openRoot()) {
            for (int i = 0; i < active.size(); i++) {
                ItemResource resource = this.getResource(i);
                if (!resource.isEmpty()) {
                    if (this.tryExtract(i, resource, transaction))
                    {
                        changed = true;
                    }
                    else {
                        transaction.close();
                        return false;
                    }

                    if (this.getAmountAsInt(i) == 0) {
                        feed(i, resource, transaction);
                    }
                }
            }
            transaction.commit();
        }
        active.forEach(consumer::apply);
        return changed;
    }

    private boolean tryExtract(int slot, ItemResource resource, Transaction transaction) {
        try (Transaction child = Transaction.open(transaction)) {
            int extracted = this.extract(slot, resource, 1, child);
            if (extracted == 0) {
                return false;
            }
            child.commit();
            return true;
        }
    }

    private int slotCount() {
        return this.copyToList().size();
    }

    @Override
    public List<ItemStack> stacks() {
        return this.copyToList();
    }

    @Override
    public Type type() {
        return this.type;
    }

    @Override
    public void set(int index, ItemResource resource, int amount) {
        if (isValid(index, resource)) {
            super.set(index, resource, amount);
        }
        // Don't throw exception here
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return resource.isEmpty() || index < this.size() && resource.is((ItemLike) ItemRegistry.GUMMY);
    }
}
