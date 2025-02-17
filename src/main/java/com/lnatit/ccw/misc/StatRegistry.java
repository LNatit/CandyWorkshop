package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StatRegistry {
    public static final DeferredRegister<ResourceLocation> STATS =
            DeferredRegister.create(Registries.CUSTOM_STAT, CandyWorkshop.MODID);

    public static final DeferredHolder<ResourceLocation, ResourceLocation> INTERACT_WITH_SUGAR_REFINERY =
            STATS.register(
                    "interact_with_sugar_refinery",
                    () -> ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "interact_with_sugar_refinery")
            );
}
