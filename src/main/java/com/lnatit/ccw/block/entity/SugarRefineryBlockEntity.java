package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarContents;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class SugarRefineryBlockEntity extends BlockEntity implements MenuProvider, Nameable
{
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    private final Contents inventory = new Contents();
    @Nullable
    private Component name;
    private boolean changed = true;
    /**
     * 0: Pause
     * >0: Progress
     */
    private int progress = 0;

    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity refinery) {
        if (level.isClientSide()) return;

        if (refinery.changed) {
            // match recipe
            refinery.progress = refinery.tryMatchRecipe() ? 1 : 0;
            refinery.changed = false;
        }
        else if (refinery.progress > 0) {
            refinery.progress++;
        }

        if (refinery.progress >= SugarRefining.REFINE_TIME) {
            refinery.progress = 0;
            // generate the outputs
            refinery.generateOutputs();
            // the flag is set during output generation
            // refinery.changed = true;
        }
    }

    private boolean tryMatchRecipe() {
        return this.inventory.hasEnoughMilkAndSugar()
                && !this.inventory.getMain().isEmpty()
                && this.inventory.outputMatches();
    }

    private void generateOutputs() {
        Holder<Sugar> flavor = this.inventory.simulate();
        Sugar.Type type;

        // consume ingredients
        ItemStack milk = this.inventory.getStackInSlot(0).copy();
        int milkConsumption = 1;
        this.acceptRemainder(milk.getCraftingRemainder(), milkConsumption);
        milk.shrink(milkConsumption);
        this.inventory.setStackInSlot(0, milk);

        ItemStack sugar = this.inventory.getStackInSlot(1).copy();
        sugar.shrink(Contents.SUGAR_CONSUMPTION);
        this.inventory.setStackInSlot(1, sugar);

        ItemStack main = this.inventory.getMain().copy();
        this.acceptRemainder(main.getCraftingRemainder());
        main.shrink(1);
        this.inventory.setStackInSlot(2, main);

        ItemStack extra = this.inventory.getExtra().copy();
        type = Sugar.Type.fromExtra(extra);
        if (type != Sugar.Type.BASE) {
            this.acceptRemainder(extra.getCraftingRemainder());
            extra.shrink(1);
            this.inventory.setStackInSlot(3, extra);
        }

        // generate sugar
        ItemStack exist = this.inventory.getStackInSlot(4);
        if (exist.isEmpty()) {
            exist = Sugar.createSugarItem(flavor, type);
            exist.setCount(Contents.SUGAR_PRODUCTION);
            this.inventory.setStackInSlot(4, exist);
        }
        else {
            exist.grow(Contents.SUGAR_PRODUCTION);
        }
    }

    private void acceptRemainder(ItemStack remainder) {
        this.acceptRemainder(remainder, 1);
    }

    private void acceptRemainder(ItemStack remainder, int count) {
        remainder.setCount(count);
        for (int i = 5; i < 8; i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.isEmpty()) {
                remainder.setCount(count);
                this.inventory.setStackInSlot(i, remainder);
                return;
            }
            else if (ItemStack.isSameItemSameComponents(stack, remainder)) {
                int consume = Math.min(stack.getMaxStackSize() - stack.getCount(), count);
                stack.grow(consume);
                this.inventory.setStackInSlot(i, stack);
                count -= consume;
                if (count == 0) {
                    return;
                }
            }
        }
        if (count > 0) {
            remainder.setCount(count);
            Containers.dropItemStack(this.level, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), remainder);
        }
    }

    @Override
    public void setChanged() {
        this.changed = true;
        super.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        this.progress = tag.getInt("progress");
        this.changed = true;
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : DEFAULT_NAME;
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    public @Nullable Component getCustomName() {
        return this.name;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SugarRefineryMenu(
                containerId,
                playerInventory,
                this.inventory,
                new DataSlot()
                {
                    @Override
                    public int get() {
                        return SugarRefineryBlockEntity.this.progress;
                    }

                    @Override
                    public void set(int value) {
                        SugarRefineryBlockEntity.this.progress = value;
                    }
                },
                ContainerLevelAccess.create(this.level, this.worldPosition)
        );
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

        @Nullable
        public Holder<Sugar> simulate() {
            return SugarRefining.sugarRefining.getSugar(getMain());
        }

        public boolean outputMatches() {
            Holder<Sugar> sugar = simulate();
            Sugar.Type type = Sugar.Type.fromExtra(getExtra());
            if (sugar == null) return false;
            ItemStack output = getStackInSlot(4);
            if (output.isEmpty()) return true;
            SugarContents outputData = output.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
            if (outputData == null) return false;
            return outputData.is(sugar, type) && output.getCount() + SUGAR_PRODUCTION <= output.getMaxStackSize();
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
