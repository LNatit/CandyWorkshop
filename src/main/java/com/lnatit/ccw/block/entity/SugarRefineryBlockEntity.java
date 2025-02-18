package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SugarRefineryBlockEntity extends BaseContainerBlockEntity
{
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    public static final int REFINE_TIME = 100;

    ItemStackHandler inventory = new ItemStackHandler(8);
    int refineTime;
    long finishTime = 0;
    boolean changed = true;


    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    @Override
    public void setChanged() {
        this.changed = true;
        super.setChanged();
    }

    public void tick(Level level) {
        // TODO we need to consider the skipped ticks!
        long delta = finishTime - level.getGameTime();
        if (this.changed) {
            if (this.isRefineable()) {
                // start: update finishTime
            }
        }
        else {
            if (delta % REFINE_TIME == 0) {
                // update the results
            }
        }
    }

    private boolean isRefineable() {

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity blockEntity) {

    }

    @Override
    protected Component getDefaultName() {
        return DEFAULT_NAME;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        // Is this really usefull?
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new SugarRefineryMenu(containerId, inventory);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }
}
