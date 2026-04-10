package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.YoukaisHomecomingCompats;
import com.lnatit.ccw.data.Effect;
import dev.xkmc.youkaishomecoming.init.registrate.YHEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class YoukaisHomecomingData extends ModDataProviders
{
    public YoukaisHomecomingData(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries
    ) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "CandyWorkshop x YoukaisHomecoming - Datapack";
    }

    static {
        Formulas.of()
                .loaded("youkaishomecoming")
                .register(YoukaisHomecomingCompats.GREEN_TEA,
                          new Effect(YHEffects.TEA, 600, 1),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisHomecomingCompats.WHITE_TEA,
                          Effect.simple(YHEffects.TEA),
                          Effect.simple(YHEffects.REFRESHING),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisHomecomingCompats.BLACK_TEA,
                          Effect.simple(YHEffects.TEA),
                          Effect.simple(YHEffects.THICK),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisHomecomingCompats.OOLONG_TEA,
                          Effect.simple(YHEffects.TEA),
                          Effect.simple(YHEffects.SMOOTHING),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisHomecomingCompats.YOUKAI_COFFEE,
                          Effect.simple(YHEffects.CAFFEINATED),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisHomecomingCompats.UDUMBARA, Effect.simple(YHEffects.UDUMBARA))
                .defaultBold()
                .clearConditions()
        ;
    }

}
