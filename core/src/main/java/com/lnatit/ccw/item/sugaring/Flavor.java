package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.data.Effect;
import com.lnatit.ccw.misc.RegRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.List;

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
                .translatable("item.ccw.gummy." + flavor.getKey().location().getPath() + ".prefix")
                .withStyle(flavor.value().style());
    }

    public static MutableComponent description(Holder<Flavor> flavor) {
        return Component
                .translatable("item.ccw.gummy." + flavor.getKey().location().getPath() + ".desc")
                .withStyle(flavor.value().style());
    }

    @Nullable
    private static List<? extends Holder<Flavor>> CACHE;

    public static void rebuildCache(Registry<Flavor> registry) {
        CACHE = registry.holders().toList();
    }

    public static Holder<Flavor> next(Holder<Flavor> flavor) {
        if (CACHE == null) {
            return flavor;
        }
        int index = CACHE.indexOf(flavor);
        if (index == -1) {
            return flavor;
        }
        return CACHE.get((index + 1) % CACHE.size());
    }

    public static Holder<Flavor> from(ItemStack stack) {
        if (CACHE == null) {
            return Flavors.ORIGINAL;
        }
        for (Holder<Flavor> flavor : CACHE) {
            if (flavor.value().ingredient().test(stack)) {
                return flavor;
            }
        }
        return Flavors.ORIGINAL;
    }
}
