package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Sugars {
    public static final DeferredRegister<Sugar> SUGARS =
            DeferredRegister.create(RegRegistry.SUGAR_KEY, CandyWorkshop.MODID);

//    public static final DeferredHolder<Sugar, Sugar> VANILLA = register("vanilla", null, 0);

    public static final DeferredHolder<Sugar, Sugar> SPEED =
            register("speed",
                    builder -> builder
                            .withEffects(MobEffects.MOVEMENT_SPEED)
                            .withDuration(600)
                            .build()
            );

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static DeferredHolder<Sugar, Sugar> register(String id, Function<Sugar.IEffectsAcceptor, Sugar> props) {
        return SUGARS.register(id, () -> props.apply(Sugar.builder(id)));
    }
}
