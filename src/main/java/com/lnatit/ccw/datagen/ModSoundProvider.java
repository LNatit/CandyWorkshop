package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.misc.SoundRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider
{
    public static final String PLUG_OFF_SUBTITLE = "sound.ccw.plug_off";

    protected ModSoundProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CandyWorkshop.MODID, existingFileHelper);
    }

    @Override
    public void registerSounds() {
        // Accepts a Supplier<SoundEvent>, a SoundEvent, or a ResourceLocation as the first parameter.
        add(SoundRegistry.PLUG_OFF,
            SoundDefinition.definition()
                           .with(
                                   sound("ccw:plug_off")
                           )
                           .subtitle(PLUG_OFF_SUBTITLE)
                           .replace(true)
        );
    }
}
