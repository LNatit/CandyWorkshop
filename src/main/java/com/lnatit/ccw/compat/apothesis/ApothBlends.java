package com.lnatit.ccw.compat.apothesis;

import com.lnatit.ccw.item.sugaring.SugarRefining;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.lnatit.ccw.item.ItemRegistry.ITEMS;

public class ApothBlends {
    public static final DeferredItem<Item> MALIGNANT_BERRY = ITEMS.registerSimpleItem("malignant_berry");
    public static final DeferredItem<Item> KNOWLEDGE_BOTTLE = ITEMS.registerSimpleItem("knowledge_bottle");
    public static final DeferredItem<Item> EROSION_SHELL = ITEMS.registerSimpleItem("erosion_shell");
    public static final DeferredItem<Item> VITALITY_BERRY = ITEMS.registerSimpleItem("vitality_berry");

    public static void init() {
        SugarRefining.addCustomBlendProviders(ApothBlends::addBlends);
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(ApothSugars.GRIEVOUS, MALIGNANT_BERRY.get());
        builder.addOverworldBlend(ApothSugars.SUNDERING, EROSION_SHELL.get());
        builder.addOverworldBlend(ApothSugars.VITALITY, VITALITY_BERRY.get());

        builder.addNetherBlend(ApothSugars.KNOWLEDGE, KNOWLEDGE_BOTTLE.get());
    }
}
