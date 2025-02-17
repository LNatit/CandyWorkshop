package com.lnatit.ccw.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class SugarRefineryMenu extends AbstractContainerMenu
{
    public SugarRefineryMenu(int containerId, Inventory playerInventory) {
        super(MenuRegistry.SUGAR_REFINERY.get(), containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }
}
