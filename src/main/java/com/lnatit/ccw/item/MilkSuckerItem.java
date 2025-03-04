package com.lnatit.ccw.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

// TODO rename
public class MilkSuckerItem extends Item {
    public MilkSuckerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Cow) {
            if (stack.nextDamageWillBreak() || player.getCooldowns().isOnCooldown(stack)) {
                return InteractionResult.FAIL;
            }

            int count = 1;
            if (player.isShiftKeyDown()) {
                count = Math.min(stack.getMaxDamage() - stack.getDamageValue(), 16);
            }

            if (!player.level().isClientSide()) {
                ItemStack result = new ItemStack(ItemRegistry.MILK_CARTON.get(), count);
                if (!player.addItem(result)) {
                    player.drop(result, false);
                }

                stack.hurtWithoutBreaking(count, player);
            }

            player.getCooldowns().addCooldown(stack, 2 * count);
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }
}
