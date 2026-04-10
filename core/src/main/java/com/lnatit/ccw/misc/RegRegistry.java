package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Flavor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

@EventBusSubscriber(modid = CandyWorkshop.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegRegistry
{
    public static final ResourceKey<Registry<Sugar>> SUGAR_KEY = ResourceKey.createRegistryKey(
            CandyWorkshop.id("sugar"));
    public static final ResourceKey<Registry<Flavor>> FLAVOR_KEY = ResourceKey.createRegistryKey(CandyWorkshop.id("flavor"));

    public static final Registry<Sugar> SUGAR = new RegistryBuilder<>(SUGAR_KEY)
            .sync(true)
            .defaultKey(CandyWorkshop.id("vanilla"))
            .create();
    public static final Registry<Flavor> FLAVOR = new RegistryBuilder<>(FLAVOR_KEY)
            .sync(true)
            .defaultKey(CandyWorkshop.id("original"))
            .onBake(Flavor::rebuildCache)
            .create();

    @SubscribeEvent
    public static void registerRegistries(final NewRegistryEvent event) {
        event.register(SUGAR);
        event.register(FLAVOR);
    }
}
