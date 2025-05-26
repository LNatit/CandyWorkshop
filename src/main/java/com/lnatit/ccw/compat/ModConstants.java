package com.lnatit.ccw.compat;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.client.SugarRefineryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModConstants
{
    public static final Component TITLE = Component.translatable("compat.ccw.rei.title");

    public static final TextureAtlasSprite BACKGROUND_SPRITE =
            Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "gui/compat/refining")
            );
    public static final TextureAtlasSprite ANIMATION_SPRITE = SugarRefineryScreen.ANIMATION_SPRITE;
}
