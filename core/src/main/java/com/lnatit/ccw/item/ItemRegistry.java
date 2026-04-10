package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.component.GummyContents;
import com.lnatit.ccw.item.component.SugarContents;
import com.lnatit.ccw.misc.RegRegistry;
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

public interface ItemRegistry
{
    TagKey<Item> FOODS_MILK_TAG = tag("c", "foods/milk");
    TagKey<Item> DRINKS_MILK_TAG = tag("c", "drinks/milk");
    TagKey<Item> CARTON_MILK_TAG = tag("carton_milk");
    TagKey<Item> OVERWORLD_SUGAR_TAG = tag("overworld_sugar");
    TagKey<Item> NETHER_SUGAR_TAG = tag("nether_sugar");
    TagKey<Item> ENDER_SUGAR_TAG = tag("ender_sugar");

    DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CandyWorkshop.MODID);

    Supplier<DataComponentType<SugarContents>> SUGAR_CONTENTS_DCTYPE = DATA_COMPONENTS.registerComponentType(
            "sugar_contents",
            sugarBuilder -> sugarBuilder.persistent(SugarContents.CODEC)
                                        .networkSynchronized(SugarContents.STREAM_CODEC)
                                        .cacheEncoding());
    Supplier<DataComponentType<GummyContents>> MAGAZINE_CONTENTS_DCTYPE = DATA_COMPONENTS.registerComponentType(
            "magazine_contents",
            mBuilder -> mBuilder.persistent(GummyContents.CODEC)
                                .networkSynchronized(GummyContents.STREAM_CODEC)
                                .cacheEncoding());
    Supplier<DataComponentType<GummyContents>> GLAZER_CONTENTS_DCTYPE = DATA_COMPONENTS.registerComponentType(
            "glazer_contents",
            gBuilder -> gBuilder.persistent(GummyContents.CODEC)
                                .networkSynchronized(GummyContents.STREAM_CODEC)
                                .cacheEncoding());

    DeferredRegister.Items ITEMS = DeferredRegister.createItems(CandyWorkshop.MODID);

    DeferredItem<GummyItem> GUMMY =
            ITEMS.register("gummy", key -> new GummyItem(new Item.Properties().food(FoodsAndConsumables.GUMMY_FOOD)));
    DeferredItem<BlockItem> SUGAR_REFINERY = ITEMS.registerSimpleBlockItem(BlockRegistry.SUGAR_REFINERY);
    DeferredItem<BlockItem> PLAIN_DRAWER_TABLE = ITEMS.registerSimpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE);
    DeferredItem<BlockItem> DRAWER_TABLE = ITEMS.registerSimpleBlockItem(BlockRegistry.DRAWER_TABLE);
    DeferredItem<MilkExtractorItem> MILK_EXTRACTOR =
            ITEMS.register("milk_extractor", key -> new MilkExtractorItem(new Item.Properties().durability(129)));
    DeferredItem<Item> MILK_PACKAGING = ITEMS.registerSimpleItem("milk_packaging");
    DeferredItem<Item> CARTON_MILK = ITEMS.register("carton_milk", key -> new CartonMilkItem(new Item.Properties()));
    DeferredItem<Item> NETHER_SUGAR = ITEMS.registerSimpleItem("nether_sugar");
    DeferredItem<Item> ENDER_SUGAR = ITEMS.registerSimpleItem("ender_sugar");
    DeferredItem<Item> ENERGY_CARROT = ITEMS.register("energy_carrot",
                                                      key -> new Item(new Item.Properties().food(FoodsAndConsumables.ENERGY_CARROT_FOOD)));
    DeferredItem<Item> SWEET_MELON_SLICE = ITEMS.register("sweet_melon_slice",
                                                          key -> new Item(new Item.Properties().food(FoodsAndConsumables.SWEET_LEMON_SLICE_FOOD)));
    DeferredItem<Item> PHANTOM_PEARL = ITEMS.registerSimpleItem("phantom_pearl");
    DeferredItem<Item> CALCIUM_RICH_MILK = ITEMS.register("calcium_rich_milk",
                                                          key -> new Item(new Item.Properties().food(FoodsAndConsumables.CALCIUM_RICH_MILK_FOOD)));
    DeferredItem<Item> VOID_CARROT = ITEMS.register("void_carrot",
                                                    key -> new Item(new Item.Properties().food(FoodsAndConsumables.VOID_CARROT_FOOD)));
    DeferredItem<Item> WEAKNESS_POWDER = ITEMS.registerSimpleItem("weakness_powder");
    DeferredItem<Item> IRON_CLAD_APPLE = ITEMS.register("iron_clad_apple",
                                                        key -> new Item(new Item.Properties().food(FoodsAndConsumables.IRON_CLAD_APPLE_FOOD)));
    DeferredItem<Item> GOLD_STUDDED_APPLE = ITEMS.register("gold_studded_apple",
                                                           key -> new Item(new Item.Properties().food(
                                                                   FoodsAndConsumables.GOLD_STUDDED_APPLE_FOOD)));
    DeferredItem<Item> BLESSED_STEAK = ITEMS.register("blessed_steak",
                                                      key -> new Item(new Item.Properties().food(FoodsAndConsumables.BLESSED_STEAK_FOOD)));
    DeferredItem<Item> GREEDY_OFFERING =
            ITEMS.register("greedy_offering", key -> new Item(new Item.Properties().craftRemainder(Items.BOWL)));
    DeferredItem<Item> DEFILED_OFFERING =
            ITEMS.register("defiled_offering", key -> new Item(new Item.Properties().craftRemainder(Items.BOWL)));
    DeferredItem<Item> DOLPHIN_COOKIE = ITEMS.register("dolphin_cookie",
                                                       key -> new Item(new Item.Properties().food(FoodsAndConsumables.DOLPHIN_COOKIE_FOOD)));
    DeferredItem<Item> OMINOUS_FLAG = ITEMS.registerSimpleItem("ominous_flag");
    DeferredItem<Item> MILK_GELATIN = ITEMS.registerSimpleItem("milk_gelatin");
    DeferredItem<Item> CARAMETAL = ITEMS.registerSimpleItem("carametal");
    DeferredItem<GummyMagazineItem> GUMMY_MAGAZINE =
            ITEMS.register("gummy_magazine", key -> GummyMagazineItem.create(Tier.PRIMARY));
    DeferredItem<GummyMagazineItem> NETHER_MAGAZINE =
            ITEMS.register("nether_magazine", key -> GummyMagazineItem.create(Tier.NETHER));
    DeferredItem<GummyMagazineItem> ENDER_MAGAZINE =
            ITEMS.register("ender_magazine", key -> GummyMagazineItem.create(Tier.ENDER));
    DeferredItem<GummyGlazerItem> GUMMY_GLAZER =
            ITEMS.register("gummy_glazer", key -> GummyGlazerItem.create(Tier.PRIMARY));
    DeferredItem<GummyGlazerItem> NETHER_GLAZER =
            ITEMS.register("nether_glazer", key -> GummyGlazerItem.create(Tier.NETHER));
    DeferredItem<GummyGlazerItem> ENDER_GLAZER =
            ITEMS.register("ender_glazer", key -> GummyGlazerItem.create(Tier.ENDER));
    DeferredItem<SmithingTemplateItem> NETHER_SMITHING_WAFER =
            ITEMS.register("nether_smithing_wafer", SmithingWafers::createNether);
    DeferredItem<SmithingTemplateItem> ENDER_SMITHING_WAFER =
            ITEMS.register("ender_smithing_wafer", SmithingWafers::createEnder);

    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CandyWorkshop.MODID);

    Supplier<CreativeModeTab> CANDY_TAB = TABS.register("candy",
                                                        () -> CreativeModeTab.builder()
                                                                             .title(Component.translatable("itemGroup."
                                                                                                           + CandyWorkshop.MODID
                                                                                                           + ".candy"))
                                                                             .icon(() -> new ItemStack(DRAWER_TABLE.asItem()))
                                                                             .displayItems((parameters, output) -> {
                                                                                 output.accept(Items.MILK_BUCKET);
                                                                                 output.accept(CARTON_MILK.get());
                                                                                 output.accept(Items.SUGAR);
                                                                                 output.accept(NETHER_SUGAR.get());
                                                                                 output.accept(ENDER_SUGAR.get());
                                                                                 output.accept(GUMMY.get());
                                                                                 ITEMS.getEntries()
                                                                                      .forEach(holder -> output.accept(
                                                                                              holder.get()));
                                                                                 parameters.holders()
                                                                                           .lookup(RegRegistry.SUGAR_KEY)
                                                                                           .ifPresent(lookups -> lookups.listElements()
                                                                                                                        // if FeatureElement implemented, we need to filter the map
                                                                                                                        //                .filter()
                                                                                                                        .map(SugarContents::createOriginalSugar)
                                                                                                                        .forEach(
                                                                                                                                result -> output.accept(
                                                                                                                                        result,
                                                                                                                                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS)));
                                                                             })
                                                                             .build());

    private static TagKey<Item> tag(String namespace, String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

    private static TagKey<Item> tag(String name) {
        return tag(CandyWorkshop.MODID, name);
    }

}
