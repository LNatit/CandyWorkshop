package com.lnatit.ccw.item.sugaring;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MultipleEffectSugar extends Sugar
{
    public final List<MobEffectInstance> originalEffects;
    public final List<MobEffectInstance> excitedEffects;
    public final List<MobEffectInstance> boldEffects;

    public MultipleEffectSugar(String name, boolean hasExcited, boolean hasBold, Effect... effects) {
        super(name, hasExcited, hasBold);
        this.originalEffects = Arrays.stream(effects).map(Effect::original).toList();
        this.boldEffects =  Arrays.stream(effects).map(Effect::bold).toList();
        this.excitedEffects = Arrays.stream(effects).map(Effect::excited).toList();
    }

    @Override
    public void applyOn(LivingEntity entity, Flavor flavor) {
        switch (flavor) {
            case EXCITED:
                excitedEffects.forEach(effect -> applyEffect(entity, effect));
                return;
            case BOLD:
                boldEffects.forEach(effect -> applyEffect(entity, effect));
                return;
            case MILKY:
                List<MobEffect> toRemove = new ArrayList<>();
                for (MobEffect effect : entity.getActiveEffectsMap().keySet()) {
                    if (originalEffects.stream().noneMatch(inst -> inst.getEffect() == effect)) {
                        toRemove.add(effect);
                    }
                }
                toRemove.forEach(entity::removeEffect);
            case ORIGINAL:
                originalEffects.forEach(effect -> applyEffect(entity, effect));
        }
    }

    @Override
    public void addSugarTooltip(Consumer<Component> tooltipAdder, Flavor flavor) {
        List<MobEffectInstance> effects =
                switch (flavor) {
                    case EXCITED -> excitedEffects;
                    case BOLD -> boldEffects;
                    default -> originalEffects;
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

    public record Effect(MobEffect effect, int baseDuration, int extendedDuration, int baseAmplifier,
                         int amplifiedAmplifier)
    {
        public static Effect simple(MobEffect effect) {
            return simple(effect, 600, 0);
        }

        public static Effect simple(MobEffect effect, int duration, int amplifier) {
            return new Effect(effect, duration, duration * 2, amplifier, amplifier + 1);
        }

        MobEffectInstance original() {
            return new MobEffectInstance(effect, baseDuration, baseAmplifier);
        }

        MobEffectInstance excited() {
            return new MobEffectInstance(effect, baseDuration, amplifiedAmplifier);
        }

        MobEffectInstance bold() {
            return new MobEffectInstance(effect, extendedDuration, baseAmplifier);
        }
    }
}
