package com.lnatit.ccw.item.sugaring;

import com.google.common.collect.ImmutableMap;
import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class SugarRefining
{
    public static final SugarRefining EMPTY = new SugarRefining(ImmutableMap.of());
    private static final List<Consumer<Builder>> customFlavors = new ArrayList<>();
    public static SugarRefining sugarRefining;

    private final ImmutableMap<Item, Holder<Sugar>> sugarFlavors;

    private SugarRefining(Map<Item, Holder<Sugar>> sugarFlavors) {
        this.sugarFlavors = ImmutableMap.copyOf(sugarFlavors);
    }

    public boolean isMain(ItemStack itemStack) {
        return sugarFlavors.containsKey(itemStack.getItem());
    }

    public boolean isExtra(ItemStack itemStack) {
        return itemStack.is(Items.COCOA_BEANS) || itemStack.is(Items.HONEY_BOTTLE);
    }

    public ItemStack makeSugar(ItemStack main, ItemStack extra) {
        Holder<Sugar> holder = sugarFlavors.get(main.getItem());
        if (holder != null) {
            return Sugar.createSugarItem(holder, Sugar.Type.fromExtra(extra));
        }
        return ItemRegistry.GUMMY_ITEM.toStack();
    }

    public static void addModFlavors(Builder builder) {
        // Add all flavor's recipe here
        builder.addFlavor(Items.SUGAR, Sugars.SPEED);
    }

    public static void addCustomFlavorProviders(Consumer<Builder> consumer) {
        customFlavors.add(consumer);
    }

    @SubscribeEvent()
    public static void bootstrap(ServerAboutToStartEvent event) {
        Builder builder = new Builder();
        addModFlavors(builder);
        customFlavors.forEach(p -> p.accept(builder));
        sugarRefining = builder.build();
    }

    public static class Builder {
        private Map<Item, Holder<Sugar>> sugarFlavors = new HashMap<>();

        private void addFlavor(Item item, Holder<Sugar> sugar) {
            sugarFlavors.put(item, sugar);
        }

        public SugarRefining build() {
            return new SugarRefining(this.sugarFlavors);
        }
    }
}
