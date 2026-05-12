package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.IFormula;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.function.Consumer;

public record RefiningRecipe(SizedIngredient milk,
                             SizedIngredient sugar,
                             Ingredient main,
                             Ingredient extra,
                             ItemStack result) implements IFormula, Recipe<RefiningInput>
{
    public static final MapCodec<RefiningRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(inst -> inst.group(SizedIngredient.NESTED_CODEC.fieldOf("milk").forGetter(RefiningRecipe::milk),
                                                           SizedIngredient.NESTED_CODEC.fieldOf("sugar").forGetter(RefiningRecipe::sugar),
                                                           Ingredient.CODEC.fieldOf("main")
                                                                           .forGetter(RefiningRecipe::main),
                                                           Ingredient.CODEC.fieldOf("extra")
                                                                           .forGetter(RefiningRecipe::extra),
                                                           ItemStack.CODEC.fieldOf("result")
                                                                          .forGetter(RefiningRecipe::result))
                                                    .apply(inst, RefiningRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RefiningRecipe> STREAM_CODEC = StreamCodec.composite(
            SizedIngredient.STREAM_CODEC,
            RefiningRecipe::milk,
            SizedIngredient.STREAM_CODEC,
            RefiningRecipe::sugar,
            Ingredient.CONTENTS_STREAM_CODEC,
            RefiningRecipe::main,
            Ingredient.CONTENTS_STREAM_CODEC,
            RefiningRecipe::extra,
            ItemStack.STREAM_CODEC,
            RefiningRecipe::result,
            RefiningRecipe::new);

    public static final RecipeSerializer<RefiningRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(RefiningInput input, Level level) {
        return this.milk.test(input.milk())
               && this.sugar.test(input.sugar())
               && this.main.test(input.main())
               && this.extra.test(input.extra());
    }

    @Override
    public ItemStack assemble(RefiningInput input) {
        CandyWorkshop.LOGGER.warn("assemble should not be called for refining recipe, use batch instead");
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public ItemStack productionOf(RefiningInput input) {
        return this.result.copy();
    }

    @Override
    public ItemStack batch(RefiningInput input, Consumer<ItemStack> remainderHandler) {
        IFormula.shrinkAndHandleRemainders(input.milk(), this.milk.count(), remainderHandler);
        IFormula.shrinkAndHandleRemainders(input.sugar(), this.sugar.count(), remainderHandler);
        if (!this.main.isEmpty()) {
            IFormula.shrinkAndHandleRemainders(input.main(), remainderHandler);
        }
        if (!this.extra.isEmpty()) {
            IFormula.shrinkAndHandleRemainders(input.extra(), remainderHandler);
        }
        return this.result.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RefiningInput>> getSerializer() {
        return RecipeRegistry.COMMON_REFINING.get();
    }

    @Override
    public RecipeType<? extends Recipe<RefiningInput>> getType() {
        return RecipeRegistry.REFINING.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeRegistry.REFINING_CATEGORY.get();
    }
}
