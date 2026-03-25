package com.lnatit.ccw.compat.apothesis;

import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.lnatit.ccw.item.ItemRegistry.ITEMS;

public interface ApothesisCompats
{
    DeferredItem<Item> MALIGNANT_BERRY = ITEMS.registerSimpleItem("malignant_berry");
    DeferredItem<Item> KNOWLEDGE_BOTTLE = ITEMS.registerSimpleItem("knowledge_bottle");
    DeferredItem<Item> EROSION_SHELL = ITEMS.registerSimpleItem("erosion_shell");
    DeferredItem<Item> VITALITY_BERRY = ITEMS.registerSimpleItem("vitality_berry");

    DeferredHolder<Sugar, Sugar> GRIEVOUS =
            Sugars.SUGARS.register("grievous", () -> new Sugar(Sugar.Type.OVERWORLD, Ingredient.of(MALIGNANT_BERRY)));
    DeferredHolder<Sugar, Sugar> SUNDERING =
            Sugars.SUGARS.register("sundering", () -> new Sugar(Sugar.Type.OVERWORLD, Ingredient.of(EROSION_SHELL)));
    DeferredHolder<Sugar, Sugar> VITALITY =
            Sugars.SUGARS.register("vitality", () -> new Sugar(Sugar.Type.OVERWORLD, Ingredient.of(VITALITY_BERRY)));
    DeferredHolder<Sugar, Sugar> KNOWLEDGE =
            Sugars.SUGARS.register("knowledge", () -> new Sugar(Sugar.Type.NETHER, Ingredient.of(KNOWLEDGE_BOTTLE)));

    static void init() {}
}
