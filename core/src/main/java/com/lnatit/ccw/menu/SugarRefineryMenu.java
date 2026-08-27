package com.lnatit.ccw.menu;

import com.lnatit.ccw.block.BlockRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.IndexModifier;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.StacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

public class SugarRefineryMenu extends ModContainerMenu {
    private final DataSlot data;
    private final ContainerLevelAccess access;

    // Server constructor
    public SugarRefineryMenu(int containerId, Inventory playerInventory, StacksResourceHandler<ItemStack, ItemResource> content, DataSlot data, ContainerLevelAccess access) {
        super(MenuRegistry.SUGAR_REFINERY.get(), containerId);
        this.data = data;
        this.access = access;
        this.addDataSlot(data);
        this.addRefinerySlots(content);
        this.addStandardInventorySlots(playerInventory, 8, 99);
    }

    // Client constructor
    public SugarRefineryMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new ItemStacksResourceHandler(8), DataSlot.standalone(), ContainerLevelAccess.NULL);
    }

    private void addRefinerySlots(StacksResourceHandler<ItemStack, ItemResource> contents) {
        this.addSlot(new InteractiveSlot(contents, 0, 23, 23));
        this.addSlot(new InteractiveSlot(contents, 1, 48, 23));
        this.addSlot(new InteractiveSlot(contents, 2, 108, 23));
        this.addSlot(new InteractiveSlot(contents, 3, 132, 23));

        this.addSlot(new LockedSlot(contents, contents::set, 4, 78, 56));

        this.addSlot(new LockedSlot(contents, contents::set, 5, 118, 71));
        this.addSlot(new LockedSlot(contents, contents::set, 6, 136, 71));
        this.addSlot(new LockedSlot(contents, contents::set, 7, 154, 71));
    }

    public int getProgress() {
        return data.get();
    }

    @Override
    public boolean clickMenuButton(Player player, int id) {
        if (id == 0) {
            data.set(getProgress() >= 0 ? -1 : 0);
            return true;
        }
        return super.clickMenuButton(player, id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < 8) {
                if (!this.moveItemStackTo(stack, 8, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(stack, 0, 4, false)) {
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
        return AbstractContainerMenu.stillValid(this.access, player, BlockRegistry.SUGAR_REFINERY.get());
    }

    public static class InteractiveSlot extends ResourceHandlerSlot {
        public InteractiveSlot(StacksResourceHandler<ItemStack, ItemResource> itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, itemHandler::set, index, xPosition, yPosition);
        }

        // Fxxk REI!
        @Override
        public boolean mayPlace(ItemStack stack) {
            if (stack.isEmpty()) {
                return true;
            }
            return super.mayPlace(stack);
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return true;
        }
    }

    public static class LockedSlot extends ResourceHandlerSlot {
        public LockedSlot(
                ResourceHandler<ItemResource> handler,
                IndexModifier<ItemResource> slotModifier,
                int handlerSlot,
                int xPosition,
                int yPosition
        ) {
            super(handler, slotModifier, handlerSlot, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
