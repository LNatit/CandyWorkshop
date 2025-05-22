package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarUtils;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.misc.critereon.CriteriaRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
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

    private static void genRoot(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.display(
                       new ItemStack(ItemRegistry.SUGAR_REFINERY.get()),
                       AdvancementResources.ROOT.name(),
                       AdvancementResources.ROOT.desc(),
                       ResourceLocation.fromNamespaceAndPath(
                               CandyWorkshop.MODID,
                               "textures/gui/advancements/backgrounds/ccw.png"
                       ),
//                        ResourceLocation.withDefaultNamespace("textures/block/pink_wool.png"),
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_sugar_refinery",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SUGAR_REFINERY.get())
               )
               .save(writer, AdvancementResources.ROOT.id());
    }

    private static void genEcoMat(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.ECOMAT.parent())
               .display(
                       new ItemStack(ItemRegistry.MILK_PACKAGING.get()),
                       AdvancementResources.ECOMAT.name(),
                       AdvancementResources.ECOMAT.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_milk_packaging",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MILK_PACKAGING.get())
               )
               .save(writer, AdvancementResources.ECOMAT.id());
    }

    private static void genCowChi(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.COWCHI.parent())
               .display(
                       new ItemStack(ItemRegistry.MILK_EXTRACTOR.get()),
                       AdvancementResources.COWCHI.name(),
                       AdvancementResources.COWCHI.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("use_milk_extractor",
                             PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                                     ItemPredicate.Builder.item().of(
                                             ItemRegistry.MILK_EXTRACTOR.get()
                                     ),
                                     ContextAwarePredicate.create(
                                             LootItemEntityPropertyCondition.hasProperties(
                                                     LootContext.EntityTarget.THIS,
                                                     EntityPredicate.Builder.entity().of(
                                                             EntityType.COW
                                                     )
                                             ).build()
                                     )

                             )
               )
               .save(writer, AdvancementResources.COWCHI.id());
    }

    private static void genExcExt(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.EXCEXT.parent())
               .display(
                       new ItemStack(ItemRegistry.CARTON_MILK.get()),
                       AdvancementResources.EXCEXT.name(),
                       AdvancementResources.EXCEXT.desc(),
                       null,
                       FrameType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("drain_milk_extractor",
                             ItemDurabilityTrigger.TriggerInstance.changedDurability(
                                     ItemPredicate.Builder.item()
                                                          .of(
                                                                  ItemRegistry.MILK_EXTRACTOR.get()
                                                          )
                                                          .build()
                                     ,
                                     MinMaxBounds.Ints.atMost(1)
                             )
               )
               .save(writer, AdvancementResources.EXCEXT.id());
    }

    private static void genMyFiCa(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.MYFICA.parent())
               .display(
                       SugarUtils.createSugar(Sugars.RED_HEART.get(), Sugar.Flavor.ORIGINAL),
                       AdvancementResources.MYFICA.name(),
                       AdvancementResources.MYFICA.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_gummy",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GUMMY_ITEM.get())
               )
               .save(writer, AdvancementResources.MYFICA.id());
    }

    private static void genAdCaMa(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.ADCAMA.parent())
               .display(
                       SugarUtils.createSugar(Sugars.GOLDEN_HEART.get(), Sugar.Flavor.ORIGINAL),
                       AdvancementResources.ADCAMA.name(),
                       AdvancementResources.ADCAMA.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("refine_flavored_sugar",
                             CriteriaRegistry.REFINE_FLAVORED_SUGAR.createInstance(ContextAwarePredicate.ANY)
               )
               .save(writer, AdvancementResources.ADCAMA.id());
    }

    private static void genNeCrFl(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.NECRFL.parent())
               .display(
                       new ItemStack(ItemRegistry.NETHER_SUGAR.get()),
                       AdvancementResources.NECRFL.name(),
                       AdvancementResources.NECRFL.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_nether_sugar",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHER_SUGAR.get())
               )
               .save(writer, AdvancementResources.NECRFL.id());
    }

    private static void genCanFin(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.CANFIN.parent())
               .display(
                       new ItemStack(ItemRegistry.ENDER_SUGAR.get()),
                       AdvancementResources.CANFIN.name(),
                       AdvancementResources.CANFIN.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_ender_sugar",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ENDER_SUGAR.get())
               )
               .save(writer, AdvancementResources.CANFIN.id());
    }

    private static void genRaiCol(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.RAICOL.parent())
               .display(
                       SugarUtils.createSugar(Sugars.RECOVERY.get(), Sugar.Flavor.ORIGINAL),
                       AdvancementResources.RAICOL.name(),
                       AdvancementResources.RAICOL.desc(),
                       null,
                       FrameType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("collect_all_sugar",
                             CriteriaRegistry.COLLECT_ALL_SUGAR.createInstance(ContextAwarePredicate.ANY)
               )
               .save(writer, AdvancementResources.RAICOL.id());
    }

    private static void genDiabet(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.DIABET.parent())
               .display(
                       SugarUtils.createSugar(Sugars.STINGER.get(), Sugar.Flavor.ORIGINAL),
                       AdvancementResources.DIABET.name(),
                       AdvancementResources.DIABET.desc(),
                       null,
                       FrameType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("develop_diabetes",
                             CriteriaRegistry.DEVELOP_DIABETES.createInstance(
                                     ContextAwarePredicate.ANY,
                                     MinMaxBounds.Ints.atLeast(100)
                             )
               )
               .save(writer, AdvancementResources.DIABET.id());
    }

    private static void genDraTab(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.DRATAB.parent())
               .display(
                       new ItemStack(ItemRegistry.PLAIN_DRAWER_TABLE.get()),
                       AdvancementResources.DRATAB.name(),
                       AdvancementResources.DRATAB.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_plain_drawer_table",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.PLAIN_DRAWER_TABLE.get())
               )
               .save(writer, AdvancementResources.DRATAB.id());
    }

    private static void genMoBeDr(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.MOBEDR.parent())
               .display(
                       new ItemStack(ItemRegistry.DRAWER_TABLE.get()),
                       AdvancementResources.MOBEDR.name(),
                       AdvancementResources.MOBEDR.desc(),
                       null,
                       FrameType.TASK,
                       true,
                       true,
                       false
               )
               .addCriterion("get_drawer_table",
                             InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DRAWER_TABLE.get())
               )
               .save(writer, AdvancementResources.MOBEDR.id());
    }

    private static void genTrCaWo(HolderLookup.Provider registries, Consumer<Advancement> writer) {
        Advancement.Builder builder = Advancement.Builder.advancement();
        builder.parent(AdvancementResources.TRCAWO.parent())
               .display(
                       new ItemStack(ItemRegistry.DRAWER_TABLE.get()),
                       AdvancementResources.TRCAWO.name(),
                       AdvancementResources.TRCAWO.desc(),
                       null,
                       FrameType.CHALLENGE,
                       true,
                       true,
                       false
               )
               .addCriterion("place_candy_workshop",
                             new ItemUsedOnLocationTrigger.TriggerInstance(
                                     CriteriaTriggers.ITEM_USED_ON_BLOCK.getId(),
                                     ContextAwarePredicate.ANY,
                                     ContextAwarePredicate.create(
                                             MatchTool.toolMatches(
                                                     ItemPredicate.Builder.item().of(
                                                             ItemRegistry.SUGAR_REFINERY.get()
                                                     )
                                             ).build(),
                                             LootItemBlockStatePropertyCondition
                                                     .hasBlockStateProperties(
                                                             BlockRegistry.DRAWER_TABLE.get()
                                                     ).build()
                                     )
                             )

               )
               .save(writer, AdvancementResources.TRCAWO.id());
    }

    public static final class AdvancementResources
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
        private final Advancement parent;
        private final String nameKey;
        private final String descKey;
        private final ResourceLocation id;

        public AdvancementResources(String name, @Nullable Advancement parent, String nameKey, String descKey, ResourceLocation id) {
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

        @Nullable
        public Advancement parent() {
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
