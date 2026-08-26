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

import java.util.ArrayList;
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
        return this.type().tierMarch * (this.tier.ordinal() + 1);
    }

    public List<ItemStack> activeSlots() {
        List<ItemStack> snapshot = this.copyToList();
        int length = Math.min(snapshot.size(), this.activeSize());
        return snapshot.subList(0, length);
    }

    private void feed(int slot) {
        ItemStack template = this.getStackSnapshot(slot);
        if (template.isEmpty()) {
            return;
        }

        // The original stack is only used as a type template; refill starts from an empty target slot.
        int targetSize = Math.min(this.getCapacity(slot, ItemResource.of(template)), template.getMaxStackSize());
        if (targetSize <= 0) {
            this.set(slot, ItemResource.EMPTY, 0);
            return;
        }

        int pulled = 0;
        try (Transaction transaction = Transaction.openRoot()) {
            for (int i = this.activeSize(); i < this.slotCount() && pulled < targetSize; i++) {
                ItemResource source = this.getResource(i);
                if (!source.isEmpty() && source.toStack().is(template.getItem())
                    && ItemStack.isSameItemSameComponents(source.toStack(), template)) {
                    pulled += this.extract(i, source, targetSize - pulled, transaction);
                }
            }

            if (pulled > 0) {
                this.insert(slot, ItemResource.of(template), pulled, transaction);
            }
            transaction.commit();
        }
    }

    public boolean apply(Function<ItemStack, ItemStack> consumer) {
        boolean changed = false;
        int active = Math.min(this.slotCount(), this.activeSize());
        List<ItemStack> results = new ArrayList<>(active);
        this.activeSlots().forEach(stack -> results.add(consumer.apply(stack)));

        for (int i = 0; i < results.size(); i++) {
            ItemStack old = this.getStackSnapshot(i);
            ItemStack updated = results.get(i);
            if (ItemStack.isSameItemSameComponents(old, updated) && old.getCount() == updated.getCount()) {
                continue;
            }
            if (updated.isEmpty()) {
                feed(i);
            }
            else {
                this.setSlot(i, updated);
            }
            changed = true;
        }
        return changed;
    }

    private void setSlot(int slot, ItemStack stack) {
        try (Transaction transaction = Transaction.openRoot()) {
            ItemResource current = this.getResource(slot);
            if (!current.isEmpty()) {
                this.extract(slot, current, this.getAmountAsInt(slot), transaction);
            }
            if (!stack.isEmpty()) {
                this.insert(slot, ItemResource.of(stack), stack.getCount(), transaction);
            }
            transaction.commit();
        }
    }

    private ItemStack getStackSnapshot(int slot) {
        ItemResource resource = this.getResource(slot);
        if (resource.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return resource.toStack().copyWithCount(this.getAmountAsInt(slot));
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
