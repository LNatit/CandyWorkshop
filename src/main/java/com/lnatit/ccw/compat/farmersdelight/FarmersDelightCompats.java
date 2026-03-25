package com.lnatit.ccw.compat.farmersdelight;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;

public interface FarmersDelightCompats
{
    Supplier<Item> GLAZED_MEAT_RICE = registerWithTab("glazed_meat_rice",
                                                             () -> new ConsumableItem(ModItems.bowlFoodItem(
                                                                                  new FoodProperties.Builder().nutrition(
                                                                                                                      12)
                                                                                                              .saturationModifier(
                                                                                                                      FoodConstants.FOOD_SATURATION_NORMAL)
                                                                                                              .effect(() -> FoodValues.nourishment(
                                                                                                                              3600),
                                                                                                                      1.0F)
                                                                                                              .build())));
    Supplier<Item> SWEET_HARVEST_SOUP = registerWithTab("sweet_harvest_soup",
                                                               () -> new ConsumableItem(ModItems.bowlFoodItem(
                                                                                    new FoodProperties.Builder().nutrition(
                                                                                                                        8)
                                                                                                                .saturationModifier(
                                                                                                                        FoodConstants.FOOD_SATURATION_NORMAL)
                                                                                                                .effect(() -> FoodValues.comfort(
                                                                                                                                6000),
                                                                                                                        1.0F)
                                                                                                                .build())));

    DeferredHolder<Sugar, Sugar> NOURISHED = Sugars.SUGARS.register("nourished",
                                                                           () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                                        Ingredient.of(
                                                                                                                GLAZED_MEAT_RICE.get())));
    DeferredHolder<Sugar, Sugar> COMFORT = Sugars.SUGARS.register("comfort",
                                                                         () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                                      Ingredient.of(
                                                                                                              SWEET_HARVEST_SOUP.get())));

    static void init() {}

    private static Supplier<Item> registerWithTab(String name, Supplier<Item> supplier) {
        Supplier<Item> item = ItemRegistry.ITEMS.register(name, supplier);
        ModItems.CREATIVE_TAB_ITEMS.add(item);
        return item;
    }
}
