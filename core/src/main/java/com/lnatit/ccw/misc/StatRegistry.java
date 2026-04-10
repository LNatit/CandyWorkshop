package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface StatRegistry {
    DeferredRegister<ResourceLocation> STATS =
            DeferredRegister.create(Registries.CUSTOM_STAT, CandyWorkshop.MODID);

    DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_SUGAR_REFINERY =
            STATS.register(
                    "interact_with_sugar_refinery",
                    () -> CandyWorkshop.id("interact_with_sugar_refinery")
            );
    DeferredHolder<ResourceLocation, ResourceLocation> OPEN_DRAWER_TABLE =
            STATS.register(
                    "open_drawer_table",
                    () -> CandyWorkshop.id("open_drawer_table")
            );
}
