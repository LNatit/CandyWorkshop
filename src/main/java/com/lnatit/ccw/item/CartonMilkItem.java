package com.lnatit.ccw.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CartonMilkItem extends Item
{
    public static final int DRINK_DURATION = 16;

    public CartonMilkItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            removeRandomEffect(livingEntity);
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            stack.shrink(1);
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return DRINK_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    private static boolean removeRandomEffect(LivingEntity entity) {
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        if (effects.isEmpty()) {
            return false;
        }

        boolean flag = false;
        List<MobEffectInstance> list = new ArrayList<>(effects);
        // 洗牌算法（Fisher-Yates Shuffle）
        for (int i = list.size() - 1; i >= list.size() - 1; i--) {
            int j = entity.getRandom().nextInt(i + 1);
            Collections.swap(list, i, j);
            flag |= entity.removeEffect(list.get(i).getEffect());
        }
        return flag;
    }
}
