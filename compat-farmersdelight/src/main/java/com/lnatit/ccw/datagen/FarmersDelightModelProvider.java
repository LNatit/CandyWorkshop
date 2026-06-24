package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.FarmersDelightCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FarmersDelightModelProvider extends CoreModelProvider
{
    public FarmersDelightModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(FarmersDelightCompats.GLAZED_MEAT_RICE.get());

        sugarModel(FarmersDelightCompats.NOURISHED);
    }
}

