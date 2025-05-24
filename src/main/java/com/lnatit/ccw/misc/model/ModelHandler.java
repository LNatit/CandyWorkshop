package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.MilkExtractorItem;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

@Mod.EventBusSubscriber(modid = CandyWorkshop.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        for (RegistryObject<Sugar> sugarHolder : Sugars.SUGARS.getEntries()) {
            event.register(sugarHolder.get().getModelId());
        }
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelMap = event.getModels();
        // TODO model related issues may occur here
        ModelResourceLocation gummy = new ModelResourceLocation(CandyWorkshop.MODID, "gummy", "inventory");
        modelMap.computeIfPresent(gummy, (k, original) -> SugarOverrideHandler.getModel(original));
    }
}
