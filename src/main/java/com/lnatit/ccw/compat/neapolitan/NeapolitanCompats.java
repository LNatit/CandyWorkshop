package com.lnatit.ccw.compat.neapolitan;

import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import net.minecraftforge.registries.RegistryObject;

public class NeapolitanCompats
{
    public static final RegistryObject<SingleEffectSugar> HOOHOO_HAHA =
            Sugars.registerSingle("hoohoo_haha",
                                  builder -> builder
                                          .withEffect(NeapolitanMobEffects.AGILITY.get())
                                          .withNoExcited()
                                          .build()
            );

    public static final RegistryObject<SingleEffectSugar> HOOHOO_SMOOTH =
            Sugars.registerSingle("hoohoo_smooth",
                                  builder -> builder
                                          .withEffect(NeapolitanMobEffects.SLIPPING.get())
                                          .withNoExcited()
                                          .build()
            );

    public static final RegistryObject<SingleEffectSugar> MINT =
            Sugars.registerSingle("mint",
                                  builder -> builder
                                          .withEffect(NeapolitanMobEffects.BERSERKING.get())
                                          .withNoExcited()
                                          .build()
            );

    public static final RegistryObject<SingleEffectSugar> RED_BEAN =
            Sugars.registerSingle("red_bean",
                                  builder -> builder
                                          .withEffect(NeapolitanMobEffects.HARMONY.get())
                                          .withNoExcited()
                                          .build()
            );

    public static final RegistryObject<SingleEffectSugar> VANILLA =
            Sugars.registerSingle("vanilla",
                                  builder -> builder
                                          .withEffect(NeapolitanMobEffects.VANILLA_SCENT.get())
                                          .withNoExcited()
                                          .build()
            );

    public static void init() {
        SugarRefining.addCustomBlendProviders(NeapolitanCompats::addBlends);
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(HOOHOO_HAHA, NeapolitanItems.DRIED_BANANA.get());
        builder.addOverworldBlend(HOOHOO_SMOOTH, NeapolitanItems.BANANA.get());
        builder.addOverworldBlend(MINT, NeapolitanItems.MINT_LEAVES.get());
        builder.addOverworldBlend(RED_BEAN, NeapolitanItems.ROASTED_ADZUKI_BEANS.get());
        builder.addOverworldBlend(VANILLA, NeapolitanItems.DRIED_VANILLA_PODS.get());
    }
}
