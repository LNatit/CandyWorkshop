package com.lnatit.ccw.menu;

import dev.shadowsoffire.placebo.menu.LockedSlot;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public abstract class ModContainerMenu extends AbstractContainerMenu {
    protected ModContainerMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }


    protected void addStandardInventorySlots(Inventory playerInventory, int x, int y) {
        addLockedInventorySlots(playerInventory, x, y, -1);
    }

    @Nullable
    protected Slot addLockedInventorySlots(Inventory playerInventory, int x, int y, int locked) {
        LockedSlot slot = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.addSlot(auto(playerInventory, j + i * 9 + 9, x + j * 18, y + i * 18, locked)) instanceof LockedSlot l) {
                    slot = l;
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            if (this.addSlot(auto(playerInventory, k, x + k * 18, y + 58, locked)) instanceof LockedSlot l) {
                slot = l;
            }
        }
        return slot;
    }

    private static Slot auto(Inventory playerInventory, int slotId, int x, int y, int locked) {
        if (slotId == locked) {
            return new LockedSlot(playerInventory, slotId, x, y);
        }
        return new Slot(playerInventory, slotId, x, y);
    }
}
