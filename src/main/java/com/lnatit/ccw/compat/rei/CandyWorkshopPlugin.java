package com.lnatit.ccw.compat.rei;

import com.lnatit.ccw.CandyWorkshop;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.minecraft.resources.ResourceLocation;

@REIPluginCommon
public class CandyWorkshopPlugin implements REICommonPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(
                ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "default/refining"),
                RefiningDisplay.SERIALIZER
                );
    }
}
