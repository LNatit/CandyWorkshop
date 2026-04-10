package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public interface SmithingWafers {
    ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;

    String NETHER_GLAZE_APPLIES_TO = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.nether_glaze.applies_to"));
    String NETHER_GLAZE_INGREDIENTS = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.nether_glaze.ingredients"));
    String NETHER_GLAZE_UPGRADE = Util.makeDescriptionId("item", CandyWorkshop.id("nether_glaze_upgrade"));
    String NETHER_GLAZE_BASE_SLOT_DESC = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.nether_glaze.base_slot_desc"));
    String NETHER_GLAZE_ADDITIONS_SLOT_DESC = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.nether_glaze.additions_slot_desc"));

    String ENDER_GLAZE_APPLIES_TO = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.ender_glaze.applies_to"));
    String ENDER_GLAZE_INGREDIENTS = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.ender_glaze.ingredients"));
    String ENDER_GLAZE_UPGRADE = Util.makeDescriptionId("item", CandyWorkshop.id("ender_glaze_upgrade"));
    String ENDER_GLAZE_BASE_SLOT_DESC = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.ender_glaze.base_slot_desc"));
    String ENDER_GLAZE_ADDITIONS_SLOT_DESC = Util.makeDescriptionId("item", CandyWorkshop.id("smithing_template.ender_glaze.additions_slot_desc"));

    ResourceLocation EMPTY_SLOT_MAGAZINE = CandyWorkshop.id("item/empty_slot_gummy_magazine");
    ResourceLocation EMPTY_SLOT_GLAZER =  CandyWorkshop.id("item/empty_slot_gummy_glazer");
    List<ResourceLocation> UPGRADE_ICONS = List.of(EMPTY_SLOT_MAGAZINE, EMPTY_SLOT_GLAZER);
    ResourceLocation EMPTY_SLOT_INGOT = ResourceLocation.withDefaultNamespace("item/empty_slot_ingot");
    ResourceLocation EMPTY_SLOT_DRAGON_HEAD = CandyWorkshop.id("item/empty_slot_dragon_head");

    static SmithingTemplateItem createNether() {
        return new SmithingTemplateItem(
                Component.translatable(SmithingWafers.NETHER_GLAZE_APPLIES_TO).withStyle(DESCRIPTION_FORMAT),
                Component.translatable(SmithingWafers.NETHER_GLAZE_INGREDIENTS).withStyle(DESCRIPTION_FORMAT),
                Component.translatable(SmithingWafers.NETHER_GLAZE_UPGRADE).withStyle(TITLE_FORMAT),
                Component.translatable(SmithingWafers.NETHER_GLAZE_BASE_SLOT_DESC),
                Component.translatable(SmithingWafers.NETHER_GLAZE_ADDITIONS_SLOT_DESC),
                UPGRADE_ICONS,
                List.of(EMPTY_SLOT_INGOT)
        );
    }

    static SmithingTemplateItem createEnder() {
        return new SmithingTemplateItem(
                Component.translatable(SmithingWafers.ENDER_GLAZE_APPLIES_TO).withStyle(DESCRIPTION_FORMAT),
                Component.translatable(SmithingWafers.ENDER_GLAZE_INGREDIENTS).withStyle(DESCRIPTION_FORMAT),
                Component.translatable(SmithingWafers.ENDER_GLAZE_UPGRADE).withStyle(TITLE_FORMAT),
                Component.translatable(SmithingWafers.ENDER_GLAZE_BASE_SLOT_DESC),
                Component.translatable(SmithingWafers.ENDER_GLAZE_ADDITIONS_SLOT_DESC),
                UPGRADE_ICONS,
                List.of(EMPTY_SLOT_DRAGON_HEAD)
        );
    }
}
