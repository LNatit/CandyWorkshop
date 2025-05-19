package com.lnatit.ccw.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public interface IItemStackHandlerContainer {
    ItemStackHandler getInventory();

    default int getAnalogOutput() {
        int size = getInventory().getSlots();
        float f = 0.0F;
        for (int i = 0; i < size; i++) {
            ItemStack itemstack = getInventory().getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                ItemStackHandler handler = getInventory();
                f += (float) itemstack.getCount() / Math.min(handler.getSlotLimit(i), itemstack.getMaxStackSize());
            }
        }
        f /= (float) size;
        return Mth.lerpDiscrete(f, 0, 15);
    }

    default void onRemove(BlockPos pos, BlockState state, Level level) {
        for (int i = 0; i < getInventory().getSlots(); i++) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), getInventory().getStackInSlot(i));
        }
    }
}
