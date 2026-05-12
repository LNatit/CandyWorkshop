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

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public interface RegRegistry
{
    ResourceKey<Registry<Sugar>> SUGAR_KEY = ResourceKey.createRegistryKey(
            CandyWorkshop.id("sugar"));
    ResourceKey<Registry<Flavor>> FLAVOR_KEY = ResourceKey.createRegistryKey(CandyWorkshop.id("flavor"));

    Registry<Sugar> SUGAR = new RegistryBuilder<>(SUGAR_KEY)
            .sync(true)
            .defaultKey(CandyWorkshop.id("vanilla"))
            .create();
    Registry<Flavor> FLAVOR = new RegistryBuilder<>(FLAVOR_KEY)
            .sync(true)
            .defaultKey(CandyWorkshop.id("original"))
            .onBake(Flavor::rebuildCache)
            .create();

    @SubscribeEvent
    static void registerRegistries(final NewRegistryEvent event) {
        event.register(SUGAR);
        event.register(FLAVOR);
    }
}
