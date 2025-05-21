package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.MilkExtractorItem;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.Map;

@EventBusSubscriber(modid = CandyWorkshop.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModelHandler
{
    public static final ResourceLocation BROKEN = ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "broken");

    @SubscribeEvent
    public static void onPropertyRegister(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
                ItemRegistry.MILK_EXTRACTOR.get(),
                BROKEN,
                (stack, level, entity, seed) -> MilkExtractorItem.isBroken(stack) ? 1.0F : 0.0F
        ));
    }

    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional event) {
        for (Holder<Sugar> sugarHolder : Sugars.SUGARS.getEntries()) {
            event.register(ModelResourceLocation.standalone(sugarHolder.value().getModelId()));
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ModelResourceLocation, BakedModel> modelMap = event.getModels();
        ModelResourceLocation gummy = ModelResourceLocation.inventory(ItemRegistry.GUMMY_ITEM.getId());
        modelMap.compute(gummy, (k, original) -> SugarOverrideHandler.getModel(original));
    }
}
