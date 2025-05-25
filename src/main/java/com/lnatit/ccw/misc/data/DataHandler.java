package com.lnatit.ccw.misc.data;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CandyWorkshop.MODID)
public class DataHandler
{
    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        // TODO need?
//        if (event.isWasDeath() && event.getOriginal().hasData(SUGAR_STAT)) {
//            event.getEntity().setData(SUGAR_STAT, event.getOriginal().getData(SUGAR_STAT));
//        }
    }
}
