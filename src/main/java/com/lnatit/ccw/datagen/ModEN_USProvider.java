package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class ModEN_USProvider extends LanguageProvider
{
    public ModEN_USProvider(PackOutput output) {
        super(output, CandyWorkshop.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("ccw", "Candy Workshop");
        this.add("itemGroup.ccw.candy", "Candy WorkShop");
        this.add("container.sugar_refinery", "Sugar Refinery");
        this.add("container.drawer_table", "Drawer Table");
        this.add("container.sugar_refinery.pause", "Pause Refining...");
        this.add("container.sugar_refinery.start", "Start Refining...");
        this.add(ModSoundProvider.PLUG_OFF_SUBTITLE, "Plug-off");
//        this.add(Tags.getTagTranslationKey(ItemRegistry.CARTON_MILK_TAG), "Carton Milk");

        this.add("stat.ccw.interact_with_sugar_refinery", "Interact With Sugar Refinery");
        this.add("stat.ccw.open_drawer_table", "Open Drawer Table");

        this.add(ItemRegistry.GUMMY_ITEM.get(), "Gummy");
        this.add("sugar.withDuration", "%s (+%s)");

        this.add(ItemRegistry.SUGAR_REFINERY.get(), "Sugar Refinery");
        this.add(ItemRegistry.PLAIN_DRAWER_TABLE.get(), "Plain Drawer Table");
        this.add(ItemRegistry.DRAWER_TABLE.get(), "Drawer Table");
        this.add(ItemRegistry.MILK_EXTRACTOR.get(), "Untellable Tooldles");
        this.add(ItemRegistry.MILK_PACKAGING.get(), "Milk Packaging");
        this.add(ItemRegistry.CARTON_MILK.get(), "Carton Milk");
        this.add(ItemRegistry.NETHER_SUGAR.get(), "Nether Sugar");
        this.add(ItemRegistry.ENDER_SUGAR.get(), "Ender Sugar");

        this.add(ItemRegistry.ENERGY_CARROT.get(), "Energy Carrot");

        this.add(ItemRegistry.SWEET_MELON_SLICE.get(), "some new melon slice");
        this.add(ItemRegistry.PHANTOM_PEARL.get(), "Phantom Pearl");
        this.add(ItemRegistry.CALCIUM_RICH_MILK.get(), "Calcium-rich Milk");
        this.add(ItemRegistry.VOID_CARROT.get(), "Void Carrot");
        this.add(ItemRegistry.WEAKNESS_POWDER.get(), "Weakness Powder");
        this.add(ItemRegistry.IRON_CLAD_APPLE.get(), "Iron-clad Apple");
        this.add(ItemRegistry.GOLD_STUDDED_APPLE.get(), "Gold-studded Apple");
        this.add(ItemRegistry.BLESSED_STEAK.get(), "Blessed Steak");
        this.add(ItemRegistry.GREEDY_OFFERING.get(), "Greedy Offering");
        this.add(ItemRegistry.DEFILED_OFFERING.get(), "Defiled Offering");
        this.add(ItemRegistry.DOLPHIN_COOKIE.get(), "Dolphin Cookie");
        this.add(ItemRegistry.OMINOUS_FLAG.get(), "Ominous Flag");

        this.add(ItemRegistry.MILK_GELATIN.get(), "Milk Gelatin");

        this.add("item.ccw.milk_extractor,desc0", "");
        this.add("item.ccw.milk_extractor,desc1", "");
        this.add("item.ccw.milk_extractor,desc2", "");

        this.add("item.ccw.carton_milk,desc0", "");
        this.add("item.ccw.carton_milk,desc1", "");

        this.add("item.ccw.gummy.excited.prefix", "Excited");
        this.add("item.ccw.gummy.bold.prefix", "Bold");
        this.add("item.ccw.gummy.milky.prefix", "Milky");

        this.add("item.ccw.gummy.excited.desc", "Excited desc");
        this.add("item.ccw.gummy.bold.desc", "Bold desc");
        this.add("item.ccw.gummy.milky.desc", "Milky desc");

        for (RegistryObject<Sugar> sugar : Sugars.SUGARS.getEntries()) {
            this.add("item." + CandyWorkshop.MODID + ".gummy." + sugar.get().name(), "placeholder:" + sugar.get().name());
        }

        for (ModAdvcmtProvider.AdvancementResources res : ModAdvcmtProvider.AdvancementResources.ALL_RESOURCES) {
            this.add(res.nameKey(), "adv name ph");
            this.add(res.descKey(), "adv desc ph");
        }

        // REI Compat
        this.add("compat.ccw.rei.title", "Refining");
    }
}
