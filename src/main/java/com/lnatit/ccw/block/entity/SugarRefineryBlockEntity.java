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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SugarRefineryBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    public static final int REFINE_TIME = 160;

    Contents inventory = new Contents();
    boolean changed = true;
    int progress = 0;
    Optional<Recipe<?>> recipeHolder = Optional.empty();

    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity refinery) {
        if (refinery.changed) {
            // rematch the recipe (check the holder first)
            refinery.changed = false;
        }

        if (refinery.recipeHolder.isPresent()) {
            refinery.progress++;
            if (refinery.progress >= REFINE_TIME) {
                refinery.progress = 0;
                // generate the outputs
                refinery.changed = true;
            }
        }
    }

    @Override
    public void setChanged() {
        this.changed = true;
        super.setChanged();
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
    public class Contents extends ItemStackHandler {
        public static final int SUGAR_CONSUMPTION = 8;
        public static final int SUGAR_PRODUCTION = 8;

        public Contents() {
            super(8);
        }

        @Override
        public void setSize(int size) {
            super.setSize(8);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot < 4;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            SugarRefineryBlockEntity.this.changed = true;
        }
    }
}
