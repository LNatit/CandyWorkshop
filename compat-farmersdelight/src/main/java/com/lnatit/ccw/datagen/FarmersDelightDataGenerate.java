package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.FarmersDelightCompats;
import com.lnatit.ccw.data.Effect;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class FarmersDelightDataGenerate {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                .loaded("farmersdelight")
                .register(FarmersDelightCompats.NOURISHED, Effect.simple(ModEffects.NOURISHMENT))
                .defaultBold()
                .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new FarmersDelightEN_USProvider(output));
        generator.addProvider(event.includeClient(), new FarmersDelightModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new FarmersDelightRecipeProvider(output, lookupProvider));

        var blockTags = new FarmersDelightTagProvider.BlockTagsCompat(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(
                event.includeServer(),
                new FarmersDelightTagProvider.ItemTagsCompat(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper)
        );
    }
}

