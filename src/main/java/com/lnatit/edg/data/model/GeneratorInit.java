package com.lnatit.edg.data.model;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface GeneratorInit
{
    GeneratorImpl withId(ResourceLocation id);

    default GeneratorImpl withId(DeferredHolder<?, ?> registryItem) {
        return this.withId(registryItem.getId());
    }
}
