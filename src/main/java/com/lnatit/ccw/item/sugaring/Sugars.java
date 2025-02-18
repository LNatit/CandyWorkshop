package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Sugars
{
    public static final DeferredRegister<Sugar> SUGARS =
            DeferredRegister.create(RegRegistry.SUGAR_KEY, CandyWorkshop.MODID);

//    public static final DeferredHolder<Sugar, Sugar> VANILLA = register("vanilla", null, 0);

    public static final DeferredHolder<Sugar, Sugar> SPEED =
            register("speed", Potions.SWIFTNESS, Potions.STRONG_SWIFTNESS, Potions.LONG_SWIFTNESS, 600);

    public static void register(IEventBus eventBus) {
        SUGARS.register(eventBus);
    }

    private static DeferredHolder<Sugar, Sugar> register(String id, Holder<Potion> base, Holder<Potion> excited, Holder<Potion> bold, int duration) {
        return SUGARS.register(id, () -> new Sugar(id, base, excited, bold, duration));
    }
}
