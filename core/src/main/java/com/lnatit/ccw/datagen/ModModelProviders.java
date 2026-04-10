package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface ModModelProviders
{
    abstract class Block extends BlockStateProvider
    {
        public Block(PackOutput output, ExistingFileHelper exFileHelper) {
            super(output, CandyWorkshop.MODID, exFileHelper);
        }

        @Override
        protected abstract void registerStatesAndModels();
    }

    abstract class Item extends ItemModelProvider
    {
        public Item(PackOutput output, ExistingFileHelper existingFileHelper) {
            super(output, CandyWorkshop.MODID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            for (DeferredHolder<Sugar, ?> sugar : Sugars.SUGARS.getEntries()) {
                basicItem(Sugar.getItemModel(sugar));
            }
        }
    }
}
