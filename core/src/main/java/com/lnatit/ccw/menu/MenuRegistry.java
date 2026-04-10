package com.lnatit.ccw.menu;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MenuRegistry {
    DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CandyWorkshop.MODID);

    DeferredHolder<MenuType<?>, MenuType<SugarRefineryMenu>> SUGAR_REFINERY =
            MENUS.register("sugar_refinery", () -> new MenuType<>(SugarRefineryMenu::new, FeatureFlags.DEFAULT_FLAGS));
    DeferredHolder<MenuType<?>, MenuType<DrawerTableMenu>> DRAWER_TABLE =
            MENUS.register("drawer_table", () -> new MenuType<>(DrawerTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
    DeferredHolder<MenuType<?>, MenuType<GummyContentMenu>> GUMMY_MAGAZINE =
            MENUS.register("gummy_magazine", () -> IMenuTypeExtension.create(GummyContentMenu::ofMagazine));
    DeferredHolder<MenuType<?>, MenuType<GummyContentMenu>> GUMMY_GLAZER =
            MENUS.register("gummy_glazer", () -> IMenuTypeExtension.create(GummyContentMenu::ofGlazer));
}
