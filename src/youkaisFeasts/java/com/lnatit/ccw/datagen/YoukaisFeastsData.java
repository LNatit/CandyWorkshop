package com.lnatit.ccw.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class YoukaisFeastsData extends ModDataProviders
{
    public YoukaisFeastsData(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "CandyWorkshop x YoukaisFeasts - Datapack";
    }

    static {

    }
}
