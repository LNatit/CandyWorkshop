package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.crafting.RecipeRegistry;
import com.lnatit.ccw.item.crafting.RefiningInput;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record RefiningRecipe(SizedIngredient milk,
                             SizedIngredient sugar,
                             Ingredient main,
                             Optional<Ingredient> extra,
                             ItemStackTemplate resultTemplate) implements IFormula, Recipe<RefiningInput>
{
    public static final MapCodec<RefiningRecipe> MAP_CODEC =
            RecordCodecBuilder.mapCodec(inst -> inst.group(SizedIngredient.NESTED_CODEC.fieldOf("milk")
                                                                                       .forGetter(RefiningRecipe::milk),
                                                           SizedIngredient.NESTED_CODEC.fieldOf("sugar")
                                                                                       .forGetter(RefiningRecipe::sugar),
                                                           Ingredient.CODEC.fieldOf("main")
                                                                           .forGetter(RefiningRecipe::main),
                                                           Ingredient.CODEC.optionalFieldOf("extra")
                                                                           .forGetter(RefiningRecipe::extra),
                                                           ItemStackTemplate.CODEC.fieldOf("resultTemplate")
                                                                                  .forGetter(RefiningRecipe::resultTemplate))
                                                    .apply(inst, RefiningRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, RefiningRecipe> STREAM_CODEC = StreamCodec.composite(
            SizedIngredient.STREAM_CODEC,
            RefiningRecipe::milk,
            SizedIngredient.STREAM_CODEC,
            RefiningRecipe::sugar,
            Ingredient.CONTENTS_STREAM_CODEC,
            RefiningRecipe::main,
            ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC),
            RefiningRecipe::extra,
            ItemStackTemplate.STREAM_CODEC,
            RefiningRecipe::resultTemplate,
            RefiningRecipe::new);

    public static final RecipeSerializer<RefiningRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    @Override
    public boolean matches(RefiningInput input, Level level) {
        return this.milk.test(input.milk())
               && this.sugar.test(input.sugar())
               && this.main.test(input.main())
               && (this.extra.isEmpty() || this.extra.get().test(input.extra()));
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
    public ItemStack result() {
        return resultTemplate.create();
    }

    @Override
    public ItemStack productionOf(RefiningInput input) {
        return this.resultTemplate.create();
    }

    @Override
    public ItemStack batch(RefiningInput input, Consumer<ItemStack> remainderHandler) {
        IFormula.shrinkAndHandleRemainders(input.milk(), this.milk.count(), remainderHandler);
        IFormula.shrinkAndHandleRemainders(input.sugar(), this.sugar.count(), remainderHandler);
        if (!this.main.isEmpty()) {
            IFormula.shrinkAndHandleRemainders(input.main(), remainderHandler);
        }
        if (this.extra.isPresent()) {
            IFormula.shrinkAndHandleRemainders(input.extra(), remainderHandler);
        }
        return this.resultTemplate.create();
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
        return PlacementInfo.createFromOptionals(List.of(Optional.of(this.milk.ingredient()),
                                                         Optional.of(this.sugar.ingredient()),
                                                         Optional.of(this.main),
                                                         this.extra));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeRegistry.REFINING_CATEGORY.get();
    }
}
