package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.misc.RegRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import javax.annotation.Nullable;
import java.util.*;

public class Sugar {
    public static final Codec<Holder<Sugar>> CODEC = RegRegistry.SUGAR.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Sugar>> STREAM_CODEC =
            ByteBufCodecs.holderRegistry(RegRegistry.SUGAR_KEY);

    private final String name;
    private final List<Holder<MobEffect>> effects;
    private final int duration;
    private final boolean hasExcited;
    private final boolean hasBold;

    private Sugar(String name, List<Holder<MobEffect>> effects, int duration, boolean hasExcited, boolean hasBold) {
        this.name = name;
        this.effects = effects;
        this.duration = duration;
        this.hasExcited = hasExcited;
        this.hasBold = hasBold;
    }

    public String name() {
        return name;
    }

    public void applyOn(ServerLevel level, LivingEntity entity, Flavor flavor) {
        int duration = this.hasBold && flavor == Flavor.BOLD ? 2 * this.duration : this.duration;
        int amplifier = this.hasExcited && flavor == Flavor.EXCITED ? 1 : 0;
        if (flavor == Flavor.MILKY) {
            entity.removeAllEffects();
        }

        for (Holder<MobEffect> effect : effects) {
            // Instantenous effect behaves differently
            if (effect.value().isInstantenous()) {
                effect.value().applyInstantenousEffect(level, entity, entity, entity, amplifier, 0.5);
            } else {
                MobEffectInstance exist = entity.getEffect(effect);
                if (exist != null && !exist.isAmbient() && exist.getAmplifier() >= amplifier) {
                    duration += exist.getDuration();
                }
                entity.addEffect(new MobEffectInstance(effect, duration, amplifier));
            }
        }
    }

    public List<Flavor> getAvailableTypes() {
        List<Flavor> flavors = new ArrayList<>();
        flavors.add(Flavor.ORIGINAL);
        if (hasExcited) {
            flavors.add(Flavor.EXCITED);
        }
        if (hasBold) {
            flavors.add(Flavor.BOLD);
        }
        flavors.add(Flavor.MILKY);
        return flavors;
    }

    public ResourceLocation getModelId() {
        return ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, this.name).withSuffix("_gummy");
    }

    public enum Flavor {
        ORIGINAL("original"),
        EXCITED("excited"),
        BOLD("bold"),
        MILKY("milky");

        public static final Codec<Flavor> CODEC = Codec.stringResolver(Flavor::toName, Flavor::fromName);
        public static final StreamCodec<FriendlyByteBuf, Flavor> STREAM_CODEC =
                NeoForgeStreamCodecs.enumCodec(Flavor.class);

        public final String name;

        Flavor(String name) {
            this.name = name;
        }

        static String toName(Flavor flavor) {
            return flavor.name;
        }

        @Nullable
        static Flavor fromName(String name) {
            return switch (name) {
                case "original" -> ORIGINAL;
                case "excited" -> EXCITED;
                case "bold" -> BOLD;
                case "milky" -> MILKY;
                default -> null;
            };
        }

        public static Flavor fromExtra(ItemStack extra) {
            if (extra.is(Items.COCOA_BEANS))
                return EXCITED;
            if (extra.is(Items.HONEY_BOTTLE))
                return BOLD;
            if (extra.is(ItemRegistry.MILK_GELATIN))
                return MILKY;
            return ORIGINAL;
        }
    }

    public static IEffectsAcceptor builder(String name) {
        return new Builder(name);
    }

    public static class Builder implements IEffectsAcceptor, IDurationAcceptor, IOptionalAcceptor {
        private String name;
        private List<Holder<MobEffect>> effects;
        private int duration = 0;
        private boolean hasExcited = true;
        private boolean hasBold = true;

        private Builder(String name) {
            this.name = name;
        }

        @Override
        @SafeVarargs
        public final IDurationAcceptor withEffects(Holder<MobEffect>... effect) {
            this.effects = List.of(effect);
            return this;
        }

        @Override
        public IOptionalAcceptor withDuration(int duration) {
            this.duration = duration;
            return this;
        }

        @Override
        public IBuilder withNoExcited() {
            this.hasExcited = false;
            return this;
        }

        @Override
        public IBuilder withNoBold() {
            this.hasBold = false;
            return this;
        }

        @Override
        public Sugar build() {
            return new Sugar(name, effects, duration, hasExcited, hasBold);
        }
    }

    public interface INameAcceptor {
        IEffectsAcceptor withName(String name);
    }

    public interface IEffectsAcceptor {
        IDurationAcceptor withEffects(Holder<MobEffect>... effect);
    }

    public interface IDurationAcceptor {
        IOptionalAcceptor withDuration(int duration);
    }

    public interface IOptionalAcceptor extends IBuilder {
        IBuilder withNoExcited();

        IBuilder withNoBold();
    }

    public interface IBuilder {
        Sugar build();
    }

    public static ItemStack createSugar(@Nullable Holder<Sugar> sugar, Flavor flavor) {
        if (sugar == null) {
            return ItemStack.EMPTY;
        }
        ItemStack itemStack = ItemRegistry.GUMMY_ITEM.toStack();
        itemStack.set(ItemRegistry.SUGAR_CONTENTS_DCTYPE, new SugarContents(Optional.of(sugar), flavor));
        itemStack.set(DataComponents.ITEM_MODEL, sugar.value().getModelId());
        return itemStack;
    }

    public static Collection<ItemStack> createAllFlavors(@Nullable Holder<Sugar> sugar) {
        if (sugar == null) {
            return Set.of();
        }
        Set<ItemStack> sugars = new HashSet<>();
        for (Flavor flavor : sugar.value().getAvailableTypes()) {
            sugars.add(createSugar(sugar, flavor));
        }
        return sugars;
    }
}
