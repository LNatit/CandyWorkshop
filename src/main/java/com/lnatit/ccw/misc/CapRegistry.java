package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CandyWorkshop.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapRegistry {
    @SubscribeEvent
    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockRegistry.SUGAR_REFINERY_BETYPE.get(),
                SugarRefineryBlockEntity::accessInventory
        );

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockRegistry.DRAWER_TABLE_BETYPE.get(),
                DrawerTableBlockEntity::accessInventory
        );
    }
}
