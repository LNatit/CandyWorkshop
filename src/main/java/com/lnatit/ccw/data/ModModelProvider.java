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

        clientItemModels.gen().withId(ItemRegistry.CARTON_MILK).all();
        clientItemModels.gen().withId(ItemRegistry.ENERGY_CARROT).all();
        clientItemModels.gen().withId(ItemRegistry.PHANTOM_PEARL).all();
        clientItemModels.gen().withId(ItemRegistry.CALCIUM_RICH_MILK).all();
        clientItemModels.gen().withId(ItemRegistry.VOID_CARROT).all();
        clientItemModels.gen().withId(ItemRegistry.WEAKNESS_POWDER).all();
        clientItemModels.gen().withId(ItemRegistry.IRON_CLAD_APPLE).all();
        clientItemModels.gen().withId(ItemRegistry.GOLD_STUDDED_APPLE).all();
        clientItemModels.gen().withId(ItemRegistry.BLESSED_STEAK).all();
        clientItemModels.gen().withId(ItemRegistry.GREEDY_OFFERING).all();
        clientItemModels.gen().withId(ItemRegistry.DEFILED_OFFERING).all();
        clientItemModels.gen().withId(ItemRegistry.DOLPHIN_COOKIE).all();
        clientItemModels.gen().withId(ItemRegistry.OMINOUS_FLAG).all();
        clientItemModels.gen().withId(ItemRegistry.MILK_GELATIN).all();

        clientItemModels.gen().withId(Sugars.SPEED.get().getModelId()).all();
    }
}
