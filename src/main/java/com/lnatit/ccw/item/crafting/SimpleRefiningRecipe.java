package com.lnatit.ccw.item.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class SimpleRefiningRecipe extends SugarRefiningRecipe {
    @Override
    public boolean matches(SugarRefinigRecipeInput input, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(SugarRefinigRecipeInput input, HolderLookup.Provider registries) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SugarRefinigRecipeInput>> getSerializer() {
        return null;
    }

    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }
}
