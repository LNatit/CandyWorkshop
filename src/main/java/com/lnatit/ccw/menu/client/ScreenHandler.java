package com.lnatit.ccw.menu.client;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CandyWorkshop.MODID)
public class ScreenHandler
{
    @SubscribeEvent
    public static void addAtlasSprite(TextureStitchEvent.Post event) {

    }
}
