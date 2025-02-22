package com.lnatit.ccw.item.crafting;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class SimpleRefiningRecipe extends SugarRefiningRecipe {
    private static final Map<Item, ItemStack> KNOWN_RECIPES = new HashMap<>();

    @Override
    public boolean matches(SugarRefinigRecipeInput input, Level level) {
        if (input.isSimple())
        {
            return KNOWN_RECIPES.get(input.getItem(2).getItem()) != null;
        }
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
