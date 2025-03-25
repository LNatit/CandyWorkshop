package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RepairExtractorRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        this.shaped(RecipeCategory.MISC, ItemRegistry.MILK_EXTRACTOR)
                .define('#', Items.GLASS_PANE)
                .define('U', Items.BUCKET)
                .define('I', Items.IRON_INGOT)
                .define('X', ItemRegistry.MILK_PACKAGING)
                .pattern("#  ")
                .pattern("UII")
                .pattern("#XI")
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .save(this.output);

        this.shaped(RecipeCategory.MISC, ItemRegistry.MILK_PACKAGING)
                .define('#', Items.PAPER)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(this.output);

//        this.shaped(RecipeCategory.MISC, ItemRegistry.CARTON_MILK, 128) // wtf?
//                .define('U', Items.MILK_BUCKET)
//                .define('#', ItemRegistry.MILK_PACKAGING)
//                .pattern("UUU")
//                .pattern("U#U")
//                .pattern("UUU")
//                .unlockedBy("has_packaging", has(ItemRegistry.MILK_PACKAGING))
//                .save(this.output);

        // TODO do we need to modify count to let a 8 bucket recipe makes sense?

        // TODO modify unlock requirements
        SpecialRecipeBuilder.special(RepairExtractorRecipe::new)
                .save(this.output, CandyWorkshop.MODID + ":repair_extractor");

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.NETHER_SUGAR)
                .requires(Items.NETHER_WART)
                .requires(Items.SUGAR, 8)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.ENDER_SUGAR)
                .requires(Items.DRAGON_BREATH)
                .requires(Items.SUGAR, 8)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.ENERGY_CARROT)
                .define('#', Items.REDSTONE)
                .define('X', Items.CARROT)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.PHANTOM_PEARL)
                .requires(Items.PHANTOM_MEMBRANE)
                .requires(Items.ENDER_PEARL)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.CALCIUM_RICH_MILK)
                .define('#', Items.BONE)
                .define('X', ItemRegistry.CARTON_MILK_TAG)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.VOID_CARROT)
                .define('#', Items.BLACK_DYE)
                .define('X', Items.CARROT)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.WEAKNESS_POWDER)
                .requires(Items.BLAZE_POWDER)
                .requires(Items.FERMENTED_SPIDER_EYE)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.IRON_CLAD_APPLE)
                .define('#', Items.IRON_NUGGET)
                .define('X', Items.APPLE)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.GOLD_STUDDED_APPLE)
                .define('#', Items.GOLD_NUGGET)
                .define('X', Items.APPLE)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.BLESSED_STEAK)
                .define('#', Items.GOLD_INGOT)
                .define('X', Items.COOKED_BEEF)
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.GREEDY_OFFERING)
                .define('#', Items.EMERALD)
                .define('X', Items.BOWL)
                .pattern(" # ")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.DEFILED_OFFERING)
                .define('#', Items.COAL)
                .define('X', Items.BOWL)
                .pattern(" # ")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shaped(RecipeCategory.BREWING, ItemRegistry.DOLPHIN_COOKIE)
                .define('#', Tags.Items.FOODS_RAW_FISH)
                .define('X', Items.COOKIE)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        Ingredient ominous_banner = new Ingredient(
                new DataComponentIngredient(
                        HolderSet.direct(Items.WHITE_BANNER.builtInRegistryHolder()),
                        DataComponentPredicate.allOf(
                                Raid.getOminousBannerInstance(this.registries.lookupOrThrow(Registries.BANNER_PATTERN))
                                        .getComponents()
                        ),
                        false
                )
        );
        this.shaped(RecipeCategory.BREWING, ItemRegistry.OMINOUS_FLAG)
                .define('#', ominous_banner)
                .define('X', Items.EMERALD)
                .pattern("# #")
                .pattern(" X ")
                .pattern("# #")
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN)
                .requires(ItemRegistry.CARTON_MILK_TAG)
                .requires(Tags.Items.SLIME_BALLS)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output);

        this.shapeless(RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN, 8)
                .requires(Items.MILK_BUCKET)
                .requires(Items.SLIME_BALL, 8)
                .unlockedBy("has_milk_carton", this.has(ItemRegistry.CARTON_MILK_TAG))
                .save(this.output, CandyWorkshop.MODID + ":milk_gelatin_from_milk_bucket");
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
