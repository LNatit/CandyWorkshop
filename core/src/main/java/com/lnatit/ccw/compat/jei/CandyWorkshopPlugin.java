package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.RecipeHelper;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.data.IFormula;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RecipeRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.menu.MenuRegistry;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import com.lnatit.ccw.menu.client.SugarRefineryScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JeiPlugin
public class CandyWorkshopPlugin implements IModPlugin
{
    public static Identifier UID =
            CandyWorkshop.id(CandyWorkshop.MODID);

    @SuppressWarnings("unchecked")
    public static final IRecipeType<List<? extends IFormula>> REFINING =
            IRecipeType.create(CandyWorkshop.MODID, "refining", (Class<List<? extends IFormula>>) (Class<?>) List.class);

    @Override
    public Identifier getPluginUid() {
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
        RecipeHelper.getRecipesByType(level, RecipeRegistry.REFINING.get()).forEach(recipe -> recipes.add(List.of(recipe.value())));
        level.registryAccess()
                .lookup(Formula.KEY)
                .ifPresent(formulaReg -> {
                    Map<Holder<Sugar>, List<Formula>> grouped = new HashMap<>();
                    for (var entry : formulaReg.entrySet()) {
                        Formula formula = entry.getValue();
                        grouped.computeIfAbsent(formula.sugar(), k -> new ArrayList<>()).add(formula);
                    }
                    recipes.addAll(grouped.values());
                });

        registration.addRecipes(REFINING, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(REFINING, ItemRegistry.SUGAR_REFINERY.get());
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
