package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

/**
 * Registers core shared datapack providers used by all datagen runs.
 */
@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class CoreDataGenerate
{
    // Use lowest so that we can init those datapacks
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeDev(),
                new CoreDataProviders(output, lookupProvider)
        );
    }
}

