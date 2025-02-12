package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class ModModelProvider extends ModelProvider
{
    public ModModelProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID);
    }

    // FUCK MOJANG!
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
//        itemModels.generateFlatItem();
        registerModel(Sugars.SPEED.value().toModelId(), itemModels);
        itemModels.generateFlatItem(ItemRegistry.GUMMY_ITEM.get(), ModelTemplates.FLAT_ITEM);

    }

    private ResourceLocation registerModel(ResourceLocation location, ItemModelGenerators itemModels) {
        ResourceLocation id = location.withPrefix("item/");
        return ModelTemplates.FLAT_ITEM.create(id, TextureMapping.layer0(id), itemModels.modelOutput);
    }
}
