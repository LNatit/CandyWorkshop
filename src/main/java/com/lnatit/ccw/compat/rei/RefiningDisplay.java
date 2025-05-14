package com.lnatit.ccw.compat.rei;

import com.lnatit.ccw.compat.rei.client.CandyWorkshopPlugin;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.ConcatenatedListView;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RefiningDisplay implements Display {
    public static final DisplaySerializer<RefiningDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(d -> d.inputs),
                    EntryIngredient.codec().fieldOf("output").forGetter(d -> d.output)
            ).apply(instance, RefiningDisplay::new)),
            StreamCodec.composite(
                    EntryIngredient.streamCodec().apply(ByteBufCodecs.list()),
                    d -> d.inputs,
                    EntryIngredient.streamCodec(),
                    d -> d.output,
                    RefiningDisplay::new
            ));

    public static final List<EntryIngredient> MILK = List.of(EntryIngredients.of(ItemRegistry.CARTON_MILK));

    private final List<EntryIngredient> inputs;
    private final EntryIngredient output;

    public RefiningDisplay(SugarRefining.Blend blend) {
        this.inputs = inputsOf(blend);
        this.output = outputOf(blend);
    }

    private RefiningDisplay(List<EntryIngredient> inputs, EntryIngredient output) {
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return inputs;
    }

    public EntryIngredient getOutput() {
        return output;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(output);
    }

    @Override
    public CategoryIdentifier<RefiningDisplay> getCategoryIdentifier() {
        return CandyWorkshopPlugin.REFINING;
    }

    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.empty();
    }

    @Override
    public @Nullable DisplaySerializer<RefiningDisplay> getSerializer() {
        return SERIALIZER;
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

    private static EntryIngredient outputOf(SugarRefining.Blend blend) {
        Holder<Sugar> sugarHolder = blend.output();
        EntryIngredient.Builder outputBuilder = EntryIngredient.builder();
        sugarHolder.value().getAvailableTypes().forEach(
                flavor -> outputBuilder.add(EntryStacks.of(Sugar.createSugar(sugarHolder, flavor)))
        );
        return outputBuilder.build();
    }
}
