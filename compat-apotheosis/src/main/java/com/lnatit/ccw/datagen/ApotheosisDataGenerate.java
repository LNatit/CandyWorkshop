package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.ApotheosisCompats;
import com.lnatit.ccw.data.Effect;
import dev.shadowsoffire.apothic_attributes.api.ALObjects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class ApotheosisDataGenerate {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                         .loaded("apotheosis")
                         .register(ApotheosisCompats.GRIEVOUS, Effect.simple(ALObjects.MobEffects.GRIEVOUS))
                         .defaultExcited()
                         .defaultBold()

                         .register(ApotheosisCompats.KNOWLEDGE, Effect.simple(ALObjects.MobEffects.KNOWLEDGE))
                         .defaultExcited()
                         .defaultBold()

                         .register(ApotheosisCompats.SUNDERING, Effect.simple(ALObjects.MobEffects.SUNDERING))
                         .defaultExcited()
                         .defaultBold()

                         .register(ApotheosisCompats.VITALITY, Effect.simple(ALObjects.MobEffects.VITALITY))
                         .defaultExcited()
                         .defaultBold()
                         .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeClient(),
                new ApotheosisModelProvider(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new ApotheosisEN_USProvider(output)
        );
        generator.addProvider(
                event.includeServer(),
                new ApotheosisRecipeProvider(output, lookupProvider)
        );
    }
}

