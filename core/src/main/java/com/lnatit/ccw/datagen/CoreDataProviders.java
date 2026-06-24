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

public class CoreDataProviders extends DatapackBuiltinEntriesProvider
{

    public static final Map<ResourceKey<?>, List<ICondition>> CONDITIONS = new HashMap<>();

    public CoreDataProviders(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output,
              registries,
              new RegistrySetBuilder().add(Formula.KEY, CoreDataProviders::register),
              CONDITIONS,
              Set.of(CandyWorkshop.MODID));
    }

    private static void register(BootstrapContext<Formula> bootstrap) {
        Formulas.CONTEXTS.forEach(context -> context.register(bootstrap));
    }

    public static Formulas get() {
        return new Formulas();
    }

    @Override
    public String getName() {
        return "Candy Workshop - Datapacks";
    }

    public record Context<V>(ResourceKey<V> key, V value) {
        public void register(BootstrapContext<V> context) {
            context.register(key, value);
        }
    }

    // BOLD: extend
    // EXCITED: amplify
    public static class Formulas {
        public static final List<Context<Formula>> CONTEXTS = new ArrayList<>();

        private List<ICondition> conditions = List.of();
        @Nullable
        private Holder<Sugar> sugar;
        private List<Effect> effects = List.of();

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
            String sugarName = ((Holder<?>) this.sugar).getKey().identifier().getPath();
            String flavorName = ((Holder<?>) flavor).getKey().identifier().getPath();
            return DataPackRegistry.of(Formula.KEY, sugarName + "_" + flavorName);
        }

        private void append(Holder<Flavor> flavor) {
            this.append(flavor, this.effects);
        }

        private void append(Holder<Flavor> flavor, List<Effect> effects) {
            if (!this.effects.isEmpty()) {
                ResourceKey<Formula> key = of(flavor);
                CONTEXTS.add(new Context<>(key, new Formula(this.sugar, flavor, effects)));
                if (!conditions.isEmpty()) {
                    CONDITIONS.put(key, conditions);
                }
            }
        }
    }
}
