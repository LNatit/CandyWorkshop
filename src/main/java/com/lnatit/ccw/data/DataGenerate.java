package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = CandyWorkshop.MODID)
public class DataGenerate
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(ModModelProvider::new);
    }
}
