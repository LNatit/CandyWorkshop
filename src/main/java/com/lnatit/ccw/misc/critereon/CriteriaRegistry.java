package com.lnatit.ccw.misc.critereon;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CriteriaRegistry
{
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(
            BuiltInRegistries.TRIGGER_TYPES,
            CandyWorkshop.MODID
    );

    public static final DeferredHolder<CriterionTrigger<?>, SimpleTrigger> REFINE_FLAVORED_SUGAR = TRIGGERS.register(
            "refine_flavored_sugar", SimpleTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SimpleTrigger> COLLECT_ALL_SUGAR = TRIGGERS.register(
            "collect_all_sugar", SimpleTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, NumericTrigger> DEVELOP_DIABETES = TRIGGERS.register(
            "develop_diabetes", NumericTrigger::new);
}
