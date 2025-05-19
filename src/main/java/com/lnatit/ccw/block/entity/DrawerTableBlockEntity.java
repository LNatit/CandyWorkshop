package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.menu.DrawerTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class DrawerTableBlockEntity extends BlockEntity implements MenuProvider, Nameable, IItemStackHandlerContainer {
    public static final int SIZE = 54;
    public static final Component DEFAULT_NAME = Component.translatable("container.drawer_table");

    private final ItemStackHandler inventory = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            DrawerTableBlockEntity.this.setChanged();
        }
    };
    @Nullable
    private Component name;

    public DrawerTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.DRAWER_TABLE_BETYPE.get(), pos, state);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        this.onRemove(pos, state, this.level);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tag.getCompound("inventory").ifPresent(t -> this.inventory.deserializeNBT(registries, t));
        this.name = parseCustomNameSafe(tag.get("CustomName"), registries);
    }

    public IItemHandler accessInventory(@Nullable Direction direction) {
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

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DrawerTableMenu(containerId, playerInventory, this.inventory,
                ContainerLevelAccess.create(this.level, this.worldPosition)
        );
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.inventory;
    }
}
