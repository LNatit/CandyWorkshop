package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RecipeRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider
{
    protected ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MILK_EXTRACTOR.get())
            .define('#', Items.GLASS_PANE)
            .define('U', Items.BUCKET)
            .define('I', Items.IRON_INGOT)
            .define('X', ItemRegistry.MILK_PACKAGING.get())
            .pattern("#  ")
            .pattern("UII")
            .pattern("#XI")
            .unlockedBy("has_milk", has(Items.MILK_BUCKET))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.MILK_PACKAGING.get())
            .define('#', Items.PAPER)
            .pattern("#")
            .pattern("#")
            .pattern("#")
            .unlockedBy("has_paper", has(Items.PAPER))
            .save(output);

//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.CARTON_MILK, 128) // wtf?
//                .define('U', Items.MILK_BUCKET)
//                .define('#', ItemRegistry.MILK_PACKAGING)
//                .pattern("UUU")
//                .pattern("U#U")
//                .pattern("UUU")
//                .unlockedBy("has_packaging", has(ItemRegistry.MILK_PACKAGING))
//                .save(ShapedRecipeBuilder.output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CARTON_MILK.get(), 8)
            .requires(Items.MILK_BUCKET)
            .requires(Items.PAPER)
            .unlockedBy("has_milk", has(Items.MILK_BUCKET))
            .save(output, CandyWorkshop.MODID + ":milk_carton_from_single_milk_bucket");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegistry.CARTON_MILK.get(), 24)
            .requires(Items.MILK_BUCKET, 3)
            .requires(Items.PAPER)
            .unlockedBy("has_milk", has(Items.MILK_BUCKET))
            .save(output);

        // TODO modify unlock requirements
        SpecialRecipeBuilder.special(RecipeRegistry.REPAIR_EXTRACTOR.get())
                            .save(output, CandyWorkshop.MODID + ":repair_extractor");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.SUGAR_REFINERY.get())
            .define('U', Items.BUCKET)
            .define('#', Items.IRON_INGOT)
            .define('/', Tags.Items.RODS_WOODEN)
            .define('X', Tags.Items.STONE)
            .pattern("U #")
            .pattern("X/X")
            .pattern(" X ")
            .unlockedBy("has_milk", has(Items.MILK_BUCKET))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.PLAIN_DRAWER_TABLE.get())
            .define('#', ItemTags.PLANKS)
            .define('X', Tags.Items.CHESTS)
            .pattern("###")
            .pattern("#X#")
            .pattern("#X#")
            .unlockedBy("has_chest", has(Items.CHEST))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegistry.DRAWER_TABLE.get())
            .define('#', ItemRegistry.PLAIN_DRAWER_TABLE.get())
            .define('X', Items.PINK_CARPET)
            .pattern("X")
            .pattern("#")
            .unlockedBy("has_plain_drawer_table", has(ItemRegistry.PLAIN_DRAWER_TABLE.get()))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.NETHER_SUGAR.get(), 8)
            .requires(Items.NETHER_WART)
            .requires(Items.SUGAR, 8)
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.ENDER_SUGAR.get(), 8)
            .requires(Items.DRAGON_BREATH)
            .requires(Items.SUGAR, 8)
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.ENERGY_CARROT.get())
            .define('#', Items.REDSTONE)
            .define('X', Items.CARROT)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.SWEET_MELON_SLICE.get())
                           .define('#', Items.SUGAR)
                           .define('X', Items.MELON_SLICE)
                           .pattern("###")
                           .pattern("#X#")
                           .pattern("###")
                           .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
                           .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.PHANTOM_PEARL.get())
            .requires(Items.PHANTOM_MEMBRANE)
            .requires(Items.ENDER_PEARL)
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.CALCIUM_RICH_MILK.get())
            .define('#', Items.BONE)
            .define('X', ItemRegistry.CARTON_MILK_TAG)
            .pattern(" # ")
            .pattern("#X#")
            .pattern(" # ")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.VOID_CARROT.get())
            .define('#', Items.BLACK_DYE)
            .define('X', Items.CARROT)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.WEAKNESS_POWDER.get())
            .requires(Items.BLAZE_POWDER)
            .requires(Items.FERMENTED_SPIDER_EYE)
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.IRON_CLAD_APPLE.get())
            .define('#', Items.IRON_NUGGET)
            .define('X', Items.APPLE)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.GOLD_STUDDED_APPLE.get())
            .define('#', Items.GOLD_NUGGET)
            .define('X', Items.APPLE)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.BLESSED_STEAK.get())
            .define('#', Items.GOLD_INGOT)
            .define('X', Items.COOKED_BEEF)
            .pattern("###")
            .pattern("#X#")
            .pattern("###")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.GREEDY_OFFERING.get())
            .define('#', Items.EMERALD)
            .define('X', Items.BOWL)
            .pattern(" # ")
            .pattern("###")
            .pattern(" X ")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.DEFILED_OFFERING.get())
            .define('#', Items.COAL)
            .define('X', Items.BOWL)
            .pattern(" # ")
            .pattern("###")
            .pattern(" X ")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.DOLPHIN_COOKIE.get())
            .define('#', ItemTags.FISHES)
            .define('X', Items.COOKIE)
            .pattern(" # ")
            .pattern("#X#")
            .pattern(" # ")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        Ingredient ominous_banner = StrictNBTIngredient.of(Raid.getLeaderBannerInstance());
        ShapedRecipeBuilder.shaped(RecipeCategory.BREWING, ItemRegistry.OMINOUS_FLAG.get())
            .define('#', ominous_banner)
            .define('X', Items.EMERALD)
            .pattern("# #")
            .pattern(" X ")
            .pattern("# #")
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN.get())
            .requires(ItemRegistry.CARTON_MILK_TAG)
            .requires(Tags.Items.SLIMEBALLS)
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BREWING, ItemRegistry.MILK_GELATIN.get(), 8)
            .requires(Items.MILK_BUCKET)
            .requires(Items.SLIME_BALL, 8)
            .unlockedBy("has_milk_carton", has(ItemRegistry.CARTON_MILK_TAG))
            .save(output, CandyWorkshop.MODID + ":milk_gelatin_from_milk_bucket");
    }
}
