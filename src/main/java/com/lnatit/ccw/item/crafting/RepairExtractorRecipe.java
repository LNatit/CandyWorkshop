package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RepairExtractorRecipe extends CustomRecipe {
    public RepairExtractorRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int ingredientCount = 0;
        boolean hasExtractor = false;
        boolean hasPackaging = false;
        for (int i = 0; i < container.getItems().size(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                ingredientCount++;
                if (ingredientCount > 2) {
                    return false;
                }
            }

            if (stack.is(ItemRegistry.MILK_EXTRACTOR.get())) {
                hasExtractor = true;
            } else if (stack.is(ItemRegistry.MILK_PACKAGING.get()) || stack.is(Items.PAPER)) {
                hasPackaging = true;
            }
        }

        return hasExtractor && hasPackaging;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack extractor = ItemStack.EMPTY;
        int repairCount = 0;
        for (int i = 0; i < container.getItems().size(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(Items.PAPER))
                    repairCount = 32;
                else if (stack.is(ItemRegistry.MILK_PACKAGING.get()))
                    repairCount = 128;
                else if (stack.is(ItemRegistry.MILK_EXTRACTOR.get()))
                    // avoid modifying the item directly
                    extractor = stack.copy();
            }
        }

        if (extractor.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int damage = extractor.getDamageValue();
        if (repairCount > 0) {
            damage -= repairCount;
            if (damage < 0)
                damage = 0;
        }
        extractor.setDamageValue(damage);

        return extractor;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeRegistry.REPAIR_EXTRACTOR.get();
    }
}
