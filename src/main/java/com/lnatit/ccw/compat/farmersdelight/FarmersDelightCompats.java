package com.lnatit.ccw.compat.farmersdelight;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;

public class FarmersDelightCompats
{
    public static final RegistryObject<SingleEffectSugar> NOURISHED = Sugars.registerSingle(
            "nourished",
            builder -> builder
                    .withEffect(ModEffects.NOURISHMENT.get())
                    .withNoExcited()
                    .build()
    );
    public static final RegistryObject<SingleEffectSugar> COMFORT = Sugars.registerSingle(
            "comfort",
            builder -> builder
                    .withEffect(ModEffects.COMFORT.get())
                    .withNoExcited()
                    .build()
    );

    public static final Supplier<Item> GLAZED_MEAT_RICE = registerWithTab(
            "glazed_meat_rice",
            () -> new ConsumableItem(
                    ModItems.bowlFoodItem(
                            new FoodProperties.Builder()
                                    .nutrition(12)
                                    .saturationMod(FoodConstants.FOOD_SATURATION_NORMAL)
                                    .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 2400, 0), 1.0F)
                                    .build()
                    )
            )
    );
    public static final Supplier<Item> SWEET_HARVEST_SOUP = registerWithTab(
            "sweet_harvest_soup",
            () -> new ConsumableItem(
                    ModItems.bowlFoodItem(
                            new FoodProperties.Builder()
                                    .nutrition(8)
                                    .saturationMod(FoodConstants.FOOD_SATURATION_NORMAL)
                                    .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 6000, 0), 1.0F)
                                    .build()
                    )
            )
    );

    public static void init() {
        SugarRefining.addCustomBlendProviders(FarmersDelightCompats::addBlends);
    }

    private static Supplier<Item> registerWithTab(String name, Supplier<Item> supplier) {
        RegistryObject<Item> item = ItemRegistry.ITEMS.register(name, supplier);
        ModItems.CREATIVE_TAB_ITEMS.add(item);
        return item;
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(NOURISHED, GLAZED_MEAT_RICE.get());
        builder.addOverworldBlend(COMFORT, SWEET_HARVEST_SOUP.get());
    }
}
