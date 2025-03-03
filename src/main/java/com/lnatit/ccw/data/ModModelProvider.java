package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.emp.data.EnhancedModelProvider;
import com.lnatit.emp.data.model.ClientItemModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public class ModModelProvider extends EnhancedModelProvider
{
    public ModModelProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels, @NotNull ClientItemModelGenerators clientItemModels) {
        blockModels.createGenericCube(BlockRegistry.SUGAR_REFINERY.get());

        itemModels.generateFlatItem(ItemRegistry.GUMMY_ITEM.get(), ModelTemplates.FLAT_ITEM);

        clientItemModels.gen().withId(Sugars.SPEED.get().getModelId()).all();
    }
}
