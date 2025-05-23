package com.lnatit.ccw.item.sugaring;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SingleEffectSugar extends Sugar
{
    private final MobEffect effect;
    private final int duration;

    private SingleEffectSugar(String name, MobEffect effect, int duration, boolean hasExcited, boolean hasBold) {
        super(name, hasExcited, hasBold);
        this.effect = effect;
        this.duration = duration;
    }

    @Override
    public void applyOn(LivingEntity entity, Flavor flavor) {
        if (entity.level() instanceof ServerLevel) {
            int duration = this.getDuration(flavor);
            int amplifier = this.getAmplifier(flavor);
            if (flavor == Flavor.MILKY) {
                List<MobEffect> toRemove =  new ArrayList<>();
                for (MobEffect effect : entity.getActiveEffectsMap().keySet()) {
                    if (effect != this.effect) {
                        toRemove.add(effect);
                    }
                }
                toRemove.forEach(entity::removeEffect);
            }

            // Instantenous effect behaves differently
            if (effect.isInstantenous()) {
                effect.applyInstantenousEffect(entity, entity, entity, amplifier, 0.5);
            }
            else {
                MobEffectInstance exist = entity.getEffect(effect);
                if (exist != null && !exist.isAmbient() && exist.getAmplifier() >= amplifier) {
                    duration += exist.getDuration();
                }
                entity.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }

    @Override
    public void addSugarTooltip(Consumer<Component> tooltipAdder, Flavor flavor) {
        MutableComponent mutablecomponent = Component.translatable(effect.getDescriptionId());

        int amplifier = this.getAmplifier(flavor);
        if (amplifier > 0) {
            mutablecomponent = Component.translatable(
                    "potion.withAmplifier", mutablecomponent,
                    Component.translatable("potion.potency." + amplifier)
            );
        }

        int duration = this.getDuration(flavor);
        if (duration > 20) {
            int i = Mth.floor((float) duration);
            Component result = Component.literal(StringUtil.formatTickDuration(i));
            mutablecomponent = Component.translatable(
                    "sugar.withDuration", mutablecomponent,
                    result
            );
        }

        tooltipAdder.accept(mutablecomponent.withStyle(effect.getCategory().getTooltipFormatting()));
    }

    private int getDuration(Flavor flavor) {
        return this.hasBold && flavor == Flavor.BOLD ? 2 * this.duration : this.duration;
    }

    private int getAmplifier(Flavor flavor) {
        return this.hasExcited && flavor == Flavor.EXCITED ? 1 : 0;
    }

    public static IEffectAcceptor builder(String name) {
        return new Builder(name);
    }

    public static class Builder implements IEffectAcceptor, IOptionalAcceptor
    {
        public static final int DEFAULT_DURATION = 600;

        private final String name;
        @Nullable
        private MobEffect effect;
        private int duration = DEFAULT_DURATION;
        private boolean hasExcited = true;
        private boolean hasBold = true;

        private Builder(String name) {
            this.name = name;
        }

        @Override
        public IOptionalAcceptor withEffect(MobEffect effect) {
            this.effect = effect;
            return this;
        }

        @Override
        public IOptionalAcceptor withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        @Override
        public IBuilder withNoExcited() {
            this.hasExcited = false;
            return this;
        }

        @Override
        public IBuilder withNoBold() {
            this.hasBold = false;
            return this;
        }

        @Override
        public SingleEffectSugar build() {
            assert effect != null;
            return new SingleEffectSugar(name, effect, duration, hasExcited, hasBold);
        }
    }

    public interface IEffectAcceptor
    {
        IOptionalAcceptor withEffect(MobEffect effect);
    }

    public interface IOptionalAcceptor extends IBuilder
    {
        IOptionalAcceptor withDuration(int duration);

        IBuilder withNoExcited();

        IBuilder withNoBold();
    }

    public interface IBuilder
    {
        SingleEffectSugar build();
    }
}
