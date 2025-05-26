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
        return new TriggerInstance(id, predicate, MinMaxBounds.Ints.fromJson(json.get("count")));
    }

    public TriggerInstance createInstance(ContextAwarePredicate player, MinMaxBounds.Ints count) {
        return new TriggerInstance(id, player, count);
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance
    {
        private final MinMaxBounds.Ints count;

        public TriggerInstance(ResourceLocation criterion, ContextAwarePredicate player, MinMaxBounds.Ints count) {
            super(criterion, player);
            this.count = count;
        }

        public boolean matches(int count) {
            return this.count.matches(count);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject jsonObject = super.serializeToJson(conditions);
            jsonObject.add("count", this.count.serializeToJson());
            return jsonObject;
        }
    }
}
