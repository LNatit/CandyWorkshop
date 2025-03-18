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

public class RepairExtractorRecipe extends CustomRecipe
{
    public RepairExtractorRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2 || input.ingredientCount() != 3) {
            return false;
        }

        boolean hasExtractor = false;
        boolean hasPaper = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(ItemRegistry.MILK_EXTRACTOR)) {
                hasExtractor = true;
            }
            else if (stack.is(Items.PAPER)) {
                hasPaper = true;
            }
        }

        return hasExtractor && hasPaper;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack extractor = ItemStack.EMPTY;
        int paperCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(Items.PAPER) && !stack.isEmpty())
                paperCount += 1;
            else if (stack.is(ItemRegistry.MILK_EXTRACTOR))
            // avoid modifying the item directly
                extractor = stack.copy();
        }

        if (extractor.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int damage = extractor.getDamageValue();
        if (paperCount > 0) {
            damage -= paperCount * 64;
            if (damage < 0)
                damage = 0;
        }
        extractor.setDamageValue(damage);

        return extractor;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeRegistry.REPAIR_EXTRACTOR.get();
    }
}
