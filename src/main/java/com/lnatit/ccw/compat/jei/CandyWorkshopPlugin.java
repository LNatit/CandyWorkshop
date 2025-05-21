package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

@JeiPlugin
public class CandyWorkshopPlugin implements IModPlugin
{
    public static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, CandyWorkshop.MODID);

    public static final RecipeType<RefiningRecipe> REFINING = RecipeType.create(CandyWorkshop.MODID, "refining", RefiningRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(new RefiningCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<RefiningRecipe> recipes =
                SugarRefining.sugarRefining.getAllBlends().stream().map(RefiningRecipe::new).toList();
        registration.addRecipes(REFINING, recipes);
    }
}
