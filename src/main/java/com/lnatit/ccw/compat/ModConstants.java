package com.lnatit.ccw.compat;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.client.SugarRefineryScreen;
import net.minecraft.resources.ResourceLocation;

public class ModConstants
{
    // Note: it's a SPRITE!
    public static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.fromNamespaceAndPath(
            CandyWorkshop.MODID,
            "compat/refining"
    );
    public static final ResourceLocation ANIMATION_SPRITE = SugarRefineryScreen.ANIMATION_SPRITE;
}
