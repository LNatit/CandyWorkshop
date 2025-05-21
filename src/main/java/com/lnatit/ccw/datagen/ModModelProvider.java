package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.misc.model.ModelHandler;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModModelProvider
{
    public static class Block extends BlockStateProvider
    {
        public Block(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            ModelFile sugarRefinery = models().withExistingParent("sugar_refinery", modLoc("block/sugar_refinery"));
            ModelFile plainDrawerTable = models().withExistingParent("plain_drawer_table",
                                                                     modLoc("block/plain_drawer_table")
            );
            ModelFile drawerTable = models().withExistingParent("drawer_table", modLoc("block/drawer_table"));

            horizontalBlock(BlockRegistry.SUGAR_REFINERY.get(), sugarRefinery);
            horizontalBlock(BlockRegistry.PLAIN_DRAWER_TABLE.get(), plainDrawerTable);
            horizontalBlock(BlockRegistry.DRAWER_TABLE.get(), drawerTable);
        }
    }

    public static class Item extends ItemModelProvider
    {
        public Item(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            simpleBlockItem(BlockRegistry.SUGAR_REFINERY.get());
            simpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE.get());
            simpleBlockItem(BlockRegistry.DRAWER_TABLE.get());

            basicItem(ItemRegistry.GUMMY_ITEM.getId());

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

            for (DeferredHolder<Sugar, ?> sugar : Sugars.SUGARS.getEntries()) {
                basicItem(sugar.get().getItemModel());
            }

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
        }
    }
}
