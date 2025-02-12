package com.lnatit.ccw.data.models;

import net.minecraft.client.data.models.ItemModelGenerators;

public class ItemlikeModelGenerator<RegType> extends ItemModelGenerators
{
    public ItemlikeModelGenerator(ItemModelGenerators itemModel) {
        super(itemModel.itemModelOutput, itemModel.modelOutput);
    }
}
