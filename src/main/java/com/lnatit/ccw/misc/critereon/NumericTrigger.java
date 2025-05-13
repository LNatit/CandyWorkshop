package com.lnatit.ccw.misc.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class NumericTrigger extends SimpleCriterionTrigger<NumericTrigger.TriggerInstance>
{
    @Override
    public Codec<NumericTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, int count) {
        this.trigger(player, instance -> instance.matches(count));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, MinMaxBounds.Ints count) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(ins -> ins.group(
                ContextAwarePredicate.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                MinMaxBounds.Ints.CODEC.fieldOf("count").forGetter(TriggerInstance::count)
        ).apply(ins, TriggerInstance::new));

        public TriggerInstance(MinMaxBounds.Ints count) {
            this(Optional.empty(), count);
        }

        public boolean matches(int count) {
            return this.count.matches(count);
        }
    }
}
