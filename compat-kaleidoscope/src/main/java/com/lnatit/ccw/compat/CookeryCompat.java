package com.lnatit.ccw.compat;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Flavor;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

public class CookeryCompat
{
    public static final DeferredHolder<Flavor, NutritiousFlavor> NUTRITIOUS =
            Flavors.FLAVORS.register("nutritious", NutritiousFlavor::new);

    public static final DeferredItem<Item> SWEET_AND_SOUR_MEAT_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("sweet_and_sour_meat_sauce");
    public static final DeferredItem<Item> FRESH_VEGGIE_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("fresh_veggie_sauce");
    public static final DeferredItem<Item> LACTO_BOLT_RED_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("lacto_bolt_red_sauce");
    public static final DeferredItem<Item> SASHIMI_SIDE_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("sashimi_side_sauce");
    public static final DeferredItem<Item> ULTRA_RICH_MARROW_CREAM =
            ItemRegistry.ITEMS.registerSimpleItem("ultra_rich_marrow_cream");
    public static final DeferredItem<Item> SILENCED_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("silenced_sauce");
    public static final DeferredItem<Item> HEARTH_WARM_MARROW_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("hearth_warm_marrow_cream");
    public static final DeferredItem<Item> LAMB_CARROT_SAUCE =
            ItemRegistry.ITEMS.registerSimpleItem("lamb_carrot_sauce");

    public static final DeferredHolder<Sugar, Sugar> SWEET_AND_SOUR_MEAT = Sugars.SUGARS.register("sweet_and_sour_meat",
                                                                                                  () -> new Sugar(Sugar.Type.NETHER,
                                                                                                                  Ingredient.of(
                                                                                                                          SWEET_AND_SOUR_MEAT_SAUCE.get())));
    public static final DeferredHolder<Sugar, Sugar> VEGGIE_FRESH = Sugars.SUGARS.register("veggie_fresh",
                                                                                           () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                                           Ingredient.of(
                                                                                                                   FRESH_VEGGIE_SAUCE.get())));
    public static final DeferredHolder<Sugar, Sugar> LACTO_BOLT = Sugars.SUGARS.register("lacto_bolt",
                                                                                          () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                                          Ingredient.of(
                                                                                                                  LACTO_BOLT_RED_SAUCE.get())));
    public static final DeferredHolder<Sugar, Sugar> SASHIMI = Sugars.SUGARS.register("sashimi",
                                                                                      () -> new Sugar(Sugar.Type.NETHER,
                                                                                                      Ingredient.of(
                                                                                                              SASHIMI_SIDE_SAUCE.get())));
    public static final DeferredHolder<Sugar, Sugar> TALLOW = Sugars.SUGARS.register("tallow",
                                                                                     () -> new Sugar(Sugar.Type.END,
                                                                                                     Ingredient.of(
                                                                                                             ULTRA_RICH_MARROW_CREAM.get())));
    public static final DeferredHolder<Sugar, Sugar> PHANTO_BYE = Sugars.SUGARS.register("phanto_bye",
                                                                                         () -> new Sugar(Sugar.Type.NETHER,
                                                                                                         Ingredient.of(
                                                                                                                 SILENCED_SAUCE.get())));
    public static final DeferredHolder<Sugar, Sugar> BROTH_WARM = Sugars.SUGARS.register("broth_warm",
                                                                                         () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                                         Ingredient.of(
                                                                                                                 HEARTH_WARM_MARROW_SAUCE.get())));
    public static final DeferredHolder<Sugar, Sugar> MUTTON_DASH = Sugars.SUGARS.register("mutton_dash",
                                                                                          () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                                          Ingredient.of(
                                                                                                                  LAMB_CARROT_SAUCE.get())));

    public static void init() {
    }
}
