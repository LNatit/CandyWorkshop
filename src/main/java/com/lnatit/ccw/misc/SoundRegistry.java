package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.sounds.SoundEvent.createVariableRangeEvent;

public class SoundRegistry
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, CandyWorkshop.MODID);

    public static final RegistryObject<SoundEvent> PLUG_OFF = SOUND_EVENTS.register(
            "plug_off",
            () -> createVariableRangeEvent(new ResourceLocation(CandyWorkshop.MODID, "plug_off"))
    );
}
