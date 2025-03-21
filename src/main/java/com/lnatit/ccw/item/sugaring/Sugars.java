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
public class Sugars
{
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

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static DeferredHolder<Sugar, Sugar> register(String id, Function<Sugar.IEffectAcceptor, Sugar> props) {
        return SUGARS.register(id, () -> props.apply(Sugar.builder(id)));
    }
}
