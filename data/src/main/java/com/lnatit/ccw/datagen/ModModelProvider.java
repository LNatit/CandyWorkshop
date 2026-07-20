package com.lnatit.ccw.datagen;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModModelProvider extends CoreModelProvider
{
    public ModModelProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(
            BlockModelGenerators blockModels,
            ItemModelGenerators itemModels,
            ClientItemModelGenerators clientItemModels
    ) {
//        simpleBlockItem(BlockRegistry.SUGAR_REFINERY.get());
//        simpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE.get());
//        simpleBlockItem(BlockRegistry.DRAWER_TABLE.get());

//        basicItem(ItemRegistry.GUMMY.getId());
        clientItemModels.gen().withId(ItemRegistry.GUMMY).all();

        clientItemModels.gen().withId(ItemRegistry.MILK_PACKAGING).all();
        clientItemModels.gen().withId(ItemRegistry.CARTON_MILK).all();
        clientItemModels.gen().withId(ItemRegistry.NETHER_SUGAR).all();
        clientItemModels.gen().withId(ItemRegistry.ENDER_SUGAR).all();

        clientItemModels.gen().withId(ItemRegistry.ENERGY_CARROT).all();
        clientItemModels.gen().withId(ItemRegistry.SWEET_MELON_SLICE).all();
        clientItemModels.gen().withId(ItemRegistry.PHANTOM_PEARL).all();
        clientItemModels.gen().withId(ItemRegistry.CALCIUM_RICH_MILK).all();
        clientItemModels.gen().withId(ItemRegistry.VOID_CARROT).all();
        clientItemModels.gen().withId(ItemRegistry.WEAKNESS_POWDER).all();
        clientItemModels.gen().withId(ItemRegistry.IRON_CLAD_APPLE).all();
        clientItemModels.gen().withId(ItemRegistry.GOLD_STUDDED_APPLE).all();
        clientItemModels.gen().withId(ItemRegistry.BLESSED_STEAK).all();
        clientItemModels.gen().withId(ItemRegistry.GREEDY_OFFERING).all();
        clientItemModels.gen().withId(ItemRegistry.DEFILED_OFFERING).all();
        clientItemModels.gen().withId(ItemRegistry.DOLPHIN_COOKIE).all();
        clientItemModels.gen().withId(ItemRegistry.OMINOUS_FLAG).all();
        clientItemModels.gen().withId(ItemRegistry.MILK_GELATIN).all();

        for (DeferredHolder<Sugar, ?> sugar : Sugars.SUGARS.getEntries()) {
            sugarModel(clientItemModels, sugar);
        }

        Identifier id = ItemRegistry.MILK_EXTRACTOR.getId();
        clientItemModels.gen().withId(id.withSuffix("_empty")).all();
//        getBuilder(id.toString())
//                .parent(new ModelFile.UncheckedModelFile("item/generated"))
//                .texture("layer0", id.withPrefix("item/").withSuffix("_full"))
//                .override()
//                .model(new ModelFile.UncheckedModelFile(id.withPrefix("item/").withSuffix("_empty")))
//                .predicate(
//                        ModelHandler.BROKEN,
//                        1.0f
//                ).end();

        clientItemModels.gen().withId(ItemRegistry.CARAMETAL).all();
        clientItemModels.gen().withId(ItemRegistry.NETHER_SMITHING_WAFER).all();
        clientItemModels.gen().withId(ItemRegistry.ENDER_SMITHING_WAFER).all();



    }

//    public static class Item extends CoreModelProvider
//    {
//        public Item(PackOutput output, ExistingFileHelper existingFileHelper) {
//            super(output, existingFileHelper);
//        }
//
//        @Override
//        protected void registerModels() {
//            simpleBlockItem(BlockRegistry.SUGAR_REFINERY.get());
//            simpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE.get());
//            simpleBlockItem(BlockRegistry.DRAWER_TABLE.get());
//
//            basicItem(ItemRegistry.GUMMY.getId());
//
//            basicItem(ItemRegistry.MILK_PACKAGING.getId());
//            basicItem(ItemRegistry.CARTON_MILK.getId());
//            basicItem(ItemRegistry.NETHER_SUGAR.getId());
//            basicItem(ItemRegistry.ENDER_SUGAR.getId());
//
//            basicItem(ItemRegistry.ENERGY_CARROT.getId());
//            basicItem(ItemRegistry.SWEET_MELON_SLICE.getId());
//            basicItem(ItemRegistry.PHANTOM_PEARL.getId());
//            basicItem(ItemRegistry.CALCIUM_RICH_MILK.getId());
//            basicItem(ItemRegistry.VOID_CARROT.getId());
//            basicItem(ItemRegistry.WEAKNESS_POWDER.getId());
//            basicItem(ItemRegistry.IRON_CLAD_APPLE.getId());
//            basicItem(ItemRegistry.GOLD_STUDDED_APPLE.getId());
//            basicItem(ItemRegistry.BLESSED_STEAK.getId());
//            basicItem(ItemRegistry.GREEDY_OFFERING.getId());
//            basicItem(ItemRegistry.DEFILED_OFFERING.getId());
//            basicItem(ItemRegistry.DOLPHIN_COOKIE.getId());
//            basicItem(ItemRegistry.OMINOUS_FLAG.getId());
//            basicItem(ItemRegistry.MILK_GELATIN.getId());
//
//            for (DeferredHolder<Sugar, ?> sugar : Sugars.SUGARS.getEntries()) {
//                sugarModel(sugar);
//            }
//
//            Identifier id = ItemRegistry.MILK_EXTRACTOR.getId();
//            basicItem(id.withSuffix("_empty"));
//            getBuilder(id.toString())
//                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
//                    .texture("layer0", id.withPrefix("item/").withSuffix("_full"))
//                    .override()
//                    .model(new ModelFile.UncheckedModelFile(id.withPrefix("item/").withSuffix("_empty")))
//                    .predicate(
//                            ModelHandler.BROKEN,
//                            1.0f
//                    ).end();
//
//            basicItem(ItemRegistry.CARAMETAL.getId());
//            basicItem(ItemRegistry.NETHER_SMITHING_WAFER.getId());
//            basicItem(ItemRegistry.ENDER_SMITHING_WAFER.getId());
//         }
//     }
//
//    public static class Block extends BlockStateProvider
//    {
//        public Block(PackOutput output, ExistingFileHelper existingFileHelper) {
//            super(output, CandyWorkshop.MODID, existingFileHelper);
//        }
//
//        @Override
//        protected void registerStatesAndModels() {
//            ModelFile sugarRefinery = models().withExistingParent("sugar_refinery", modLoc("block/sugar_refinery"));
//            ModelFile plainDrawerTable = models().withExistingParent("plain_drawer_table",
//                                                                     modLoc("block/plain_drawer_table")
//            );
//            ModelFile drawerTable = models().withExistingParent("drawer_table", modLoc("block/drawer_table"));
//
//            horizontalBlock(BlockRegistry.SUGAR_REFINERY.get(), sugarRefinery);
//            horizontalBlock(BlockRegistry.PLAIN_DRAWER_TABLE.get(), plainDrawerTable);
//            horizontalBlock(BlockRegistry.DRAWER_TABLE.get(), drawerTable);
//        }
//    }
 }
