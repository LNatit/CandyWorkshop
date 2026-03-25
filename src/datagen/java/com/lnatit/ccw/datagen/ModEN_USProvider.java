package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.apothesis.ApothesisCompats;
import com.lnatit.ccw.compat.farmersdelight.FarmersDelightCompats;
import com.lnatit.ccw.item.GummyGlazerItem;
import com.lnatit.ccw.item.GummyMagazineItem;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.SmithingWafers;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEN_USProvider extends LanguageProvider {
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
        this.add(Tags.getTagTranslationKey(ItemRegistry.CARTON_MILK_TAG), "Carton Milk");

        this.add("stat.ccw.interact_with_sugar_refinery", "Interact With Sugar Refinery");
        this.add("stat.ccw.open_drawer_table", "Open Drawer Table");

        this.add(ItemRegistry.GUMMY.get(), "Gummy");
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

        this.add(ItemRegistry.CARAMETAL.get(), "Carametal");
        this.add(ItemRegistry.NETHER_SMITHING_WAFER.get(), "Nether Smithing Wafer");
        this.add(ItemRegistry.ENDER_SMITHING_WAFER.get(), "Ender Smithing Wafer");

        this.add(ItemRegistry.GUMMY_MAGAZINE.get(), "Gummy Magazine");
        this.add(ItemRegistry.NETHER_MAGAZINE.get(), "Nether Magazine");
        this.add(ItemRegistry.ENDER_MAGAZINE.get(), "Ender Magazine");
        this.add(ItemRegistry.GUMMY_GLAZER.get(), "Gummy Glazer");
        this.add(ItemRegistry.NETHER_GLAZER.get(), "Nether Glazer");
        this.add(ItemRegistry.ENDER_GLAZER.get(), "Ender Glazer");

//        this.add("item.ccw.milk_extractor,desc0", "");
//        this.add("item.ccw.milk_extractor,desc1", "");
//        this.add("item.ccw.milk_extractor,desc2", "");
//
//        this.add("item.ccw.carton_milk,desc0", "");
//        this.add("item.ccw.carton_milk,desc1", "");

        for (var flavor : Flavors.FLAVORS.getEntries()) {
            if (flavor == Flavors.ORIGINAL)
                continue;
            this.add("item.ccw.gummy." + flavor.getKey().location().getPath() + ".prefix", "prefix: " + flavor.getRegisteredName());
            this.add("item.ccw.gummy." + flavor.getKey().location().getPath() + ".desc", "desc: " + flavor.getRegisteredName());
        }

        // Smithing wafer templates
        this.add(SmithingWafers.NETHER_GLAZE_APPLIES_TO, "placeholder: nether applies to");
        this.add(SmithingWafers.NETHER_GLAZE_INGREDIENTS, "placeholder: nether ingredients");
        this.add(SmithingWafers.NETHER_GLAZE_UPGRADE, "placeholder: nether upgrade");
        this.add(SmithingWafers.NETHER_GLAZE_BASE_SLOT_DESC, "placeholder: nether base slot");
        this.add(SmithingWafers.NETHER_GLAZE_ADDITIONS_SLOT_DESC, "placeholder: nether additions slot");
        this.add(SmithingWafers.ENDER_GLAZE_APPLIES_TO, "placeholder: ender applies to");
        this.add(SmithingWafers.ENDER_GLAZE_INGREDIENTS, "placeholder: ender ingredients");
        this.add(SmithingWafers.ENDER_GLAZE_UPGRADE, "placeholder: ender upgrade");
        this.add(SmithingWafers.ENDER_GLAZE_BASE_SLOT_DESC, "placeholder: ender base slot");
        this.add(SmithingWafers.ENDER_GLAZE_ADDITIONS_SLOT_DESC, "placeholder: ender additions slot");

        this.add(GummyMagazineItem.DESC_UNFOLD_KEY, "- Press [Shift] to Unfold");

        // Gummy Magazine tooltips
        this.add(GummyMagazineItem.DESC_1_KEY, "Consume multiple gummies with one click");
        this.add(GummyMagazineItem.DESC_2_KEY, "Sneak + Right-click to open the GUI");
        this.add(GummyMagazineItem.FOLDED_1_KEY, "+ Targets gummies in §elightning slots§r only");
        this.add(GummyMagazineItem.FOLDED_2_KEY, "+ Consume gummies via §cbuttons§r in the GUI");
        this.add(GummyMagazineItem.FOLDED_3_KEY, "+ Upgradeable at a §6Smithing Table§r");

        // Gummy Glazer tooltips
        this.add(GummyGlazerItem.DESC_1_KEY, "placeholder: glazer desc 0");
        this.add(GummyGlazerItem.DESC_2_KEY, "placeholder: glazer desc 1");
        this.add(GummyGlazerItem.DESC_MODE_SELECTION_KEY, "placeholder: glazer mode selection (%s)");
        this.add(GummyGlazerItem.DESC_MODE_SAVE_KEY, "placeholder: glazer mode save");
        this.add(GummyGlazerItem.DESC_MODE_EXTEND_KEY, "placeholder: glazer mode extend");
        this.add(GummyGlazerItem.FOLDED_1_KEY, "placeholder: glazer folded 0");
        this.add(GummyGlazerItem.FOLDED_2_KEY, "placeholder: glazer folded 1");
        this.add(GummyGlazerItem.FOLDED_3_KEY, "placeholder: glazer folded 2");
        this.add(GummyGlazerItem.FOLDED_4_KEY, "placeholder: glazer folded 3");
        this.add(GummyGlazerItem.FOLDED_5_KEY, "placeholder: glazer folded 4");
        this.add(GummyGlazerItem.FOLDED_6_KEY, "placeholder: glazer folded 5");

        for (DeferredHolder<Sugar, ? extends Sugar> sugar : Sugars.SUGARS.getEntries()) {
            this.add("item." + CandyWorkshop.MODID + ".gummy." + ((Holder<?>) sugar).getKey().location().getPath(), "placeholder:" + ((Holder<?>) sugar)
                    .getKey()
                    .location()
                    .getPath());
        }

        for (ModAdvcmtProvider.AdvancementResources res : ModAdvcmtProvider.AdvancementResources.ALL_RESOURCES) {
            this.add(res.nameKey(), "adv name ph");
            this.add(res.descKey(), "adv desc ph");
        }

        // Apotheosis
        this.add(ApothesisCompats.MALIGNANT_BERRY.get(), "Malignant Berry");
        this.add(ApothesisCompats.KNOWLEDGE_BOTTLE.get(), "Knowledge Bottle");
        this.add(ApothesisCompats.EROSION_SHELL.get(), "Erosion Shell");
        this.add(ApothesisCompats.VITALITY_BERRY.get(), "Vitality Berry");

        // FarmersDelight
        this.add(FarmersDelightCompats.GLAZED_MEAT_RICE.get(), "Glazed Meat Rice");
        this.add(FarmersDelightCompats.SWEET_HARVEST_SOUP.get(), "Sweet Harvest Soup");

        // REI Compat
        this.add("compat.ccw.rei.title", "Refining");
    }
}
