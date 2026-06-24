package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugar;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.registries.DeferredHolder;

public abstract class CoreModelProvider extends EnhancedModelProvider
{
    public CoreModelProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID);
    }

    protected void sugarModel(ClientItemModelGenerators clientItemModels, DeferredHolder<Sugar, ?> sugar) {
        clientItemModels.gen().withId(Sugar.getItemModel(sugar)).all();
    }
}
