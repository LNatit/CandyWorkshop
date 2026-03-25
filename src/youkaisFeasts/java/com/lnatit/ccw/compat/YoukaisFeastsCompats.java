package com.lnatit.ccw.compat;

import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import dev.xkmc.youkaishomecoming.init.food.YHTea;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface YoukaisFeastsCompats
{
    DeferredHolder<Sugar, Sugar> GREEN_TEA = Sugars.SUGARS.register("green_tea",
                                                                    () -> new Sugar(Sugar.Type.OVERWORLD,
                                                                                    Ingredient.of(YHTea.GREEN.leaves)));

    static void init() {}
}
