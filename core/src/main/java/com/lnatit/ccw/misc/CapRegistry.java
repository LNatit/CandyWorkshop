package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public interface CapRegistry {
    @SubscribeEvent
    static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                BlockRegistry.SUGAR_REFINERY_BETYPE.get(),
                SugarRefineryBlockEntity::accessInventory
        );

        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                BlockRegistry.DRAWER_TABLE_BETYPE.get(),
                DrawerTableBlockEntity::accessInventory
        );
    }
}
