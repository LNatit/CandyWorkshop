package com.lnatit.ccw.compat.fruitsdelight;

import com.lnatit.ccw.item.sugaring.*;
import dev.xkmc.fruitsdelight.init.food.FruitType;
import dev.xkmc.fruitsdelight.init.registrate.FDEffects;
import dev.xkmc.fruitsdelight.init.registrate.FDItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class FruitsDelightCompats
{
    public static final RegistryObject<SingleEffectSugar> BLUEBERRY =
            Sugars.registerSingle("blueberry",
                                  builder -> builder
                                          .withEffect(FDEffects.BRIGHTENING.get())
                                          .withNoExcited()
                                          .build()
            );

    public static final RegistryObject<SingleEffectSugar> HAWBERRY =
            Sugars.registerSingle("hawthorn",
                                  builder -> builder
                                          .withEffect(FDEffects.APPETIZING.get())
                                          .withNoExcited()
                                          .build()
            );

    public static final RegistryObject<SingleEffectSugar> MANGO =
            Sugars.registerSingle(
                    "mango",
                    builder -> builder
                            .withEffect(FDEffects.RAGE_AURA.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> ORANGE =
            Sugars.registerSingle(
                    "orange",
                    builder -> builder
                            .withEffect(FDEffects.RECOVERING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> PEACH =
            Sugars.registerSingle(
                    "peach",
                    builder -> builder
                            .withEffect(FDEffects.HEAL_AURA.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> PEAR =
            Sugars.registerSingle(
                    "snow_pear",
                    builder -> builder
                            .withEffect(FDEffects.LOZENGE.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> PERSIMMON =
            Sugars.registerSingle(
                    "persimmon",
                    builder -> builder
                            .withEffect(FDEffects.ASTRINGENT.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> PINEAPPLE =
            Sugars.registerSingle(
                    "pineapple",
                    builder -> builder
                            .withEffect(FDEffects.SWEETENING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> LEMON =
            Sugars.registerSingle(
                    "lemon",
                    builder -> builder
                            .withEffect(FDEffects.REFRESHING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> CRANBERRY =
            Sugars.registerSingle(
                    "cranberry",
                    builder -> builder
                            .withEffect(FDEffects.SHRINKING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> MANGOSTEEN =
            Sugars.registerSingle(
                    "mangosteen",
                    builder -> builder
                            .withEffect(FDEffects.SLIDING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> CHORUS =
            Sugars.registerSingle(
                    "chorus",
                    builder -> builder
                            .withEffect(FDEffects.CHORUS.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> BAYBERRY =
            Sugars.registerSingle(
                    "bayberry",
                    builder -> builder
                            .withEffect(FDEffects.LEAF_PIERCING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> KIWI =
            Sugars.registerSingle(
                    "kiwi",
                    builder -> builder
                            .withEffect(FDEffects.CYCLING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<SingleEffectSugar> FIG =
            Sugars.registerSingle(
                    "fig",
                    builder -> builder
                            .withEffect(FDEffects.DIGESTING.get())
                            .withNoExcited()
                            .build()
            );

    public static final RegistryObject<MultipleEffectSugar> DURIAN =
            Sugars.SUGARS.register("durian",
                                   () -> new MultipleEffectSugar("durian", false, true,
                                                                 MultipleEffectSugar.Effect.simple(
                                                                         FDEffects.ALIENATING.get()),
                                                                 MultipleEffectSugar.Effect.simple(
                                                                         FDEffects.SUSPICIOUS_SMELL.get())

                                   )
            );

    public static void init() {
        SugarRefining.addCustomBlendProviders(FruitsDelightCompats::addBlends);
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(BLUEBERRY, getJello(FruitType.BLUEBERRY));
        builder.addOverworldBlend(HAWBERRY, getJello(FruitType.HAWBERRY));
        builder.addOverworldBlend(MANGO, getJello(FruitType.MANGO));
        builder.addOverworldBlend(ORANGE, getJello(FruitType.ORANGE));
        builder.addOverworldBlend(PEACH, getJello(FruitType.PEACH));
        builder.addOverworldBlend(PEAR, getJello(FruitType.PEAR));
        builder.addOverworldBlend(PERSIMMON, getJello(FruitType.PERSIMMON));
        builder.addOverworldBlend(PINEAPPLE, getJello(FruitType.PINEAPPLE));
        builder.addOverworldBlend(LEMON, getJello(FruitType.LEMON));
        builder.addOverworldBlend(CRANBERRY, getJello(FruitType.CRANBERRY));
        builder.addOverworldBlend(MANGOSTEEN, getJello(FruitType.MANGOSTEEN));
        builder.addOverworldBlend(CHORUS, getJello(FruitType.CHORUS));
        builder.addOverworldBlend(BAYBERRY, getJello(FruitType.BAYBERRY));
        builder.addOverworldBlend(KIWI, getJello(FruitType.KIWI));
        builder.addOverworldBlend(FIG, getJello(FruitType.FIG));
        builder.addOverworldBlend(DURIAN, getJello(FruitType.DURIAN));
    }

    private static Item getJello(FruitType type) {
        return FDItems.JELLO[type.ordinal()].get();
    }
}
