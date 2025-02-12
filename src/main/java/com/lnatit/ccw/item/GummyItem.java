package com.lnatit.ccw.item;

import com.lnatit.ccw.item.sugaring.Sugar;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class GummyItem extends Item {
    public static final int EAT_DURATION = 16;

    public GummyItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Consumable consumable = stack.get(DataComponents.CONSUMABLE);
        return consumable != null ? onConsumeSugar(consumable, level, livingEntity, stack) : stack;
    }

    // TODO modify after implemented sugaring
//    @Override
//    public String getDescriptionId() {
//        return super.getDescriptionId();
//    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    // Proxy Consumable::onConsume
    private ItemStack onConsumeSugar(Consumable consumable, Level level, LivingEntity livingEntity, ItemStack stack) {
        RandomSource randomsource = livingEntity.getRandom();
        consumable.emitParticlesAndSounds(randomsource, livingEntity, stack, 16);
        if (livingEntity instanceof ServerPlayer serverplayer) {
            serverplayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
        }

        stack.getAllOfType(ConsumableListener.class).forEach(c -> c.onConsume(level, livingEntity, stack, consumable));
        if (!level.isClientSide) {
            Holder<Sugar> holder = stack.get(ItemRegistry.SUGAR_DCTYPE);
            if (holder != null) {
                holder.value().applySugarOn((ServerLevel) level, livingEntity);
            }
        }

        livingEntity.gameEvent(GameEvent.EAT);
        stack.consume(1, livingEntity);
        return stack;
    }
}
