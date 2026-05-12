package com.lnatit.ccw.misc;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.GummyGlazerItem;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.component.GlazerMode;
import com.lnatit.ccw.misc.network.UpdateGlazerModePayload;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT, modid = CandyWorkshop.MODID)
public interface KeyRegistry {
    Lazy<KeyMapping> SWITCH_MODE = Lazy.of(() -> new KeyMapping(
            "key.ccw.switch_mode",
            KeyConflictContext.GUI,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_D,
            KeyMapping.Category.MISC));

    @SubscribeEvent
    static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(SWITCH_MODE.get());
    }

    @SubscribeEvent
    static void onKeyPressed(ScreenEvent.KeyPressed.Pre event) {
        if (event.getScreen() instanceof AbstractContainerScreen<?> screen
                && SWITCH_MODE.get().isActiveAndMatches(InputConstants.getKey(event.getKeyEvent()))) {
            Player player = Minecraft.getInstance().player;
            Slot slot = screen.getHoveredSlot();
            if (slot != null && player != null
                    && slot.allowModification(player)
                    && slot.hasItem()
                    && slot.getItem().getItem() instanceof GummyGlazerItem) {
                GlazerMode old = GlazerMode.getOrDefault(slot.getItem());
                GlazerMode newMode = old == GlazerMode.SAVE ? GlazerMode.EXTEND : GlazerMode.SAVE;
                slot.getItem().set(ItemRegistry.GLAZER_MODE_DCTYPE, newMode);
                // Notify server
                ClientPacketDistributor.sendToServer(new UpdateGlazerModePayload(slot.index, newMode));
                event.setCanceled(true);
            }
        }
    }
}
