package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.RegistryRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistry
{
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(
                    Registries.DATA_COMPONENT_TYPE,
                    CandyWorkshop.MODID
            );

    public static final Supplier<DataComponentType<Holder<Sugar>>> SUGAR_DCTYPE =
            DATA_COMPONENTS.registerComponentType(
                    "sugar",
                    sugarBuilder -> sugarBuilder.persistent(Sugar.CODEC).networkSynchronized(Sugar.STREAM_CODEC)
            );

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(CandyWorkshop.MODID);

    public static final DeferredItem<GummyItem> GUMMY_ITEM =
            ITEMS.register(
                    "gummy",
                    () -> new GummyItem(new Item.Properties().component(SUGAR_DCTYPE, Sugars.VANILLA))
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
                                                     parameters.holders()
                                                               .lookup(RegistryRegistry.SUGAR_KEY)
                                                               .ifPresent(
                                                                       lookups -> generateSugarTypes(output,
                                                                                                     lookups,
                                                                                                     CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                                                                       )
                                                               );
                                                 }
                                         ).build()
            );

    public static void register(IEventBus eventBus) {
        Sugars.register(eventBus);
        DATA_COMPONENTS.register(eventBus);
        ITEMS.register(eventBus);
        TABS.register(eventBus);
    }

    private static void generateSugarTypes(
            CreativeModeTab.Output output,
            HolderLookup<Sugar> sugars,
            CreativeModeTab.TabVisibility visibility
    ) {
        sugars.listElements()
              // if FeatureElement implemented, we need to filter the map
//                .filter()
              .map(Sugar::createSugarItem)
              .forEach(result -> output.accept(result, visibility));
    }
}
