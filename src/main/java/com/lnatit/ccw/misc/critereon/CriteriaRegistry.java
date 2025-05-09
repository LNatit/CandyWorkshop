package com.lnatit.ccw.misc.critereon;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CriteriaRegistry
{
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES,
                                                                                                 CandyWorkshop.MODID
    );

    public static final DeferredHolder<CriterionTrigger<?>, SimpleModTrigger> GET_FLAVORED_SUGAR = TRIGGERS.register("get_flavored_sugar", SimpleModTrigger::new);
    public static final DeferredHolder<CriterionTrigger<?>, SimpleModTrigger> GET_ALL_SUGAR = TRIGGERS.register("get_all_sugar", SimpleModTrigger::new);

}
