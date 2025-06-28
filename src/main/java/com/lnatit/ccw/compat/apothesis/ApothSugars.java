package com.lnatit.ccw.compat.apothesis;

import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ApothSugars {
    public static final DeferredHolder<Sugar, SingleEffectSugar> BLEEDING = Sugars.registerSingle(
            "bleeding",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.BLEEDING)
                    .build()
    );
    public static final DeferredHolder<Sugar, SingleEffectSugar> GRIEVOUS = Sugars.registerSingle(
            "grievous",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.GRIEVOUS)
                    .build()
    );
    public static final DeferredHolder<Sugar, SingleEffectSugar> KNOWLEDGE = Sugars.registerSingle(
            "knowledge",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.KNOWLEDGE)
                    .build()
    );
    public static final DeferredHolder<Sugar, SingleEffectSugar> SUNDERING = Sugars.registerSingle(
            "sundering",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.SUNDERING)
                    .build()
    );
    public static final DeferredHolder<Sugar, SingleEffectSugar> VITALITY = Sugars.registerSingle(
            "vitality",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.VITALITY)
                    .build()
    );

    public static void init() {}
}
