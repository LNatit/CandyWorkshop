package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.compat.ModConstants;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.data.IFormula;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.data.RefiningRecipe;
import com.lnatit.ccw.item.sugaring.Flavors;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefiningCategory extends AbstractRecipeCategory<CandyWorkshopPlugin.RefiningRecipe>
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
            CandyWorkshopPlugin.RefiningRecipe recipe,
            IRecipeSlotsView recipeSlotsView,
            GuiGraphicsExtractor guiGraphics,
            double mouseX,
            double mouseY
    ) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ModConstants.BACKGROUND, 0, 0, 150, 66);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ModConstants.ANIMATION_SPRITE, 61, 5, 28, 19);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CandyWorkshopPlugin.RefiningRecipe recipe, IFocusGroup focuses) {
        // using unsafe here will cause a display glitch
        builder.addInputSlot(13, 7).addItemStacks(getMilk(recipe.formulas()));
        builder.addInputSlot(38, 7).addItemStacks(getSugar(recipe.formulas()));

        switch (recipe.formulas().getFirst()) {
            case Formula formula -> {
                builder.addInputSlot(96, 7).addItemStacks(of(formula.sugar().value().ingredient()));
                builder.addInputSlot(120, 7).addItemStacks(getExtra((List<Formula>) recipe.formulas()));
            }
            case RefiningRecipe refiningRecipe -> {
                builder.addInputSlot(96, 7).add(refiningRecipe.main().display());
                refiningRecipe.extra().ifPresent(ingredient -> builder.addInputSlot(120, 7).add(ingredient));
            }
        }

        builder.addOutputSlot(67, 39).addItemStacks(getOutput(recipe.formulas()));
    }

    @Override
    public @org.jspecify.annotations.Nullable Identifier getIdentifier(CandyWorkshopPlugin.RefiningRecipe recipe) {
        return recipe.id();
    }

    private static List<ItemStack> getMilk(List<? extends IFormula> recipe) {
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            // Refining Recipe only has one entry
            return ofSized(refiningRecipe.milk());
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
            return ofSized(refiningRecipe.sugar());
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

    private static List<ItemStack> getExtra(List<Formula> recipe) {
        return recipe.stream().map(f -> {
            if (f.flavor().is(Flavors.ORIGINAL)) {
                return ItemStack.EMPTY;
            }
            return of(f.flavor().value().ingredient()).getFirst();
        }).toList();
    }

    private static List<ItemStack> getOutput(List<? extends IFormula> recipe) {
        if (recipe.getFirst() instanceof RefiningRecipe refiningRecipe) {
            return List.of(refiningRecipe.result());
        }
        return recipe.stream().map(IFormula::result).toList();
    }

    private static List<ItemStack> of(com.lnatit.ccw.item.sugaring.Ingredient ingredient) {
        switch (ingredient) {
            case com.lnatit.ccw.item.sugaring.Ingredient.Listed listed -> {
                return listed.passes().stream().map(ItemStack::new).toList();
            }
            case com.lnatit.ccw.item.sugaring.Ingredient.Tagged tagged -> {
                ArrayList<ItemStack> stacks = new ArrayList<>();
                BuiltInRegistries.ITEM.getTagOrEmpty(tagged.tag()).forEach(item -> stacks.add(new ItemStack(item)));
                return stacks;
            }
        }
    }

    private static List<ItemStack> ofSized(SizedIngredient sized) {
        return sized.ingredient().items().map(i -> new ItemStack(i, sized.count())).toList();
    }
}
