package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.data.Effect;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MilkyFlavor extends SimpleFlavor {
    public MilkyFlavor() {
        super(0xFFFFFF, Ingredient.of(ItemRegistry.MILK_GELATIN));
    }

    @Override
    public @Nullable Holder<Flavor> proxy() {
        return Flavors.ORIGINAL;
    }

    @Override
    public void preConsume(LivingEntity entity, List<Effect> effectsToApply) {
        List<Holder<MobEffect>> toRemove = new ArrayList<>();
        for (Holder<MobEffect> effect : entity.getActiveEffectsMap().keySet()) {
            if (effectsToApply.stream().noneMatch(inst -> inst.is(effect))) {
                toRemove.add(effect);
            }
        }
        toRemove.forEach(entity::removeEffect);
    }
}
