package com.lnatit.ccw.misc.critereon;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class CriteriaEventHandler
{
    @SubscribeEvent
    public static void onStatAward(StatAwardEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getStat().getType() == Stats.ITEM_USED && event.getStat().getValue() == ItemRegistry.GUMMY_ITEM.get()) {
            // The entity in is always ServerPlayer
            CriteriaRegistry.DEVELOP_DIABETES.get().trigger(player, event.getValue());
        }
    }
}
