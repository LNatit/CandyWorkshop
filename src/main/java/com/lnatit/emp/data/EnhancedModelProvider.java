package com.lnatit.emp.data;

import com.lnatit.emp.data.model.ClientItemModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.core.Holder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class EnhancedModelProvider extends ModelProvider
{
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final ClientItemModelGenerators.ClientItemCollector clientItemCollector;

    public EnhancedModelProvider(PackOutput output, String modId) {
        super(output, modId);
        this.itemInfoPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.clientItemCollector = new ClientItemModelGenerators.ClientItemCollector();
    }

    /**
     * @deprecated Call {@link EnhancedModelProvider#registerModels(BlockModelGenerators, ItemModelGenerators, ClientItemModelGenerators)} instead to customize clientItem & models
     */
    @Deprecated
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerModels(blockModels, itemModels, new ClientItemModelGenerators(this.clientItemCollector, itemModels));
    }

    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels, ClientItemModelGenerators clientItemModels) {
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of();
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        // No validation needed maybe
        return CompletableFuture.allOf(super.run(output), this.clientItemCollector.save(output, this.itemInfoPathProvider));
    }
}
