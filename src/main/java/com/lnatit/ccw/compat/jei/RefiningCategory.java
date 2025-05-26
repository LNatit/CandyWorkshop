package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.compat.ModConstants;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;

public class RefiningCategory extends AbstractRecipeCategory<RefiningRecipe>
{

    public RefiningCategory(IGuiHelper guiHelper) {
        super(
                CandyWorkshopPlugin.REFINING,
                ModConstants.TITLE,
                guiHelper.createDrawableItemLike(BlockRegistry.SUGAR_REFINERY.get()),
                150,
                66
        );
    }

    @Override
    public void draw(RefiningRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(0, 0, 0, 150, 66, ModConstants.BACKGROUND_SPRITE);
        guiGraphics.blit(61, 5, 0, 28, 19, ModConstants.ANIMATION_SPRITE);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RefiningRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(13, 7)
                .addItemStacks(recipe.getMilk());

        builder.addInputSlot(38, 7)
                .addItemStack(recipe.getSugar());

        builder.addInputSlot(96, 7)
               .addIngredients(recipe.getMain());

        builder.addInputSlot(120, 7)
               .addItemStacks(recipe.getExtra());

        builder.addOutputSlot(67, 39)
                .addItemStacks(recipe.getOutput());
    }
}
