package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEN_USProvider extends LanguageProvider {
    public ModEN_USProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("ccw", "Candy Workshop");
        this.add("itemGroup.ccw.candy", "Candy WorkShop");
        this.add("container.sugar_refinery", "Sugar Refinery");
        this.add("container.sugar_refinery.pause", "Pause Refining...");
        this.add("container.sugar_refinery.start", "Start Refining...");
        this.add(ModSoundProvider.PLUG_OFF_SUBTITLE, "Plug-off");

        this.add(ItemRegistry.GUMMY_ITEM.get(), "Gummy");
        this.add(ItemRegistry.SUGAR_REFINERY.get(), "Sugar Refinery");
        this.add(ItemRegistry.MILK_SUCKER.get(), "Untellable Tooldles");
        this.add(ItemRegistry.CARTON_MILK.get(), "Carton Milk");
        this.add(ItemRegistry.ENERGY_CARROT.get(), "Energy Carrot");
        this.add(ItemRegistry.PHANTOM_PEARL.get(), "Phantom Pearl");
        this.add(ItemRegistry.CALCIUM_RICH_MILK.get(), "Calcium-rich Milk");
        this.add(ItemRegistry.VOID_CARROT.get(), "Void Carrot");
        this.add(ItemRegistry.WEAKNESS_POWDER.get(), "Weakness Powder");
        this.add(ItemRegistry.IRON_CLAD_APPLE.get(), "Iron-clad Apple");
        this.add(ItemRegistry.GOLD_STUDDED_APPLE.get(), "Gold-studded Apple");
        this.add(ItemRegistry.BLESSED_STEAK.get(), "Blessed Steak");
        this.add(ItemRegistry.GREEDY_OFFERING.get(), "Greedy Offering");
        this.add(ItemRegistry.DEFILED_OFFERING.get(), "Defiled Offering");
        this.add(ItemRegistry.DOLPHIN_COOKIE.get(), "Dolphin Cookie");
        this.add(ItemRegistry.OMINOUS_FLAG.get(), "Ominous Flag");
        this.add(ItemRegistry.MILK_GELATIN.get(), "Milk Gelatin");

        for (DeferredHolder<Sugar, ? extends Sugar> sugar : Sugars.SUGARS.getEntries()) {
            for (Sugar.Flavor flavor : sugar.get().getAvailableTypes()) {
                this.add("item." + CandyWorkshop.MODID + ".gummy." + sugar.get().name() + "." + flavor.name, "placeholder");
            }
        }
    }
}
