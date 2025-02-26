package com.lnatit.ccw;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.menu.MenuRegistry;
import com.lnatit.ccw.misc.StatRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CandyWorkshop.MODID)
public class CandyWorkshop {
    public static final String MODID = "ccw";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CandyWorkshop(IEventBus modEventBus, ModContainer modContainer) {
        Sugars.register(modEventBus);
        StatRegistry.STATS.register(modEventBus);
        ItemRegistry.DATA_COMPONENTS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        ItemRegistry.TABS.register(modEventBus);
        MenuRegistry.MENUS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        BlockRegistry.BLOCK_ENTITIES.register(modEventBus);
    }
}
