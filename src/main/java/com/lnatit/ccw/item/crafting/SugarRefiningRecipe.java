package com.lnatit.ccw.item.crafting;

import net.minecraft.world.item.crafting.*;

public abstract class SugarRefiningRecipe implements Recipe<SugarRefinigRecipeInput> {
    @Override
    public RecipeType<? extends Recipe<SugarRefinigRecipeInput>> getType() {
        return RecipeRegistry.SUGAR_REFINING.get();
    }
}
