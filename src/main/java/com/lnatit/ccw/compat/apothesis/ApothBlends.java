package com.lnatit.ccw.compat.apothesis;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import dev.shadowsoffire.apotheosis.Apoth;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ApothBlends {
    public static final RegistryObject<Item> MALIGNANT_BERRY = ItemRegistry.registerSimpleItem("malignant_berry");
    public static final RegistryObject<Item> KNOWLEDGE_BOTTLE = ItemRegistry.registerSimpleItem("knowledge_bottle");
    public static final RegistryObject<Item> EROSION_SHELL = ItemRegistry.registerSimpleItem("erosion_shell");
    public static final RegistryObject<Item> VITALITY_BERRY = ItemRegistry.registerSimpleItem("vitality_berry");

    public static void init() {
        SugarRefining.addCustomBlendProviders(ApothBlends::addBlends);
    }

    private static void addBlends(SugarRefining.Builder builder) {
        builder.addOverworldBlend(ApothSugars.GRIEVOUS, MALIGNANT_BERRY.get());
        builder.addOverworldBlend(ApothSugars.SUNDERING, EROSION_SHELL.get());
        builder.addOverworldBlend(ApothSugars.VITALITY, VITALITY_BERRY.get());

        builder.addNetherBlend(ApothSugars.KNOWLEDGE, KNOWLEDGE_BOTTLE.get());
        builder.addNetherBlend(ApothSugars.BLEEDING, Apoth.Items.BROADHEAD_ARROW.get());
    }
}
