package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class AttributeTooltipEventHandler {
    @SubscribeEvent
    public static void onItemTooltip(AddAttributeTooltipsEvent event) {
        event.getStack().addToTooltip(
                ItemRegistry.SUGAR_CONTENTS_DCTYPE.get(),
                event.getContext(),
                event::addTooltipLines,
                event.getContext().flag());
    }
}
