package com.lnatit.ccw.compat.rei;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class RefiningDisplay extends BasicDisplay
{
    public static final List<EntryIngredient> MILK = List.of(EntryIngredients.of(ItemRegistry.CARTON_MILK));

    public RefiningDisplay(SugarRefining.Blend blend) {
        super(inputsOf(blend), outputOf(blend));
    }

    @Override
    public CategoryIdentifier<RefiningDisplay> getCategoryIdentifier() {
        return RefiningCateglory.REFINING;
    }

    // TODO
    @Override
    public @Nullable DisplaySerializer<RefiningDisplay> getSerializer() {
        return null;
    }

    private static List<EntryIngredient> inputsOf(SugarRefining.Blend blend) {
        EntryIngredient sugar = EntryIngredients.of(blend.sugar());
        EntryIngredient main = EntryIngredients.ofIngredient(blend.main());

        EntryIngredient.Builder extraBuilder = EntryIngredient.builder();
        blend.output().value().getAvailableTypes().forEach(
                flavor -> extraBuilder.add(EntryStacks.of(Sugar.Flavor.toExtra(flavor)))
        );
        EntryIngredient extra = extraBuilder.build();

        return ConcatenatedListView.of(MILK, List.of(sugar, main, extra));
    }

    private static List<EntryIngredient> outputOf(SugarRefining.Blend blend) {
        Holder<Sugar> sugarHolder = blend.output();
        EntryIngredient.Builder outputBuilder = EntryIngredient.builder();
        sugarHolder.value().getAvailableTypes().forEach(
                flavor -> outputBuilder.add(EntryStacks.of(Sugar.createSugar(sugarHolder, flavor)))
        );
        return Collections.singletonList(outputBuilder.build());
    }
}
