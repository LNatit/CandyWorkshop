package com.lnatit.ccw.item.coneffect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public record RemoveRandomStatusEffectsConsumeEffect(int count) {
    public static final MapCodec<RemoveRandomStatusEffectsConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(ExtraCodecs.POSITIVE_INT.optionalFieldOf("count", 1)
                            .forGetter(RemoveRandomStatusEffectsConsumeEffect::count))
                    .apply(inst, RemoveRandomStatusEffectsConsumeEffect::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, RemoveRandomStatusEffectsConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, RemoveRandomStatusEffectsConsumeEffect::count, RemoveRandomStatusEffectsConsumeEffect::new
    );

    public RemoveRandomStatusEffectsConsumeEffect() {
        this(1);
    }

    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        Collection<MobEffectInstance> effects = entity.getActiveEffects();
        if (effects.isEmpty()) {
            return false;
        }

        boolean flag = false;
        List<MobEffectInstance> list = new ArrayList<>(effects);
        // 洗牌算法（Fisher-Yates Shuffle）
        for (int i = list.size() - 1; i >= list.size() - this.count; i--) {
            int j = entity.getRandom().nextInt(i + 1);
            Collections.swap(list, i, j);
            flag |= entity.removeEffect(list.get(i).getEffect());
        }
        return flag;
    }
}
