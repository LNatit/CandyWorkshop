package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RepairSuckerRecipe extends CustomRecipe
{
    public RepairSuckerRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) {
            return false;
        }

        boolean hasSucker = false;
        boolean hasPaper = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(ItemRegistry.MILK_SUCKER)) {
                hasSucker = true;
            }
            else if (stack.is(Items.PAPER)) {
                hasPaper = true;
            }
        }

        return hasSucker && hasPaper;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack sucker = ItemStack.EMPTY;
        ItemStack paper = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(Items.PAPER))
                paper = stack;
            else if (stack.is(ItemRegistry.MILK_SUCKER))
            // avoid modifying the item directly
                sucker = stack.copy();
        }

        if (sucker.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int damage = sucker.getDamageValue();
        if (paper.getCount() > 0) {
            damage -= 32;
            if (damage < 0)
                damage = 0;
        }
        sucker.setDamageValue(damage);

        return sucker;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeRegistry.REPAIR_SUCKER.get();
    }
}
