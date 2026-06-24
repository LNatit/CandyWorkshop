package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.SoundRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider
{
    public static final String PLUG_OFF_SUBTITLE = "sound.ccw.plug_off";

    protected ModSoundProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID);
    }

    @Override
    public void registerSounds() {
        // Accepts a Supplier<SoundEvent>, a SoundEvent, or a Identifier as the first parameter.
        add(SoundRegistry.PLUG_OFF,
            SoundDefinition.definition()
                           .with(
                                   sound("ccw:plug_off")
                           )
                           .subtitle(PLUG_OFF_SUBTITLE)
        );
        add(SoundRegistry.SWITCH_MODE,
            SoundDefinition.definition()
                           .with(
                                   sound("ccw:switch_mode_1"),
                                   sound("ccw:switch_mode_2")
                           )
        );
        add(SoundRegistry.UNFOLD_DESC,
            SoundDefinition.definition()
                           .with(
                                   sound("ccw:unfold_desc")
                           )
        );
        add(SoundRegistry.FOLD_DESC,
            SoundDefinition.definition()
                           .with(
                                   sound("ccw:fold_desc")
                           )
        );
    }
}
