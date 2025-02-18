package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CapRegistry {
    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockRegistry.SUGAR_REFINERY_BETYPE.get(),
                (be, side) -> be.getInventory()
        );
    }
}
