package com.lnatit.ccw.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
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
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvcmtProvider extends AdvancementProvider {

    public ModAdvcmtProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
                List.of(
                        ModAdvcmtProvider::genRoot,
                        ModAdvcmtProvider::genEcoMat,
                        ModAdvcmtProvider::genCowChi,
                        ModAdvcmtProvider::genExcExt
                )
        );
    }

    private static void genRoot(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.display(
                        new ItemStack(ItemRegistry.SUGAR_REFINERY.asItem()),
                        AdvancementResources.ROOT.name(),
                        AdvancementResources.ROOT.desc(),
                        // TODO
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_sugar_refinery", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SUGAR_REFINERY))
                .requirements(AdvancementRequirements.allOf(List.of("get_sugar_refinery")))
                .save(writer, AdvancementResources.ROOT.id);
    }

    private static void genEcoMat(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.ECOMAT.parent())
                .display(
                        new ItemStack(ItemRegistry.MILK_PACKAGING.asItem()),
                        AdvancementResources.ECOMAT.name(),
                        AdvancementResources.ECOMAT.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_milk_packaging", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MILK_PACKAGING))
                .requirements(AdvancementRequirements.allOf(List.of("get_milk_packaging")))
                .save(writer, AdvancementResources.ECOMAT.id());
    }

    private static void genCowChi(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.COWCHI.parent())
                .display(
                        new ItemStack(ItemRegistry.MILK_EXTRACTOR.asItem()),
                        AdvancementResources.COWCHI.name(),
                        AdvancementResources.COWCHI.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_milk_extractor", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MILK_EXTRACTOR))
                .requirements(AdvancementRequirements.allOf(List.of("get_milk_extractor")))
                .save(writer, AdvancementResources.COWCHI.id());
    }

    private static void genExcExt(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.EXCEXT.parent())
                .display(
                        new ItemStack(ItemRegistry.CARTON_MILK.asItem()),
                        AdvancementResources.EXCEXT.name(),
                        AdvancementResources.EXCEXT.desc(),
                        null,
                        AdvancementType.CHALLENGE,
                        true,
                        true,
                        false
                )
                // TODO add criterion
                .save(writer, AdvancementResources.EXCEXT.id());
    }

    private static void genMyFiCa(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.MYFICA.parent())
                .display(
                        Sugar.createSugar(Sugars.RED_HEART, Sugar.Flavor.ORIGINAL),
                        AdvancementResources.MYFICA.name(),
                        AdvancementResources.MYFICA.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_gummy", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GUMMY_ITEM))
                .requirements(AdvancementRequirements.allOf(List.of("get_gummy")))
                .save(writer, AdvancementResources.MYFICA.id());
    }

    private static void genAdCaMat(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.ADCAMA.parent())
                .display(
                        Sugar.createSugar(Sugars.GOLDEN_HEART, Sugar.Flavor.ORIGINAL),
                        AdvancementResources.ADCAMA.name(),
                        AdvancementResources.ADCAMA.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                // TODO add criterion
                .save(writer, AdvancementResources.ADCAMA.id());
    }

    private static void genNeCrFl(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.NECRFL.parent())
                .display(
                        new ItemStack(ItemRegistry.NETHER_SUGAR.asItem()),
                        AdvancementResources.NECRFL.name(),
                        AdvancementResources.NECRFL.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_nether_sugar",  InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHER_SUGAR))
                .requirements(AdvancementRequirements.allOf(List.of("get_nether_sugar")))
                .save(writer, AdvancementResources.NECRFL.id());
    }

    private static void genCanFin(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.CANFIN.parent())
                .display(
                        new ItemStack(ItemRegistry.ENDER_SUGAR.asItem()),
                        AdvancementResources.CANFIN.name(),
                        AdvancementResources.CANFIN.desc(),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("get_ender_sugar",   InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ENDER_SUGAR))
                .requirements(AdvancementRequirements.allOf(List.of("get_ender_sugar")))
                .save(writer, AdvancementResources.CANFIN.id());
    }

    private static void genRaiCol(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
//        Advancement.Builder builder = Advancement.Builder.advancement();
//        builder.parent(AdvancementResources.RAICOL.parent())
//                .display(
//                        new ItemStack()
//                )
    }

    public static final class AdvancementResources {
        public static List<AdvancementResources> ALL_RESOURCES = new ArrayList<>();

        public static AdvancementResources ROOT = root();

        public static AdvancementResources ECOMAT = of("eco_friendly_material", ROOT);
        public static AdvancementResources COWCHI = of("cows_choice", ECOMAT);
        public static AdvancementResources EXCEXT = of("excessive_extraction", COWCHI);

        public static AdvancementResources MYFICA = of("my_first_candy", ROOT);
        public static AdvancementResources ADCAMA = of("advanced_candy_maker", MYFICA);
        public static AdvancementResources NECRFL = of("nether_crazy_flavor", ADCAMA);
        public static AdvancementResources CANFIN = of("candy_finale", NECRFL);
        public static AdvancementResources RAICOL = of("rainbow_collector", CANFIN);
        public static AdvancementResources DIABET = of("diabetes", MYFICA);

        public static AdvancementResources DRATAB = of("drawer_tables", ROOT);
        public static AdvancementResources MOBEDR = of("more_buautiful_drawer", DRATAB);
        public static AdvancementResources TRCAWO = of("the_real_candy_workshop", MOBEDR);

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

            ALL_RESOURCES.add(this);
        }

        public Component name() {
            return Component.translatable(this.nameKey);
        }

        public Component desc() {
            return Component.translatable(this.descKey);
        }

        static AdvancementResources root() {
            return new AdvancementResources(
                    "root",
                    null,
                    "advancements." + CandyWorkshop.MODID + ".root.title",
                    "advancements." + CandyWorkshop.MODID + ".root.description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "root")
            );
        }

        static AdvancementResources root(String preLoc) {
            return new AdvancementResources(
                    "root",
                    null,
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + ".root.title",
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + ".root.description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, preLoc + "/root")
            );
        }

        static AdvancementResources of(String name, String parentLoc) {
            return new AdvancementResources(
                    name,
                    AdvancementSubProvider.createPlaceholder(CandyWorkshop.MODID + ":" + parentLoc),
                    "advancements." + CandyWorkshop.MODID + "." + name + ".title",
                    "advancements." + CandyWorkshop.MODID + "." + name + ".description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, name)
            );
        }

        static AdvancementResources of(String name, AdvancementResources parent) {
            return of(name, parent.name);
        }

        static AdvancementResources of(String name, String preLoc, String parentLoc) {
            return new AdvancementResources(
                    name,
                    AdvancementSubProvider.createPlaceholder(CandyWorkshop.MODID + ":" + parentLoc),
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + "." + name + ".title",
                    "advancements." + CandyWorkshop.MODID + "." + preLoc + "." + name + ".description",
                    ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, preLoc + "/" + name)
            );
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
    }
}
