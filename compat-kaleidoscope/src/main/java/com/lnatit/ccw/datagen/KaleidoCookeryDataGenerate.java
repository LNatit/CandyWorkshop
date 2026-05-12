package com.lnatit.ccw.datagen;

import com.github.ysbbbbbb.kaleidoscopecookery.init.ModEffects;
import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.CookeryCompat;
import com.lnatit.ccw.data.Effect;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class KaleidoCookeryDataGenerate {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                .loaded("kaleidoscope_cookery")
                .register(CookeryCompat.SWEET_AND_SOUR_MEAT, Effect.simple(ModEffects.VIGOR))
                .defaultBold()

                .register(CookeryCompat.VEGGIE_FRESH, Effect.simple(ModEffects.PRESERVATION))
                .defaultBold()

                .register(CookeryCompat.LACTO_BOLT, Effect.simple(ModEffects.FLATULENCE))
                .defaultBold()

                .register(CookeryCompat.SASHIMI, Effect.simple(ModEffects.MUSTARD))
                .defaultBold()

                .register(CookeryCompat.TALLOW, Effect.simple(ModEffects.SATIATED_SHIELD))
                .defaultBold()

                .register(CookeryCompat.PHANTO_BYE, Effect.simple(ModEffects.SULFUR))
                .defaultBold()

                .register(CookeryCompat.BROTH_WARM, Effect.simple(ModEffects.WARMTH))
                .defaultBold()

                .register(CookeryCompat.MUTTON_DASH, Effect.simple(ModEffects.TUNDRA_STRIDER))
                .defaultBold()
                .clearConditions();

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new KaleidoCookeryEN_USProvider(output));
        generator.addProvider(event.includeClient(), new KaleidoCookeryModelProvider(output, existingFileHelper));
    }
}

