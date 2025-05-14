package com.lnatit.ccw.item;

import com.lnatit.ccw.item.coneffect.RemoveRandomStatusEffectsConsumeEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class FoodsAndConsumables {
    public static final FoodProperties GUMMY_FOOD =
            new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(0.3F)
                    .build();
    public static final Consumable GUMMY_CONSUMABLE =
            Consumables.defaultFood()
                    .consumeSeconds(0.4f)
                    .build();

    public static final Consumable CARTON_MILK_CONSUMABLE =
            Consumables.defaultFood()
                    .consumeSeconds(0.4f)
                    .onConsume(new RemoveRandomStatusEffectsConsumeEffect())
                    .build();

    public static final FoodProperties ENERGY_CARROT_FOOD =
            new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_GOOD)
                    .build();
    public static final Consumable ENERGY_CARROT_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.JUMP, 1200)))
                    .build();

    public static final FoodProperties SWEET_LEMON_SLICE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_GOOD)
                    .build();
    public static final Consumable SWEET_LEMON_SLICE_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200)))
                    .build();

    public static final FoodProperties CALCIUM_RICH_MILK_FOOD =
            new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_SUPERNATURAL)
                    .build();
    public static final Consumable CALCIUM_RICH_MILK_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200)))
                    .build();

    public static final FoodProperties VOID_CARROT_FOOD =
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_NORMAL)
                    .build();
    public static final Consumable VOID_CARROT_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200)))
                    .build();

    public static final FoodProperties IRON_CLAD_APPLE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_GOOD)
                    .build();
    public static final Consumable IRON_CLAD_APPLE_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 1200)))
                    .build();

    public static final FoodProperties GOLD_STUDDED_APPLE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_SUPERNATURAL)
                    .build();
    public static final Consumable GOLD_STUDDED_APPLE_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200)))
                    .build();

    public static final FoodProperties BLESSED_STEAK_FOOD =
            new FoodProperties.Builder()
                    .nutrition(20)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_SUPERNATURAL)
                    .build();
    public static final Consumable BLESSED_STEAK_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SATURATION, 1200)))
                    .build();

    public static final FoodProperties DOLPHIN_COOKIE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(8)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_GOOD)
                    .build();
    public static final Consumable DOLPHIN_COOKIE_CONSUMABLE =
            Consumables.defaultFood()
                    .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 1200)))
                    .build();
}
