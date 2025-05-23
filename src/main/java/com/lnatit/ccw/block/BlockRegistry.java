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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Function;

public class BlockRegistry {
    public static final TagKey<Block> DRAWER_TABLE_TAG = tag("drawer_table");

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, CandyWorkshop.MODID);

    public static final RegistryObject<SugarRefineryBlock> SUGAR_REFINERY =
            registerBlock("sugar_refinery",
                    SugarRefineryBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .strength(1.5F)
                            .noOcclusion()
            );
    public static final RegistryObject<DrawerTableBlock> PLAIN_DRAWER_TABLE =
            registerBlock("plain_drawer_table",
                    DrawerTableBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(2.5F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
            );
    public static final RegistryObject<DrawerTableBlock> DRAWER_TABLE =
            registerBlock("drawer_table",
                    DrawerTableBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(2.5F)
                            .sound(SoundType.WOOD)
                            .noOcclusion()
            );

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CandyWorkshop.MODID);

    public static final RegistryObject<BlockEntityType<SugarRefineryBlockEntity>> SUGAR_REFINERY_BETYPE =
            BLOCK_ENTITIES.register("sugar_refinery",
                    () -> new BlockEntityType<>(
                            SugarRefineryBlockEntity::new,
                            Set.of(SUGAR_REFINERY.get()),
                            null
                    )
            );
    public static final RegistryObject<BlockEntityType<DrawerTableBlockEntity>> DRAWER_TABLE_BETYPE =
            BLOCK_ENTITIES.register("drawer_table",
                    () -> new BlockEntityType<>(
                            DrawerTableBlockEntity::new,
                            Set.of(PLAIN_DRAWER_TABLE.get(), DRAWER_TABLE.get()),
                            null
                    )
            );

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        return BLOCKS.register(name, () -> block.apply(properties));
    }

    private static TagKey<Block> tag(String namespace, String name) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

    private static TagKey<Block> tag(String name) {
        return tag(CandyWorkshop.MODID, name);
    }
}
