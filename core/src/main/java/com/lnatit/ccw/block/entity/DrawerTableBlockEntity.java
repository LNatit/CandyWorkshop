package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.menu.DrawerTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class DrawerTableBlockEntity extends BlockEntity implements MenuProvider, Nameable, IItemStackHandlerContainer
{
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
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.inventory.deserialize(input.childOrEmpty("inventory"));
        this.name = parseCustomNameSafe(input, "CustomName");
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("inventory", this.inventory);
        output.storeNullable("CustomName", ComponentSerialization.CODEC, this.name);
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

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            this.onRemove(pos, this.level, state);
        }
    }
}
