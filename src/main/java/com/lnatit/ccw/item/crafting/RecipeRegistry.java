package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CandyWorkshop.MODID);
    public static final DeferredHolder<RecipeType<?>, RecipeType<SugarRefiningRecipe>> SUGAR_REFINING =
            RECIPE_TYPES.register("sugar_refining", RecipeType::simple);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CandyWorkshop.MODID);
//    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<SugarRefiningRecipe>> SIMPLE_REFINING =
//            RECIPE_SERIALIZERS.register("simple_refining", );
}
