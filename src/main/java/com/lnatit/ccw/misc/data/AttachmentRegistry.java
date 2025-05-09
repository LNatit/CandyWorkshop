package com.lnatit.ccw.misc.data;

import com.lnatit.ccw.CandyWorkshop;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AttachmentRegistry
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, CandyWorkshop.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SugarStat>> SUGAR_STAT = ATTACHMENT_TYPES.register("sugar_stat", SugarStat::type);
}
