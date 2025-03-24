package com.lnatit.ccw.item.sugaring;

import com.google.common.collect.ImmutableList;
import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class SugarRefining
{
    public static final SugarRefining EMPTY = new SugarRefining(List.of(), Set.of());
    public static final int REFINE_TIME = 160;
    private static final List<Consumer<Builder>> customBlendProviders = new ArrayList<>();
    public static SugarRefining sugarRefining = EMPTY;

    private final List<Blend> sugarBlends;
    private final Set<Item> sugarItems;

    private SugarRefining(List<Blend> sugarBlends, Set<Item> sugarItems) {
        this.sugarBlends = ImmutableList.copyOf(sugarBlends);
        this.sugarItems = Set.copyOf(sugarItems);
    }

    public boolean isSugar(ItemStack stack) {
        return sugarItems.contains(stack.getItem());
    }

    public boolean isMain(ItemStack stack) {
        for (Blend blend : sugarBlends) {
            if (blend.main.test(stack)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExtra(ItemStack stack) {
        return stack.is(Items.COCOA_BEANS)
                || stack.is(Items.HONEY_BOTTLE)
                || stack.is(ItemRegistry.MILK_GELATIN);
    }

    public ItemStack makeSugar(ItemStack sugar, ItemStack main, ItemStack extra) {
        if (sugar.isEmpty() || main.isEmpty()) {
            return ItemStack.EMPTY;
        }

        for (Blend blend : sugarBlends) {
            if (sugar.is(blend.sugar) && blend.main.test(main)) {
                return Sugar.createSugar(blend.output, Sugar.Flavor.fromExtra(extra));
            }
        }
        return ItemStack.EMPTY;
    }

    public static void addModBlends(Builder builder) {
        // Add all blend's recipe here
        builder.addOverworldBlend(Sugars.SPEED, Items.SUGAR);
        // TODO
    }

    public static void addCustomBlendProviders(Consumer<Builder> consumer) {
        customBlendProviders.add(consumer);
    }

    @SubscribeEvent()
    public static void bootstrap(ServerAboutToStartEvent event) {
        Builder builder = new Builder();
        addModBlends(builder);
        customBlendProviders.forEach(p -> p.accept(builder));
        sugarRefining = builder.build();
    }

    public static class Builder
    {
        private final List<Blend> sugarBlends = new ArrayList<>();
        private final Set<Item> sugarItems = new HashSet<>();

        public void addBlend(Holder<Sugar> output, Item sugar, Ingredient main) {
            sugarBlends.add(new Blend(sugar, main, output));
            sugarItems.add(sugar);
        }

        public void addBlend(Holder<Sugar> output, Item sugar, Item... main) {
            addBlend(output, sugar, Ingredient.of(main));
        }

        public void addOverworldBlend(Holder<Sugar> output, Item... main) {
            addBlend(output, Items.SUGAR, Ingredient.of(main));
        }

        public void addNetherBlend(Holder<Sugar> output, Item... main) {
            addBlend(output, ItemRegistry.NETHER_SUGAR.get(), Ingredient.of(main));
        }

        public void addEndBlend(Holder<Sugar> output, Item... main) {
            addBlend(output, ItemRegistry.ENDER_SUGAR.get(), Ingredient.of(main));
        }

        public SugarRefining build() {
            return new SugarRefining(this.sugarBlends, this.sugarItems);
        }
    }

    record Blend(Item sugar, Ingredient main, Holder<Sugar> output)
    {
    }
}
