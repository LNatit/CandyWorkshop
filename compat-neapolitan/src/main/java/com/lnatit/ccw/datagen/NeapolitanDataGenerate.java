package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.NeapolitanCompats;
import com.lnatit.ccw.data.Effect;
import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class NeapolitanDataGenerate {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                         .loaded("neapolitan")
                         .register(NeapolitanCompats.HOOHOO_HAHA, Effect.simple(NeapolitanMobEffects.AGILITY))
                         .defaultBold()

                         .register(NeapolitanCompats.HOOHOO_SMOOTH, Effect.simple(NeapolitanMobEffects.SLIPPING))
                         .defaultBold()

                         .register(NeapolitanCompats.MINT, Effect.simple(NeapolitanMobEffects.BERSERKING))
                         .defaultBold()

                         .register(NeapolitanCompats.RED_BEAN, Effect.simple(NeapolitanMobEffects.HARMONY))
                         .defaultBold()

                         .register(NeapolitanCompats.VANILLA, Effect.simple(NeapolitanMobEffects.VANILLA_SCENT))
                         .defaultBold()

                         .register(NeapolitanCompats.SUGAR_RUSH, Effect.simple(NeapolitanMobEffects.SUGAR_RUSH))
                         .defaultExcited()
                         .defaultBold()
                         .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new CoreEN_USProvider(output));
        generator.addProvider(event.includeClient(), new NeapolitanModelProvider(output, existingFileHelper));
    }
}

