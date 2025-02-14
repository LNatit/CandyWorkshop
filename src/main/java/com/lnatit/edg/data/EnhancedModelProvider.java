package com.lnatit.edg.data;

import com.lnatit.edg.data.model.ClientItemModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class EnhancedModelProvider extends ModelProvider
{
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final ClientItemModelGenerators.ClientItemCollector clientItemCollector;

    public EnhancedModelProvider(PackOutput output, String modId) {
        super(output, modId);
        this.itemInfoPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.clientItemCollector = new ClientItemModelGenerators.ClientItemCollector();
    }

    @Deprecated
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerModels(blockModels, itemModels, new ClientItemModelGenerators(this.clientItemCollector, itemModels));
    }

    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels, ClientItemModelGenerators clientItemModels) {
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        // No validation needed maybe
        return CompletableFuture.allOf(super.run(output), this.clientItemCollector.save(output, this.itemInfoPathProvider));
    }

    public static ResourceLocation registerModel(ResourceLocation location, ItemModelGenerators itemModels) {
        ResourceLocation id = location.withPrefix("item/");
        return ModelTemplates.FLAT_ITEM.create(id, TextureMapping.layer0(id), itemModels.modelOutput);
    }
}
