package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.IFormula;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public record RefiningRecipe(
        SizedIngredient milk,
        SizedIngredient sugar,
        Ingredient main,
        Ingredient extra,
        ItemStack result
) implements IFormula, Recipe<RefiningInput>
{
    @Override
    public boolean matches(RefiningInput input, Level level) {
        return this.milk.test(input.milk())
                && this.sugar.test(input.sugar())
                && this.main.test(input.main())
                && this.extra.test(input.extra());
    }

    @Override
    public ItemStack assemble(RefiningInput input, HolderLookup.Provider registries) {
        CandyWorkshop.LOGGER.warn("assemble should not be called for refining recipe, use batch instead");
        return ItemStack.EMPTY;
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
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(RefiningInput input) {
        List<ItemStack> remainders = new ArrayList<>();
        ItemStack r;

        if (input.milk().hasCraftingRemainingItem()) {
            r = input.milk().getCraftingRemainingItem();
            r.setCount(this.milk.count());
            remainders.add(r);
        }
        if (input.sugar().hasCraftingRemainingItem()) {
            r = input.sugar().getCraftingRemainingItem();
            r.setCount(this.sugar.count());
            remainders.add(r);
        }
        if (input.main().hasCraftingRemainingItem()) {
            remainders.add(input.main().getCraftingRemainingItem());
        }
        if (input.extra().hasCraftingRemainingItem()) {
            remainders.add(input.extra().getCraftingRemainingItem());
        }

        return NonNullList.copyOf(remainders);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(this.milk.ingredient(), this.sugar.ingredient(), this.main, this.extra);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.COMMON_REFINING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.REFINING.get();
    }

    public static class Serializer implements RecipeSerializer<RefiningRecipe>
    {
        public static final Codec<SizedIngredient> ANY_SIZED = Codec.withAlternative(SizedIngredient.FLAT_CODEC,
                                                                                     SizedIngredient.NESTED_CODEC);
        public static final MapCodec<RefiningRecipe> CODEC = RecordCodecBuilder.mapCodec(
                inst -> inst.group(
                                    ANY_SIZED.fieldOf("milk").forGetter(RefiningRecipe::milk),
                                    ANY_SIZED.fieldOf("sugar").forGetter(RefiningRecipe::sugar),
                                    Ingredient.CODEC.fieldOf("main").forGetter(RefiningRecipe::main),
                                    Ingredient.CODEC.fieldOf("extra").forGetter(RefiningRecipe::extra),
                                    ItemStack.CODEC.fieldOf("result").forGetter(RefiningRecipe::result)
                            )
                            .apply(inst, RefiningRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, RefiningRecipe> STREAM_CODEC = StreamCodec.composite(
                SizedIngredient.STREAM_CODEC, RefiningRecipe::milk,
                SizedIngredient.STREAM_CODEC, RefiningRecipe::sugar,
                Ingredient.CONTENTS_STREAM_CODEC, RefiningRecipe::main,
                Ingredient.CONTENTS_STREAM_CODEC, RefiningRecipe::extra,
                ItemStack.STREAM_CODEC, RefiningRecipe::result,
                RefiningRecipe::new
        );

        @Override
        public MapCodec<RefiningRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RefiningRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
