package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.data.Effect;
import com.lnatit.ccw.misc.RegRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.IdMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public abstract class Flavor {
    public static final Codec<Holder<Flavor>> CODEC = RegRegistry.FLAVOR.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Flavor>> STREAM_CODEC = ByteBufCodecs.holderRegistry(
            RegRegistry.FLAVOR_KEY);
    public static final Flavor ORIGINAL = new Flavor() {};
    public static final int SUGAR_PRODUCTION = 8;

    public Style style() {
        return Style.EMPTY;
    }

    public Ingredient ingredient() {
        return Ingredient.EMPTY;
    }

    /**
     * @return null if no proxy, else proxy to the corresponding flavor
     */
    @Nullable
    public Holder<Flavor> proxy() {
        return null;
    }

    public void onApply(ItemStack stack) {
    }

    public void onRemove(ItemStack stack) {
    }

    /**
     * @param entity         the entity consuming the gummy
     * @param effectsToApply the effect list to apply
     */
    public void preConsume(LivingEntity entity, List<Effect> effectsToApply) {
    }

    public void postConsume(LivingEntity entity, List<Effect> appliedEffects) {
    }

    public int craftCount() {
        return SUGAR_PRODUCTION;
    }

    public static MutableComponent prefix(Holder<Flavor> flavor) {
        return Component
                .translatable("item.ccw.gummy." + flavor.getKey().identifier().getPath() + ".prefix")
                .withStyle(flavor.value().style());
    }

    public static MutableComponent description(Holder<Flavor> flavor) {
        return Component
                .translatable("item.ccw.gummy." + flavor.getKey().identifier().getPath() + ".desc")
                .withStyle(flavor.value().style());
    }

    public static Holder<Flavor> next(Holder<Flavor> flavor) {
        IdMap<Holder<Flavor>> list = RegRegistry.FLAVOR.asHolderIdMap();
        int index = list.getId(flavor);
        if (index == -1) {
            return flavor;
        }
        return Objects.requireNonNull(list.byId((index + 1) % list.size()));
    }

    public static Holder<Flavor> from(ItemStack stack) {
        IdMap<Holder<Flavor>> list = RegRegistry.FLAVOR.asHolderIdMap();
        for (Holder<Flavor> flavor : list) {
            if (flavor.value().ingredient().test(stack)) {
                return flavor;
            }
        }
        return Flavors.ORIGINAL;
    }
}
