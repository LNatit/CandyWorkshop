package com.lnatit.ccw.item.coneffect;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ConEffRegistry {
    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECTS = DeferredRegister.create(
            BuiltInRegistries.CONSUME_EFFECT_TYPE, CandyWorkshop.MODID
    );

    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<RemoveRandomStatusEffectsConsumeEffect>> REMOVE_RANDOM_EFFECTS =
            CONSUME_EFFECTS.register("remove_random_effects",
                    () -> new ConsumeEffect.Type<>(
                            RemoveRandomStatusEffectsConsumeEffect.CODEC,
                            RemoveRandomStatusEffectsConsumeEffect.STREAM_CODEC
                    )
            );
}
