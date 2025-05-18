package com.lnatit.ccw.datagen;

import com.lnatit.ccw.block.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootProvider extends LootTableProvider {
    public ModLootProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(BlockLoots.getEntry()), registries);
    }

    public static class BlockLoots extends BlockLootSubProvider {
        protected BlockLoots(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return BlockRegistry.BLOCKS.getEntries().stream().map(e -> (Block) e.get()).toList();
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
