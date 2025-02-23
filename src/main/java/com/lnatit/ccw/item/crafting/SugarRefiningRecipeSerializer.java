package com.lnatit.ccw.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SugarRefiningRecipeSerializer implements RecipeSerializer<SugarRefiningRecipe> {
    public static final MapCodec<SugarRefiningRecipe> CODEC = null;
    public static final StreamCodec<RegistryFriendlyByteBuf, SugarRefiningRecipe> STREAM_CODEC = null;

    @Override
    public MapCodec<SugarRefiningRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SugarRefiningRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
