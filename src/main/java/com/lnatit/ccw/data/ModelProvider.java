package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModelProvider
{

    public static class Item extends ItemModelProvider
    {
        public Item(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            basicItem(Sugars.SPEED.getId());
        }
    }
}
