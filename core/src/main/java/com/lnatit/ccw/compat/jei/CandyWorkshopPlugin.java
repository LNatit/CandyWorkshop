package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.data.IFormula;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RecipeRegistry;
import com.lnatit.ccw.menu.MenuRegistry;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import com.lnatit.ccw.menu.client.SugarRefineryScreen;
import com.lnatit.ccw.misc.RegRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CandyWorkshopPlugin implements IModPlugin
{
    public static ResourceLocation UID =
            CandyWorkshop.id(CandyWorkshop.MODID);

    @SuppressWarnings("unchecked")
    public static final RecipeType<List<? extends IFormula>> REFINING =
            RecipeType.create(CandyWorkshop.MODID, "refining", (Class<List<? extends IFormula>>) (Class<?>) List.class);

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ItemRegistry.GUMMY.get(), GummySubtypeInterpreter.INSTANCE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(new RefiningCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<List<? extends IFormula>> recipes = new ArrayList<>();
        ClientLevel level = Minecraft.getInstance().level;
        assert level != null;
        RecipeManager manager = level.getRecipeManager();
        manager.getAllRecipesFor(RecipeRegistry.REFINING.get()).forEach(recipe -> recipes.add(List.of(recipe.value())));
        level.registryAccess()
             .registry(RegRegistry.FLAVOR_KEY)
             .ifPresent(flavorReg -> level.registryAccess()
                                          .registry(RegRegistry.SUGAR_KEY)
                                          .ifPresent(registry -> registry.holders().forEach(sugar -> {
                                              List<Formula> formulas = new ArrayList<>();
                                              flavorReg.holders()
                                                       .forEach(flavor -> Formula.getFormulaOptional(sugar, flavor)
                                                                                 .ifPresent(formulas::add));
                                              recipes.add(formulas);
                                          })));

        registration.addRecipes(REFINING, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ItemRegistry.SUGAR_REFINERY.get(), REFINING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SugarRefineryScreen.class, 108, 43, 39, 16, REFINING);
        registration.addRecipeClickArea(SugarRefineryScreen.class, 24, 43, 39, 18, REFINING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(SugarRefineryMenu.class,
                                              MenuRegistry.SUGAR_REFINERY.get(),
                                              REFINING,
                                              0,
                                              4,
                                              8,
                                              36);
    }
}
