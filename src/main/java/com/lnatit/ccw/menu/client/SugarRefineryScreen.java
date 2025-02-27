package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SugarRefineryScreen extends AbstractContainerScreen<SugarRefineryMenu>
{
    public static final ResourceLocation BACKGROUND_LOCATION =
            ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "textures/gui/container/sugar_refinery.png");
    public static final int WIDTH = 176;
    public static final int HEIGHT = 181;


    public SugarRefineryScreen(SugarRefineryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = WIDTH;
        this.imageHeight = HEIGHT;
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
    }
}
