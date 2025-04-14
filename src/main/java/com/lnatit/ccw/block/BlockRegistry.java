package com.lnatit.ccw.block;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CandyWorkshop.MODID);

    public static final DeferredBlock<SugarRefineryBlock> SUGAR_REFINERY =
            BLOCKS.registerBlock("sugar_refinery",
                                 SugarRefineryBlock::new,
                                 BlockBehaviour.Properties.of()
                                                          .mapColor(MapColor.WOOD)
                                                          .requiresCorrectToolForDrops()
                                                          .strength(3.5F)
                                                          .noOcclusion()
            );

    public static final DeferredBlock<DrawerTableBlock> DRAWER_TABLE =
            BLOCKS.registerBlock("drawer_table",
                                 DrawerTableBlock::new,
                                 BlockBehaviour.Properties.of()
                                                          .mapColor(MapColor.WOOD)
                                                          .requiresCorrectToolForDrops()
                                                          .strength(3.5F)
                                                          .noOcclusion()
            );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CandyWorkshop.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SugarRefineryBlockEntity>> SUGAR_REFINERY_BETYPE =
            BLOCK_ENTITIES.register("sugar_refinery",
                                    () -> new BlockEntityType<>(
                                            SugarRefineryBlockEntity::new,
                                            SUGAR_REFINERY.get()
                                    )
            );

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DrawerTableBlockEntity>> DRAWER_TABLE_BETYPE =
            BLOCK_ENTITIES.register("drawer_table",
                                    () -> new BlockEntityType<>(
                                            DrawerTableBlockEntity::new,
                                            DRAWER_TABLE.get()
                                    )
            );
}
