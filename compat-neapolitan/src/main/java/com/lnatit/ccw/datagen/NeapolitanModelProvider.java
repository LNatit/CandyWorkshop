package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.NeapolitanCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class NeapolitanModelProvider extends CoreModelProvider
{
    public NeapolitanModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        sugarModel(NeapolitanCompats.HOOHOO_HAHA);
        sugarModel(NeapolitanCompats.HOOHOO_SMOOTH);
        sugarModel(NeapolitanCompats.MINT);
        sugarModel(NeapolitanCompats.RED_BEAN);
        sugarModel(NeapolitanCompats.VANILLA);
        sugarModel(NeapolitanCompats.SUGAR_RUSH);
    }
}

