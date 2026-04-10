package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface SoundRegistry
{
    DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CandyWorkshop.MODID);

    DeferredHolder<SoundEvent, SoundEvent> PLUG_OFF = SOUND_EVENTS.register(
            "plug_off",
            SoundEvent::createVariableRangeEvent
    );
}
