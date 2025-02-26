package com.lnatit.ccw.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class CustomRefiningRecipe extends SugarRefiningRecipe {
    public static final MapCodec<CustomRefiningRecipe> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    Ingredient.CODEC.fieldOf("milk").forGetter(c -> c.milk),
                    SizedIngredient.NESTED_CODEC.fieldOf("sugar").forGetter(c -> c.sugar),
                    Ingredient.CODEC.fieldOf("main").forGetter(c -> c.main),
                    Ingredient.CODEC.fieldOf("extra").forGetter(c -> c.extra),
                    ItemStack.CODEC.fieldOf("result").forGetter(c -> c.result)
            ).apply(inst, CustomRefiningRecipe::new)
    );

    private final Ingredient milk;
    private final SizedIngredient sugar;
    private final Ingredient main;
    private final Ingredient extra;
    private final ItemStack result;

    public CustomRefiningRecipe(Ingredient milk, SizedIngredient sugar, Ingredient main, Ingredient extra, ItemStack result) {
        this.milk = milk;
        this.sugar = sugar;
        this.main = main;
        this.extra = extra;
        this.result = result;
    }

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
