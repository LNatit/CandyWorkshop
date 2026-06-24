package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.ApotheosisCompats;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ApotheosisModelProvider extends CoreModelProvider
{
    public ApotheosisModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ApotheosisCompats.MALIGNANT_BERRY.asItem());
        basicItem(ApotheosisCompats.KNOWLEDGE_BOTTLE.asItem());
        basicItem(ApotheosisCompats.EROSION_SHELL.asItem());
        basicItem(ApotheosisCompats.VITALITY_BERRY.asItem());

        sugarModel(ApotheosisCompats.GRIEVOUS);
        sugarModel(ApotheosisCompats.SUNDERING);
        sugarModel(ApotheosisCompats.VITALITY);
        sugarModel(ApotheosisCompats.KNOWLEDGE);
    }
}

