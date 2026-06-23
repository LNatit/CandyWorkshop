package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.GummyContentMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class GummyGlazerScreen extends AbstractContainerScreen<GummyContentMenu> {
    public static final Identifier BACKGROUND_LOCATION =
            CandyWorkshop.id("textures/gui/container/gummy_glazer.png");
    public static final Identifier ACTIVE_SLOT_SPRITE =
            CandyWorkshop.id("container/gummy_container/active_slot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 188;
    private final int activeSlots;

    public GummyGlazerScreen(GummyContentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, WIDTH, HEIGHT);
        this.activeSlots = menu.activeSize();
        this.inventoryLabelY = this.imageHeight - 95;
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                BACKGROUND_LOCATION,
                this.leftPos,
                this.topPos,
                0, 0,
                this.imageWidth,
                this.imageHeight,
                256, 256
        );
        int slotCount = 0;
        for (int i = 0; i < 3; i++) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, ACTIVE_SLOT_SPRITE, this.leftPos + 80, this.topPos + 13 + i * 19, 16, 16);
            slotCount++;
            if (slotCount >= activeSlots) {
                return;
            }
        }
    }
}
