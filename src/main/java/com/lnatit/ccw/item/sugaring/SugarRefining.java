package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class SugarRefining {
    public static final TagKey<Item> EXTRAS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "extras"));
    public static final Map<Item, Holder<Sugar>> KNOWN_REFINERS = new HashMap<>();

    public static void bootstrap() {
        KNOWN_REFINERS.put(Items.SUGAR, Sugars.SPEED);
    }
}
