package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
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

        this.add(ItemRegistry.GUMMY_ITEM.get(), "Gummy");
        this.add(ItemRegistry.SUGAR_REFINERY.get(), "Sugar Refinery");
        // TODO more crafting ingredients

        // duplicated
//        this.add(BlockRegistry.SUGAR_REFINERY.get(), "Sugar Refinery");

        for (DeferredHolder<Sugar, ? extends Sugar> sugar : Sugars.SUGARS.getEntries()) {
            for (Sugar.Type type : sugar.get().getAvailableTypes()) {
                this.add("item." + CandyWorkshop.MODID + ".gummy." + sugar.get().name() + "." + type.getSerializedName(), "placeholder");
            }
        }
    }
}
