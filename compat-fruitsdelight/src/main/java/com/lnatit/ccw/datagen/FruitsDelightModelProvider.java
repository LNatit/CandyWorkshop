package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.FruitsDelightCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FruitsDelightModelProvider extends CoreModelProvider
{
    public FruitsDelightModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        sugarModel(FruitsDelightCompats.BLUEBERRY);
        sugarModel(FruitsDelightCompats.HAWTHORN);
        sugarModel(FruitsDelightCompats.MANGO);
        sugarModel(FruitsDelightCompats.ORANGE);
        sugarModel(FruitsDelightCompats.PEACH);
        sugarModel(FruitsDelightCompats.PEAR);
        sugarModel(FruitsDelightCompats.PERSIMMON);
        sugarModel(FruitsDelightCompats.PINEAPPLE);
        sugarModel(FruitsDelightCompats.LEMON);
        sugarModel(FruitsDelightCompats.CRANBERRY);
        sugarModel(FruitsDelightCompats.MANGOSTEEN);
        sugarModel(FruitsDelightCompats.CHORUS);
        sugarModel(FruitsDelightCompats.BAYBERRY);
        sugarModel(FruitsDelightCompats.KIWI);
        sugarModel(FruitsDelightCompats.FIG);
        sugarModel(FruitsDelightCompats.DURIAN);
    }
}

