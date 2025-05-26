package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final TagKey<Item> MILK_TAG = tag("c", "drinks/milk");
    public static final TagKey<Item> CARTON_MILK_TAG = tag("carton_milk");

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, CandyWorkshop.MODID);

    public static final RegistryObject<GummyItem> GUMMY_ITEM =
            ITEMS.register(
                    "gummy",
                    () -> new GummyItem(
                            new Item.Properties()
                                    .food(FoodsAndConsumables.GUMMY_FOOD)
                    )
            );
    public static final RegistryObject<BlockItem> SUGAR_REFINERY = registerSimpleBlockItem(BlockRegistry.SUGAR_REFINERY);
    public static final RegistryObject<BlockItem> PLAIN_DRAWER_TABLE = registerSimpleBlockItem(BlockRegistry.PLAIN_DRAWER_TABLE);
    public static final RegistryObject<BlockItem> DRAWER_TABLE = registerSimpleBlockItem(BlockRegistry.DRAWER_TABLE);
    public static final RegistryObject<MilkExtractorItem> MILK_EXTRACTOR = ITEMS.register(
            "milk_extractor",
            () -> new MilkExtractorItem(
                    new Item.Properties()
                            .durability(129)
            )
    );
    public static final RegistryObject<Item> MILK_PACKAGING = registerSimpleItem("milk_packaging");
    public static final RegistryObject<CartonMilkItem> CARTON_MILK = ITEMS.register(
            "carton_milk",
            () -> new CartonMilkItem(new Item.Properties())
    );
    public static final RegistryObject<Item> NETHER_SUGAR = registerSimpleItem("nether_sugar");
    public static final RegistryObject<Item> ENDER_SUGAR = registerSimpleItem("ender_sugar");
    public static final RegistryObject<Item> ENERGY_CARROT = ITEMS.register(
            "energy_carrot",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.ENERGY_CARROT_FOOD)
            )
    );
    public static final RegistryObject<Item> SWEET_MELON_SLICE = ITEMS.register(
            "sweet_melon_slice",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.SWEET_LEMON_SLICE_FOOD)
            )
    );
    public static final RegistryObject<Item> PHANTOM_PEARL = registerSimpleItem("phantom_pearl");
    public static final RegistryObject<Item> CALCIUM_RICH_MILK = ITEMS.register(
            "calcium_rich_milk",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.CALCIUM_RICH_MILK_FOOD)
            )
    );
    public static final RegistryObject<Item> VOID_CARROT = ITEMS.register(
            "void_carrot",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.VOID_CARROT_FOOD)
            )
    );
    public static final RegistryObject<Item> WEAKNESS_POWDER = registerSimpleItem("weakness_powder");
    public static final RegistryObject<Item> IRON_CLAD_APPLE = ITEMS.register(
            "iron_clad_apple",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.IRON_CLAD_APPLE_FOOD)
            )
    );
    public static final RegistryObject<Item> GOLD_STUDDED_APPLE = ITEMS.register(
            "gold_studded_apple",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.GOLD_STUDDED_APPLE_FOOD)
            )
    );
    public static final RegistryObject<Item> BLESSED_STEAK = ITEMS.register(
            "blessed_steak",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.BLESSED_STEAK_FOOD)
            )
    );
    public static final RegistryObject<Item> GREEDY_OFFERING = ITEMS.register(
            "greedy_offering",
            () -> new Item(
                    new Item.Properties()
                            .craftRemainder(Items.BOWL)
            )
    );
    public static final RegistryObject<Item> DEFILED_OFFERING = ITEMS.register(
            "defiled_offering",
            () -> new Item(
                    new Item.Properties()
                            .craftRemainder(Items.BOWL)
            )
    );
    public static final RegistryObject<Item> DOLPHIN_COOKIE = ITEMS.register(
            "dolphin_cookie",
            () -> new Item(
                    new Item.Properties()
                            .food(FoodsAndConsumables.DOLPHIN_COOKIE_FOOD)
            )
    );
    public static final RegistryObject<Item> OMINOUS_FLAG = registerSimpleItem("ominous_flag");
    public static final RegistryObject<Item> MILK_GELATIN = registerSimpleItem("milk_gelatin");

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(
                    Registries.CREATIVE_MODE_TAB,
                    CandyWorkshop.MODID
            );

    public static final RegistryObject<CreativeModeTab> CANDY_TAB =
            TABS.register(
                    "candy",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup." + CandyWorkshop.MODID + ".candy"))
                            .icon(() -> new ItemStack(DRAWER_TABLE.get()))
                            .displayItems(
                                    (parameters, output) ->
                                    {
                                        output.accept(Items.MILK_BUCKET);
                                        output.accept(GUMMY_ITEM.get());
                                        ITEMS.getEntries().forEach(
                                                holder -> output.accept(holder.get())
                                        );
                                        Sugars.SUGAR_SUPPLIER.get()
                                                .forEach(
                                                        sugar -> output.acceptAll(Sugar.createAllFlavors(sugar))
                                                );
                                    }
                            ).build()
            );
    
    private static <T extends Block> RegistryObject<BlockItem> registerSimpleBlockItem(RegistryObject<T> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RegistryObject<Item> registerSimpleItem(String id) {
        return ITEMS.register(id, () -> new Item(new Item.Properties()));
    }

    private static TagKey<Item> tag(String namespace, String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
    }

    private static TagKey<Item> tag(String name) {
        return tag(CandyWorkshop.MODID, name);
    }
}
