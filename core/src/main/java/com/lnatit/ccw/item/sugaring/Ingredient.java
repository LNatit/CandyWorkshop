package com.lnatit.ccw.item.sugaring;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.function.Predicate;

public interface Ingredient extends Predicate<ItemStack>
{
    static Ingredient of(ItemLike... items) {
        return new Listed(List.of(items));
    }

    static Ingredient of(TagKey<Item> tag) {
        return new Tagged(tag);
    }

    record Listed(List<ItemLike> passes) implements Ingredient
    {
        @Override
        public boolean test(ItemStack stack) {
            return passes.stream().anyMatch(item -> stack.is(item.asItem()));
        }
    }

    record Tagged(TagKey<Item> tag) implements Ingredient
    {
        @Override
        public boolean test(ItemStack stack) {
            return stack.is(tag);
        }
    }
}
