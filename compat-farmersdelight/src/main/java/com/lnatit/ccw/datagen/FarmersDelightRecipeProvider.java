package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.FarmersDelightCompats;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class FarmersDelightRecipeProvider extends RecipeProvider {
    protected FarmersDelightRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider registries) {
        RecipeOutput conditional = output.withConditions(new ModLoadedCondition("farmersdelight"));

        CookingPotRecipeBuilder.cookingPotRecipe(FarmersDelightCompats.GLAZED_MEAT_RICE.get(), 1, 200, 1.0F)
                .addIngredient(ItemRegistry.GUMMY)
                .addIngredient(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/raw_meat")))
                .addIngredient(ItemTags.create(Identifier.fromNamespaceAndPath("c", "crops/rice")))
                .addIngredient(ItemTags.create(Identifier.fromNamespaceAndPath("c", "foods/leafy_green")))
                .unlockedBy("has_gummy", has(ItemRegistry.GUMMY))
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(conditional);
    }
}


