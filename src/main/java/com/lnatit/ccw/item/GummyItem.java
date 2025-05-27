package com.lnatit.ccw.item;

import com.lnatit.ccw.item.sugaring.SugarContents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GummyItem extends Item
{
    public GummyItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        return sugarContents != null ? sugarContents.getName(this.descriptionId) : super.getName(stack);
    }
}
