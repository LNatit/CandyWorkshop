package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarUtils;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class GummySubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack>
{
    public static final GummySubtypeInterpreter INSTANCE = new GummySubtypeInterpreter();

    @Override
    public String apply(ItemStack ingredient, UidContext context) {
        if (!ingredient.hasTag()) {
            return IIngredientSubtypeInterpreter.NONE;
        }

        CompoundTag sugarContents = SugarUtils.getSugarContents(ingredient);
        StringBuilder stringBuilder = new StringBuilder();

        if (sugarContents.contains(SugarUtils.TAG_SUGAR, CompoundTag.TAG_STRING)) {
            stringBuilder.append(sugarContents.getString(SugarUtils.TAG_SUGAR));
        }

        if (sugarContents.contains(SugarUtils.TAG_FLAVOR, CompoundTag.TAG_STRING)) {
            stringBuilder.append(sugarContents.getString(SugarUtils.TAG_FLAVOR));
        }
        else {
            stringBuilder.append(Sugar.Flavor.toName(Sugar.Flavor.ORIGINAL));
        }

        return stringBuilder.toString();
    }
}
