package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public interface DataPackRegistry
{
    @SubscribeEvent
    static void onDataPackRegister(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Formula.KEY, Formula.CODEC, Formula.CODEC, builder -> builder.onBake(Formula::rebuildCache));
    }

    static <T> ResourceKey<T> of(ResourceKey<Registry<T>> registry, String name) {
        return ResourceKey.create(registry, CandyWorkshop.id(name));
    }
}
