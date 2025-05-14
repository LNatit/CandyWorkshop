package com.lnatit.ccw.compat.rei.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.rei.RefiningDisplay;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import com.lnatit.ccw.menu.client.SugarRefineryScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class CandyWorkshopPlugin implements REIClientPlugin
{
    public static final CategoryIdentifier<RefiningDisplay> REFINING = CategoryIdentifier.of(CandyWorkshop.MODID, "plugin/refining");

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new RefiningCateglory());
        registry.addWorkstations(REFINING, EntryStacks.of(ItemRegistry.SUGAR_REFINERY));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.beginFiller(SugarRefining.Blend.class)
                .fill(RefiningDisplay::new);
        // Is it ok to add here? Where to handle the network issues?
        SugarRefining.sugarRefining.getAllBlends().forEach(registry::add);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(
                new Rectangle(108, 43, 40, 17),
                SugarRefineryScreen.class,
                REFINING
        );
    }

    @Override
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        registry.register(
                SimpleTransferHandler.create(
                        SugarRefineryMenu.class,
                        REFINING,
                        new SimpleTransferHandler.IntRange(0, 3)
                )
        );
    }
}
