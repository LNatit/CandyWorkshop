package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.GummyGlazerItem;
import com.lnatit.ccw.item.GummyMagazineItem;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.SmithingWafers;
import com.lnatit.ccw.item.component.GlazerMode;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class CoreEN_USProvider extends LanguageProvider {
    public CoreEN_USProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (var flavor : Flavors.FLAVORS.getEntries()) {
            if (flavor == Flavors.ORIGINAL)
                continue;
            this.add("item.ccw.gummy." + flavor.getKey().identifier().getPath() + ".prefix", "prefix: " + flavor.getRegisteredName());
            this.add("item.ccw.gummy." + flavor.getKey().identifier().getPath() + ".desc", "desc: " + flavor.getRegisteredName());
        }

        for (DeferredHolder<Sugar, ? extends Sugar> sugar : Sugars.SUGARS.getEntries()) {
            this.add("item." + CandyWorkshop.MODID + ".gummy." + ((Holder<?>) sugar).getKey().identifier().getPath(), "placeholder:" + ((Holder<?>) sugar)
                    .getKey()
                    .identifier()
                    .getPath());
        }
    }
}
