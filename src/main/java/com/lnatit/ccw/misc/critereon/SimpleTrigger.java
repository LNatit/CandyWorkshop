package com.lnatit.ccw.misc.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SimpleTrigger extends SimpleCriterionTrigger<SimpleTrigger.TriggerInstance>
{
    private final ResourceLocation id;

    public SimpleTrigger(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        return new TriggerInstance(id, predicate);
    }

    public TriggerInstance createInstance(ContextAwarePredicate player) {
        return new TriggerInstance(id, player);
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance
        {
            private final ContextAwarePredicate player;

            public TriggerInstance(ResourceLocation criterion, ContextAwarePredicate player) {
                super(criterion, player);
                this.player = player;
            }
        }
}
