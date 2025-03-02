package com.lnatit.ccw.menu;

import com.lnatit.ccw.block.BlockRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SugarRefineryMenu extends AbstractContainerMenu {
    private final DataSlot data;
    private final ContainerLevelAccess access;

    // Server constructor
    public SugarRefineryMenu(int containerId, Inventory playerInventory, IItemHandler content, DataSlot data, ContainerLevelAccess access) {
        super(MenuRegistry.SUGAR_REFINERY.get(), containerId);
        this.data = data;
        this.access = access;
        this.addDataSlot(data);
        this.addRefinerySlots(content);
        this.addStandardInventorySlots(playerInventory, 8, 99);
    }

    // Client constructor
    public SugarRefineryMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new ItemStackHandler(8), DataSlot.standalone(), ContainerLevelAccess.NULL);
    }

    private void addRefinerySlots(IItemHandler contents) {
        this.addSlot(new SlotItemHandler(contents, 0, 23, 23));
        this.addSlot(new SlotItemHandler(contents, 1, 48, 23));
        this.addSlot(new SlotItemHandler(contents, 2, 108, 23));
        this.addSlot(new SlotItemHandler(contents, 3, 132, 23));

        this.addSlot(new SlotItemHandler(contents, 4, 78, 56));

        this.addSlot(new SlotItemHandler(contents, 5, 118, 71));
        this.addSlot(new SlotItemHandler(contents, 6, 136, 71));
        this.addSlot(new SlotItemHandler(contents, 7, 154, 71));
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
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, BlockRegistry.SUGAR_REFINERY.get());
    }
}
