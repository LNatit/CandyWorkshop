package com.lnatit.ccw.misc.critereon;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class CriteriaRegistry
{
    public static SimpleTrigger REFINE_FLAVORED_SUGAR = registerTrigger(SimpleTrigger::new, "refine_flavored_sugar");
    public static SimpleTrigger COLLECT_ALL_SUGAR = registerTrigger(SimpleTrigger::new, "collect_all_sugar");
    public static NumericTrigger DEVELOP_DIABETES = registerTrigger(NumericTrigger::new, "develop_diabetes");

    public static void init() {}

    private static <T extends CriterionTrigger<?>> T registerTrigger(Function<ResourceLocation, T> constructor, String name) {
        return CriteriaTriggers.register(
                constructor.apply(ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, name)));
    }
}
