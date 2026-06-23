package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;

public class SugarRefineryScreen extends AbstractContainerScreen<SugarRefineryMenu> {
    public static final Identifier BACKGROUND_LOCATION =
            CandyWorkshop.id("textures/gui/container/sugar_refinery.png");
    public static final Identifier ANIMATION_SPRITE =
            CandyWorkshop.id("container/sugar_refinery/stirring");
    public static final Identifier PROGRESS_SPRITE =
            CandyWorkshop.id("container/sugar_refinery/progress");
    public static final Tooltip PAUSE = Tooltip.create(Component.translatable("container.sugar_refinery.pause"));
    public static final Tooltip START = Tooltip.create(Component.translatable("container.sugar_refinery.start"));
    public static final int WIDTH = 176;
    public static final int HEIGHT = 181;


    public SugarRefineryScreen(SugarRefineryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, WIDTH, HEIGHT);
        this.inventoryLabelY = this.imageHeight - 95;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        Button switcher = new InvisibleButton(
                this.leftPos + 70,
                this.topPos + 18,
                this::onIconPress);
        switcher.setTooltip(this.menu.getProgress() >= 0 ? PAUSE : START);
        this.addRenderableWidget(switcher);
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

        int progress = this.menu.getProgress();

        if (progress > 0)
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    ANIMATION_SPRITE,
                    this.leftPos + 72,
                    this.topPos + 22,
                    28, 19
            );
        else progress = 0;

        progress = -15 * progress / SugarRefineryBlockEntity.REFINE_TIME;
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                PROGRESS_SPRITE,
                this.leftPos + 72 - progress,
                this.topPos + 41,
                28 + progress * 2, 1
        );
    }

    private void onIconPress(Button button) {
        assert this.minecraft != null;
        assert this.minecraft.gameMode != null;
        this.minecraft.gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
        button.setTooltip(this.menu.getProgress() >= 0 ? START : PAUSE);
    }

    private static class InvisibleButton extends ExtendedButton {
        public InvisibleButton(int xPos, int yPos, OnPress handler) {
            super(xPos, yPos, 32, 23, Component.empty(), handler);
        }

        @Override
        public void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        }
    }
}
