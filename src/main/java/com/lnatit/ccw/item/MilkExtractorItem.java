package com.lnatit.ccw.item;

import com.lnatit.ccw.misc.SoundRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MilkExtractorItem extends Item {
    public MilkExtractorItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof Cow) {
            if (nextDamageWillBreak(stack) || player.getCooldowns().isOnCooldown(stack.getItem()))
                return InteractionResult.FAIL;

            int count = 1;
            if (player.isShiftKeyDown())
                count = Math.min(stack.getMaxDamage() - stack.getDamageValue(), 16);

            if (!player.level().isClientSide()) {
                ItemStack result = new ItemStack(ItemRegistry.CARTON_MILK.get(), count);
                if (!player.addItem(result))
                    player.drop(result, false);

                stack.hurtAndBreak(1, player, net.minecraft.world.entity.EquipmentSlot.CHEST);
            }

            interactionTarget.playSound(SoundRegistry.PLUG_OFF.get());
            player.getCooldowns().addCooldown(stack.getItem(), 2 * count);
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }

    private static boolean nextDamageWillBreak(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }
}
