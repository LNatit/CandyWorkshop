package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.FruitsDelightCompats;
import com.lnatit.ccw.data.Effect;
import dev.xkmc.fruitsdelight.init.registrate.FDEffects;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class FruitsDelightDataGenerate {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                         .loaded("fruitsdelight")
                         .register(FruitsDelightCompats.BLUEBERRY, Effect.simple(FDEffects.BRIGHTENING))
                         .defaultBold()

                         .register(FruitsDelightCompats.HAWTHORN, Effect.simple(FDEffects.APPETIZING))
                         .defaultBold()

                         .register(FruitsDelightCompats.MANGO, Effect.simple(FDEffects.RAGE_AURA))
                         .defaultBold()

                         .register(FruitsDelightCompats.ORANGE, Effect.simple(FDEffects.RECOVERING))
                         .defaultBold()

                         .register(FruitsDelightCompats.PEACH, Effect.simple(FDEffects.HEAL_AURA))
                         .defaultBold()

                         .register(FruitsDelightCompats.PEAR, Effect.simple(FDEffects.LOZENGE))
                         .defaultBold()

                         .register(FruitsDelightCompats.PERSIMMON, Effect.simple(FDEffects.ASTRINGENT))
                         .defaultBold()

                         .register(FruitsDelightCompats.PINEAPPLE, Effect.simple(FDEffects.SWEETENING))
                         .defaultBold()

                         .register(FruitsDelightCompats.LEMON, Effect.simple(FDEffects.REFRESHING))
                         .defaultBold()

                         .register(FruitsDelightCompats.CRANBERRY, Effect.simple(FDEffects.SHRINKING))
                         .defaultBold()

                         .register(FruitsDelightCompats.MANGOSTEEN, Effect.simple(FDEffects.SLIDING))
                         .defaultBold()

                         .register(FruitsDelightCompats.CHORUS, Effect.simple(FDEffects.CHORUS))
                         .defaultBold()

                         .register(FruitsDelightCompats.BAYBERRY, Effect.simple(FDEffects.LEAF_PIERCING))
                         .defaultBold()

                         .register(FruitsDelightCompats.KIWI, Effect.simple(FDEffects.CYCLING))
                         .defaultBold()

                         .register(FruitsDelightCompats.FIG, Effect.simple(FDEffects.DIGESTING))
                         .defaultBold()

                         .register(FruitsDelightCompats.DURIAN,
                                            Effect.simple(FDEffects.ALIENATING),
                                            Effect.simple(FDEffects.SUSPICIOUS_SMELL))
                         .defaultBold()
                         .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new CoreEN_USProvider(output));
        generator.addProvider(event.includeClient(), new FruitsDelightModelProvider(output, existingFileHelper));
    }
}

