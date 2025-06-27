package com.lnatit.ccw.compat.farmersdelight;

import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;

public class FarmersDelightCompats {
    public static final DeferredHolder<Sugar, SingleEffectSugar> NOURISHED = Sugars.registerSingle(
            "nourished",
            builder -> builder
                    .withEffect(ModEffects.NOURISHMENT)
                    .withNoExcited()
                    .build()
    );
    public static final DeferredHolder<Sugar, SingleEffectSugar> COMFORT = Sugars.registerSingle(
            "comfort",
            builder -> builder
                    .withEffect(ModEffects.COMFORT)
                    .withNoExcited()
                    .build()
    );

    // TODO add compat recipe
    // TODO include in #farmersdelight:meals
    public static final Supplier<Item> GLAZED_MEAT_RICE = ModItems.registerWithTab(
            "glazed_meat_rice",
            () -> new ConsumableItem(
                    ModItems.bowlFoodItem(
                            new FoodProperties.Builder()
                                    .nutrition(12)
                                    .saturationModifier(FoodConstants.FOOD_SATURATION_NORMAL)
                                    .effect(() -> FoodValues.nourishment(3600), 1.0F)
                                    .build()
                    )
            )
    );
    public static final Supplier<Item> SWEET_HARVEST_SOUP = ModItems.registerWithTab(
            "sweet_harvest_soup",
            () -> new ConsumableItem(
                    ModItems.bowlFoodItem(
                            new FoodProperties.Builder()
                                    .nutrition(8)
                                    .saturationModifier(FoodConstants.FOOD_SATURATION_NORMAL)
                                    .effect(() -> FoodValues.comfort(6000), 1.0F)
                                    .build()
                    )
            )
    );

    public static void init() {
        SugarRefining.addCustomBlendProviders(FarmersDelightCompats::addBlends);
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(NOURISHED, GLAZED_MEAT_RICE.get());
        builder.addOverworldBlend(COMFORT, SWEET_HARVEST_SOUP.get());
    }
}
