package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvcmtProvider extends AdvancementProvider {

    public ModAdvcmtProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
                List.of(
                        ModAdvcmtProvider::genRoot,
                        ModAdvcmtProvider::genEcoMat,
                        ModAdvcmtProvider::genCowChi
                )
        );
    }

    public static AdvancementResources ROOT = AdvancementResources.root();

    private static void genRoot(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.display(
                        new ItemStack(ItemRegistry.SUGAR_REFINERY.asItem()),
                        ROOT.name(),
                        ROOT.desc(),
                        // TODO
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_sugar_refinery", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SUGAR_REFINERY))
                .requirements(AdvancementRequirements.allOf(List.of("get_sugar_refinery")))
                .save(writer, ROOT.id);
    }

    public static AdvancementResources ECOMAT = AdvancementResources.of("eco_friendly_material", ROOT);

    private static void genEcoMat(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(ECOMAT.parent())
                .display(
                        new ItemStack(ItemRegistry.MILK_PACKAGING.asItem()),
                        ECOMAT.name(),
                        ECOMAT.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_milk_packaging", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MILK_PACKAGING))
                .requirements(AdvancementRequirements.allOf(List.of("get_milk_packaging")))
                .save(writer, ECOMAT.id());
    }

    public static AdvancementResources COWCHI = AdvancementResources.of("cows_choice", ECOMAT);

    private static void genCowChi(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(COWCHI.parent())
                .display(
                        new ItemStack(ItemRegistry.MILK_EXTRACTOR.asItem()),
                        COWCHI.name(),
                        COWCHI.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_milk_extractor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MILK_EXTRACTOR))
                .requirements(AdvancementRequirements.allOf(List.of("get_milk_extractor")))
                .save(writer, COWCHI.id());
    }

    public static AdvancementResources EXCEXT = AdvancementResources.of("excessive_extraction", ECOMAT);

    private static void genExcExt(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(EXCEXT.parent())
                .display(
                        new ItemStack(ItemRegistry.CARTON_MILK.asItem()),
                        EXCEXT.name(),
                        EXCEXT.desc(),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        true
                )
                // TODO add criterion
                .save(writer, EXCEXT.id());
    }

    public static final class AdvancementResources {
        public static List<AdvancementResources> ALL_RESOURCES = new ArrayList<>();

        private final String name;
        @Nullable
        private final AdvancementHolder parent;
        private final String nameKey;
        private final String descKey;
        private final ResourceLocation id;

        public AdvancementResources(String name, @Nullable AdvancementHolder parent, String nameKey, String descKey, ResourceLocation id) {
            this.name = name;
            this.parent = parent;
            this.nameKey = nameKey;
            this.descKey = descKey;
            this.id = id;
        }

        public Component name() {
            return Component.translatable(this.nameKey);
        }

        public Component desc() {
            return Component.translatable(this.descKey);
        }

        static AdvancementResources root() {
            var res = new AdvancementResources(
                    "root",
                    null,
                    "advancements." + CandyWorkshop.MODID + ".root.title",
                    "advancements." + CandyWorkshop.MODID + ".root.description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "root")
            );
            ALL_RESOURCES.add(res);
            return res;
        }

        static AdvancementResources root(String preLoc) {
            var res = new AdvancementResources(
                    "root",
                    null,
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + ".root.title",
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + ".root.description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, preLoc + "/root")
            );
            ALL_RESOURCES.add(res);
            return res;
        }

        static AdvancementResources of(String name, String parentLoc) {
            var res = new AdvancementResources(
                    name,
                    AdvancementSubProvider.createPlaceholder(CandyWorkshop.MODID + ":" + parentLoc),
                    "advancements." + CandyWorkshop.MODID + "." + name + ".title",
                    "advancements." + CandyWorkshop.MODID + "." + name + ".description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, name)
            );
            ALL_RESOURCES.add(res);
            return res;
        }

        static AdvancementResources of(String name, AdvancementResources parent) {
            return of(name, parent.name);
        }

        static AdvancementResources of(String name, String preLoc, String parentLoc) {
            var res = new AdvancementResources(
                    name,
                    AdvancementSubProvider.createPlaceholder(CandyWorkshop.MODID + ":" + parentLoc),
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + "." + name + ".title",
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + "." + name + ".description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, preLoc + "/" + name)
            );
            ALL_RESOURCES.add(res);
            return res;
        }

        @Nullable
        public AdvancementHolder parent() {
            return parent;
        }

        public String nameKey() {
            return nameKey;
        }

        public String descKey() {
            return descKey;
        }

        public ResourceLocation id() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (AdvancementResources) obj;
            return Objects.equals(this.parent, that.parent) &&
                    Objects.equals(this.nameKey, that.nameKey) &&
                    Objects.equals(this.descKey, that.descKey) &&
                    Objects.equals(this.id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parent, nameKey, descKey, id);
        }

        @Override
        public String toString() {
            return "AdvancementResources[" +
                    "parent=" + parent + ", " +
                    "nameKey=" + nameKey + ", " +
                    "descKey=" + descKey + ", " +
                    "id=" + id + ']';
        }

    }
}
