package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.compat.ModConstants;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.data.IFormula;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RefiningRecipe;
import com.lnatit.ccw.item.sugaring.Flavors;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefiningCategory extends AbstractRecipeCategory<List<? extends IFormula>>
{
    @Nullable
    public static List<ItemStack> MILK;
    public static Map<TagKey<Item>, List<ItemStack>> SUGARS = new HashMap<>();

    public RefiningCategory(IGuiHelper guiHelper) {
        super(CandyWorkshopPlugin.REFINING,
                ModConstants.TITLE,
                guiHelper.createDrawableItemLike(BlockRegistry.SUGAR_REFINERY),
                150,
                66);
    }

    @Override
    public void draw(
            List<? extends IFormula> recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphics guiGraphics,
            double mouseX,
            double mouseY
    ) {
        guiGraphics.blitSprite(ModConstants.BACKGROUND, 0, 0, 150, 66);
        guiGraphics.blitSprite(ModConstants.ANIMATION_SPRITE, 61, 5, 28, 19);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, List<? extends IFormula> recipe, IFocusGroup focuses) {
        if (recipe.isEmpty()) {
            return;
        }
        // using unsafe here will cause a display glitch
        builder.addInputSlot(13, 7).addItemStacks(getMilk(recipe));
        builder.addInputSlot(38, 7).addItemStacks(getSugar(recipe));
        builder.addInputSlot(96, 7).addIngredients(getMain(recipe));
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            builder.addInputSlot(120, 7).addIngredients(refiningRecipe.extra());
        }
        else {
            builder.addInputSlot(120, 7).addItemStacks(getExtra(recipe));
        }
        builder.addOutputSlot(67, 39).addItemStacks(getOutput(recipe));
    }

    private static List<ItemStack> getMilk(List<? extends IFormula> recipe) {
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            // Refining Recipe only has one entry
            return List.of(refiningRecipe.milk().getItems());
        }

        if (MILK == null) {
            List<ItemStack> milk = new ArrayList<>();
            List<Holder<Item>> items = new ArrayList<>();

            BuiltInRegistries.ITEM.getTagOrEmpty(ItemRegistry.FOODS_MILK_TAG).forEach(items::add);
            BuiltInRegistries.ITEM.getTagOrEmpty(ItemRegistry.DRINKS_MILK_TAG).forEach(i -> {
                if (!items.contains(i)) {
                    items.add(i);
                }
            });

            items.forEach(holder -> {
                ItemStack stack = new ItemStack(holder);
                if (holder.is(ItemRegistry.CARTON_MILK_TAG)) {
                    stack.setCount(8);
                }
                milk.add(stack);
            });

            MILK = milk;
        }

        return MILK;
    }

    private static List<ItemStack> getSugar(List<? extends IFormula> recipe) {
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            return List.of(refiningRecipe.sugar().getItems());
        }
        else if (recipe.getFirst() instanceof Formula formula) {
            // for a listed IFormula, they share a same Sugar
            TagKey<Item> tag = formula.sugar().value().type().tag();
            if (!SUGARS.containsKey(tag)) {
                List<ItemStack> stacks = new ArrayList<>();
                BuiltInRegistries.ITEM.getTagOrEmpty(tag)
                        .forEach(item -> stacks.add(new ItemStack(item, Formula.SUGAR_CONSUMPTION)));
                SUGARS.put(tag, stacks);
            }
            return SUGARS.get(tag);
        }
        return List.of();
    }

    private static Ingredient getMain(List<? extends IFormula> recipe) {
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            return refiningRecipe.main();
        }
        else if (recipe.getFirst() instanceof Formula formula) {
            // Share a same main
            return formula.sugar().value().ingredient();
        }
        return Ingredient.EMPTY;
    }

    private static List<ItemStack> getExtra(List<? extends IFormula> recipe) {
        return recipe.stream().map(f -> {
            if (f instanceof Formula formula) {
                if (formula.flavor().is(Flavors.ORIGINAL)) {
                    return ItemStack.EMPTY;
                }
                ItemStack[] items = formula.flavor().value().ingredient().getItems();
                return items.length > 0 ? items[0] : ItemStack.EMPTY;
            }
            return ItemStack.EMPTY;
        }).toList();
    }

    private static List<ItemStack> getOutput(List<? extends IFormula> recipe) {
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            return List.of(refiningRecipe.result());
        }
        return recipe.stream().map(IFormula::result).toList();
    }
}
