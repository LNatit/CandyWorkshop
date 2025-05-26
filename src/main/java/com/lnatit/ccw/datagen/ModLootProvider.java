package com.lnatit.ccw.datagen;

import com.lnatit.ccw.block.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

public class ModLootProvider extends LootTableProvider {
    public ModLootProvider(PackOutput output) {
        super(output, Set.of(), List.of(BlockLoots.getEntry()));
    }

    public static class BlockLoots extends BlockLootSubProvider {
        protected BlockLoots() {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
        }

        @Override
        protected void generate() {
            this.dropSelf(BlockRegistry.SUGAR_REFINERY.get());
            this.dropSelf(BlockRegistry.PLAIN_DRAWER_TABLE.get());
            this.dropSelf(BlockRegistry.DRAWER_TABLE.get());
        }

        public static LootTableProvider.SubProviderEntry getEntry() {
            return new SubProviderEntry(BlockLoots::new, LootContextParamSets.BLOCK);
        }
    }
}
