package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class ModEN_USProvider extends LanguageProvider
{
    public ModEN_USProvider(PackOutput output) {super(output, CandyWorkshop.MODID, "en_us");}

    @Override
    protected abstract void addTranslations();
}
