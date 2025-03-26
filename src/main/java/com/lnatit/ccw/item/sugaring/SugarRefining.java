package com.lnatit.ccw.item.sugaring;

import com.google.common.collect.ImmutableList;
import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
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
                return Sugar.createSugar(blend.output, SingleEffectSugar.Flavor.fromExtra(extra));
            }
        }
        return ItemStack.EMPTY;
    }

    public static void addModBlends(Builder builder) {
        // Add all blend's recipe here
        builder.addOverworldBlend(Sugars.SPEED, Items.SUGAR);
        builder.addOverworldBlend(Sugars.BUNNY, Items.RABBIT_FOOT, ItemRegistry.ENERGY_CARROT.get());
        builder.addOverworldBlend(Sugars.HEALING, Items.GLISTERING_MELON_SLICE);
        builder.addOverworldBlend(Sugars.POISON, Items.SPIDER_EYE);
        builder.addOverworldBlend(Sugars.PUFFERFISH, Items.PUFFERFISH);
        builder.addOverworldBlend(Sugars.NIGHT_VISION, Items.GOLDEN_CARROT);
        builder.addOverworldBlend(Sugars.STRENGTH, Items.BLAZE_POWDER);
        builder.addOverworldBlend(Sugars.RECOVERY, Items.GHAST_TEAR, ItemRegistry.SWEET_MELON_SLICE.get());
        builder.addOverworldBlend(Sugars.TURTLE, Items.TURTLE_SCUTE);
        builder.addOverworldBlend(Sugars.FLUTTER, Items.PHANTOM_MEMBRANE);
        builder.addOverworldBlend(Sugars.SNAIL, Items.SOUL_SAND);
        builder.addBlend(Sugars.STINKY, Items.SUGAR, tagIngredient(Tags.Items.MUSHROOMS));
        builder.addOverworldBlend(Sugars.BLINDING, ItemRegistry.VOID_CARROT.get());
        builder.addOverworldBlend(Sugars.WEAKNESS, ItemRegistry.WEAKNESS_POWDER.get());
        builder.addOverworldBlend(Sugars.BRIGHTNESS, Items.GLOW_BERRIES);
        builder.addOverworldBlend(Sugars.DARKNESS, Items.BLACK_DYE);
        builder.addOverworldBlend(Sugars.HUNGER, Items.ROTTEN_FLESH);

        builder.addNetherBlend(Sugars.INVISIBILITY, ItemRegistry.PHANTOM_PEARL.get());
        builder.addNetherBlend(Sugars.STINGER, Items.CACTUS);
        builder.addNetherBlend(Sugars.BUG, Items.STONE);
        builder.addNetherBlend(Sugars.STICKY, Items.SLIME_BLOCK);
        builder.addNetherBlend(Sugars.BINDING, Items.COBWEB);
        builder.addNetherBlend(Sugars.GALE, Items.BREEZE_ROD);
        builder.addNetherBlend(Sugars.REFRESHING, Items.COCOA_BEANS);
        builder.addNetherBlend(Sugars.LAZY, Items.COBBLESTONE);
        builder.addNetherBlend(Sugars.SOLID, ItemRegistry.CALCIUM_RICH_MILK.get());
        builder.addNetherBlend(Sugars.FIREPROOF, Items.OBSIDIAN);
        builder.addNetherBlend(Sugars.WITHERING, Items.WITHER_ROSE);
        builder.addNetherBlend(Sugars.RED_HEART, ItemRegistry.IRON_CLAD_APPLE.get());
        builder.addNetherBlend(Sugars.FLOATING, Items.SHULKER_SHELL);

        builder.addEndBlend(Sugars.GOLDEN_HEART, ItemRegistry.GOLD_STUDDED_APPLE.get());
        builder.addEndBlend(Sugars.SATIATING, ItemRegistry.BLESSED_STEAK.get());
        builder.addEndBlend(Sugars.LUCKY, ItemRegistry.GREEDY_OFFERING.get());
        builder.addEndBlend(Sugars.UNLUCKY, ItemRegistry.DEFILED_OFFERING.get());
        builder.addEndBlend(Sugars.TIDAL, Items.NAUTILUS_SHELL);
        builder.addEndBlend(Sugars.FISH_SWIM, ItemRegistry.DOLPHIN_COOKIE.get());
        builder.addEndBlend(Sugars.TAUNTING, ItemRegistry.OMINOUS_FLAG.get());
        builder.addEndBlend(Sugars.DISCOUNT, Items.EMERALD_BLOCK);
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

    private static Ingredient tagIngredient(TagKey<Item> tag) {
        return Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(tag));
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
