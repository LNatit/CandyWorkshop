package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModClientItemExtension implements IClientItemExtensions
{
    private final GummyGlazerRenderer MOD_RENDERER = new GummyGlazerRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return MOD_RENDERER;
    }

    @SubscribeEvent
    public static void register(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new ModClientItemExtension(),
                ItemRegistry.GUMMY_GLAZER,
                ItemRegistry.NETHER_GLAZER,
                ItemRegistry.ENDER_GLAZER
        );
    }
}
