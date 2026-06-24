package com.lnatit.ccw.menu;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class DrawerTableMenu extends ModContainerMenu
{
    private final ContainerLevelAccess access;

    // Server Constructor
    public DrawerTableMenu(int containerId, Inventory playerInventory, StacksResourceHandler<ItemStack, ItemResource> content, ContainerLevelAccess access) {
        super(MenuRegistry.DRAWER_TABLE.get(), containerId);
        this.access = access;
        this.addSixRowsSlots(content);
        this.addStandardInventorySlots(playerInventory, 8, 139);
    }

    // Client Constructor
    public DrawerTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new ItemStacksResourceHandler(DrawerTableBlockEntity.SIZE),
             ContainerLevelAccess.NULL
        );
    }

    private void addSixRowsSlots(StacksResourceHandler<ItemStack, ItemResource> contents) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new ResourceHandlerSlot(contents, contents::set, j + i * 9, 8 + j * 18, 18 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < 6 * 9) {
                if (!this.moveItemStackTo(stack, 54, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(stack, 0, 54, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate(
                (level, pos) -> level.getBlockState(pos).is(BlockRegistry.DRAWER_TABLE_TAG) &&
                        player.isWithinBlockInteractionRange(pos, 8.0),
                true
        );
    }
}
