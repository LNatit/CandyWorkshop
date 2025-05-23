package com.lnatit.ccw.item.sugaring;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TurtleSugar extends Sugar
{
    public static final List<MobEffectInstance> BASE_EFFECTS = List.of(
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 3),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 2)
    );
    public static final List<MobEffectInstance> BOLD_EFFECTS = List.of(
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 3),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2)
    );
    public static final List<MobEffectInstance> EXCITED_EFFECTS = List.of(
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 5),
            new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 3)
    );

    public TurtleSugar(String name) {
        super(name, true, true);
    }

    @Override
    public void applyOn(LivingEntity entity, Flavor flavor) {
        switch (flavor) {
            case EXCITED:
                EXCITED_EFFECTS.forEach(effect -> applyEffect(entity, effect));
                return;
            case BOLD:
                BOLD_EFFECTS.forEach(effect -> applyEffect(entity, effect));
                return;
            case MILKY:
                List<MobEffect> toRemove = new ArrayList<>();
                for (MobEffect effect : entity.getActiveEffectsMap().keySet()) {
                    if (effect != MobEffects.MOVEMENT_SLOWDOWN && effect != MobEffects.DAMAGE_RESISTANCE) {
                        toRemove.add(effect);
                    }
                }
                toRemove.forEach(entity::removeEffect);
            case ORIGINAL:
                BASE_EFFECTS.forEach(effect -> applyEffect(entity, effect));
        }
    }

    @Override
    public void addSugarTooltip(Consumer<Component> tooltipAdder, Flavor flavor) {
        List<MobEffectInstance> effects =
                switch (flavor) {
                    case EXCITED -> EXCITED_EFFECTS;
                    case BOLD -> BOLD_EFFECTS;
                    default -> BASE_EFFECTS;
                };

        for (MobEffectInstance mobeffectinstance : effects) {
            MutableComponent mutablecomponent = Component.translatable(mobeffectinstance.getDescriptionId());
            MobEffect effect = mobeffectinstance.getEffect();
            if (mobeffectinstance.getAmplifier() > 0) {
                mutablecomponent = Component.translatable(
                        "potion.withAmplifier", mutablecomponent,
                        Component.translatable("potion.potency." + mobeffectinstance.getAmplifier())
                );
            }

            if (!mobeffectinstance.endsWithin(20)) {
                mutablecomponent = Component.translatable(
                        "sugar.withDuration", mutablecomponent,
                        MobEffectUtil.formatDuration(mobeffectinstance, 1.0F)
                );
            }

            tooltipAdder.accept(mutablecomponent.withStyle(effect.getCategory().getTooltipFormatting()));
        }
    }

    private void applyEffect(LivingEntity entity, MobEffectInstance effectInstance) {
        int amplifier = effectInstance.getAmplifier();
        int duration = effectInstance.getDuration();

        MobEffectInstance exist = entity.getEffect(effectInstance.getEffect());
        if (exist != null && !exist.isAmbient() && exist.getAmplifier() >= amplifier) {
            duration += exist.getDuration();
        }
        entity.addEffect(new MobEffectInstance(effectInstance.getEffect(), duration, amplifier));
    }
}
