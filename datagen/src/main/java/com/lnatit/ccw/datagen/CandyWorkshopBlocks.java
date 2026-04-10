package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CandyWorkshopBlocks extends ModModelProviders.Block
{
    public CandyWorkshopBlocks(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile sugarRefinery = models().withExistingParent("sugar_refinery", modLoc("block/sugar_refinery"));
        ModelFile plainDrawerTable = models().withExistingParent("plain_drawer_table",
                                                                 modLoc("block/plain_drawer_table")
        );
        ModelFile drawerTable = models().withExistingParent("drawer_table", modLoc("block/drawer_table"));

        horizontalBlock(BlockRegistry.SUGAR_REFINERY.get(), sugarRefinery);
        horizontalBlock(BlockRegistry.PLAIN_DRAWER_TABLE.get(), plainDrawerTable);
        horizontalBlock(BlockRegistry.DRAWER_TABLE.get(), drawerTable);
    }
}
