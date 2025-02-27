package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SugarRefineryScreen extends AbstractContainerScreen<SugarRefineryMenu> {
    public static final ResourceLocation BACKGROUND_LOCATION =
            ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "textures/gui/container/sugar_refinery.png");
    public static final ResourceLocation ANIMATION_SPRITE =
            ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "container/sugar_refinery/stirring");
    public static final ResourceLocation PROGRESS_SPRITE =
            ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "container/sugar_refinery/progress");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 181;


    public SugarRefineryScreen(SugarRefineryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                RenderType::guiTextured,
                BACKGROUND_LOCATION,
                this.leftPos,
                this.topPos,
                0, 0,
                this.imageWidth,
                this.imageHeight,
                256, 256
        );
        int progress = this.menu.getProgress();
        if (progress != 0)
        {
            guiGraphics.blitSprite(
                    RenderType::guiTextured,
                    ANIMATION_SPRITE,
                    this.leftPos + 70,
                    this.topPos + 18,
                    32, 23
            );
        }
        // TODO need modification
        progress += 1;
        progress = 14 - 14 * progress / SugarRefining.REFINE_TIME;
        guiGraphics.blitSprite(
                RenderType::guiTextured,
                PROGRESS_SPRITE,
                this.leftPos + 86 - progress,
                this.topPos + 41,
                progress * 2, 1
        );
    }
}
