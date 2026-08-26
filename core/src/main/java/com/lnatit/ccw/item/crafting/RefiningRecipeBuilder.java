package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.data.RefiningRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RefiningRecipeBuilder implements RecipeBuilder
{
    protected SizedIngredient milk;
    protected SizedIngredient sugar;
//    {SizedIngredient.of(Items.SUGAR, IFormula.SUGAR_CONSUMPTION);}
    protected Ingredient main;
    protected Optional<Ingredient> extra;
    protected ItemStackTemplate result;

    public static RefiningRecipeBuilder of(SizedIngredient milk, SizedIngredient sugar, Ingredient main, Optional<Ingredient> extra, ItemStackTemplate result) {
        return new RefiningRecipeBuilder(milk, sugar, main, extra, result);
    }

    private RefiningRecipeBuilder(
            SizedIngredient milk,
            SizedIngredient sugar,
            Ingredient main,
            Optional<Ingredient> extra,
            ItemStackTemplate result
    ) {
        this.milk = milk;
        this.sugar = sugar;
        this.main = main;
        this.extra = extra;
        this.result = result;
    }

    @Override
    public RecipeBuilder unlockedBy(String s, Criterion<?> criterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String s) {
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceKey<Recipe<?>> location) {
        saveInternal(this.milk, recipeOutput, location);
    }

    private void saveInternal(SizedIngredient milk, RecipeOutput recipeOutput, ResourceKey<Recipe<?>> location) {
        RefiningRecipe recipe = new RefiningRecipe(milk, this.sugar, this.main, this.extra, this.result);
        recipeOutput.accept(location, recipe, null);
    }
}
