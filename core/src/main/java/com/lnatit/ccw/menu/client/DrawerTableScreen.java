package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.menu.DrawerTableMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class DrawerTableScreen extends AbstractContainerScreen<DrawerTableMenu> {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 222;
    private static final Identifier CONTAINER_BACKGROUND = Identifier.withDefaultNamespace(
            "textures/gui/container/generic_54.png");

    public DrawerTableScreen(DrawerTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, WIDTH, HEIGHT);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int xo = (this.width - this.imageWidth) / 2;
        int yo = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_BACKGROUND, xo, yo, 0.0F, 0.0F, this.imageWidth, 125, 256, 256);
        graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_BACKGROUND, xo, yo + 125, 0.0F, 126.0F, this.imageWidth, 96, 256, 256);
    }
}
