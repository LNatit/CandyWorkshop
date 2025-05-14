package com.lnatit.ccw.compat.rei;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;

@REIPluginClient
public class CandyWorkshopPlugin implements REIClientPlugin
{
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new RefiningCateglory());
        registry.addWorkstations(RefiningCateglory.REFINING, EntryStacks.of(ItemRegistry.SUGAR_REFINERY));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.beginFiller(SugarRefining.Blend.class)
                .fill(RefiningDisplay::new);

        SugarRefining.sugarRefining.getAllBlends().forEach(registry::add);
    }
}
