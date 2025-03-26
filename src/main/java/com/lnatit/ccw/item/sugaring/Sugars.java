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

    public static final DeferredHolder<Sugar, SingleEffectSugar> SPEED =
            registerSingle("speed",
                    builder -> builder
                            .withEffect(MobEffects.MOVEMENT_SPEED)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> BUNNY =
            registerSingle("bunny",
                    builder -> builder
                            .withEffect(MobEffects.JUMP)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> HEALING =
            registerSingle("healing",
                    builder -> builder
                            .withEffect(MobEffects.HEAL)
                            .withNoBold()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> POISON =
            registerSingle("poison",
                    builder -> builder
                            .withEffect(MobEffects.POISON)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> PUFFERFISH =
            registerSingle("pufferfish",
                    builder -> builder
                            .withEffect(MobEffects.WATER_BREATHING)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> NIGHT_VISION =
            registerSingle("night_vision",
                    builder -> builder
                            .withEffect(MobEffects.NIGHT_VISION)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> STRENGTH =
            registerSingle("strength",
                    builder -> builder
                            .withEffect(MobEffects.DAMAGE_BOOST)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> RECOVERY =
            registerSingle("recovery",
                    builder -> builder
                            .withEffect(MobEffects.REGENERATION)
                            .build()
            );
    public static final DeferredHolder<Sugar, TurtleSugar> TURTLE =
            SUGARS.register("turtle", () -> new TurtleSugar("turtle"));
    public static final DeferredHolder<Sugar, SingleEffectSugar> FLUTTER =
            registerSingle("flutter",
                    builder -> builder
                            .withEffect(MobEffects.SLOW_FALLING)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> INVISIBILITY =
            registerSingle("invisibility",
                    builder -> builder
                            .withEffect(MobEffects.INVISIBILITY)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> STINGER =
            registerSingle("stinger",
                    builder -> builder
                            .withEffect(MobEffects.HARM)
                            .withNoBold()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> SNAIL =
            registerSingle("snail",
                    builder -> builder
                            .withEffect(MobEffects.MOVEMENT_SLOWDOWN)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> BUG =
            registerSingle("bug",
                    builder -> builder
                            .withEffect(MobEffects.INFESTED)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> STICKY =
            registerSingle("sticky",
                    builder -> builder
                            .withEffect(MobEffects.OOZING)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> BINDING =
            registerSingle("binding",
                    builder -> builder
                            .withEffect(MobEffects.WEAVING)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> GALE =
            registerSingle("gale",
                    builder -> builder
                            .withEffect(MobEffects.WIND_CHARGED)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> REFRESHING =
            registerSingle("refreshing",
                    builder -> builder
                            .withEffect(MobEffects.DIG_SPEED)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> LAZY =
            registerSingle("lazy",
                    builder -> builder
                            .withEffect(MobEffects.DIG_SLOWDOWN)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> STINKY =
            registerSingle("stinky",
                    builder -> builder
                            .withEffect(MobEffects.CONFUSION)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> SOLID =
            registerSingle("solid",
                    builder -> builder
                            .withEffect(MobEffects.DAMAGE_RESISTANCE)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> FIREPROOF =
            registerSingle("fireproof",
                    builder -> builder
                            .withEffect(MobEffects.FIRE_RESISTANCE)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> BLINDING =
            registerSingle("blinding",
                    builder -> builder
                            .withEffect(MobEffects.BLINDNESS)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> WEAKNESS =
            registerSingle("weakness",
                    builder -> builder
                            .withEffect(MobEffects.WEAKNESS)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> WITHERING =
            registerSingle("withering",
                    builder -> builder
                            .withEffect(MobEffects.WITHER)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> RED_HEART =
            registerSingle("red_heart",
                    builder -> builder
                            .withEffect(MobEffects.HEALTH_BOOST)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> GOLDEN_HEART =
            registerSingle("golden_heart",
                    builder -> builder
                            .withEffect(MobEffects.ABSORPTION)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> SATIATING =
            registerSingle("satiating",
                    builder -> builder
                            .withEffect(MobEffects.SATURATION)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> BRIGHTNESS =
            registerSingle("brightness",
                    builder -> builder
                            .withEffect(MobEffects.GLOWING)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> FLOATING =
            registerSingle("floating",
                    builder -> builder
                            .withEffect(MobEffects.LEVITATION)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> LUCKY =
            registerSingle("lucky",
                    builder -> builder
                            .withEffect(MobEffects.LUCK)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> UNLUCKY =
            registerSingle("unlucky",
                    builder -> builder
                            .withEffect(MobEffects.UNLUCK)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> TIDAL =
            registerSingle("tidal",
                    builder -> builder
                            .withEffect(MobEffects.CONDUIT_POWER)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> FISH_SWIM =
            registerSingle("fish_swim",
                    builder -> builder
                            .withEffect(MobEffects.DOLPHINS_GRACE)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> TAUNTING =
            registerSingle("taunting",
                    builder -> builder
                            .withEffect(MobEffects.BAD_OMEN)
                            .withNoExcited() // TODO do we need?
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> DISCOUNT =
            registerSingle("discount",
                    builder -> builder
                            .withEffect(MobEffects.HERO_OF_THE_VILLAGE)
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> DARKNESS =
            registerSingle("darkness",
                    builder -> builder
                            .withEffect(MobEffects.DARKNESS)
                            .withNoExcited()
                            .build()
            );
    public static final DeferredHolder<Sugar, SingleEffectSugar> HUNGER =
            registerSingle("hunger",
                    builder -> builder
                            .withEffect(MobEffects.HUNGER)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static DeferredHolder<Sugar, SingleEffectSugar> registerSingle(String id, Function<SingleEffectSugar.IEffectAcceptor, SingleEffectSugar> props) {
        return SUGARS.register(id, () -> props.apply(SingleEffectSugar.builder(id)));
    }
}
