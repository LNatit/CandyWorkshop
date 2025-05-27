package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class SugarRefineryScreen extends AbstractContainerScreen<SugarRefineryMenu>
{
    public static final ResourceLocation BACKGROUND_LOCATION =
            new ResourceLocation(CandyWorkshop.MODID, "textures/gui/container/sugar_refinery.png");
    public static final TextureAtlasSprite PROGRESS_SPRITE =
            Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(
                    new ResourceLocation(CandyWorkshop.MODID, "gui/container/sugar_refinery/progress")
            );
    public static final TextureAtlasSprite ANIMATION_SPRITE =
            Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(
                    new ResourceLocation(CandyWorkshop.MODID, "gui/container/sugar_refinery/stirring")
            );
    public static final Tooltip PAUSE = Tooltip.create(Component.translatable("container.sugar_refinery.pause"));
    public static final Tooltip START = Tooltip.create(Component.translatable("container.sugar_refinery.start"));
    public static final int WIDTH = 176;
    public static final int HEIGHT = 181;


    public SugarRefineryScreen(SugarRefineryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
        this.inventoryLabelY = this.imageHeight - 95;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        Button switcher = new InvisibleButton(
                this.leftPos + 70,
                this.topPos + 18,
                this::onIconPress
        );
        switcher.setTooltip(this.menu.getProgress() >= 0 ? PAUSE : START);
        this.addRenderableWidget(switcher);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
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

        int progress = this.menu.getProgress();

        if (progress > 0) {
            guiGraphics.blit(
                    this.leftPos + 72,
                    this.topPos + 22,
                    0,
                    28, 19,
                    ANIMATION_SPRITE
            );
        }
        else {
            progress = 0;
        }

        progress = -15 * progress / SugarRefining.REFINE_TIME;
        guiGraphics.blit(
                this.leftPos + 72 - progress,
                this.topPos + 41,
                0,
                28 + progress * 2, 1,
                PROGRESS_SPRITE
        );
    }

    private void onIconPress(Button button) {
        assert this.minecraft != null;
        assert this.minecraft.gameMode != null;
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
        button.setTooltip(this.menu.getProgress() >= 0 ? START : PAUSE);
    }

    private static class InvisibleButton extends ExtendedButton
    {
        public InvisibleButton(int xPos, int yPos, OnPress handler) {
            super(xPos, yPos, 32, 23, Component.empty(), handler);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // no extra rendering needed...
        }
    }
}
