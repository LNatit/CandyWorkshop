package com.lnatit.ccw.item.component;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.Tier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MutableContents extends ItemStackHandler implements IContents
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
            this.stacks.set(i, stacks.get(i));
        }
    }

    public int activeSize() {
        return this.type().tierMarch * (this.tier.ordinal() + 1);
    }

    public List<ItemStack> activeSlots() {
        int length = Math.min(this.stacks.size(), this.activeSize());
        return this.stacks.subList(0, length);
    }

    public List<ItemStack> updateSlots(List<ItemStack> stacks) {
        stacks = stacks.subList(0, Math.min(stacks.size(), this.activeSize()));
        List<ItemStack> unaccepted = new ArrayList<>();
        for (int i = 0; i < stacks.size(); i++) {
            ItemStack stack = stacks.get(i);
            if (isItemValid(i, stack)) {
                if (stack.isEmpty()) {
                    feed(i);
                }
                else {
                    this.stacks.set(i, stack);
                }
            }
            else {
                this.stacks.set(i, ItemStack.EMPTY);
                unaccepted.add(stack);
            }
        }
        return unaccepted;
    }

    private void feed(int slot) {
        ItemStack target = this.stacks.get(slot);
        for (int i = this.activeSize(); i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (ItemStack.isSameItemSameComponents(target, stack)) {
                this.stacks.set(slot, stack);
                this.stacks.set(i, ItemStack.EMPTY);
                return;
            }
        }
    }

    public List<ItemStack> apply(Function<ItemStack, ItemStack> consumer) {
        List<ItemStack> results = this.activeSlots().stream().map(consumer).toList();
        return updateSlots(results);
    }

    @Override
    public List<ItemStack> stacks() {
        return this.stacks;
    }

    @Override
    public Type type() {
        return this.type;
    }

    @Override
    public void setSize(int size) {
        throw new RuntimeException("Resize is not allowed!");
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (isItemValid(slot, stack)) {
            super.setStackInSlot(slot, stack);
        }
        else {
            throw new RuntimeException("Invalid item " + stack + " in slot " + slot + "!");
        }
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.isEmpty() || slot < this.stacks.size() && stack.is(ItemRegistry.GUMMY);
    }

}
