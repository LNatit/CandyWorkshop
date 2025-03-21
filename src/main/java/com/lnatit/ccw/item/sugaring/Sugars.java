package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

/**
 * @see MobEffects
 * Bold: extended
 * Excited: amplified
 */
public class Sugars {
    public static final DeferredRegister<Sugar> SUGARS =
            DeferredRegister.create(RegRegistry.SUGAR_KEY, CandyWorkshop.MODID);

    public static final DeferredHolder<Sugar, Sugar> SPEED =
            register("speed",
                    builder -> builder
                            .withEffect(MobEffects.MOVEMENT_SPEED)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> BUNNY =
            register("bunny",
                    builder -> builder
                            .withEffect(MobEffects.JUMP)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> HEALING =
            register("healing",
                    builder -> builder
                            .withEffect(MobEffects.HEAL)
                            .withNoBold()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> POISON =
            register("poison",
                    builder -> builder
                            .withEffect(MobEffects.POISON)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> PUFFERFISH =
            register("pufferfish",
                    builder -> builder
                            .withEffect(MobEffects.WATER_BREATHING)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> NIGHT_VISION =
            register("night_vision",
                    builder -> builder
                            .withEffect(MobEffects.NIGHT_VISION)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> STRENGTH =
            register("strength",
                    builder -> builder
                            .withEffect(MobEffects.DAMAGE_BOOST)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> RECOVERY =
            register("recovery",
                    builder -> builder
                            .withEffect(MobEffects.REGENERATION)
                            .build()
            );
    //    public static final DeferredHolder<Sugar, Sugar> TURTLE =
//    register("",
//             builder -> builder
//        .withEffect(MobEffects.)
//        .build()
//            );
    public static final DeferredHolder<Sugar, Sugar> FLUTTER =
            register("flutter",
                    builder -> builder
                            .withEffect(MobEffects.SLOW_FALLING)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> INVISIBILITY =
            register("invisibility",
                    builder -> builder
                            .withEffect(MobEffects.INVISIBILITY)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> STINGER =
            register("stinger",
                    builder -> builder
                            .withEffect(MobEffects.HARM)
                            .withNoBold()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> SNAIL =
            register("snail",
                    builder -> builder
                            .withEffect(MobEffects.MOVEMENT_SLOWDOWN)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> BUG =
            register("bug",
                    builder -> builder
                            .withEffect(MobEffects.INFESTED)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> STICKY =
            register("sticky",
                    builder -> builder
                            .withEffect(MobEffects.OOZING)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> BINDING =
            register("binding",
                    builder -> builder
                            .withEffect(MobEffects.WEAVING)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> GALE =
            register("gale",
                    builder -> builder
                            .withEffect(MobEffects.WIND_CHARGED)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> REFRESHING =
            register("refreshing",
                    builder -> builder
                            .withEffect(MobEffects.DIG_SPEED)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> LAZY =
            register("lazy",
                    builder -> builder
                            .withEffect(MobEffects.DIG_SLOWDOWN)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> STINKY =
            register("stinky",
                    builder -> builder
                            .withEffect(MobEffects.CONFUSION)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> SOLID =
            register("solid",
                    builder -> builder
                            .withEffect(MobEffects.DAMAGE_RESISTANCE)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> FIREPROOF =
            register("fireproof",
                    builder -> builder
                            .withEffect(MobEffects.FIRE_RESISTANCE)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> BLINDING =
            register("blinding",
                    builder -> builder
                            .withEffect(MobEffects.BLINDNESS)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> WEAKNESS =
            register("weakness",
                    builder -> builder
                            .withEffect(MobEffects.WEAKNESS)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> WITHERING =
            register("withering",
                    builder -> builder
                            .withEffect(MobEffects.WITHER)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> RED_HEART =
            register("red_heart",
                    builder -> builder
                            .withEffect(MobEffects.HEALTH_BOOST)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> GOLDEN_HEART =
            register("golden_heart",
                    builder -> builder
                            .withEffect(MobEffects.ABSORPTION)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> SATIATING =
            register("satiating",
                    builder -> builder
                            .withEffect(MobEffects.SATURATION)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> BRIGHTNESS =
            register("brightness",
                    builder -> builder
                            .withEffect(MobEffects.GLOWING)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> FLOATING =
            register("floating",
                    builder -> builder
                            .withEffect(MobEffects.LEVITATION)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> LUCKY =
            register("lucky",
                    builder -> builder
                            .withEffect(MobEffects.LUCK)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> UNLUCKY =
            register("unlucky",
                    builder -> builder
                            .withEffect(MobEffects.UNLUCK)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> TIDAL =
            register("tidal",
                    builder -> builder
                            .withEffect(MobEffects.CONDUIT_POWER)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> FISH_SWIM =
            register("fish_swim",
                    builder -> builder
                            .withEffect(MobEffects.DOLPHINS_GRACE)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> TAUNTING =
            register("taunting",
                    builder -> builder
                            .withEffect(MobEffects.BAD_OMEN)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> DISCOUNT =
            register("discount",
                    builder -> builder
                            .withEffect(MobEffects.HERO_OF_THE_VILLAGE)
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> DARKNESS =
            register("darkness",
                    builder -> builder
                            .withEffect(MobEffects.DARKNESS)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, Sugar> HUNGER =
            register("hunger",
                    builder -> builder
                            .withEffect(MobEffects.HUNGER)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static DeferredHolder<Sugar, Sugar> register(String id, Function<Sugar.IEffectAcceptor, Sugar> props) {
        return SUGARS.register(id, () -> props.apply(Sugar.builder(id)));
    }
}
