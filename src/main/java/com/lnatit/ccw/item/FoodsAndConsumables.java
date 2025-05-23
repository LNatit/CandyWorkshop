package com.lnatit.ccw.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;

public class FoodsAndConsumables {
    public static final FoodProperties GUMMY_FOOD =
            new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationMod(FoodConstants.FOOD_SATURATION_LOW)
                    .alwaysEat()
                    .fast()
                    .build();

    public static final FoodProperties ENERGY_CARROT_FOOD =
            new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(FoodConstants.FOOD_SATURATION_GOOD)
                    .effect(() -> new MobEffectInstance(MobEffects.JUMP, 1200), 1.0f)
                    .build();

    public static final FoodProperties SWEET_LEMON_SLICE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(FoodConstants.FOOD_SATURATION_GOOD)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200), 1.0f)
                    .build();

    public static final FoodProperties CALCIUM_RICH_MILK_FOOD =
            new FoodProperties.Builder()
                    .nutrition(1)
                    .saturationMod(FoodConstants.FOOD_SATURATION_SUPERNATURAL)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200), 1.0f)
                    .build();

    public static final FoodProperties VOID_CARROT_FOOD =
            new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(FoodConstants.FOOD_SATURATION_NORMAL)
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 1200), 1.0f)
                    .build();

    public static final FoodProperties IRON_CLAD_APPLE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(FoodConstants.FOOD_SATURATION_GOOD)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200), 1.0f)
                    .build();

    public static final FoodProperties GOLD_STUDDED_APPLE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(FoodConstants.FOOD_SATURATION_SUPERNATURAL)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200), 1.0f)
                    .build();

    public static final FoodProperties BLESSED_STEAK_FOOD =
            new FoodProperties.Builder()
                    .nutrition(20)
                    .saturationMod(FoodConstants.FOOD_SATURATION_SUPERNATURAL)
                    .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 1200), 1.0f)
                    .build();

    public static final FoodProperties DOLPHIN_COOKIE_FOOD =
            new FoodProperties.Builder()
                    .nutrition(8)
                    .saturationMod(FoodConstants.FOOD_SATURATION_GOOD)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 1200), 1.0f)
                    .build();
}
