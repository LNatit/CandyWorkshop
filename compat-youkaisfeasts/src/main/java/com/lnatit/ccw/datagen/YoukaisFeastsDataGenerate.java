package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.YoukaisFeastsCompats;
import com.lnatit.ccw.data.Effect;
import dev.xkmc.youkaishomecoming.init.registrate.YHEffects;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class YoukaisFeastsDataGenerate {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                         .loaded("youkaisfeasts")
                         .register(YoukaisFeastsCompats.GREEN_TEA,
                                            new Effect(YHEffects.TEA, 600, 1),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisFeastsCompats.WHITE_TEA,
                                            Effect.simple(YHEffects.TEA),
                                            Effect.simple(YHEffects.REFRESHING),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisFeastsCompats.BLACK_TEA,
                                            Effect.simple(YHEffects.TEA),
                                            Effect.simple(YHEffects.THICK),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisFeastsCompats.OOLONG_TEA,
                                            Effect.simple(YHEffects.TEA),
                                            Effect.simple(YHEffects.SMOOTHING),
                                            Effect.simple(YHEffects.SOBER))
                         .defaultBold()

                         .register(YoukaisFeastsCompats.UDUMBARA, Effect.simple(YHEffects.UDUMBARA))
                         .defaultBold()
                         .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new CoreEN_USProvider(output));
        generator.addProvider(event.includeClient(), new YoukaisFeastsModelProvider(output, existingFileHelper));
    }
}

