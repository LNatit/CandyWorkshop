/*
 Copyright (c) 2025 Loci_Natit
 SPDX-License-Identifier: ARR
 All rights reserved. Unauthorized use prohibited.
*/
package com.lnatit.ccw;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RecipeRegistry;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.menu.MenuRegistry;
import com.lnatit.ccw.misc.SoundRegistry;
import com.lnatit.ccw.misc.StatRegistry;
import com.lnatit.ccw.misc.critereon.CriteriaRegistry;
import com.lnatit.ccw.misc.data.AttachmentRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(CandyWorkshop.MODID)
public class CandyWorkshop {
    public static final String MODID = "ccw";

    public CandyWorkshop(IEventBus modEventBus, ModContainer modContainer) {
        Sugars.register(modEventBus);
        StatRegistry.STATS.register(modEventBus);
        CriteriaRegistry.TRIGGERS.register(modEventBus);
        SoundRegistry.SOUND_EVENTS.register(modEventBus);
//        ConEffRegistry.CONSUME_EFFECTS.register(modEventBus);
        ItemRegistry.DATA_COMPONENTS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        ItemRegistry.TABS.register(modEventBus);
        RecipeRegistry.RECIPE_SERIALIZERS.register(modEventBus);
        MenuRegistry.MENUS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockRegistry.BLOCK_ENTITIES.register(modEventBus);
        AttachmentRegistry.ATTACHMENT_TYPES.register(modEventBus);
    }
}
