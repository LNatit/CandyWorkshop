package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.data.IFormula;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

public class RefiningRecipeBuilder implements RecipeBuilder
{
    protected SizedIngredient milk;
    protected SizedIngredient sugar = SizedIngredient.of(Items.SUGAR, IFormula.SUGAR_CONSUMPTION);
    protected Ingredient main;
    protected Ingredient extra;
    protected ItemStack result;

    public static RefiningRecipeBuilder of(SizedIngredient milk, SizedIngredient sugar, Ingredient main, Ingredient extra, ItemStack result) {
        return new RefiningRecipeBuilder(milk, sugar, main, extra, result);
    }

    private RefiningRecipeBuilder(
            SizedIngredient milk,
            SizedIngredient sugar,
            Ingredient main,
            Ingredient extra,
            ItemStack result
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
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        saveInternal(this.milk, recipeOutput, resourceLocation);
    }

    private void saveInternal(SizedIngredient milk, RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        RefiningRecipe recipe = new RefiningRecipe(milk, this.sugar, this.main, this.extra, this.result);
        recipeOutput.accept(resourceLocation, recipe, null);
    }
}
