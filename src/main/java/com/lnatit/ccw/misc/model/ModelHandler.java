package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.Map;

@EventBusSubscriber(modid = CandyWorkshop.MODID, value = Dist.CLIENT)
public class ModelHandler {
    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> modelMap = event.getModels();
        ModelResourceLocation sugar = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "items/sugar"));
        modelMap.compute(sugar, (k, original) -> SugarOverrideHandler.getModel(original));
    }
}
