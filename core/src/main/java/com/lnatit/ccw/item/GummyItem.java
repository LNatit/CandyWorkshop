package com.lnatit.ccw.item;

import com.lnatit.ccw.item.component.SugarContents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
        SugarContents.applySugarEffects(stack, livingEntity);
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (usedHand == InteractionHand.MAIN_HAND && player.isCreative() && player.isShiftKeyDown()) {
            ItemStack itemstack = player.getItemInHand(usedHand).copy();
            SugarContents contents = itemstack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
            if (contents != null) {
                contents.flavor().value().onRemove(itemstack);
                contents = contents.cycle();
                contents.flavor().value().onApply(itemstack);
                itemstack.set(ItemRegistry.SUGAR_CONTENTS_DCTYPE, contents);

                player.setItemInHand(usedHand, itemstack);
                return InteractionResultHolder.consume(itemstack);
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        if (sugarContents != null) {
            sugarContents.addSugarTooltip(tooltipComponents::add, context.tickRate());
        }
    }
}
