package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.item.ItemRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class RepairExtractorRecipe extends CustomRecipe {
    public static final RepairExtractorRecipe INSTANCE = new RepairExtractorRecipe();
    public static final MapCodec<RepairExtractorRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, RepairExtractorRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);
    public static final RecipeSerializer<RepairExtractorRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    public RepairExtractorRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() != 2) {
            return false;
        }

        boolean hasExtractor = false;
        boolean hasPackaging = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.is(ItemRegistry.MILK_EXTRACTOR)) {
                hasExtractor = true;
            } else if (stack.is(ItemRegistry.MILK_PACKAGING) || stack.is(Items.PAPER)) {
                hasPackaging = true;
            }
        }

        return hasExtractor && hasPackaging;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack extractor = ItemStack.EMPTY;
        int repairCount = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(Items.PAPER))
                    repairCount = 32;
                else if (stack.is(ItemRegistry.MILK_PACKAGING))
                    repairCount = 128;
                else if (stack.is(ItemRegistry.MILK_EXTRACTOR))
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
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeRegistry.REPAIR_EXTRACTOR.get();
    }
}
