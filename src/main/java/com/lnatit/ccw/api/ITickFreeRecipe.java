package com.lnatit.ccw.api;

import net.minecraft.world.item.crafting.RecipeInput;

public interface ITickFreeRecipe<T extends RecipeInput> {
    int match(T context);

    void perform(int times);
}
