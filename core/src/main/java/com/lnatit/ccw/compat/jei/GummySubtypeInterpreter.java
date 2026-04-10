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

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
        SugarContents sugarContents = ingredient.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        if (sugarContents == null) {
            return "";
        }

        return String.format(
                "%s[%s]",
                sugarContents.sugar().getKey().location().getPath(),
                sugarContents.flavor().getKey().location().getPath()
        );
    }
}
