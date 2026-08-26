package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.RefiningRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface RecipeRegistry {
    DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, CandyWorkshop.MODID);
    DeferredHolder<RecipeType<?>, RecipeType<RefiningRecipe>> REFINING =
            RECIPE_TYPES.register("refining", RecipeType::simple);

    DeferredRegister<RecipeBookCategory> RECIPE_CATEGORIES =
            DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, CandyWorkshop.MODID);
    DeferredHolder<RecipeBookCategory, RecipeBookCategory> REFINING_CATEGORY =
            RECIPE_CATEGORIES.register("refining", RecipeBookCategory::new);

    DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CandyWorkshop.MODID);
    DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RepairExtractorRecipe>> REPAIR_EXTRACTOR =
            RECIPE_SERIALIZERS.register("repair_extractor", () -> RepairExtractorRecipe.SERIALIZER);
    DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RefiningRecipe>> COMMON_REFINING =
            RECIPE_SERIALIZERS.register("common_refining", () -> RefiningRecipe.SERIALIZER);
}
