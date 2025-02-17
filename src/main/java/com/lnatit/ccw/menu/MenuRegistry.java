package com.lnatit.ccw.menu;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CandyWorkshop.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SugarRefineryMenu>> SUGAR_REFINERY =
            MENUS.register("sugar_refinery", () -> new MenuType<>(SugarRefineryMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
