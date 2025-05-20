package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CandyWorkshop.MODID)
public class DataGenerate
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        event.createProvider(ModAdvcmtProvider::new);
        event.createProvider(ModEN_USProvider::new);
        event.createProvider(ModLootProvider::new);
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModSoundProvider::new);
        event.createBlockAndItemTags(ModTagProvider.Blocks::new, ModTagProvider.Items::new);
    }
}
