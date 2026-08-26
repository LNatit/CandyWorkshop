package com.lnatit.ccw.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public interface ExtractedContainer
{
    ItemStacksResourceHandler getInventory();

    default int getAnalogOutput() {
        var inv = getInventory();
        int size = inv.size();
        float f = 0.0F;
        for (int i = 0; i < size; i++) {
            ItemResource resource = inv.getResource(i);
            if (!resource.isEmpty()) {
                f += (float) inv.getAmountAsInt(i) / resource.getMaxStackSize();
            }
        }
        f /= (float) size;
        return Mth.lerpDiscrete(f, 0, 15);
    }

    default void onRemove(BlockPos pos, Level level, BlockState state) {
        var inv = getInventory();
        for (ItemStack stack : inv.copyToList()) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        level.updateNeighbourForOutputSignal(pos, state.getBlock());
    }
}
