package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
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
    public static final int REFINE_TIME = 160;

    Contents inventory = new Contents();
    boolean changed = true;
    int progress = 0;
    ItemStack making = ItemStack.EMPTY;

    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity refinery) {
        if (level.isClientSide()) return;

        if (refinery.changed) {
            // rematch the recipe
            refinery.matchRecipe();
            refinery.changed = false;
        }

        if (!refinery.making.isEmpty()) {
            refinery.progress++;
            if (refinery.progress >= REFINE_TIME) {
                refinery.progress = 0;
                // generate the outputs
                refinery.generateOutputs();
                refinery.changed = true;
            }
        }
    }

    private void matchRecipe() {
        if (this.inventory.hasEnoughMilkAndSugar()) {
            ItemStack main = this.inventory.getMain();
            ItemStack output = this.inventory.makeSugar();
            if (main.isEmpty() || !this.inventory.outputMatches(output)) {
                output = ItemStack.EMPTY;
            }

            if (!ItemStack.isSameItemSameComponents(this.making, output)) {
                this.progress = 0;
            }
            this.making = output;
        }
    }

    private void generateOutputs() {
        ItemStack output = this.inventory.getStackInSlot(4);
        // TODO consume ingredients
        if (output.isEmpty()) {
            this.inventory.setStackInSlot(4, this.making);
        }
        else {
            output.grow(output.getCount());
        }
        this.making = ItemStack.EMPTY;
    }

    @Override
    public void setChanged() {
        this.changed = true;
        super.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("progress", progress);
        tag.put("making", making.save(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        making = ItemStack.parseOptional(registries, tag.getCompound("making"));
        changed = true;
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
        return new SugarRefineryMenu(containerId, playerInventory, ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    /**
     * milk, sugar, main, extra, output, misc1, misc2, misc3
     */
    public class Contents extends ItemStackHandler
    {
        public static final int SUGAR_CONSUMPTION = 8;
        public static final int SUGAR_PRODUCTION = 8;

        public Contents() {
            super(8);
        }

        public boolean hasEnoughMilkAndSugar() {
            ItemStack milk = getStackInSlot(0);
            ItemStack sugar = getStackInSlot(1);
            return milk.is(Items.MILK_BUCKET) &&
                    sugar.is(Items.SUGAR) &&
                    sugar.getCount() >= SUGAR_CONSUMPTION;
        }

        public ItemStack getMain() {
            return getStackInSlot(2);
        }

        public ItemStack getExtra() {
            return getStackInSlot(3);
        }

        public ItemStack makeSugar() {
            ItemStack sugar = SugarRefining.sugarRefining.makeSugar(getMain(), getExtra());
            sugar.setCount(SUGAR_PRODUCTION);
            return sugar;
        }

        public boolean outputMatches(ItemStack itemStack) {
            ItemStack output = getStackInSlot(4);
            return output.isEmpty() ||
                    ItemStack.isSameItemSameComponents(output, itemStack) &&
                            output.getCount() + itemStack.getCount() <= output.getMaxStackSize();
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
