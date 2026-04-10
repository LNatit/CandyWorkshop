package com.lnatit.ccw.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record RefiningInput(ItemStack milk, ItemStack sugar, ItemStack main, ItemStack extra) implements RecipeInput
{
    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> milk;
            case 1 -> sugar;
            case 2 -> main;
            case 3 -> extra;
            default -> throw new IllegalArgumentException("Recipe does not contain slot " + index);
        };
    }

    @Override
    public int size() {
        return 4;
    }
}
