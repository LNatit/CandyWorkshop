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
    // TODO rename
    public static final DeferredItem<MilkSuckerItem> MILK_SUCKER = ITEMS.register(
            "suker",
            key -> new MilkSuckerItem(
                    new Item.Properties()
                            .durability(128)
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> MILK_CARTON = ITEMS.register(
            "milk_carton",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
//    public static final DeferredItem<Item> 糖粉
    public static final DeferredItem<Item> ENERGY_CARROT = ITEMS.register(
            "energy_carrot",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> PHANTOM_PEARL = ITEMS.register(
            "phantom_pearl",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> CALCIUM_RICH_MILK = ITEMS.register(
            "calcium_rich_milk",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> VOID_CARROT = ITEMS.register(
            "void_carrot",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> WEAKNESS_POWDER = ITEMS.register(
            "weakness_powder",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> IRON_CLAD_APPLE = ITEMS.register(
            "iron_clad_apple",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> GOLD_STUDDED_APPLE = ITEMS.register(
            "gold_studded_apple",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> BLESSED_STEAK = ITEMS.register(
            "blessed_steak",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> GREEDY_OFFERING = ITEMS.register(
            "greedy_offering",
            key -> new Item(
                    new Item.Properties()
                            .craftRemainder(Items.BOWL)
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> DEFILED_OFFERING = ITEMS.register(
            "defiled_offering",
            key -> new Item(
                    new Item.Properties()
                            .craftRemainder(Items.BOWL)
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> DOLPHIN_COOKIE = ITEMS.register(
            "dolphin_cookie",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> OMINOUS_FLAG = ITEMS.register(
            "ominous_flag",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );
    public static final DeferredItem<Item> MILK_GELATIN = ITEMS.register(
            "milk_gelatin",
            key -> new Item(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
            )
    );

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
