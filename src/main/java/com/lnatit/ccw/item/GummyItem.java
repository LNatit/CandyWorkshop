package com.lnatit.ccw.item;

import com.lnatit.ccw.item.sugaring.SugarUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GummyItem extends Item
{
    public GummyItem(Properties properties) {
        super(properties);
    }

    // Since we have a more complex name, so we don't override getDescriptionId
    @Override
    public Component getName(ItemStack stack) {
        return stack.is(ItemRegistry.GUMMY_ITEM.get()) ? SugarUtils.getName(this.getDescriptionId(), stack) :
                super.getName(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        SugarUtils.consume(livingEntity, stack);
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        SugarUtils.addSugarTooltip(stack, tooltip, flag);
    }
}
