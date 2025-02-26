package com.lnatit.ccw.menu;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class SugarRefineryMenu extends AbstractContainerMenu
{
    private ContainerLevelAccess access;

    // Server constructor
    public SugarRefineryMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuRegistry.SUGAR_REFINERY.get(), containerId);
        this.access = access;
    }

    // Client constructor
    public SugarRefineryMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, BlockRegistry.SUGAR_REFINERY.get());
    }
}
