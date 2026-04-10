package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface Flavors {
    DeferredRegister<Flavor> FLAVORS = DeferredRegister.create(RegRegistry.FLAVOR, CandyWorkshop.MODID);

    DeferredHolder<Flavor, Flavor> ORIGINAL = FLAVORS.register("original", () -> Flavor.ORIGINAL);
    DeferredHolder<Flavor, SimpleFlavor> EXCITED = FLAVORS.register("excited", () -> new SimpleFlavor(43520, Ingredient.of(Items.COCOA_BEANS)));
    DeferredHolder<Flavor, SimpleFlavor> BOLD = FLAVORS.register("bold", () -> new SimpleFlavor(16755200, Ingredient.of(Items.HONEY_BOTTLE)));
    DeferredHolder<Flavor, MilkyFlavor> MILKY = FLAVORS.register("milky", MilkyFlavor::new);

    static Holder<Flavor> byName(String name) {
        // since old version only have these four flavors
        return switch (name) {
            case "excited" -> EXCITED;
            case "bold" -> BOLD;
            case "milky" -> MILKY;
            default -> ORIGINAL;
        };
    }
}
