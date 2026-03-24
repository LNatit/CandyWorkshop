package com.lnatit.ccw.compat.youkaishomecoming;

import com.lnatit.ccw.item.sugaring.*;
import dev.xkmc.youkaishomecoming.init.food.YHCrops;
import dev.xkmc.youkaishomecoming.init.food.YHTea;
import dev.xkmc.youkaishomecoming.init.registrate.YHItems;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;

public class YoukaisHomecomingCompats
{
    public static final DeferredHolder<Sugar, Sugar> GREEN_TEA =
            Sugars.SUGARS.register("green_tea",
                                   () -> new Sugar(Sugar.Type.OVERWORLD,
                                                   Ingredient.of(YHTea.GREEN.leaves))
            );
    public static final DeferredHolder<Sugar, Sugar> WHITE_TEA =
            Sugars.SUGARS.register("white_tea",
                                   () -> new Sugar(Sugar.Type.OVERWORLD,
                                                   Ingredient.of(YHTea.WHITE.leaves))
            );
    public static final DeferredHolder<Sugar, Sugar> BLACK_TEA =
            Sugars.SUGARS.register("black_tea",
                                   () -> new Sugar(Sugar.Type.OVERWORLD,
                                                   Ingredient.of(YHTea.BLACK.leaves))
            );
    public static final DeferredHolder<Sugar, Sugar> OOLONG_TEA =
            Sugars.SUGARS.register("oolong_tea",
                                   () -> new Sugar(Sugar.Type.OVERWORLD,
                                                   Ingredient.of(YHTea.OOLONG.leaves))
            );
//    public static final DeferredHolder<Sugar, Sugar> YOUKAI_COFFEE =
//            Sugars.SUGARS.register("youkai_coffee",
//                                   () -> new Sugar(Sugar.Type.OVERWORLD,
//                                                   Ingredient.of(YHItems.COFFEE_POWDER))
//            );
    public static final DeferredHolder<Sugar, Sugar> UDUMBARA =
            Sugars.SUGARS.register("udumbara",
                                   () -> new Sugar(Sugar.Type.OVERWORLD,
                                                   Ingredient.of(YHCrops.UDUMBARA.getFruits()))
            );

    public static void init() {
    }
}
