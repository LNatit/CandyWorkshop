package com.lnatit.ccw.misc.data;

import com.lnatit.ccw.CandyWorkshop;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class AttachmentRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, CandyWorkshop.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<SugarStat>> SUGAR_STAT = ATTACHMENT_TYPES.register("sugar_stat", SugarStat::type);

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getOriginal().hasData(SUGAR_STAT)) {
            event.getEntity().setData(SUGAR_STAT, event.getOriginal().getData(SUGAR_STAT));
        }
    }
}
