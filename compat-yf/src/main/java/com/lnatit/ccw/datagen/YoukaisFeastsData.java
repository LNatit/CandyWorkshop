package com.lnatit.ccw.datagen;

import com.lnatit.ccw.compat.YoukaisFeastsCompats;
import com.lnatit.ccw.data.Effect;
import dev.xkmc.youkaishomecoming.init.registrate.YHEffects;
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
        Formulas.of()
                .loaded("youkaisfeasts")
                .register(YoukaisFeastsCompats.GREEN_TEA,
                          new Effect(YHEffects.TEA, 600, 1),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisFeastsCompats.WHITE_TEA,
                          Effect.simple(YHEffects.TEA),
                          Effect.simple(YHEffects.REFRESHING),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisFeastsCompats.BLACK_TEA,
                          Effect.simple(YHEffects.TEA),
                          Effect.simple(YHEffects.THICK),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisFeastsCompats.OOLONG_TEA,
                          Effect.simple(YHEffects.TEA),
                          Effect.simple(YHEffects.SMOOTHING),
                          Effect.simple(YHEffects.SOBER))
                .defaultBold()

                .register(YoukaisFeastsCompats.UDUMBARA, Effect.simple(YHEffects.UDUMBARA))
                .defaultBold()
                .clearConditions()
        ;
    }
}
