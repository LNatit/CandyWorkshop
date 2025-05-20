package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.SugarContents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

public class SugarOverrideHandler extends ItemOverrides {
    public static BakedModel getModel(BakedModel model) {
        return new BakedModelWrapper<>(model) {
            @Override
            public ItemOverrides getOverrides() {
                return new SugarOverrideHandler();
            }
        };
    }

    @Override
    public @Nullable BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        if (stack.is(ItemRegistry.GUMMY_ITEM)) {
            SugarContents sugar = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE.get());
            if (sugar != null && sugar.sugar().isPresent()) {
                BakedModel bakedModel =
                        Minecraft.getInstance()
                                .getModelManager()
                                .getModelBakery()
                                .getBakedTopLevelModels()
                                .get(
                                        ModelResourceLocation.inventory(
                                                sugar.sugar()
                                                        .get()
                                                        .value()
                                                        .getModelId()
                                        )
                                );
                return bakedModel.getOverrides().resolve(bakedModel, stack,  level, entity, seed);
            }
        }

        return super.resolve(model, stack, level, entity, seed);
    }
}
