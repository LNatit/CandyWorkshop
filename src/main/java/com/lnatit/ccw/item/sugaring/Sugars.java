package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @see MobEffects
 * Bold: extended
 * Excited: amplified
 */

public class Sugars {
    public static final ResourceLocation SUGAR_LOCATION = new ResourceLocation(CandyWorkshop.MODID, "sugar");
    public static final RegistryBuilder<Sugar> SUGAR_BUILDER = builder();
    public static final DeferredRegister<Sugar> SUGARS =
            DeferredRegister.create(SUGAR_LOCATION, CandyWorkshop.MODID);

    public static final RegistryObject<SingleEffectSugar> SPEED =
            registerSingle("speed",
                    builder -> builder
                            .withEffect(MobEffects.MOVEMENT_SPEED)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> BUNNY =
            registerSingle("bunny",
                    builder -> builder
                            .withEffect(MobEffects.JUMP)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> HEALING =
            registerSingle("healing",
                    builder -> builder
                            .withEffect(MobEffects.HEAL)
                            .withDuration(1)
                            .withNoBold()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> POISON =
            registerSingle("poison",
                    builder -> builder
                            .withEffect(MobEffects.POISON)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> PUFFERFISH =
            registerSingle("pufferfish",
                    builder -> builder
                            .withEffect(MobEffects.WATER_BREATHING)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> NIGHT_VISION =
            registerSingle("night_vision",
                    builder -> builder
                            .withEffect(MobEffects.NIGHT_VISION)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> STRENGTH =
            registerSingle("strength",
                    builder -> builder
                            .withEffect(MobEffects.DAMAGE_BOOST)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> RECOVERY =
            registerSingle("recovery",
                    builder -> builder
                            .withEffect(MobEffects.REGENERATION)
                            .build()
            );
    public static final RegistryObject<TurtleSugar> TURTLE =
            SUGARS.register("turtle", () -> new TurtleSugar("turtle"));
    public static final RegistryObject<SingleEffectSugar> FLUTTER =
            registerSingle("flutter",
                    builder -> builder
                            .withEffect(MobEffects.SLOW_FALLING)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> INVISIBILITY =
            registerSingle("invisibility",
                    builder -> builder
                            .withEffect(MobEffects.INVISIBILITY)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> STINGER =
            registerSingle("stinger",
                    builder -> builder
                            .withEffect(MobEffects.HARM)
                            .withDuration(1)
                            .withNoBold()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> SNAIL =
            registerSingle("snail",
                    builder -> builder
                            .withEffect(MobEffects.MOVEMENT_SLOWDOWN)
                            .build()
            );
//    public static final RegistryObject<SingleEffectSugar> BUG =
//            registerSingle("bug",
//                    builder -> builder
//                            .withEffect(MobEffects.INFESTED)
//                            .withNoExcited()
//                            .build()
//            );
//    public static final RegistryObject<SingleEffectSugar> STICKY =
//            registerSingle("sticky",
//                    builder -> builder
//                            .withEffect(MobEffects.OOZING)
//                            .withNoExcited()
//                            .build()
//            );
//    public static final RegistryObject<SingleEffectSugar> BINDING =
//            registerSingle("binding",
//                    builder -> builder
//                            .withEffect(MobEffects.WEAVING)
//                            .withNoExcited()
//                            .build()
//            );
//    public static final RegistryObject<SingleEffectSugar> GALE =
//            registerSingle("gale",
//                    builder -> builder
//                            .withEffect(MobEffects.WIND_CHARGED)
//                            .withNoExcited()
//                            .build()
//            );
    public static final RegistryObject<SingleEffectSugar> REFRESHING =
            registerSingle("refreshing",
                    builder -> builder
                            .withEffect(MobEffects.DIG_SPEED)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> LAZY =
            registerSingle("lazy",
                    builder -> builder
                            .withEffect(MobEffects.DIG_SLOWDOWN)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> STINKY =
            registerSingle("stinky",
                    builder -> builder
                            .withEffect(MobEffects.CONFUSION)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> SOLID =
            registerSingle("solid",
                    builder -> builder
                            .withEffect(MobEffects.DAMAGE_RESISTANCE)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> FIREPROOF =
            registerSingle("fireproof",
                    builder -> builder
                            .withEffect(MobEffects.FIRE_RESISTANCE)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> BLINDING =
            registerSingle("blinding",
                    builder -> builder
                            .withEffect(MobEffects.BLINDNESS)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> WEAKNESS =
            registerSingle("weakness",
                    builder -> builder
                            .withEffect(MobEffects.WEAKNESS)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> WITHERING =
            registerSingle("withering",
                    builder -> builder
                            .withEffect(MobEffects.WITHER)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> RED_HEART =
            registerSingle("red_heart",
                    builder -> builder
                            .withEffect(MobEffects.HEALTH_BOOST)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> GOLDEN_HEART =
            registerSingle("golden_heart",
                    builder -> builder
                            .withEffect(MobEffects.ABSORPTION)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> SATIATING =
            registerSingle("satiating",
                    builder -> builder
                            .withEffect(MobEffects.SATURATION)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> BRIGHTNESS =
            registerSingle("brightness",
                    builder -> builder
                            .withEffect(MobEffects.GLOWING)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> FLOATING =
            registerSingle("floating",
                    builder -> builder
                            .withEffect(MobEffects.LEVITATION)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> LUCKY =
            registerSingle("lucky",
                    builder -> builder
                            .withEffect(MobEffects.LUCK)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> UNLUCKY =
            registerSingle("unlucky",
                    builder -> builder
                            .withEffect(MobEffects.UNLUCK)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> TIDAL =
            registerSingle("tidal",
                    builder -> builder
                            .withEffect(MobEffects.CONDUIT_POWER)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> FISH_SWIM =
            registerSingle("fish_swim",
                    builder -> builder
                            .withEffect(MobEffects.DOLPHINS_GRACE)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> TAUNTING =
            registerSingle("taunting",
                    builder -> builder
                            .withEffect(MobEffects.BAD_OMEN)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> DISCOUNT =
            registerSingle("discount",
                    builder -> builder
                            .withEffect(MobEffects.HERO_OF_THE_VILLAGE)
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> DARKNESS =
            registerSingle("darkness",
                    builder -> builder
                            .withEffect(MobEffects.DARKNESS)
                            .withNoExcited()
                            .build()
            );
    public static final RegistryObject<SingleEffectSugar> HUNGER =
            registerSingle("hunger",
                    builder -> builder
                            .withEffect(MobEffects.HUNGER)
                            .build()
            );

    public static Supplier<IForgeRegistry<Sugar>> SUGAR_SUPPLIER = SUGARS.makeRegistry(() -> SUGAR_BUILDER);

    private static RegistryBuilder<Sugar> builder() {
        RegistryBuilder<Sugar> builder = new RegistryBuilder<Sugar>();
        builder.setName(SUGAR_LOCATION);
        return builder;
    }

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static RegistryObject<SingleEffectSugar> registerSingle(String id, Function<SingleEffectSugar.IEffectAcceptor, SingleEffectSugar> props) {
        return SUGARS.register(id, () -> props.apply(SingleEffectSugar.builder(id)));
    }
}
