package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CandyWorkshop.MODID)
public class CapRegistry {
    @SubscribeEvent
    public static void attachCapabilities(final AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof SugarRefineryBlockEntity inst)
        {
            event.addCapability(BlockRegistry.SUGAR_REFINERY_BETYPE.getId(), inst);
        }

        if (event.getObject() instanceof DrawerTableBlockEntity inst)
        {
            event.addCapability(BlockRegistry.DRAWER_TABLE_BETYPE.getId(), inst);
        }
    }
}
