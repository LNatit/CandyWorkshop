package com.lnatit.ccw.misc.critereon;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.function.Function;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class CriteriaHandler
{
    public static SimpleTrigger REFINE_FLAVORED_SUGAR;
    public static SimpleTrigger COLLECT_ALL_SUGAR;
    public static NumericTrigger DEVELOP_DIABETES;

    // TODO check event
    @SubscribeEvent
    public static void onTriggerRegistry(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REFINE_FLAVORED_SUGAR = registerTrigger(SimpleTrigger::new, "refine_flavored_sugar");
            COLLECT_ALL_SUGAR = registerTrigger(SimpleTrigger::new, "collect_all_sugar");
            DEVELOP_DIABETES = registerTrigger(NumericTrigger::new, "develop_diabetes");
        });
    }

    @SubscribeEvent
    public static void onStatAward(Award event) {
        if (event.getEntity() instanceof ServerPlayer player && event.getStat().getType() == Stats.ITEM_USED && event.getStat().getValue() == ItemRegistry.GUMMY_ITEM.get()) {
            // The entity in is always ServerPlayer
            DEVELOP_DIABETES.trigger(player, event.getValue());
        }
    }

    private static <T extends CriterionTrigger<?>> T registerTrigger(Function<ResourceLocation, T> constructor, String name) {
        return CriteriaTriggers.register(constructor.apply(ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, name)));
    }
}
