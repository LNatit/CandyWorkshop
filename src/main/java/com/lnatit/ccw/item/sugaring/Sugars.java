package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.RegistryRegistry;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.numeric.CompassAngle;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Holder;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Sugars
{
    public static final DeferredRegister<Sugar> SUGARS =
            DeferredRegister.create(RegistryRegistry.SUGAR_KEY, CandyWorkshop.MODID);

    public static final Holder<Sugar> VANILLA = register("vanilla", null, 0);

    public static final Holder<Sugar> SPEED = register("speed", Potions.SWIFTNESS, 600);

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static DeferredHolder<Sugar, Sugar> register(String id, Holder<Potion> potion, int duration) {
        return SUGARS.register(id, () -> new Sugar(id, potion, duration));
    }
}
