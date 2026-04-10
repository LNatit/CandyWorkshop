package com.lnatit.ccw.item.component;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record GummyContents(ImmutableList<ItemStack> stacks, Type type) implements IContents {
    public static final Codec<GummyContents> CODEC = IContents.codec(GummyContents::of);
    public static final StreamCodec<RegistryFriendlyByteBuf, GummyContents> STREAM_CODEC = IContents.streamCodec(GummyContents::of);


    private static GummyContents of(List<ItemStack> stacks, Type type) {
        // TODO check list size & contents
        return new GummyContents(ImmutableList.copyOf(stacks), type);
    }

    public static void set(ItemStack stack, MutableContents mutable) {
        if (stack.isEmpty())
            return;
        stack.set(mutable.type().dataComponentType, of(mutable.stacks(), mutable.type()));
    }
}
