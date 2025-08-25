package com.lnatit.ccw.compat.apothesis;

import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import dev.shadowsoffire.attributeslib.api.ALObjects;
import net.minecraftforge.registries.RegistryObject;

public class ApothSugars {
    public static final RegistryObject<SingleEffectSugar> GRIEVOUS = Sugars.registerSingle(
            "grievous",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.GRIEVOUS.get())
                    .build()
    );
    public static final RegistryObject<SingleEffectSugar> KNOWLEDGE = Sugars.registerSingle(
            "knowledge",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.KNOWLEDGE.get())
                    .build()
    );
    public static final RegistryObject<SingleEffectSugar> SUNDERING = Sugars.registerSingle(
            "sundering",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.SUNDERING.get())
                    .build()
    );
    public static final RegistryObject<SingleEffectSugar> VITALITY = Sugars.registerSingle(
            "vitality",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.VITALITY.get())
                    .build()
    );
    public static final RegistryObject<SingleEffectSugar> BLEEDING = Sugars.registerSingle(
            "bleeding",
            builder -> builder
                    .withEffect(ALObjects.MobEffects.BLEEDING.get())
                    .build()
    );

    public static void init() {}
}
