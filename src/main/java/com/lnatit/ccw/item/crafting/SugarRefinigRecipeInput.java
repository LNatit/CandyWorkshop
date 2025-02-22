package com.lnatit.ccw.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record SugarRefinigRecipeInput(List<ItemStack> inputs, List<ItemStack> returns) implements RecipeInput {
    public boolean isSimple() {
        return inputs.get(0).is(Items.MILK_BUCKET) && inputs.get(1).is(Items.SUGAR);
    }

    @Override
    public ItemStack getItem(int index) {
        if (index < 4) {
            return inputs.get(index);
        }
        return returns.get(index - 4);
    }

    @Override
    public int size() {
        return 4;
    }
}
