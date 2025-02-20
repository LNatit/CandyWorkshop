package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class SugarRefineryBlockEntity extends BlockEntity implements MenuProvider, Nameable
{
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    public static final int REFINE_TIME = 100;
    public static final long MASK = 0xFFFF_FFFF_0000_0000L;

    ItemStackHandler inventory = new Contents();
    int matchTime = 0;
    long startTime = 0;
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
        // RecipeHolder will match the recipe & store max craft times, and update the data accrodingly
        // BE will store nearest finish time, check it on each tick and update if needed
        // Each time when the contents is changed by external operations (other than recipe update)
        // will trigger a rematch of RecipeHolder
        long delta = level.getGameTime() - this.startTime;
        if (this.changed) {
            this.matchTime = matchRecipe();
            if (this.matchTime > 0) {
                this.startTime = level.getGameTime();
            }
        }
        else {
            if (matchTime > 0 && delta >= REFINE_TIME) {
                int times = toInt(delta / REFINE_TIME);
                if (times > matchTime) {
                    times = this.matchTime;
                }
                this.matchTime -= times;
                this.startTime += (long) times * REFINE_TIME;
                genOutputs(times);
                // no need to mark flag since it's an internal update
                super.setChanged();
            }
        }
    }

    private int matchRecipe() {
        return 0;
    }

    private void genOutputs(int times) {

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity blockEntity) {

    }

    private static int toInt(long value) {
        if ((value & MASK) != 0) {
            return value > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
        return (int) value;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    // TODO copy BaseContainer
    @Override
    public Component getName() {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return null;
    }

    /**
     * milk, sugar, main, extra, output, misc1, misc2, misc3
     */
    public class Contents extends ItemStackHandler
    {
        public Contents() {
            super(8);
        }

        @Override
        public void setSize(int size) {
            super.setSize(8);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 ->
                    // TODO
                        stack.is(Items.MILK_BUCKET);
                case 1 -> stack.is(Items.SUGAR);
                case 2, 3 -> true;
                default -> false;
            };
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
        }
    }
}
