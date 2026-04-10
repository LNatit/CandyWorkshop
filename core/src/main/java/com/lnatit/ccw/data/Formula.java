package com.lnatit.ccw.data;

import com.google.common.collect.ImmutableMap;
import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.component.SugarContents;
import com.lnatit.ccw.item.crafting.RefiningInput;
import com.lnatit.ccw.item.sugaring.Flavor;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public record Formula(Holder<Sugar> sugar, Holder<Flavor> flavor, List<Effect> effects) implements IFormula
{
    public static final ResourceKey<Registry<Formula>> KEY = ResourceKey.createRegistryKey(CandyWorkshop.id("formula"));
    public static final Codec<Formula> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Sugar.CODEC.fieldOf("sugar").forGetter(Formula::sugar),
                   Flavor.CODEC.fieldOf("flavor").forGetter(Formula::flavor),
                   Effect.CODEC.listOf().fieldOf("effects").forGetter(Formula::effects))
            .apply(instance, Formula::new));

    private Key key() {
        return new Key(sugar, flavor);
    }

    private Formula of(Holder<Flavor> flavor) {
        return new Formula(this.sugar, flavor, this.effects);
    }

    @Override
    public ItemStack productionOf(RefiningInput input) {
        if (!IFormula.hasEnoughMilkAndSugar(input)) {
            return ItemStack.EMPTY;
        }
        return this.result();
    }

    @Override
    public ItemStack batch(
            RefiningInput input,
            Consumer<ItemStack> remainderHandler
    ) {
        IFormula.shrinkAndHandleRemainders(input.milk(), IFormula.getMilkConsumption(input.milk()), remainderHandler);
        IFormula.shrinkAndHandleRemainders(input.sugar(), SUGAR_CONSUMPTION, remainderHandler);
        IFormula.shrinkAndHandleRemainders(input.main(), remainderHandler);
        Holder<Flavor> flavor = Flavor.from(input.extra());
        if (!flavor.is(Flavors.ORIGINAL)) {
            IFormula.shrinkAndHandleRemainders(input.extra(), remainderHandler);
        }
        return this.result();
    }

    public ItemStack result() {
        ItemStack result = SugarContents.createSugar(this.sugar, this.flavor);
        result.setCount(this.flavor.value().craftCount());
        return result;
    }

    @Nullable
    private static Map<Key, Formula> CACHE;

    public static void rebuildCache(Registry<Formula> registry) {
        if (registry == null) {
            CACHE = null;
        }
        else {
            Map<Key, Formula> map = new HashMap<>();
            for (var e : registry.entrySet()) {
                Formula f = e.getValue();
                map.put(f.key(), f);
            }
            CACHE = ImmutableMap.copyOf(map);
        }
    }

    // 虽然目前的设计会导致不同Modid path相同的Flavor搜索Formula出现问题，但我不想为了修这一个小问题增加方法的复杂度了
    public static Optional<Formula> getFormulaOptional(Holder<Sugar> sugar, Holder<Flavor> flavor) {
        if (CACHE == null) {
            return Optional.empty();
        }
        Key key = new Key(sugar, flavor);
        Formula formula = CACHE.get(key);
        if (formula == null) {
            Key proxy = new Key(sugar, flavor.value().proxy());
            formula = CACHE.get(proxy);
            if (formula != null) {
                formula = formula.of(flavor);
            }
        }
        return Optional.ofNullable(formula);
    }

    private record Key(Holder<Sugar> sugar, @Nullable Holder<Flavor> flavor)
    {
        @Override
        public boolean equals(Object obj) {
            if (flavor == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) return false;
            Key other = (Key) obj;
            return sugar.equals(other.sugar) && flavor.equals(other.flavor);
        }
    }
}
