package com.lnatit.ccw.compat.farmersrespite;

import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.registry.FREffects;
import umpaz.farmersrespite.common.registry.FRItems;

public class FarmersRespiteCompats
{
    public static final RegistryObject<SingleEffectSugar> CAFFEINATED =
            Sugars.registerSingle("caffeinated",
                                  builder -> builder
                                          .withEffect(FREffects.CAFFEINATED.get())
                                          .build()
            );

    public static void init() {
        SugarRefining.addCustomBlendProviders(FarmersRespiteCompats::addBlends);
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(CAFFEINATED, FRItems.COFFEE_CAKE_SLICE.get());
    }
}
