package com.lnatit.ccw.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public interface IItemStackHandlerContainer
{
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
        return Mth.lerpInt(f, 0, 15);
    }

    default void onRemove(BlockState state, BlockState newState, Level level, BlockPos pos) {
        if (!state.is(newState.getBlock())) {
            for (int i = 0; i < getInventory().getSlots(); i++) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), getInventory().getStackInSlot(i));
            }
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }
}
