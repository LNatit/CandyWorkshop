package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.misc.RegRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import javax.annotation.Nullable;
import java.util.*;

public abstract class Sugar {
    public static final Codec<Holder<Sugar>> CODEC = RegRegistry.SUGAR.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Sugar>> STREAM_CODEC = ByteBufCodecs.holderRegistry(RegRegistry.SUGAR_KEY);
    protected final String name;
    protected final boolean hasExcited;
    protected final boolean hasBold;

    public Sugar(String name, boolean hasExcited, boolean hasBold) {
        this.name = name;
        this.hasExcited = hasExcited;
        this.hasBold = hasBold;
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
            sugars.add(Sugar.createSugar(sugar, flavor));
        }
        return sugars;
    }

    public String name() {
        return name;
    }

    public abstract void applyOn(LivingEntity entity, Flavor flavor);

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
        ORIGINAL("original"), EXCITED("excited"), BOLD("bold"), MILKY("milky");

        public static final Codec<Flavor> CODEC = Codec.stringResolver(Flavor::toName, Flavor::fromName);
        public static final StreamCodec<FriendlyByteBuf, Flavor> STREAM_CODEC = NeoForgeStreamCodecs.enumCodec(Flavor.class);

        public final String name;

        Flavor(String name) {
            this.name = name;
        }

        public static Flavor fromExtra(ItemStack extra) {
            if (extra.is(Items.COCOA_BEANS)) {
                return EXCITED;
            }
            if (extra.is(Items.HONEY_BOTTLE)) {
                return BOLD;
            }
            if (extra.is(ItemRegistry.MILK_GELATIN)) {
                return MILKY;
            }
            return ORIGINAL;
        }

        @Nullable
        public static Component descriptionOf(Flavor flavor) {
            return switch (flavor) {
                case EXCITED -> Component.translatable("item.ccw.gummy.excited").withStyle(ChatFormatting.DARK_GREEN);
                case BOLD -> Component.translatable("item.ccw.gummy.bold").withStyle(ChatFormatting.GOLD);
                case MILKY -> Component.translatable("item.ccw.gummy.milky").withStyle(ChatFormatting.WHITE);
                default -> null;
            };
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
    }
}
