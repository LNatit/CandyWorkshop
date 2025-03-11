package com.lnatit.ccw.data;

import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider
{
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
//        this.shaped(RecipeCategory.MISC, );

        // TODO modify unlock requirements
        this.shaped(RecipeCategory.BREWING, ItemRegistry.ENERGY_CARROT)
            .define('#', Items.REDSTONE)
            .define('C', Items.CARROT)
            .pattern("###")
            .pattern("#C#")
            .pattern("###")
            .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
            .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN)
            .requires(ItemRegistry.MILK_CARTON)
            .requires(Tags.Items.SLIME_BALLS)
            .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
            .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner
    {
        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "CandyWorkshop Recipes";
        }
    }
}
