package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.RegRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import javax.annotation.Nullable;
import java.util.*;

public record Sugar(String name, Holder<Potion> base, @Nullable Holder<Potion> excited, @Nullable Holder<Potion> bold,
                    int duration) {
    public static final Codec<Holder<Sugar>> CODEC = RegRegistry.SUGAR.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Sugar>> STREAM_CODEC = ByteBufCodecs.holderRegistry(RegRegistry.SUGAR_KEY);

    public static ItemStack createSugarItem(Holder<Sugar> sugar, Type type) {
        ItemStack itemStack = ItemRegistry.GUMMY_ITEM.toStack(1);
        itemStack.set(ItemRegistry.SUGAR_CONTENTS_DCTYPE, new SugarContents(Optional.of(sugar), type));
//        if (sugar != Su)
        itemStack.set(DataComponents.ITEM_MODEL, sugar.value().toModelId());
        return itemStack;
    }

    public static Collection<ItemStack> createSugarItems(Holder<Sugar> sugar) {
        Set<ItemStack> sugarItems = new HashSet<>();
        for (Type type : Type.values()) {
            sugarItems.add(createSugarItem(sugar, type));
        }
        return sugarItems;
    }

    public void applySugarOn(ServerLevel level, LivingEntity entity, Type type) {
        Holder<Potion> potion = this.base;
        int duration = this.duration;
        if (type == Type.EXCITED && this.excited != null) {
            potion = this.excited;
        }
        if (type == Type.BOLD && this.bold != null) {
            potion = this.bold;
            duration *= 2;
        }

        List<MobEffectInstance> effects = potion.value().getEffects();
        for (MobEffectInstance effect : effects) {
            Holder<MobEffect> apply = effect.getEffect();
            // Instantenous effect behaves differently
            if (apply.value().isInstantenous()) {
                apply.value().applyInstantenousEffect(level, entity, entity, entity, effect.getAmplifier(), 0.5);
            } else {
                MobEffectInstance exist = entity.getEffect(apply);
//                    while (exist != null && exist.getAmplifier() != effect.getAmplifier()) {
//                        exist = exist.getEffect()
//                    }
                if (exist != null && !exist.isAmbient() && exist.getAmplifier() >= effect.getAmplifier()) {
                    duration += exist.getDuration();
                }
                entity.addEffect(new MobEffectInstance(apply, duration, effect.getAmplifier()));
            }
        }
    }

    public ResourceLocation toModelId() {
        return ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, this.name).withSuffix("_gummy");
    }

    public enum Type implements StringRepresentable {
        BASE("base"), EXCITED("excited"), BOLD("bold");

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        public static final StreamCodec<FriendlyByteBuf, Type> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(Type.class);

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
