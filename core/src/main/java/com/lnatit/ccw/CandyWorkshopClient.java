package com.lnatit.ccw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = CandyWorkshop.MODID, dist = Dist.CLIENT)
public class CandyWorkshopClient {
    private static boolean folded = true;

    public static boolean folded() {
        return folded;
    }

    public static void setFolded(boolean folded) {
        CandyWorkshopClient.folded = folded;
    }

    public static void playSound(Holder<SoundEvent> unfoldDesc) {
        Minecraft.getInstance()
                .getSoundManager()
                .play(SimpleSoundInstance.forUI(unfoldDesc.value(), 1.0f));
    }
}



