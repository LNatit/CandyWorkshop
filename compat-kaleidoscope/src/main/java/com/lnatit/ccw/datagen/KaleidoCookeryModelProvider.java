package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.CookeryCompat;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class KaleidoCookeryModelProvider extends CoreModelProvider
{
    public KaleidoCookeryModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(CookeryCompat.SWEET_AND_SOUR_MEAT_SAUCE.getId());
        basicItem(CookeryCompat.FRESH_VEGGIE_SAUCE.getId());
        basicItem(CookeryCompat.LACTO_BOLT_RED_SAUCE.getId());
        basicItem(CookeryCompat.SASHIMI_SIDE_SAUCE.getId());
        basicItem(CookeryCompat.ULTRA_RICH_MARROW_CREAM.getId());
        basicItem(CookeryCompat.SILENCED_SAUCE.getId());
        basicItem(CookeryCompat.HEARTH_WARM_MARROW_SAUCE.getId());
        basicItem(CookeryCompat.LAMB_CARROT_SAUCE.getId());

        sugarModel(CookeryCompat.SWEET_AND_SOUR_MEAT);
        sugarModel(CookeryCompat.VEGGIE_FRESH);
        sugarModel(CookeryCompat.LACTO_BOLT);
        sugarModel(CookeryCompat.SASHIMI);
        sugarModel(CookeryCompat.TALLOW);
        sugarModel(CookeryCompat.PHANTO_BYE);
        sugarModel(CookeryCompat.BROTH_WARM);
        sugarModel(CookeryCompat.MUTTON_DASH);
    }
}

