package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CandyWorkshop.MODID);

    public static final RegistryObject<RecipeSerializer<RepairExtractorRecipe>> REPAIR_EXTRACTOR =
            RECIPE_SERIALIZERS.register("repair_extractor", () -> new SimpleCraftingRecipeSerializer<>(RepairExtractorRecipe::new));
}
