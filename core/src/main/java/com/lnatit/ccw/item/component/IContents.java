package com.lnatit.ccw.item.component;

import com.google.common.collect.ImmutableList;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.menu.GummyContentMenu;
import com.lnatit.ccw.menu.MenuRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public interface IContents
{
    List<ItemStack> stacks();

    Type type();

    default NonNullList<ItemStack> items() {
        return NonNullList.copyOf(this.stacks());
    }

    static <S extends IContents> Codec<S> codec(BiFunction<List<ItemStack>, Type, S> factory) {
        return RecordCodecBuilder.create(inst -> inst.group(Codec.list(ItemStack.OPTIONAL_CODEC)
                                                                 .fieldOf("stacks")
                                                                 .forGetter(IContents::items),
                                                            Type.CODEC.fieldOf("type").forGetter(IContents::type))
                                                     .apply(inst, factory));
    }

    static <S extends IContents> StreamCodec<RegistryFriendlyByteBuf, S> streamCodec(BiFunction<List<ItemStack>, Type, S> factory) {
        return StreamCodec.composite(ItemStack.OPTIONAL_LIST_STREAM_CODEC,
                                     IContents::stacks,
                                     Type.STREAM_CODEC,
                                     IContents::type,
                                     factory);
    }

    enum Type implements StringRepresentable
    {
        MAGAZINE("magazine", 6, 2, ItemRegistry.MAGAZINE_CONTENTS_DCTYPE, MenuRegistry.GUMMY_MAGAZINE),
        GLAZER("glazer", 3, 1, ItemRegistry.GLAZER_CONTENTS_DCTYPE, MenuRegistry.GUMMY_GLAZER),
        ;

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
        public static final StreamCodec<RegistryFriendlyByteBuf, Type> STREAM_CODEC =
                NeoForgeStreamCodecs.enumCodec(Type.class);

        private final String name;
        public final int size;
        public final int tierMarch;
        public final Supplier<DataComponentType<GummyContents>> dataComponentType;
        public final Supplier<MenuType<GummyContentMenu>> menuType;

        Type(
                String name,
                int size,
                int tierMarch,
                Supplier<DataComponentType<GummyContents>> dataComponentType,
                Supplier<MenuType<GummyContentMenu>> menuType
        ) {
            this.name = name;
            this.size = size;
            this.tierMarch = tierMarch;
            this.dataComponentType = dataComponentType;
            this.menuType = menuType;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public GummyContents defaultContents() {
            return new GummyContents(ImmutableList.copyOf(Collections.nCopies(this.size, ItemStack.EMPTY)), this);
        }
    }
}
