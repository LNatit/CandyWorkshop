package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.MenuRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = CandyWorkshop.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ScreenRegistry
{
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.SUGAR_REFINERY.get(), SugarRefineryScreen::new);
        event.register(MenuRegistry.DRAWER_TABLE.get(), DrawerTableScreen::new);
    }
}
