package com.lnatit.ccw.datagen;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.Damage;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        generateModBlockState(blockModels, BlockRegistry.SUGAR_REFINERY);
        generateModBlockState(blockModels, BlockRegistry.PLAIN_DRAWER_TABLE);
        generateModBlockState(blockModels, BlockRegistry.DRAWER_TABLE);

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
        Identifier empty = id.withSuffix("_empty");
        Identifier full = id.withSuffix("_full");
        clientItemModels.gen().withId(empty).modelOnly();
        clientItemModels.gen().withId(full).modelOnly();

        itemModels.itemModelOutput.accept(
                ItemRegistry.MILK_EXTRACTOR.get(),
                new RangeSelectItemModel.Unbaked(
                        Optional.empty(),
                        new Damage(false),
                        1,
                        List.of(
                                new RangeSelectItemModel.Entry(
                                        128,
                                        new CuboidItemModelWrapper.Unbaked(
                                                empty.withPrefix("item/"),
                                                Optional.empty(),
                                                Collections.emptyList()
                                        )
                                )
                        ),
                        Optional.of(
                                new CuboidItemModelWrapper.Unbaked(
                                        full.withPrefix("item/"),
                                        Optional.empty(),
                                        Collections.emptyList()
                                )
                        )
                )
        );

        clientItemModels.gen().withId(ItemRegistry.CARAMETAL).all();
        clientItemModels.gen().withId(ItemRegistry.NETHER_SMITHING_WAFER).all();
        clientItemModels.gen().withId(ItemRegistry.ENDER_SMITHING_WAFER).all();

        clientItemModels.gen().withId(ItemRegistry.GUMMY_MAGAZINE).clientItemOnly();
        clientItemModels.gen().withId(ItemRegistry.NETHER_MAGAZINE).clientItemOnly();
        clientItemModels.gen().withId(ItemRegistry.ENDER_MAGAZINE).clientItemOnly();

        clientItemModels.gen().withId(ItemRegistry.GUMMY_GLAZER).clientItemOnly();
        clientItemModels.gen().withId(ItemRegistry.NETHER_GLAZER).clientItemOnly();
        clientItemModels.gen().withId(ItemRegistry.ENDER_GLAZER).clientItemOnly();
    }

    private static void generateModBlockState(BlockModelGenerators blockModels, DeferredBlock<? extends Block> block) {
        Identifier modelLoc = block.getId().withPrefix("block/");
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block.get(),
                                               BlockModelGenerators.variant(new Variant(modelLoc)))
                                     .with(
                                             PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
                                                             .select(Direction.NORTH, BlockModelGenerators.NOP)
                                                             .select(Direction.EAST, BlockModelGenerators.Y_ROT_90)
                                                             .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
                                                             .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)
                                     )
        );
        blockModels.registerSimpleItemModel(block.get(), modelLoc);
    }
}
