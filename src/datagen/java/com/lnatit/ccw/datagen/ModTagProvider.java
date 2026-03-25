package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.compat.farmersdelight.FarmersDelightCompats;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModTagProvider
{
    public static class BlockTags extends BlockTagsProvider
    {
        public BlockTags(
                PackOutput output,
                CompletableFuture<HolderLookup.Provider> lookupProvider,
                ExistingFileHelper existingFileHelper
        ) {
            super(output, lookupProvider, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(BlockRegistry.DRAWER_TABLE_TAG)
                .add(BlockRegistry.PLAIN_DRAWER_TABLE.get())
                .add(BlockRegistry.DRAWER_TABLE.get());
        }
    }

    public static class ItemTags extends ItemTagsProvider
    {
        public ItemTags(
                PackOutput output,
                CompletableFuture<HolderLookup.Provider> lookupProvider,
                CompletableFuture<TagLookup<Block>> blockTags,
                ExistingFileHelper existingFileHelper
        ) {
            super(output, lookupProvider, blockTags, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ItemRegistry.FOODS_MILK_TAG)
                .add(ItemRegistry.CARTON_MILK.get())
                .add(ItemRegistry.CALCIUM_RICH_MILK.get());

            this.tag(ItemRegistry.DRINKS_MILK_TAG)
                .add(ItemRegistry.CARTON_MILK.get())
                .add(ItemRegistry.CALCIUM_RICH_MILK.get());

            this.tag(ItemRegistry.CARTON_MILK_TAG)
                .add(ItemRegistry.CARTON_MILK.get())
                .add(ItemRegistry.CALCIUM_RICH_MILK.get())
//                .addOptional(ResourceLocation.parse("kitchenkarrot:milk"))
            ;

            this.tag(ItemRegistry.OVERWORLD_SUGAR_TAG)
                .add(net.minecraft.world.item.Items.SUGAR);

            this.tag(ItemRegistry.NETHER_SUGAR_TAG)
                .add(ItemRegistry.NETHER_SUGAR.get());

            this.tag(ItemRegistry.ENDER_SUGAR_TAG)
                .add(ItemRegistry.ENDER_SUGAR.get());


            this.tag(net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("farmersdelight", "meals")))
                .add(FarmersDelightCompats.GLAZED_MEAT_RICE.get())
                .add(FarmersDelightCompats.SWEET_HARVEST_SOUP.get());
        }
    }
}
