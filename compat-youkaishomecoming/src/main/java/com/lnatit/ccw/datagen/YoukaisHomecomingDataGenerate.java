package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.YoukaisHomecomingCompats;
import com.lnatit.ccw.data.Effect;
import dev.xkmc.youkaishomecoming.init.registrate.YHEffects;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class YoukaisHomecomingDataGenerate {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                         .loaded("youkaishomecoming")
                         .register(YoukaisHomecomingCompats.GREEN_TEA,
                                            new Effect(YHEffects.TEA, 600, 1),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisHomecomingCompats.WHITE_TEA,
                                            Effect.simple(YHEffects.TEA),
                                            Effect.simple(YHEffects.REFRESHING),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisHomecomingCompats.BLACK_TEA,
                                            Effect.simple(YHEffects.TEA),
                                            Effect.simple(YHEffects.THICK),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisHomecomingCompats.OOLONG_TEA,
                                            Effect.simple(YHEffects.TEA),
                                            Effect.simple(YHEffects.SMOOTHING),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisHomecomingCompats.YOUKAI_COFFEE,
                                            Effect.simple(YHEffects.CAFFEINATED),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisHomecomingCompats.UDUMBARA, Effect.simple(YHEffects.UDUMBARA))
                         .defaultBold()
                         .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new CoreEN_USProvider(output));
        generator.addProvider(event.includeClient(), new YoukaisHomecomingModelProvider(output, existingFileHelper));
    }
}

