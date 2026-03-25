package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.DataPackRegistry;
import com.lnatit.ccw.data.Effect;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.item.sugaring.Flavor;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public abstract class ModDataProviders extends DatapackBuiltinEntriesProvider
{
    protected static final Map<ResourceKey<?>, List<ICondition>> CONDITIONS = new HashMap<>();

    public ModDataProviders(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output,
              registries,
              new RegistrySetBuilder().add(Formula.KEY, ModDataProviders::register),
              CONDITIONS,
              Set.of(CandyWorkshop.MODID));
    }

    protected static void register(BootstrapContext<Formula> bootstrap) {
        Formulas.CONTEXTS.forEach(context -> context.register(bootstrap));
    }

    @Override
    public abstract String getName();

    private record Context<V>(ResourceKey<V> key, V value)
    {
        public void register(BootstrapContext<V> context) {
            context.register(key, value);
        }
    }

    // TODO maybe we should push it further?
    protected static class Formulas
    {
        private static final List<Context<Formula>> CONTEXTS = new ArrayList<>();
        private List<ICondition> conditions = List.of();
        @Nullable
        private Holder<Sugar> sugar;
        private List<Effect> effects = List.of();

        public static Formulas of() {
            return new Formulas();
        }

        public Formulas with(ICondition... conditions) {
            this.conditions = List.of(conditions);
            return this;
        }

        public Formulas loaded(String modid) {
            return with(new ModLoadedCondition(modid));
        }

        public Formulas clearConditions() {
            this.conditions = List.of();
            return this;
        }

        public Formulas register(Holder<Sugar> sugar, Effect... effects) {
            this.sugar = sugar;
            this.effects = List.of(effects);
            append(Flavors.ORIGINAL);
            return this;
        }

        public Formulas excited(Effect... effects) {
            append(Flavors.EXCITED, List.of(effects));
            return this;
        }

        public Formulas defaultExcited() {
            return this.excited(this.effects.stream().map(Effect::enhanceAmplifier).toArray(Effect[]::new));
        }

        public Formulas bold(Effect... effects) {
            append(Flavors.BOLD, List.of(effects));
            return this;
        }

        public Formulas defaultBold() {
            return this.bold(this.effects.stream().map(Effect::doubleDuration).toArray(Effect[]::new));
        }

        public Formulas add(Holder<Flavor> flavor, Effect... effects) {
            append(flavor, List.of(effects));
            return this;
        }

        private ResourceKey<Formula> of(Holder<Flavor> flavor) {
            String sugarName = ((Holder<?>) this.sugar).getKey().location().getPath();
            String flavorName = ((Holder<?>) flavor).getKey().location().getPath();
            return DataPackRegistry.of(Formula.KEY, sugarName + "_" + flavorName);
        }

        private void append(Holder<Flavor> flavor) {
            this.append(flavor, this.effects);
        }

        private void append(Holder<Flavor> flavor, List<Effect> effects) {
            if (!this.effects.isEmpty()) {
                ResourceKey<Formula> key = of(flavor);
                CONTEXTS.add(new Context<>(key, new Formula(this.sugar, flavor, effects))); if (!conditions.isEmpty()) {
                    CONDITIONS.put(key, conditions);
                }
            }
        }
    }
}
