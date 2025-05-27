package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public abstract class Sugar
{
//    public static final Codec<Sugar> CODEC = Sugars.SUGAR.getCodec();
//    public static final StreamCodec<FriendlyByteBuf, Sugar> STREAM_CODEC = ByteBufCodecs.holderRegistry(
//            RegRegistry.SUGAR_KEY);
    protected final String name;
    protected final boolean hasExcited;
    protected final boolean hasBold;

    public Sugar(String name, boolean hasExcited, boolean hasBold) {
        this.name = name;
        this.hasExcited = hasExcited;
        this.hasBold = hasBold;
    }

    public static Collection<ItemStack> createAllFlavors(@Nullable Sugar sugar) {
        if (sugar == null) {
            return Set.of();
        }
        Set<ItemStack> sugars = new HashSet<>();
        for (Flavor flavor : sugar.getAvailableFlavors()) {
            sugars.add(SugarUtils.createSugar(sugar, flavor));
        }
        return sugars;
    }

    public String name() {
        return name;
    }

    public abstract void applyOn(LivingEntity entity, Flavor flavor);

    public void addSugarTooltip(Consumer<Component> tooltipAdder, Flavor flavor) {
    }

    public List<Flavor> getAvailableFlavors() {
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

    public ResourceLocation getItemModel() {
        return new ResourceLocation(CandyWorkshop.MODID, this.name)
                               .withSuffix("_gummy");

    }

    public ResourceLocation getModelId() {
        return getItemModel().withPrefix("item/");
    }

    public enum Flavor
    {
        ORIGINAL("original", null),
        EXCITED("excited", ChatFormatting.DARK_GREEN),
        BOLD("bold", ChatFormatting.GOLD),
        MILKY("milky", ChatFormatting.WHITE);

//        public static final Codec<Flavor> CODEC = Codec.stringResolver(Flavor::toName, Flavor::fromNameSafe);

        public final String name;
        @Nullable
        public final ChatFormatting formatting;

        Flavor(String name, @Nullable ChatFormatting formatting) {
            this.name = name;
            this.formatting = formatting;
        }

        public static Flavor fromExtra(ItemStack extra) {
            if (extra.is(Items.COCOA_BEANS)) {
                return EXCITED;
            }
            if (extra.is(Items.HONEY_BOTTLE)) {
                return BOLD;
            }
            if (extra.is(ItemRegistry.MILK_GELATIN.get())) {
                return MILKY;
            }
            return ORIGINAL;
        }

        public static ItemStack toExtra(Flavor flavor) {
            return switch (flavor) {
                case EXCITED -> new ItemStack(Items.COCOA_BEANS);
                case BOLD -> new ItemStack(Items.HONEY_BOTTLE);
                case MILKY -> new ItemStack(ItemRegistry.MILK_GELATIN.get());
                default -> ItemStack.EMPTY;
            };
        }

        @Nullable
        public static MutableComponent nameOf(Flavor flavor) {
            return flavor == Flavor.ORIGINAL ? null :
                    Component.translatable("item.ccw.gummy.".concat(flavor.name).concat(".prefix")).withStyle(
                            flavor.formatting);
        }

        @Nullable
        public static Component descriptionOf(Flavor flavor) {
            return flavor == Flavor.ORIGINAL ? null :
                    Component.translatable("item.ccw.gummy.".concat(flavor.name).concat(".desc")).withStyle(
                            flavor.formatting);
        }

        public static String toName(Flavor flavor) {
            return flavor.name;
        }

        public static Flavor fromNameSafe(String name) {
            return switch (name) {
                case "excited" -> EXCITED;
                case "bold" -> BOLD;
                case "milky" -> MILKY;
                default -> ORIGINAL;
            };
        }
    }
}
