package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.api.ITickFreeRecipe;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.HashMap;
import java.util.Map;

public class SugarRefining implements ITickFreeRecipe<RecipeWrapper> {
    private final Map<Item, Holder<Sugar>> knownRefiners = new HashMap();

    public SugarRefining() {
        knownRefiners.put(Items.SUGAR, Sugars.SPEED);
    }

    @Override
    public int match(RecipeWrapper context) {
        ItemStack slot3 = context.getItem(2);
        if (!knownRefiners.containsKey(slot3.getItem())) {
            return 0;
        }

        int milkCount = context.getItem(0).is(Items.MILK_BUCKET) ? 1 : 0;
        ItemStack slot2 = context.getItem(1);
        int sugarCount = slot2.is(Items.SUGAR) ? slot2.getCount() >> 3 : 0;
        int mainCount = slot3.getCount();
        int extraCount = context.getItem(3).getCount();

        return 0;
    }

    @Override
    public void perform(int times) {

    }
}
