package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface RecipeRegistry {
    DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CandyWorkshop.MODID);
    DeferredHolder<RecipeType<?>, RecipeType<RefiningRecipe>> REFINING =
            RECIPE_TYPES.register("refining", RecipeType::simple);

    DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CandyWorkshop.MODID);
    DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RepairExtractorRecipe>> REPAIR_EXTRACTOR =
            RECIPE_SERIALIZERS.register("repair_extractor", () -> new SimpleCraftingRecipeSerializer<>(RepairExtractorRecipe::new));
    DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RefiningRecipe>> COMMON_REFINING =
            RECIPE_SERIALIZERS.register("common_refining", RefiningRecipe.Serializer::new);
}
