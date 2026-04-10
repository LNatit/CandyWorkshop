package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.GummyContentMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GummyGlazerScreen extends AbstractContainerScreen<GummyContentMenu> {
    public static final ResourceLocation BACKGROUND_LOCATION =
            CandyWorkshop.id("textures/gui/container/gummy_glazer.png");
    public static final ResourceLocation ACTIVE_SLOT_SPRITE =
            CandyWorkshop.id("container/gummy_container/active_slot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 188;
    private final int activeSlots;

    public GummyGlazerScreen(GummyContentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.activeSlots = menu.activeSize();
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
        this.inventoryLabelY = this.imageHeight - 95;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
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
                guiGraphics.blitSprite(ACTIVE_SLOT_SPRITE, this.leftPos + 80, this.topPos + 13 + i * 19, 16, 16);
                slotCount++;
                if (slotCount >= activeSlots) {
                    return;
            }
        }
    }
}
