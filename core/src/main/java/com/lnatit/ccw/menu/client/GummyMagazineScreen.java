package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.GummyContentMenu;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;

public class GummyMagazineScreen extends AbstractContainerScreen<GummyContentMenu> {
    public static final ResourceLocation BACKGROUND_LOCATION =
            CandyWorkshop.id("textures/gui/container/gummy_magazine.png");
    public static final ResourceLocation ACTIVE_SLOT_SPRITE =
            CandyWorkshop.id("container/gummy_container/active_slot");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 188;
    private final int activeSlots;

    public GummyMagazineScreen(GummyContentMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.activeSlots = menu.activeSize();
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
        this.inventoryLabelY = this.imageHeight - 95;
    }

    @Override
    protected void init() {
        super.init();
        Button button = new MagazineButton(
                this.leftPos + 113,
                this.topPos + 11,
                this::onButtonPress
                );
        this.addRenderableWidget(button);
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
            for (int j = 0; j < 2; j++) {
                guiGraphics.blitSprite(ACTIVE_SLOT_SPRITE, this.leftPos + 71 + j * 19, this.topPos + 13 + i * 19, 16, 16);
                slotCount++;
                if (slotCount >= activeSlots) {
                    return;
                }
            }
        }
    }

    private void onButtonPress(Button button) {
        assert this.minecraft != null;
        assert this.minecraft.gameMode != null;
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
    }

    private static class MagazineButton extends ExtendedButton {
        public static final ResourceLocation BUTTON_SPRITE =
                CandyWorkshop.id("container/gummy_magazine/button");
        public static final int MSPF = 50;
        private long lastPressTime = -1;

        public MagazineButton(int xPos, int yPos, OnPress handler) {
            super(xPos, yPos, 15, 30, Component.empty(), handler);
        }

        @Override
        public void onPress() {
            super.onPress();
            this.lastPressTime = Util.getMillis();
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            long delta = Util.getMillis() - this.lastPressTime;
            int frame = (int) (delta / MSPF);
            if (frame < 4) {
                guiGraphics.blitSprite(BUTTON_SPRITE, 60, 30, 20 + frame * 10, 0, this.getX(), this.getY(), 10, this.getHeight());
            } else if (this.isHovered()) {
                guiGraphics.blitSprite(BUTTON_SPRITE, 60, 30, 10, 0, this.getX(), this.getY(), 10, this.getHeight());
            } else {
                guiGraphics.blitSprite(BUTTON_SPRITE, 60, 30, 0, 0, this.getX(), this.getY(), 10, this.getHeight());
            }
        }
    }
}
