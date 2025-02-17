package com.lnatit.ccw;

import com.lnatit.ccw.item.sugaring.Sugar;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = CandyWorkshop.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegistryRegistry
{
    public static final ResourceKey<Registry<Sugar>> SUGAR_KEY = ResourceKey.createRegistryKey(
            ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "sugar"));

    public static final Registry<Sugar> SUGAR = new RegistryBuilder<>(SUGAR_KEY)
            .sync(true)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "vanilla"))
            .create();

    @SubscribeEvent
    public static void registerRegistry(final NewRegistryEvent event) {
        event.register(SUGAR);
    }
}
