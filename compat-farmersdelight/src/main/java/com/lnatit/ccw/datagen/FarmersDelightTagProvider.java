package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.FarmersDelightCompats;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.resources.Identifier;

import java.util.concurrent.CompletableFuture;

public class FarmersDelightTagProvider {
    public static class BlockTagsCompat extends BlockTagsProvider {
        public BlockTagsCompat(
                PackOutput output,
                CompletableFuture<HolderLookup.Provider> lookupProvider,
                ExistingFileHelper existingFileHelper
        ) {
            super(output, lookupProvider, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
        }
    }

    public static class ItemTagsCompat extends ItemTagsProvider {
        public ItemTagsCompat(
                PackOutput output,
                CompletableFuture<HolderLookup.Provider> lookupProvider,
                CompletableFuture<TagLookup<Block>> blockTags,
                ExistingFileHelper existingFileHelper
        ) {
            super(output, lookupProvider, blockTags, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ItemTags.create(Identifier.fromNamespaceAndPath("farmersdelight", "meals")))
                    .add(FarmersDelightCompats.GLAZED_MEAT_RICE.get());
        }
    }
}


