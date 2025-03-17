package com.lnatit.ccw.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;

public class FoodsAndConsumables
{
    // TODO define foodprop and consumable (if default then delete entry)
    public static final FoodProperties GUMMY_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final Consumable GUMMY_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.4f).build();

    public static final FoodProperties CARTON_MILK_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final Consumable CARTON_MILK_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.4f).build();

    public static final FoodProperties ENERGY_CARROT_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final Consumable ENERGY_CARROT_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.4f).build();

    public static final FoodProperties CALCIUM_RICH_MILK_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final Consumable CALCIUM_RICH_MILK_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.4f).build();

    public static final FoodProperties VOID_CARROT_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3F).build();
    public static final Consumable VOID_CARROT_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.4f).build();

    public static final FoodProperties IRON_CLAD_APPLE_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final Consumable IRON_CLAD_APPLE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.3f).build();

    public static final FoodProperties GOLD_STUDDED_APPLE_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final Consumable GOLD_STUDDED_APPLE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.3f).build();

    public static final FoodProperties BLESSED_STEAK_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final Consumable BLESSED_STEAK_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.3f).build();

    public static final FoodProperties DOLPHIN_COOKIE_FOOD = new FoodProperties.Builder().nutrition(1).saturationModifier(0.3f).build();
    public static final Consumable DOLPHIN_COOKIE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.3f).build();
}
