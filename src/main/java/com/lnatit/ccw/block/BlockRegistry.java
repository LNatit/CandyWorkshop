package com.lnatit.ccw.block;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final TagKey<Block> DRAWER_TABLE_TAG = tag("drawer_table");

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CandyWorkshop.MODID);

    public static final DeferredBlock<SugarRefineryBlock> SUGAR_REFINERY =
            BLOCKS.registerBlock("sugar_refinery",
                    SugarRefineryBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .strength(1.5F)
                            .noOcclusion()
            );
    public static final DeferredBlock<DrawerTableBlock> PLAIN_DRAWER_TABLE =
            BLOCKS.registerBlock("plain_drawer_table",
                    DrawerTableBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(2.5F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
            );
    public static final DeferredBlock<DrawerTableBlock> DRAWER_TABLE =
            BLOCKS.registerBlock("drawer_table",
                    DrawerTableBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(2.5F)
                            .sound(SoundType.WOOD)
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
                            PLAIN_DRAWER_TABLE.get(),
                            DRAWER_TABLE.get()
                    )
            );

    private static TagKey<Block> tag(String namespace, String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

    private static TagKey<Block> tag(String name) {
        return tag(CandyWorkshop.MODID, name);
    }
}
