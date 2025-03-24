package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider
{
    public static class Blocks extends BlockTagsProvider
    {
        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, CandyWorkshop.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }

    public static class Items extends ItemTagsProvider
    {
        public Items(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
            super(output, lookupProvider, blockTags, CandyWorkshop.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ItemRegistry.MILK_TAG)
                .add(ItemRegistry.CARTON_MILK.get())
                .add(ItemRegistry.CALCIUM_RICH_MILK.get());

            this.tag(ItemRegistry.CARTON_MILK_TAG)
                .add(ItemRegistry.CARTON_MILK.get())
                .add(ItemRegistry.CALCIUM_RICH_MILK.get())
                .addOptional(ResourceLocation.parse("kitchenkarrot:milk"));
        }
    }
}
