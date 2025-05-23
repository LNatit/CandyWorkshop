package com.lnatit.ccw.misc.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class NumericTrigger extends SimpleCriterionTrigger<NumericTrigger.TriggerInstance>
{
    private final ResourceLocation id;

    public NumericTrigger(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public void trigger(ServerPlayer player, int count) {
        this.trigger(player, instance -> instance.matches(count));
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance();
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final ContextAwarePredicate player;
        private final MinMaxBounds.Ints count;

        public TriggerInstance(ResourceLocation id, ContextAwarePredicate player, MinMaxBounds.Ints count) {
            super(id, player);
            this.player = player;
            this.count = count;
        }

        public boolean matches(int count) {
            return this.count.matches(count);
        }
    }
}
