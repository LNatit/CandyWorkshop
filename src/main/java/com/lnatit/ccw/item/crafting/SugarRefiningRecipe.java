package com.lnatit.ccw.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class SugarRefiningRecipe extends SingleItemRecipe
{
    public SugarRefiningRecipe(String group, Ingredient input, ItemStack result) {
        super(group, input, result);
    }
}