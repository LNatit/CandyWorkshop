package com.lnatit.ccw.item;

import com.lnatit.ccw.item.sugaring.SugarContents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class GummyItem extends Item {
    public GummyItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        return sugarContents != null ? sugarContents.getName(this.getDescriptionId()) : super.getName(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        if (sugarContents != null) {
            sugarContents.onConsume(livingEntity);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        if (sugarContents != null) {
            sugarContents.addSugarTooltip(tooltipComponents::add, context.tickRate());
        }
    }
}
