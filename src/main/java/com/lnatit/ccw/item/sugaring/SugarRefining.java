package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.entity.SugarRefineryBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class SugarRefining
{
    public static final TagKey<Item> EXTRAS =
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "extras"));
    private final Map<Item, Holder<Sugar>> knownRefiners = new HashMap<>();

    public SugarRefining() {
        knownRefiners.put(Items.SUGAR, Sugars.SPEED);
    }

    public int match(SugarRefineryBlockEntity.Contents context) {
        int count = matchKnown(context);
        if (count > 0) {
            return count;
        }
        // match custom recipes...
        return 0;
    }

    public int matchKnown(SugarRefineryBlockEntity.Contents context) {
        ItemStack slot1 = context.getStackInSlot(0);
        ItemStack slot2 = context.getStackInSlot(1);
        ItemStack slot3 = context.getStackInSlot(2);
        ItemStack slot4 = context.getStackInSlot(3);
        if (slot1.is(Items.MILK_BUCKET) ||
                slot2.is(Items.SUGAR) ||
                !knownRefiners.containsKey(slot3.getItem()) ||
                slot4.is(EXTRAS)
        ) {
            return 0;
        }

        int count = Math.min(slot1.getCount(), slot2.getCount() >> 3);
        count = Math.min(count, slot3.getCount());
        count = Math.min(count, slot4.getCount());
        // check returns' availability
        return count;
    }

    public void perform(int times) {

    }
}
