package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.YoukaisFeastsCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class YoukaisFeastsModelProvider extends CoreModelProvider
{
    public YoukaisFeastsModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        sugarModel(YoukaisFeastsCompats.GREEN_TEA);
        sugarModel(YoukaisFeastsCompats.WHITE_TEA);
        sugarModel(YoukaisFeastsCompats.BLACK_TEA);
        sugarModel(YoukaisFeastsCompats.OOLONG_TEA);
        sugarModel(YoukaisFeastsCompats.UDUMBARA);
    }
}

