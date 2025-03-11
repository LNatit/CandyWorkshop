package com.lnatit.ccw.data;

import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.util.datafix.fixes.OminousBannerRarityFix;
import net.minecraft.util.datafix.fixes.OminousBannerRenameFix;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
//        this.shaped(RecipeCategory.MISC, );

        // TODO modify unlock requirements
        this.shaped(RecipeCategory.BREWING, ItemRegistry.ENERGY_CARROT)
                .define('#', Items.REDSTONE)
                .define('X', Items.CARROT)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.PHANTOM_PEARL)
                .requires(Items.PHANTOM_MEMBRANE)
                .requires(Items.ENDER_PEARL)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.CALCIUM_RICH_MILK)
                .define('#', Items.BONE)
                .define('X', ItemRegistry.MILK_CARTON)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.VOID_CARROT)
                .define('#', Items.BLACK_DYE)
                .define('X', Items.CARROT)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.WEAKNESS_POWDER)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.FERMENTED_SPIDER_EYE)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.IRON_CLAD_APPLE)
                .define('#', Items.IRON_NUGGET)
                .define('X', Items.APPLE)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.GOLD_STUDDED_APPLE)
                .define('#', Items.GOLD_NUGGET)
                .define('X', Items.APPLE)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.BLESSED_STEAK)
                .define('#', Items.GOLD_INGOT)
                .define('X', Items.COOKED_BEEF)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.GREEDY_OFFERING)
                .define('#', Items.EMERALD)
                .define('X', Items.BOWL)
                .pattern(" # ")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.DEFILED_OFFERING)
                .define('#', Items.COAL)
                .define('X', Items.BOWL)
                .pattern(" # ")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.DOLPHIN_COOKIE)
                .define('#', Tags.Items.FOODS_RAW_FISH)
                .define('X', Items.COOKIE)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);

        // TODO use special recipe
//        this.shaped(RecipeCategory.BREWING, ItemRegistry.OMINOUS_FLAG)
//                .define('#', Items.)
//                .define('X', Items.EMERALD)
//                .pattern("# #")
//                .pattern(" X ")
//                .pattern("# #")
//                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
//                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN)
                .requires(ItemRegistry.MILK_CARTON)
                .requires(Tags.Items.SLIME_BALLS)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.MILK_CARTON))
                .save(this.output);
    }

    public static class Runner extends RecipeProvider.Runner {
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
