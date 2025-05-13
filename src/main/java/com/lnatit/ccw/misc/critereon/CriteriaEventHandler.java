package com.lnatit.ccw.misc.critereon;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.StatAwardEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class CriteriaEventHandler
{
    @SubscribeEvent
    public static void onStatAward(StatAwardEvent event) {
        if (event.getStat().getType() == Stats.ITEM_USED && event.getStat().getValue() == ItemRegistry.GUMMY_ITEM.get()) {
            // The entity in is always ServerPlayer
            CriteriaRegistry.DEVELOP_DIABETES.get().trigger((ServerPlayer) event.getEntity(), event.getValue());
        }
    }
}
