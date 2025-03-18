package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CandyWorkshop.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RepairExtractorRecipe>> REPAIR_EXTRACTOR =
            RECIPE_SERIALIZERS.register("repair_extractor", () -> new CustomRecipe.Serializer<>(RepairExtractorRecipe::new));
}
