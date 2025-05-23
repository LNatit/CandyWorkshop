package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.menu.MenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CandyWorkshop.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ScreenRegistry
{
    @SubscribeEvent
    public static void registerScreens(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(MenuRegistry.SUGAR_REFINERY.get(), SugarRefineryScreen::new);
            MenuScreens.register(MenuRegistry.DRAWER_TABLE.get(), DrawerTableScreen::new);
        });
    }
}
