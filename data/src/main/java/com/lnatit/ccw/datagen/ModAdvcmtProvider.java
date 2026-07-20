package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.misc.critereon.CriteriaRegistry;
import com.lnatit.ccw.misc.critereon.NumericTrigger;
import com.lnatit.ccw.misc.critereon.SimpleTrigger;
import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvcmtProvider extends AdvancementProvider
{

    public ModAdvcmtProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,
              List.of(
                      ModAdvcmtProvider::genRoot,
                      ModAdvcmtProvider::genEcoMat,
                      ModAdvcmtProvider::genCowChi,
                      ModAdvcmtProvider::genExcExt,
                      ModAdvcmtProvider::genMyFiCa,
                      ModAdvcmtProvider::genAdCaMa,
                      ModAdvcmtProvider::genNeCrFl,
                      ModAdvcmtProvider::genCanFin,
                      ModAdvcmtProvider::genRaiCol,
                      ModAdvcmtProvider::genDiabet,
                      ModAdvcmtProvider::genDraTab,
                      ModAdvcmtProvider::genMoBeDr,
                      ModAdvcmtProvider::genTrCaWo
              )
        );
    }

    private static void genRoot(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.display(
                       new ItemStackTemplate(ItemRegistry.SUGAR_REFINERY),
                       AdvancementResources.ROOT.name(),
                       AdvancementResources.ROOT.desc(),
                       Identifier.fromNamespaceAndPath(
                               CandyWorkshop.MODID,
                               "textures/gui/advancements/backgrounds/ccw.png"
                       ),
//                        Identifier.withDefaultNamespace("textures/block/pink_wool.png"),
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_sugar_refinery",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SUGAR_REFINERY)
               )
               .requirements(AdvancementRequirements.allOf(List.of("get_sugar_refinery")))
               .save(writer, AdvancementResources.ROOT.id());
    }

    private static void genEcoMat(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.ECOMAT.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.MILK_PACKAGING),
                       AdvancementResources.ECOMAT.name(),
                       AdvancementResources.ECOMAT.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_milk_packaging",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MILK_PACKAGING)
               )
               .requirements(AdvancementRequirements.allOf(List.of("get_milk_packaging")))
               .save(writer, AdvancementResources.ECOMAT.id());
    }

    private static void genCowChi(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.COWCHI.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.MILK_EXTRACTOR),
                       AdvancementResources.COWCHI.name(),
                       AdvancementResources.COWCHI.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("use_milk_extractor",
                             PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                                     ItemPredicate.Builder.item().of(
                                             registries.lookupOrThrow(Registries.ITEM),
                                             ItemRegistry.MILK_EXTRACTOR
                                     ),
                                     Optional.of(
                                             ContextAwarePredicate.create(
                                                     LootItemEntityPropertyCondition.hasProperties(
                                                             LootContext.EntityTarget.THIS,
                                                             EntityPredicate.Builder.entity().of(
                                                                     registries.lookupOrThrow(Registries.ENTITY_TYPE),
                                                                     EntityType.COW
                                                             )
                                                     ).build()
                                             )
                                     )
                             )
               )
               .requirements(AdvancementRequirements.allOf(List.of("use_milk_extractor")))
               .save(writer, AdvancementResources.COWCHI.id());
    }

    private static void genExcExt(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.EXCEXT.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.CARTON_MILK),
                       AdvancementResources.EXCEXT.name(),
                       AdvancementResources.EXCEXT.desc(),
                       null,
                       AdvancementType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("drain_milk_extractor",
                             ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                     Optional.of(
                                             ItemPredicate.Builder.item()
                                                                  .of(
                                                                          registries.lookupOrThrow(Registries.ITEM),
                                                                          ItemRegistry.MILK_EXTRACTOR
                                                                  )
                                                                  .build()
                                     ),
                                     MinMaxBounds.Ints.atMost(1)
                             )
               )
               .requirements(AdvancementRequirements.allOf(List.of("drain_milk_extractor")))
               .save(writer, AdvancementResources.EXCEXT.id());
    }

    private static void genMyFiCa(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.MYFICA.parent())
               .display(
                       getSugarIcon(Sugars.RED_HEART),
                       AdvancementResources.MYFICA.name(),
                       AdvancementResources.MYFICA.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_gummy", InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GUMMY))
               .requirements(AdvancementRequirements.allOf(List.of("get_gummy")))
               .save(writer, AdvancementResources.MYFICA.id());
    }

    private static void genAdCaMa(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.ADCAMA.parent())
               .display(
                       getSugarIcon(Sugars.GOLDEN_HEART),
                       AdvancementResources.ADCAMA.name(),
                       AdvancementResources.ADCAMA.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("refine_flavored_sugar",
                             CriteriaRegistry.REFINE_FLAVORED_SUGAR.get().createCriterion(
                                     new SimpleTrigger.TriggerInstance()
                             )
               )
               .requirements(AdvancementRequirements.allOf(List.of("refine_flavored_sugar")))
               .save(writer, AdvancementResources.ADCAMA.id());
    }

    private static void genNeCrFl(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.NECRFL.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.NETHER_SUGAR),
                       AdvancementResources.NECRFL.name(),
                       AdvancementResources.NECRFL.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_nether_sugar",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHER_SUGAR)
               )
               .requirements(AdvancementRequirements.allOf(List.of("get_nether_sugar")))
               .save(writer, AdvancementResources.NECRFL.id());
    }

    private static void genCanFin(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.CANFIN.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.ENDER_SUGAR),
                       AdvancementResources.CANFIN.name(),
                       AdvancementResources.CANFIN.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_ender_sugar",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ENDER_SUGAR)
               )
               .requirements(AdvancementRequirements.allOf(List.of("get_ender_sugar")))
               .save(writer, AdvancementResources.CANFIN.id());
    }

    private static void genRaiCol(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.RAICOL.parent())
               .display(
                       getSugarIcon(Sugars.RECOVERY),
                       AdvancementResources.RAICOL.name(),
                       AdvancementResources.RAICOL.desc(),
                       null,
                       AdvancementType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("collect_all_sugar",
                             CriteriaRegistry.COLLECT_ALL_SUGAR.get().createCriterion(
                                     new SimpleTrigger.TriggerInstance()
                             )
               )
               .requirements(AdvancementRequirements.allOf(List.of("collect_all_sugar")))
               .save(writer, AdvancementResources.RAICOL.id());
    }

    private static void genDiabet(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.DIABET.parent())
               .display(
                       getSugarIcon(Sugars.STINGER),
                       AdvancementResources.DIABET.name(),
                       AdvancementResources.DIABET.desc(),
                       null,
                       AdvancementType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("develop_diabetes",
                             CriteriaRegistry.DEVELOP_DIABETES.get().createCriterion(
                                     new NumericTrigger.TriggerInstance(
                                             MinMaxBounds.Ints.atLeast(100)
                                     )
                             )
               )
               .requirements(AdvancementRequirements.allOf(List.of("develop_diabetes")))
               .save(writer, AdvancementResources.DIABET.id());
    }

    private static void genDraTab(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.DRATAB.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.PLAIN_DRAWER_TABLE),
                       AdvancementResources.DRATAB.name(),
                       AdvancementResources.DRATAB.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_plain_drawer_table",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.PLAIN_DRAWER_TABLE)
               )
               .requirements(AdvancementRequirements.allOf(List.of("get_plain_drawer_table")))
               .save(writer, AdvancementResources.DRATAB.id());
    }

    private static void genMoBeDr(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.MOBEDR.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.DRAWER_TABLE),
                       AdvancementResources.MOBEDR.name(),
                       AdvancementResources.MOBEDR.desc(),
                       null,
                       AdvancementType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_drawer_table",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DRAWER_TABLE)
               )
               .requirements(AdvancementRequirements.allOf(List.of("get_drawer_table")))
               .save(writer, AdvancementResources.MOBEDR.id());
    }

    private static void genTrCaWo(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.TRCAWO.parent())
               .display(
                       new ItemStackTemplate(ItemRegistry.DRAWER_TABLE),
                       AdvancementResources.TRCAWO.name(),
                       AdvancementResources.TRCAWO.desc(),
                       null,
                       AdvancementType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("place_candy_workshop",
                             CriteriaTriggers.ITEM_USED_ON_BLOCK.createCriterion(
                                     new ItemUsedOnLocationTrigger.TriggerInstance(
                                             Optional.empty(),
                                             Optional.of(
                                                     ContextAwarePredicate.create(
                                                             MatchTool.toolMatches(
                                                                     ItemPredicate.Builder.item().of(
                                                                             registries.lookupOrThrow(Registries.ITEM),
                                                                             ItemRegistry.SUGAR_REFINERY
                                                                     )
                                                             ).build(),
                                                             LootItemBlockStatePropertyCondition
                                                                     .hasBlockStateProperties(
                                                                             BlockRegistry.DRAWER_TABLE.get()
                                                                     ).build()
                                                     )
                                             )
                                     )
                             )
               )
               .requirements(AdvancementRequirements.allOf(List.of("place_candy_workshop")))
               .save(writer, AdvancementResources.TRCAWO.id());
    }

    private static ItemStackTemplate getSugarIcon(Holder<Sugar> sugar) {
        return new ItemStackTemplate(ItemRegistry.GUMMY,
                                     DataComponentPatch.builder()
                                                       .set(DataComponents.ITEM_MODEL,
                                                            Sugar.getModelId(sugar))
                                                       .build());
    }

    public static class AdvancementResources
    {
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
        private final Identifier id;

        public AdvancementResources(
                String name,
                @Nullable AdvancementHolder parent,
                String nameKey,
                String descKey,
                Identifier id
        ) {
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
                    CandyWorkshop.id("root")
            );
        }

        static AdvancementResources of(String name, String parentLoc) {
            return new AdvancementResources(
                    name,
                    AdvancementSubProvider.createPlaceholder(CandyWorkshop.MODID + ":" + parentLoc),
                    "advancements." + CandyWorkshop.MODID + "." + name + ".title",
                    "advancements." + CandyWorkshop.MODID + "." + name + ".description",
                    CandyWorkshop.id(name)
            );
        }

        static AdvancementResources of(String name, AdvancementResources parent) {
            return of(name, parent.name);
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

        public String id() {
            return id.toString();
        }
    }
}
