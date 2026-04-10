package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @see MobEffects
 * Excited: amplified
 * Bold: extended
 */
public interface Sugars
{
    DeferredRegister<Sugar> SUGARS = DeferredRegister.create(RegRegistry.SUGAR_KEY, CandyWorkshop.MODID);

    // Overworld blends
    DeferredHolder<Sugar, Sugar> SPEED = SUGARS.register("speed",
                                                         () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                         Ingredient.of(Items.SUGAR)));
    DeferredHolder<Sugar, Sugar> BUNNY = SUGARS.register("bunny",
                                                         () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                         Ingredient.of(Items.RABBIT_FOOT,
                                                                                       ItemRegistry.ENERGY_CARROT.get())));

    DeferredHolder<Sugar, Sugar> HEALING = SUGARS.register("healing",
                                                           () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                           Ingredient.of(Items.GLISTERING_MELON_SLICE)));
    DeferredHolder<Sugar, Sugar> POISON = SUGARS.register("poison",
                                                          () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                          Ingredient.of(Items.SPIDER_EYE)));
    DeferredHolder<Sugar, Sugar> PUFFERFISH = SUGARS.register("pufferfish",
                                                              () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                              Ingredient.of(Items.PUFFERFISH)));
    DeferredHolder<Sugar, Sugar> NIGHT_VISION = SUGARS.register("night_vision",
                                                                () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                Ingredient.of(Items.GOLDEN_CARROT)));
    DeferredHolder<Sugar, Sugar> STRENGTH = SUGARS.register("strength",
                                                            () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                            Ingredient.of(Items.BLAZE_POWDER)));
    DeferredHolder<Sugar, Sugar> RECOVERY = SUGARS.register("recovery",
                                                            () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                            Ingredient.of(Items.GHAST_TEAR,
                                                                                          ItemRegistry.SWEET_MELON_SLICE.get())));

    DeferredHolder<Sugar, Sugar> TURTLE = SUGARS.register("turtle",
                                                          () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                          Ingredient.of(Items.TURTLE_SCUTE)));
    DeferredHolder<Sugar, Sugar> FLUTTER = SUGARS.register("flutter",
                                                           () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                           Ingredient.of(Items.PHANTOM_MEMBRANE)));
    DeferredHolder<Sugar, Sugar> SNAIL = SUGARS.register("snail",
                                                         () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                         Ingredient.of(Items.SOUL_SAND)));
    DeferredHolder<Sugar, Sugar> STINKY = SUGARS.register("stinky",
                                                          () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                          Ingredient.of(Tags.Items.MUSHROOMS)));

    DeferredHolder<Sugar, Sugar> BLINDING = SUGARS.register("blinding",
                                                            () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                            Ingredient.of(ItemRegistry.VOID_CARROT.get())));


    DeferredHolder<Sugar, Sugar> WEAKNESS = SUGARS.register("weakness",
                                                            () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                            Ingredient.of(ItemRegistry.WEAKNESS_POWDER.get())));

    DeferredHolder<Sugar, Sugar> BRIGHTNESS = SUGARS.register("brightness",
                                                              () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                              Ingredient.of(Items.GLOW_BERRIES)));
    DeferredHolder<Sugar, Sugar> DARKNESS = SUGARS.register("darkness",
                                                            () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                            Ingredient.of(Items.BLACK_DYE)));
    DeferredHolder<Sugar, Sugar> HUNGER = SUGARS.register("hunger",
                                                          () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                          Ingredient.of(Items.ROTTEN_FLESH)));

    // Nether blends
    DeferredHolder<Sugar, Sugar> INVISIBILITY = SUGARS.register("invisibility",
                                                                () -> new Sugar(Sugar.Type.NETHER,
                                                                                Ingredient.of(ItemRegistry.PHANTOM_PEARL.get())));


    DeferredHolder<Sugar, Sugar> STINGER = SUGARS.register("stinger",
                                                           () -> new Sugar(Sugar.Type.NETHER,
                                                                           Ingredient.of(Items.CACTUS)));
    DeferredHolder<Sugar, Sugar> BUG = SUGARS.register("bug",
                                                       () -> new Sugar(Sugar.Type.NETHER, Ingredient.of(Items.STONE)));
    DeferredHolder<Sugar, Sugar> STICKY = SUGARS.register("sticky",
                                                          () -> new Sugar(Sugar.Type.NETHER,
                                                                          Ingredient.of(Items.SLIME_BLOCK)));
    DeferredHolder<Sugar, Sugar> BINDING = SUGARS.register("binding",
                                                           () -> new Sugar(Sugar.Type.NETHER,
                                                                           Ingredient.of(Items.COBWEB)));
    DeferredHolder<Sugar, Sugar> GALE = SUGARS.register("gale",
                                                        () -> new Sugar(Sugar.Type.NETHER,
                                                                        Ingredient.of(Items.BREEZE_ROD)));
    DeferredHolder<Sugar, Sugar> REFRESHING = SUGARS.register("refreshing",
                                                              () -> new Sugar(Sugar.Type.NETHER,
                                                                              Ingredient.of(Items.COCOA_BEANS)));
    DeferredHolder<Sugar, Sugar> LAZY = SUGARS.register("lazy",
                                                        () -> new Sugar(Sugar.Type.NETHER,
                                                                        Ingredient.of(Items.COBBLESTONE)));
    DeferredHolder<Sugar, Sugar> SOLID = SUGARS.register("solid",
                                                         () -> new Sugar(Sugar.Type.NETHER,
                                                                         Ingredient.of(ItemRegistry.CALCIUM_RICH_MILK.get())));


    DeferredHolder<Sugar, Sugar> FIREPROOF = SUGARS.register("fireproof",
                                                             () -> new Sugar(Sugar.Type.NETHER,
                                                                             Ingredient.of(Items.MAGMA_CREAM,
                                                                                           Items.OBSIDIAN)));
    DeferredHolder<Sugar, Sugar> WITHERING = SUGARS.register("withering",
                                                             () -> new Sugar(Sugar.Type.NETHER,
                                                                             Ingredient.of(Items.WITHER_ROSE)));
    DeferredHolder<Sugar, Sugar> RED_HEART = SUGARS.register("red_heart",
                                                             () -> new Sugar(Sugar.Type.NETHER,
                                                                             Ingredient.of(ItemRegistry.IRON_CLAD_APPLE.get())));


    DeferredHolder<Sugar, Sugar> FLOATING = SUGARS.register("floating",
                                                            () -> new Sugar(Sugar.Type.NETHER,
                                                                            Ingredient.of(Items.SHULKER_SHELL)));

    // End blends
    DeferredHolder<Sugar, Sugar> GOLDEN_HEART = SUGARS.register("golden_heart",
                                                                () -> new Sugar(Sugar.Type.END,
                                                                                Ingredient.of(ItemRegistry.GOLD_STUDDED_APPLE.get())));


    DeferredHolder<Sugar, Sugar> SATIATING = SUGARS.register("satiating",
                                                             () -> new Sugar(Sugar.Type.END,
                                                                             Ingredient.of(ItemRegistry.BLESSED_STEAK.get())));

    DeferredHolder<Sugar, Sugar> LUCKY = SUGARS.register("lucky",
                                                         () -> new Sugar(Sugar.Type.END,
                                                                         Ingredient.of(ItemRegistry.GREEDY_OFFERING.get())));


    DeferredHolder<Sugar, Sugar> UNLUCKY = SUGARS.register("unlucky",
                                                           () -> new Sugar(Sugar.Type.END,
                                                                           Ingredient.of(ItemRegistry.DEFILED_OFFERING.get())));

    DeferredHolder<Sugar, Sugar> TIDAL = SUGARS.register("tidal",
                                                         () -> new Sugar(Sugar.Type.END,
                                                                         Ingredient.of(Items.NAUTILUS_SHELL)));
    DeferredHolder<Sugar, Sugar> FISH_SWIM = SUGARS.register("fish_swim",
                                                             () -> new Sugar(Sugar.Type.END,
                                                                             Ingredient.of(ItemRegistry.DOLPHIN_COOKIE.get())));

    DeferredHolder<Sugar, Sugar> TAUNTING = SUGARS.register("taunting",
                                                            () -> new Sugar(Sugar.Type.END,
                                                                            Ingredient.of(ItemRegistry.OMINOUS_FLAG.get())));

    DeferredHolder<Sugar, Sugar> DISCOUNT = SUGARS.register("discount",
                                                            () -> new Sugar(Sugar.Type.END,
                                                                            Ingredient.of(Items.EMERALD_BLOCK)));
}
