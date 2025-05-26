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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final TagKey<Item> MILK_TAG = tag("c", "drinks/milk");
    public static final TagKey<Item> CARTON_MILK_TAG = tag("carton_milk");

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
                                    .food(FoodsAndConsumables.GUMMY_FOOD)
                                    .component(SUGAR_CONTENTS_DCTYPE, SugarContents.VANILLA)
                    )
            );
    public static final DeferredItem<BlockItem> SUGAR_REFINERY = ITEMS.registerSimpleBlockItem(BlockRegistry.SUGAR_REFINERY);
    public static final DeferredItem<BlockItem> PLAIN_DRAWER_TABLE = ITEMS.registerSimpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE);
    public static final DeferredItem<BlockItem> DRAWER_TABLE = ITEMS.registerSimpleBlockItem(BlockRegistry.DRAWER_TABLE);
    public static final DeferredItem<MilkExtractorItem> MILK_EXTRACTOR = ITEMS.register(
            "milk_extractor",
            key -> new MilkExtractorItem(
                    new Item.Properties()
                            .durability(129)
            )
    );
    public static final DeferredItem<Item> MILK_PACKAGING = ITEMS.registerSimpleItem("milk_packaging");
    public static final DeferredItem<Item> CARTON_MILK = ITEMS.register(
            "carton_milk",
            key -> new CartonMilkItem(new Item.Properties())
    );
    public static final DeferredItem<Item> NETHER_SUGAR = ITEMS.registerSimpleItem("nether_sugar");
    public static final DeferredItem<Item> ENDER_SUGAR = ITEMS.registerSimpleItem("ender_sugar");
    public static final DeferredItem<Item> ENERGY_CARROT = ITEMS.register(
            "energy_carrot",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.ENERGY_CARROT_FOOD)
            )
    );
    public static final DeferredItem<Item> SWEET_MELON_SLICE = ITEMS.register(
            "sweet_melon_slice",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.SWEET_LEMON_SLICE_FOOD)
            )
    );
    public static final DeferredItem<Item> PHANTOM_PEARL = ITEMS.registerSimpleItem("phantom_pearl");
    public static final DeferredItem<Item> CALCIUM_RICH_MILK = ITEMS.register(
            "calcium_rich_milk",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.CALCIUM_RICH_MILK_FOOD)
            )
    );
    public static final DeferredItem<Item> VOID_CARROT = ITEMS.register(
            "void_carrot",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.VOID_CARROT_FOOD)
            )
    );
    public static final DeferredItem<Item> WEAKNESS_POWDER = ITEMS.registerSimpleItem("weakness_powder");
    public static final DeferredItem<Item> IRON_CLAD_APPLE = ITEMS.register(
            "iron_clad_apple",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.IRON_CLAD_APPLE_FOOD)
            )
    );
    public static final DeferredItem<Item> GOLD_STUDDED_APPLE = ITEMS.register(
            "gold_studded_apple",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.GOLD_STUDDED_APPLE_FOOD)
            )
    );
    public static final DeferredItem<Item> BLESSED_STEAK = ITEMS.register(
            "blessed_steak",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.BLESSED_STEAK_FOOD)
            )
    );
    public static final DeferredItem<Item> GREEDY_OFFERING = ITEMS.register(
            "greedy_offering",
            key -> new Item(
                    new Item.Properties()
                            .craftRemainder(Items.BOWL)
            )
    );
    public static final DeferredItem<Item> DEFILED_OFFERING = ITEMS.register(
            "defiled_offering",
            key -> new Item(
                    new Item.Properties()
                            .craftRemainder(Items.BOWL)
            )
    );
    public static final DeferredItem<Item> DOLPHIN_COOKIE = ITEMS.register(
            "dolphin_cookie",
            key -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.DOLPHIN_COOKIE_FOOD)
            )
    );
    public static final DeferredItem<Item> OMINOUS_FLAG = ITEMS.registerSimpleItem("ominous_flag");
    public static final DeferredItem<Item> MILK_GELATIN = ITEMS.registerSimpleItem("milk_gelatin");

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
                            .icon(() -> new ItemStack(DRAWER_TABLE.asItem()))
                            .displayItems(
                                    (parameters, output) ->
                                    {
                                        output.accept(Items.MILK_BUCKET);
                                        output.accept(CARTON_MILK.get());
                                        output.accept(Items.SUGAR);
                                        output.accept(NETHER_SUGAR.get());
                                        output.accept(ENDER_SUGAR.get());
                                        output.accept(GUMMY_ITEM.get());
                                        ITEMS.getEntries().forEach(
                                                holder -> output.accept(holder.get())
                                        );
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

    private static TagKey<Item> tag(String namespace, String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

    private static TagKey<Item> tag(String name) {
        return tag(CandyWorkshop.MODID, name);
    }

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
