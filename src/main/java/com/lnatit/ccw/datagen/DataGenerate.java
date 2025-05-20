package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CandyWorkshop.MODID)
public class DataGenerate
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeClient(),
                new ModModelProvider.Block(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new ModModelProvider.Item(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new ModEN_USProvider(output)
        );
        generator.addProvider(
                event.includeClient(),
                new ModSoundProvider(output, existingFileHelper)
        );

        generator.addProvider(
                event.includeServer(),
                new ModAdvcmtProvider(output, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModRecipeProvider(output, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModLootProvider(output, lookupProvider)
        );
        var blockTags = new ModTagProvider.Blocks(output, lookupProvider, existingFileHelper);
        generator.addProvider(
                event.includeServer(),
                blockTags

        );
        generator.addProvider(
                event.includeServer(),
                new ModTagProvider.Items(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper)
        );

    }
}
