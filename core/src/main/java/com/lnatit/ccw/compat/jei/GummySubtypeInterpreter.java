package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.component.SugarContents;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GummySubtypeInterpreter implements ISubtypeInterpreter<ItemStack>
{
    public static final GummySubtypeInterpreter INSTANCE = new GummySubtypeInterpreter();

    @Override
    public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
        return ingredient.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
    }

    public String getStringName(ItemStack stack) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        if (sugarContents == null) {
            return "";
        }

        return String.format(
                "%s[%s]",
                sugarContents.sugar().getKey().identifier().getPath(),
                sugarContents.flavor().getKey().identifier().getPath()
        );
    }
}
