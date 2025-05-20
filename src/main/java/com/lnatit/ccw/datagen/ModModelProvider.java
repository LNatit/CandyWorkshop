package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModModelProvider{
    public static class Block extends BlockStateProvider {
        public Block(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void registerStatesAndModels() {
            ModelFile sugarRefinery = models().withExistingParent("sugar_refinery", this.mcLoc("block/sugar_refinery"));
            ModelFile plainDrawerTable = models().withExistingParent("plain_drawer_table", this.mcLoc("block/plain_drawer_table"));
            ModelFile drawerTable = models().withExistingParent("drawer_table", this.mcLoc("block/drawer_table"));

            horizontalFaceBlock(BlockRegistry.SUGAR_REFINERY.get(), sugarRefinery);
            horizontalFaceBlock(BlockRegistry.PLAIN_DRAWER_TABLE.get(), plainDrawerTable);
            horizontalFaceBlock(BlockRegistry.DRAWER_TABLE.get(),  drawerTable);
        }
    }

    public static class Item extends ItemModelProvider {
        public Item(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            simpleBlockItem(BlockRegistry.SUGAR_REFINERY.get());
            simpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE.get());
            simpleBlockItem(BlockRegistry.DRAWER_TABLE.get());

            withExistingParent(ItemRegistry.GUMMY_ITEM.getId().toString(), modLoc("item/gummy"));

            withExistingParent(ItemRegistry.MILK_PACKAGING.getId().toString(), modLoc("item/milk_packaging"));
            withExistingParent(ItemRegistry.CARTON_MILK.getId().toString(), modLoc("item/carton_milk"));
            withExistingParent(ItemRegistry.NETHER_SUGAR.getId().toString(), modLoc("item/nether_sugar"));
            withExistingParent(ItemRegistry.ENDER_SUGAR.getId().toString(), modLoc("item/ender_sugar"));

            withExistingParent(ItemRegistry.ENERGY_CARROT.getId().toString(), modLoc("item/energy_carrot"));
            withExistingParent(ItemRegistry.SWEET_MELON_SLICE.getId().toString(), modLoc("item/sweet_melon_slice"));
            withExistingParent(ItemRegistry.PHANTOM_PEARL.getId().toString(), modLoc("item/phantom_pearl"));
            withExistingParent(ItemRegistry.CALCIUM_RICH_MILK.getId().toString(), modLoc("item/calcium_rich_milk"));
            withExistingParent(ItemRegistry.VOID_CARROT.getId().toString(), modLoc("item/void_carrot"));
            withExistingParent(ItemRegistry.WEAKNESS_POWDER.getId().toString(), modLoc("item/weakness_powder"));
            withExistingParent(ItemRegistry.IRON_CLAD_APPLE.getId().toString(), modLoc("item/iron_clad_apple"));
            withExistingParent(ItemRegistry.GOLD_STUDDED_APPLE.getId().toString(), modLoc("item/gold_studded_apple"));
            withExistingParent(ItemRegistry.BLESSED_STEAK.getId().toString(), modLoc("item/blessed_steak"));
            withExistingParent(ItemRegistry.GREEDY_OFFERING.getId().toString(), modLoc("item/greedy_offering"));
            withExistingParent(ItemRegistry.DEFILED_OFFERING.getId().toString(), modLoc("item/defiled_offering"));
            withExistingParent(ItemRegistry.DOLPHIN_COOKIE.getId().toString(), modLoc("item/dolphin_cookie"));
            withExistingParent(ItemRegistry.OMINOUS_FLAG.getId().toString(), modLoc("item/ominous_flag"));
            withExistingParent(ItemRegistry.MILK_GELATIN.getId().toString(), modLoc("item/milk_gelatin"));

            for (DeferredHolder<Sugar, ?> sugar : Sugars.SUGARS.getEntries()) {
                withExistingParent(sugar.getId().toString(), sugar.value().getModelId());
            }

            withExistingParent(ItemRegistry.MILK_EXTRACTOR.getId() + "_full", modLoc("item/milk_extractor_full"));
            withExistingParent(ItemRegistry.MILK_EXTRACTOR.getId() + "_empty", modLoc("item/milk_extractor_empty"));
        }
    }
}
