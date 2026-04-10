package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RefiningInput;
import com.lnatit.ccw.misc.RegRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

public record Sugar(Type type, Ingredient ingredient)
{
    public static final Codec<Holder<Sugar>> CODEC = RegRegistry.SUGAR.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Sugar>> STREAM_CODEC = ByteBufCodecs.holderRegistry(
            RegRegistry.SUGAR_KEY);

    public static ResourceLocation getItemModel(Holder<Sugar> sugar) {
        return sugar.getKey().location().withSuffix("_gummy");
    }

    public static ResourceLocation getModelId(Holder<Sugar> sugar) {
        return getItemModel(sugar).withPrefix("item/");
    }

    public enum Type
    {
        OVERWORLD,
        NETHER,
        END;

        public TagKey<Item> tag() {
            return switch (this) {
                case OVERWORLD -> ItemRegistry.OVERWORLD_SUGAR_TAG;
                case NETHER -> ItemRegistry.NETHER_SUGAR_TAG;
                case END -> ItemRegistry.ENDER_SUGAR_TAG;
            };
        }
    }

    @Nullable
    public static Holder<Sugar> from(RefiningInput input) {
        return RegRegistry.SUGAR.holders()
                                .filter(sugar -> sugar.value().ingredient().test(input.main()))
                                .filter(sugar -> input.sugar().is(sugar.value().type().tag()))
                                .findFirst()
                                .orElse(null);
    }
}
