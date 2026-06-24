package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.YoukaisHomecomingCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class YoukaisHomecomingModelProvider extends CoreModelProvider
{
    public YoukaisHomecomingModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        sugarModel(YoukaisHomecomingCompats.GREEN_TEA);
        sugarModel(YoukaisHomecomingCompats.WHITE_TEA);
        sugarModel(YoukaisHomecomingCompats.BLACK_TEA);
        sugarModel(YoukaisHomecomingCompats.OOLONG_TEA);
        sugarModel(YoukaisHomecomingCompats.YOUKAI_COFFEE);
        sugarModel(YoukaisHomecomingCompats.UDUMBARA);
    }
}

