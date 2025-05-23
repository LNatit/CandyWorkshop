package com.lnatit.ccw.menu;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, CandyWorkshop.MODID);

    public static final RegistryObject<MenuType<SugarRefineryMenu>> SUGAR_REFINERY =
            MENUS.register("sugar_refinery", () -> new MenuType<>(SugarRefineryMenu::new, FeatureFlags.DEFAULT_FLAGS));
    public static final RegistryObject<MenuType<DrawerTableMenu>> DRAWER_TABLE =
            MENUS.register("drawer_table", () -> new MenuType<>(DrawerTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
