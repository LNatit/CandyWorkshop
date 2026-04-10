package com.lnatit.ccw.item.sugaring;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class SimpleFlavor extends Flavor
{
    private final int color;
    private final Ingredient ingredient;

    public SimpleFlavor(int color, Ingredient ingredient) {
        this.color = color;
        this.ingredient = ingredient;
    }

    @Override
    public Style style() {
        return Style.EMPTY.withColor(color);
    }

    @Override
    public Ingredient ingredient() {
        return ingredient;
    }

    @Override
    @Nullable
    public Holder<Flavor> proxy() {
        return null;
    }
}
