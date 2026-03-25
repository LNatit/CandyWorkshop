package com.lnatit.ccw.compat;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.lnatit.ccw.item.FoodsAndConsumables;
import com.lnatit.ccw.item.sugaring.Flavor;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.SimpleFlavor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodConstants;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NutritiousFlavor extends SimpleFlavor
{
    public static final FoodProperties NUTRITIOUS_FOOD =
            new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(FoodConstants.FOOD_SATURATION_LOW)
                    .alwaysEdible()
                    .fast()
                    .build();

    public NutritiousFlavor() {
        super(0xA2C36D, Ingredient.of(ModItems.CATERPILLAR));
    }

    @Override
    public @Nullable Holder<Flavor> proxy() {
        return Flavors.ORIGINAL;
    }

    @Override
    public void onApply(@NotNull ItemStack stack) {
        stack.set(DataComponents.FOOD, NUTRITIOUS_FOOD);
    }

    @Override
    public void onRemove(@NotNull ItemStack stack) {
        stack.set(DataComponents.FOOD, FoodsAndConsumables.GUMMY_FOOD);
    }
}
