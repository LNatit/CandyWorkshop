package com.lnatit.ccw.datagen;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.compat.ApothesisCompats;
import com.lnatit.ccw.compat.FarmersDelightCompats;
import com.lnatit.ccw.compat.CookeryCompat;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.misc.model.ModelHandler;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CandyWorkshopItems extends ModModelProviders.Item
{
    public CandyWorkshopItems(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleBlockItem(BlockRegistry.SUGAR_REFINERY.get());
        simpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE.get());
        simpleBlockItem(BlockRegistry.DRAWER_TABLE.get());

        basicItem(ItemRegistry.GUMMY.getId());

        basicItem(ItemRegistry.MILK_PACKAGING.getId());
        basicItem(ItemRegistry.CARTON_MILK.getId());
        basicItem(ItemRegistry.NETHER_SUGAR.getId());
        basicItem(ItemRegistry.ENDER_SUGAR.getId());

        basicItem(ItemRegistry.ENERGY_CARROT.getId());
        basicItem(ItemRegistry.SWEET_MELON_SLICE.getId());
        basicItem(ItemRegistry.PHANTOM_PEARL.getId());
        basicItem(ItemRegistry.CALCIUM_RICH_MILK.getId());
        basicItem(ItemRegistry.VOID_CARROT.getId());
        basicItem(ItemRegistry.WEAKNESS_POWDER.getId());
        basicItem(ItemRegistry.IRON_CLAD_APPLE.getId());
        basicItem(ItemRegistry.GOLD_STUDDED_APPLE.getId());
        basicItem(ItemRegistry.BLESSED_STEAK.getId());
        basicItem(ItemRegistry.GREEDY_OFFERING.getId());
        basicItem(ItemRegistry.DEFILED_OFFERING.getId());
        basicItem(ItemRegistry.DOLPHIN_COOKIE.getId());
        basicItem(ItemRegistry.OMINOUS_FLAG.getId());
        basicItem(ItemRegistry.MILK_GELATIN.getId());

        super.registerModels();

        ResourceLocation id = ItemRegistry.MILK_EXTRACTOR.getId();
        basicItem(id.withSuffix("_empty"));
        getBuilder(id.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", id.withPrefix("item/").withSuffix("_full"))
                .override()
                .model(new ModelFile.UncheckedModelFile(id.withPrefix("item/").withSuffix("_empty")))
                .predicate(
                        ModelHandler.BROKEN,
                        1.0f
                ).end();

        basicItem(ItemRegistry.CARAMETAL.getId());
        basicItem(ItemRegistry.NETHER_SMITHING_WAFER.getId());
        basicItem(ItemRegistry.ENDER_SMITHING_WAFER.getId());

        // Compats below
        // Apotheosis
        basicItem(ApothesisCompats.MALIGNANT_BERRY.asItem());
        basicItem(ApothesisCompats.KNOWLEDGE_BOTTLE.asItem());
        basicItem(ApothesisCompats.EROSION_SHELL.asItem());
        basicItem(ApothesisCompats.VITALITY_BERRY.asItem());
        // FarmersDelight
        basicItem(FarmersDelightCompats.GLAZED_MEAT_RICE.get());
        basicItem(FarmersDelightCompats.SWEET_HARVEST_SOUP.get());
        // Kaleidoscope Cookery
        basicItem(CookeryCompat.SWEET_AND_SOUR_MEAT_SAUCE.getId());
        basicItem(CookeryCompat.FRESH_VEGGIE_SAUCE.getId());
        basicItem(CookeryCompat.LACTO_BOLT_RED_SAUCE.getId());
        basicItem(CookeryCompat.SASHIMI_SIDE_SAUCE.getId());
        basicItem(CookeryCompat.ULTRA_RICH_MARROW_CREAM.getId());
        basicItem(CookeryCompat.SILENCED_SAUCE.getId());
        basicItem(CookeryCompat.HEARTH_WARM_MARROW_SAUCE.getId());
        basicItem(CookeryCompat.LAMB_CARROT_SAUCE.getId());
    }
}
