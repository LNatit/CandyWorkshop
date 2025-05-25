package com.lnatit.ccw.compat.jei;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.item.sugaring.SugarUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RefiningRecipe
{
    public static final List<ItemStack> MILK = milk();

    private final SugarRefining.Blend blend;
    private final ItemStack sugar;
    private final List<ItemStack> extra = new ArrayList<>();
    private final List<ItemStack> output = new ArrayList<>();

    public RefiningRecipe(SugarRefining.Blend blend) {
        this.blend = blend;
        this.sugar = new ItemStack(blend.sugar());
        this.sugar.setCount(8);
        blend.output().getAvailableFlavors().forEach(flavor -> {
            extra.add(Sugar.Flavor.toExtra(flavor));
            output.add(SugarUtils.createSugar(blend.output(), flavor));
        });
    }

    public List<ItemStack> getMilk() {
        return MILK;
    }

    public ItemStack getSugar() {
        return sugar;
    }

    public Ingredient getMain() {
        return blend.main();
    }

    public List<ItemStack> getExtra() {
        return extra;
    }

    public List<ItemStack> getOutput() {
        return output;
    }

    private static List<ItemStack> milk() {
        List<ItemStack> milk = new ArrayList<>();
        for (Holder<Item> holder : BuiltInRegistries.ITEM.getTagOrEmpty(ItemRegistry.MILK_TAG)) {
            ItemStack stack = new ItemStack(holder);
            if (holder.is(ItemRegistry.CARTON_MILK_TAG))
                stack.setCount(8);
            milk.add(stack);
        }
        return milk;
    }
}
