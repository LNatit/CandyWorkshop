package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugar;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = CandyWorkshop.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegRegistry
{
    public static final ResourceLocation SUGAR_LOCATION = ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "sugar");
    public static final ResourceKey<Registry<Sugar>> SUGAR_KEY = ResourceKey.createRegistryKey(SUGAR_LOCATION);
    public static final RegistryBuilder<Object> SUGAR_BUILDER = RegistryBuilder.of(SUGAR_LOCATION)
            .setDefaultKey(ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "vanilla"));
    public static IForgeRegistry<Sugar> SUGAR;

    @SubscribeEvent
    public static void registerRegistries(final NewRegistryEvent event) {
        SUGAR = (IForgeRegistry<Sugar>) (Object) event.create(SUGAR_BUILDER).get();
    }
}
