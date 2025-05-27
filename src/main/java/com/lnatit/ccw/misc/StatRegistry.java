package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class StatRegistry {
    public static final DeferredRegister<ResourceLocation> STATS =
            DeferredRegister.create(Registries.CUSTOM_STAT, CandyWorkshop.MODID);

    public static final RegistryObject<ResourceLocation> INTERACT_WITH_SUGAR_REFINERY =
            STATS.register(
                    "interact_with_sugar_refinery",
                    () -> new ResourceLocation(CandyWorkshop.MODID, "interact_with_sugar_refinery")
            );
    public static final RegistryObject<ResourceLocation> OPEN_DRAWER_TABLE =
            STATS.register(
                    "open_drawer_table",
                    () -> new ResourceLocation(CandyWorkshop.MODID, "open_drawer_table")
            );
}
