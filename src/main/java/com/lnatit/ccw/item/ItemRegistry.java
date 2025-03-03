package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarContents;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(
                    Registries.DATA_COMPONENT_TYPE,
                    CandyWorkshop.MODID
            );

    public static final Supplier<DataComponentType<SugarContents>> SUGAR_CONTENTS_DCTYPE =
            DATA_COMPONENTS.registerComponentType(
                    "sugar_contents",
                    sugarBuilder -> sugarBuilder.persistent(SugarContents.CODEC).networkSynchronized(SugarContents.STREAM_CODEC).cacheEncoding()
            );

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CandyWorkshop.MODID);

    public static final DeferredItem<GummyItem> GUMMY_ITEM =
            ITEMS.register(
                    "gummy",
                    key -> new GummyItem(
                            new Item.Properties()
                                    .food(GummyItem.GUMMY_FOOD, GummyItem.GUMMY_CONSUMABLE)
                                    .component(SUGAR_CONTENTS_DCTYPE, SugarContents.VANILLA)
                                    .setId(ResourceKey.create(Registries.ITEM, key))
                    )
            );
    public static final DeferredItem<BlockItem> SUGAR_REFINERY = ITEMS.registerSimpleBlockItem(BlockRegistry.SUGAR_REFINERY);
    public static final DeferredItem<Item> MILK_GELATIN = ITEMS.register(
            "milk_gelatin",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
//    public static final DeferredItem<Item> 能量萝卜;
//    public static final DeferredItem<Item> 隐身核心;
//    public static final DeferredItem<Item> 高钙牛奶;
//    public static final DeferredItem<Item> 黑胡萝卜;
//    public static final DeferredItem<Item> 虚弱粉;
//    public static final DeferredItem<Item> 空壳苹果;
//    public static final DeferredItem<Item> 镶金苹果;
//    public static final DeferredItem<Item> 神圣肉排;
//    public static final DeferredItem<Item> 贪婪贡品;
//    public static final DeferredItem<Item> 亵渎贡品;
//    public static final DeferredItem<Item> 海豚饼干;
//    public static final DeferredItem<Item> 挑衅旗帜;

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(
                    Registries.CREATIVE_MODE_TAB,
                    CandyWorkshop.MODID
            );

    public static final Supplier<CreativeModeTab> CANDY_TAB =
            TABS.register(
                    "candy",
                    () -> CreativeModeTab.builder()
                                         .title(Component.translatable("itemGroup." + CandyWorkshop.MODID + ".candy"))
                                         .icon(() -> new ItemStack(Items.MILK_BUCKET))
                                         .displayItems(
                                                 (parameters, output) ->
                                                 {
                                                     output.accept(Items.MILK_BUCKET);
                                                     output.accept(GUMMY_ITEM);
                                                     parameters.holders()
                                                               .lookup(RegRegistry.SUGAR_KEY)
                                                               .ifPresent(
                                                                       lookups -> generateSugarTypes(output,
                                                                                                     lookups,
                                                                                                     CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                                                                       )
                                                               );
                                                 }
                                         ).build()
            );

    private static void generateSugarTypes(
            CreativeModeTab.Output output,
            HolderLookup<Sugar> sugars,
            CreativeModeTab.TabVisibility visibility
    ) {
        sugars.listElements()
                // if FeatureElement implemented, we need to filter the map
//                .filter()
                .map(Sugar::createAllFlavors)
                .forEach(result -> output.acceptAll(result, visibility));
    }
}
