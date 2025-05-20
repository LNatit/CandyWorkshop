package com.lnatit.ccw.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class ModContainerMenu extends AbstractContainerMenu {
    protected ModContainerMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }


    protected void addStandardInventorySlots(Inventory playerInventory, int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }

        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k, x + k * 18, y + 58));
        }
    }
}
