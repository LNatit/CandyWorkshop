package com.lnatit.ccw.data;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RefiningInput;
import com.lnatit.ccw.misc.RegRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public interface IFormula
{
    int COMMON_MILK_CONSUMPTION = 1;
    int CARTON_MILK_CONSUMPTION = 8;
    int SUGAR_CONSUMPTION = 8;
    int SUGAR_PRODUCTION = 8;

    ItemStack result();

    ItemStack productionOf(RefiningInput input);

    ItemStack batch(RefiningInput input, Consumer<ItemStack> remainderHandler);

    static void shrinkAndHandleRemainders(ItemStack stack, Consumer<ItemStack> remainderHandler) {
        shrinkAndHandleRemainders(stack, 1, remainderHandler);
    }

    static void shrinkAndHandleRemainders(ItemStack stack, int count, Consumer<ItemStack> remainderHandler) {
        if (stack.hasCraftingRemainingItem()) {
            ItemStack remainder = stack.getCraftingRemainingItem();
            remainder.setCount(count);
            remainderHandler.accept(remainder);
        }
        stack.shrink(count);
    }

    static boolean hasEnoughMilkAndSugar(RefiningInput input) {
        if (input.milk().isEmpty() || input.sugar().isEmpty())
            return false;
        if (!isMilk(input.milk()) || !isSugar(input.sugar()))
            return false;

        int milkCount = getMilkConsumption(input.milk());
        int sugarCount = SUGAR_CONSUMPTION;

        return input.milk().getCount() >= milkCount && input.sugar().getCount() >= sugarCount;
    }

    static int getMilkConsumption(ItemStack milk) {
        return milk.is(ItemRegistry.CARTON_MILK_TAG) ? CARTON_MILK_CONSUMPTION : COMMON_MILK_CONSUMPTION;
    }

    static boolean isMilk(ItemStack stack) {
        return stack.is(ItemRegistry.FOODS_MILK_TAG) || stack.is(ItemRegistry.DRINKS_MILK_TAG);
    }

    static boolean isSugar(ItemStack stack) {
        return stack.is(Items.SUGAR) || stack.is(ItemRegistry.NETHER_SUGAR) || stack.is(ItemRegistry.ENDER_SUGAR);
    }

    static boolean isMain(ItemStack stack) {
        return RegRegistry.SUGAR.holders().anyMatch(ref -> ref.value().ingredient().test(stack));
    }

    static boolean isExtra(ItemStack stack) {
        return RegRegistry.FLAVOR.holders().anyMatch(ref -> ref.value().ingredient().test(stack));
    }
}
