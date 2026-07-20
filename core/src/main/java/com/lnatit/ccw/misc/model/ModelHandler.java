package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.model.standalone.SimpleUnbakedStandaloneModel;

@EventBusSubscriber(modid = CandyWorkshop.MODID, value = Dist.CLIENT)
public interface ModelHandler
{
//    Identifier BROKEN = CandyWorkshop.id("broken");
//
//    @SubscribeEvent
//    static void onPropertyRegister(FMLClientSetupEvent event) {
//        event.enqueueWork(() -> ItemProperties.register(ItemRegistry.MILK_EXTRACTOR.get(),
//                                                        BROKEN,
//                                                        (stack, level, entity, seed) -> MilkExtractorItem.isBroken(stack)
//                                                                                        ? 1.0F
//                                                                                        : 0.0F));
//    }

//    Identifier GUMMY_GLAZER = ModelIdentifier.standalone(CandyWorkshop.id("item/gummy_glazer_base"));
//    Identifier NETHER_GLAZER = ModelIdentifier.standalone(CandyWorkshop.id("item/nether_glazer_base"));
//    Identifier ENDER_GLAZER = ModelIdentifier.standalone(CandyWorkshop.id("item/ender_glazer_base"));

//    @SubscribeEvent
//    static void onModelRegister(ModelEvent.RegisterStandalone event) {
//        for (Holder<Sugar> sugarHolder : Sugars.SUGARS.getEntries()) {
//            event.register(Sugar.getModelId(sugarHolder));
//        }
//
//        event.register(GUMMY_GLAZER, SimpleUnbakedStandaloneModel.simpleModelWrapper(GUMMY_GLAZER));
//        event.register(NETHER_GLAZER, SimpleUnbakedStandaloneModel.simpleModelWrapper(NETHER_GLAZER));
//        event.register(ENDER_GLAZER, SimpleUnbakedStandaloneModel.simpleModelWrapper(ENDER_GLAZER));
//    }

//    @SubscribeEvent
//    static void onModelBake(ModelEvent.ModifyBakingResult event) {
//        Map<ModelIdentifier, BakedModel> modelMap = event.getModels();
//        ModelIdentifier gummy = ModelIdentifier.inventory(ItemRegistry.GUMMY.getId());
//        modelMap.compute(gummy, (k, original) -> SugarOverrideHandler.getModel(original));
//    }

    @SubscribeEvent // on the mod event bus only on the physical client
    static void registerSpecialRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(
                // The name to reference as the type
                CandyWorkshop.id("glazer"),
                // The map codec
                GummyGlazerRenderer.Unbaked.MAP_CODEC
        );
    }
}
