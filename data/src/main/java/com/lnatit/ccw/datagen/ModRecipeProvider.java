package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RefiningRecipeBuilder;
import com.lnatit.ccw.item.crafting.RepairExtractorRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider
{
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderLookup.RegistryLookup<Item> lookup = registries.lookupOrThrow(Registries.ITEM);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.MILK_EXTRACTOR)
                           .define('#', Items.GLASS_PANE)
                           .define('U', Items.BUCKET)
                           .define('I', Items.IRON_INGOT)
                           .define('X', ItemRegistry.MILK_PACKAGING)
                           .pattern("#  ")
                           .pattern("UII")
                           .pattern("#XI")
                           .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.MILK_PACKAGING)
                           .define('#', Items.PAPER)
                           .pattern("#")
                           .pattern("#")
                           .pattern("#")
                           .unlockedBy("has_paper", has(Items.PAPER))
                           .save(output);

//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CARTON_MILK, 128) // wtf?
//                .define('U', ItemTags.MILK_BUCKET)
//                .define('#', ItemRegistry.MILK_PACKAGING)
//                .pattern("UUU")
//                .pattern("U#U")
//                .pattern("UUU")
//                .unlockedBy("has_packaging", has(ItemRegistry.MILK_PACKAGING))
//                .save(ShapedRecipeBuilder.output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.MISC, ItemRegistry.CARTON_MILK, 8)
                              .requires(Items.MILK_BUCKET)
                              .requires(Items.PAPER)
                              .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                              .save(output, CandyWorkshop.MODID + ":milk_carton_from_single_milk_bucket");

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.MISC, ItemRegistry.CARTON_MILK, 24)
                              .requires(Items.MILK_BUCKET, 3)
                              .requires(Items.PAPER)
                              .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                              .save(output);

        // TODO modify unlock requirements
        SpecialRecipeBuilder.special(RepairExtractorRecipe::new)
                            .save(output, CandyWorkshop.MODID + ":repair_extractor");

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.SUGAR_REFINERY)
                           .define('U', Items.BUCKET)
                           .define('#', Items.IRON_INGOT)
                           .define('/', Tags.Items.RODS_WOODEN)
                           .define('X', Tags.Items.STONES)
                           .pattern("U #")
                           .pattern("X/X")
                           .pattern(" X ")
                           .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.PLAIN_DRAWER_TABLE)
                           .define('#', net.minecraft.tags.ItemTags.PLANKS)
                           .define('X', Tags.Items.CHESTS)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("#X#")
                           .unlockedBy("has_chest", has(Items.CHEST))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.DRAWER_TABLE)
                           .define('#', ItemRegistry.PLAIN_DRAWER_TABLE)
                           .define('X', Items.PINK_CARPET)
                           .pattern("X")
                           .pattern("#")
                           .unlockedBy("has_plain_drawer_table", has(ItemRegistry.PLAIN_DRAWER_TABLE))
                           .save(output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BREWING, ItemRegistry.NETHER_SUGAR, 8)
                              .requires(Items.NETHER_WART)
                              .requires(Items.SUGAR, 8)
                              .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                              .save(output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BREWING, ItemRegistry.ENDER_SUGAR, 8)
                              .requires(Items.DRAGON_BREATH)
                              .requires(Items.SUGAR, 8)
                              .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                              .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.ENERGY_CARROT)
                           .define('#', Items.REDSTONE)
                           .define('X', Items.CARROT)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.SWEET_MELON_SLICE)
                           .define('#', Items.SUGAR)
                           .define('X', Items.MELON_SLICE)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BREWING, ItemRegistry.PHANTOM_PEARL)
                              .requires(Items.PHANTOM_MEMBRANE)
                              .requires(Items.ENDER_PEARL)
                              .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                              .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.CALCIUM_RICH_MILK)
                           .define('#', Items.BONE)
                           .define('X', ItemRegistry.CARTON_MILK_TAG)
                           .pattern(" # ")
                           .pattern("#X#")
                           .pattern(" # ")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.VOID_CARROT)
                           .define('#', Items.BLACK_DYE)
                           .define('X', Items.CARROT)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BREWING, ItemRegistry.WEAKNESS_POWDER)
                              .requires(Items.BLAZE_POWDER)
                              .requires(Items.FERMENTED_SPIDER_EYE)
                              .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                              .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.IRON_CLAD_APPLE)
                           .define('#', Items.IRON_NUGGET)
                           .define('X', Items.APPLE)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.GOLD_STUDDED_APPLE)
                           .define('#', Items.GOLD_NUGGET)
                           .define('X', Items.APPLE)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.BLESSED_STEAK)
                           .define('#', Items.GOLD_INGOT)
                           .define('X', Items.COOKED_BEEF)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.GREEDY_OFFERING)
                           .define('#', Items.EMERALD)
                           .define('X', Items.BOWL)
                           .pattern(" # ")
                           .pattern("###")
                           .pattern(" X ")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.DEFILED_OFFERING)
                           .define('#', Items.COAL)
                           .define('X', Items.BOWL)
                           .pattern(" # ")
                           .pattern("###")
                           .pattern(" X ")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.DOLPHIN_COOKIE)
                           .define('#', Tags.Items.FOODS_RAW_FISH)
                           .define('X', Items.COOKIE)
                           .pattern(" # ")
                           .pattern("#X#")
                           .pattern(" # ")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        Ingredient ominous_banner = new Ingredient(
                new DataComponentIngredient(
                        HolderSet.direct(Items.WHITE_BANNER.builtInRegistryHolder()),
                        Raid.getBannerComponentPatch(registries.lookupOrThrow(Registries.BANNER_PATTERN)),
                        false
                )
        );
        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.BREWING, ItemRegistry.OMINOUS_FLAG)
                           .define('#', ominous_banner)
                           .define('X', Items.EMERALD)
                           .pattern("# #")
                           .pattern(" X ")
                           .pattern("# #")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN)
                              .requires(ItemRegistry.CARTON_MILK_TAG)
                              .requires(Tags.Items.SLIME_BALLS)
                              .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                              .save(output);

        ShapelessRecipeBuilder.shapeless(lookup, RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN, 8)
                              .requires(Items.MILK_BUCKET)
                              .requires(Items.SLIME_BALL, 8)
                              .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                              .save(output, CandyWorkshop.MODID + ":milk_gelatin_from_milk_bucket");

        // TODO check amount and output
        RefiningRecipeBuilder.of(
                                     new SizedIngredient(Ingredient.of(registries.lookupOrThrow(Registries.ITEM).getOrThrow(ItemRegistry.CARTON_MILK_TAG)), 8),
                                     SizedIngredient.of(Items.SUGAR, 8),
                                     Ingredient.of(Items.COPPER_BLOCK),
                                     Ingredient.of(),
                                     new ItemStack(ItemRegistry.CARAMETAL.asItem())
                             )
                             .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.GUMMY_MAGAZINE)
                           .define('#', ItemRegistry.CARAMETAL)
                           .define('C', Items.CHEST)
                           .define('S', Items.IRON_TRAPDOOR)
                           .pattern("#S#")
                           .pattern("#C#")
                           .pattern("###")
                           .unlockedBy("has_carametal", has(ItemRegistry.CARAMETAL))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.GUMMY_GLAZER)
                           .define('#', ItemRegistry.CARAMETAL)
                           .define('C', Items.BLAST_FURNACE)
                           .define('D', Items.LAVA_BUCKET)
                           .define('R', Items.HOPPER)
                           .pattern("###")
                           .pattern("#CR")
                           .pattern("#D#")
                           .unlockedBy("has_carametal", has(ItemRegistry.CARAMETAL))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.NETHER_SMITHING_WAFER)
                           .define('#', ItemRegistry.CARAMETAL)
                           .define('C', Items.DIAMOND)
                           .define('S', ItemRegistry.NETHER_SUGAR_TAG)
                           .pattern("#S#")
                           .pattern("#C#")
                           .pattern("###")
                           .unlockedBy("has_nether_sugar", has(ItemRegistry.NETHER_SUGAR_TAG))
                           .save(output);

        ShapedRecipeBuilder.shaped(lookup, RecipeCategory.MISC, ItemRegistry.ENDER_SMITHING_WAFER)
                           .define('#', Items.POPPED_CHORUS_FRUIT)
                           .define('C', Items.ECHO_SHARD)
                           .define('S', ItemRegistry.ENDER_SUGAR_TAG)
                           .pattern("#S#")
                           .pattern("#C#")
                           .pattern("###")
                           .unlockedBy("has_ender_sugar", has(ItemRegistry.ENDER_SUGAR_TAG))
                           .save(output);

        SmithingTransformRecipeBuilder.smithing(
                                              Ingredient.of(ItemRegistry.NETHER_SMITHING_WAFER),
                                              Ingredient.of(ItemRegistry.GUMMY_MAGAZINE),
                                              Ingredient.of(Items.NETHERITE_INGOT),
                                              RecipeCategory.TOOLS,
                                              ItemRegistry.NETHER_MAGAZINE.asItem()
                                      )
                                      .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                                      .save(output, "magazine_nether_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                                              Ingredient.of(ItemRegistry.NETHER_SMITHING_WAFER),
                                              Ingredient.of(ItemRegistry.GUMMY_GLAZER),
                                              Ingredient.of(Items.NETHERITE_INGOT),
                                              RecipeCategory.TOOLS,
                                              ItemRegistry.NETHER_GLAZER.asItem()
                                      )
                                      .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                                      .save(output, "glazer_nether_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                                              Ingredient.of(ItemRegistry.ENDER_SMITHING_WAFER),
                                              Ingredient.of(ItemRegistry.NETHER_MAGAZINE),
                                              Ingredient.of(Items.DRAGON_HEAD),
                                              RecipeCategory.TOOLS,
                                              ItemRegistry.ENDER_MAGAZINE.asItem()
                                      )
                                      .unlocks("has_dragon_head", has(Items.DRAGON_HEAD))
                                      .save(output, "magazine_ender_upgrade");

        SmithingTransformRecipeBuilder.smithing(
                                              Ingredient.of(ItemRegistry.ENDER_SMITHING_WAFER),
                                              Ingredient.of(ItemRegistry.NETHER_GLAZER),
                                              Ingredient.of(Items.DRAGON_HEAD),
                                              RecipeCategory.TOOLS,
                                              ItemRegistry.ENDER_GLAZER.asItem()
                                      )
                                      .unlocks("has_dragon_head", has(Items.DRAGON_HEAD))
                                      .save(output, "glazer_ender_upgrade");
    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from the `GatherDataEvent`s.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Candy Workshop Recipes";
        }
    }
}
