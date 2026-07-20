package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.SugarRefineryBlock;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.data.IFormula;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.crafting.RecipeRegistry;
import com.lnatit.ccw.item.crafting.RefiningInput;
import com.lnatit.ccw.item.crafting.RefiningRecipe;
import com.lnatit.ccw.item.sugaring.Flavor;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import com.lnatit.ccw.misc.critereon.CriteriaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.transfer.RangedResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class SugarRefineryBlockEntity extends BlockEntity implements MenuProvider, Nameable, ExtractedContainer
{
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    public static final int REFINE_TIME = 160;
    private final Data data = new Data();
    @Nullable
    private Component name;

    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity refinery) {
        if (level.isClientSide()) return;

        Optional<ResourceHandler<ItemResource>> drawer = level
                .getBlockEntity(pos.below(), BlockRegistry.DRAWER_TABLE_BETYPE.get())
                .map(DrawerTableBlockEntity::getInventory);
        if (refinery.data.tick(drawer.orElse(null))) {
            refinery.setChanged();
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.data.deserialize(input.childOrEmpty("data"));
        this.name = parseCustomNameSafe(input, "CustomName");
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putChild("data", this.data);
        output.storeNullable("CustomName", ComponentSerialization.CODEC, this.name);
    }

    public ResourceHandler<ItemResource> accessInventory(@Nullable Direction direction) {
        return this.data.getInventoryAccess(this.getBlockState().getValue(SugarRefineryBlock.FACING), direction);
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : DEFAULT_NAME;
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    public @Nullable Component getCustomName() {
        return this.name;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SugarRefineryMenu(containerId,
                                     playerInventory,
                                     this.data,
                                     this.data.getDataAccess(),
                                     ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    @Override
    public ItemStacksResourceHandler getInventory() {
        return this.data;
    }

    private void refineFlavoredCallback() {
        if (this.level == null) return;
        int i = worldPosition.getX();
        int j = worldPosition.getY();
        int k = worldPosition.getZ();
        for (ServerPlayer serverplayer : this.level.getEntitiesOfClass(ServerPlayer.class,
                                                                       new AABB(i, j, k, i, j - 4, k).inflate(10.0,
                                                                                                              5.0,
                                                                                                              10.0))) {
            CriteriaRegistry.REFINE_FLAVORED_SUGAR.get().trigger(serverplayer);
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        if (this.level != null) {
            this.onRemove(pos, this.level, state);
        }
    }

    public class Data extends ItemStacksResourceHandler
    {
        private boolean changedExternal = true;
        private int progress = 0;
        private Optional<? extends IFormula> formula = Optional.empty();

        private Data() {
            super(8);
        }

        private DataSlot getDataAccess() {
            return new DataSlot()
            {
                @Override
                public int get() {
                    return formula.isEmpty() ? ~progress : progress;
                }

                @Override
                public void set(int value) {
                    if (value < 0) {
                        formula = Optional.empty();
                        progress = 0;
                    }
                    else {
                        changedExternal = true;
                    }
                }
            };
        }

        private boolean tick(@Nullable ResourceHandler<ItemResource> drawer) {
            boolean flag = false;
            if (changedExternal) {
                // match formula
                if (updateFormula()) {
                    progress = 0;
                }
                changedExternal = false;
                flag = true;
            }

            if (formula.isPresent()) {
                progress++;
                flag = true;
            }

            if (progress >= REFINE_TIME) {
                progress = 0;
                // generate the outputs
                generateOutputs(drawer);
                // the flag is set during output generation
                changedExternal = true;
                flag = true;
            }

            return flag;
        }

        private RefiningInput getInput() {
            return new RefiningInput(stacks.get(0), stacks.get(1), stacks.get(2), stacks.get(3));
        }

        /**
         * @return true if new matched formula is different from the old one
         */
        private boolean updateFormula() {
            if (SugarRefineryBlockEntity.this.level instanceof ServerLevel serverLevel) {
                RefiningInput input = getInput();
                Optional<? extends IFormula> newFormula = Optional.empty();

                // Refining Match first
                Holder<Sugar> sugar = Sugar.from(input);
                if (sugar != null) {
                    newFormula = Formula.getFormulaOptional(sugar, Flavor.from(input.extra()));
                }

                // Fall back to vanilla recipe
                if (newFormula.isEmpty()) {
                    newFormula = serverLevel
                            .recipeAccess()
                            .getRecipeFor(RecipeRegistry.REFINING.get(), input, serverLevel)
                            .map(RecipeHolder::value);
                }

                if (newFormula.isPresent()) {
                    ItemResource output = this.getResource(4);
                    if (!output.isEmpty()) {
                        ItemStack production = newFormula.get().productionOf(input);
                        if (production.isEmpty()
                            || !ItemStack.isSameItemSameComponents(output.toStack(), production)
                            || this.getAmountAsInt(4) + production.getCount() > output.getMaxStackSize()) {
                            newFormula = Optional.empty();
                        }
                    }
                }

                if (this.formula.equals(newFormula)) {
                    return false;
                }

                this.formula = newFormula;
                return true;
            }
            return false;
        }

        private void generateOutputs(@Nullable ResourceHandler<ItemResource> drawer) {
            if (this.formula.isEmpty()) {
                return;
            }

            ItemStack batched = this.formula
                    .get()
                    .batch(this.getInput(), remainder -> acceptRemainder(remainder, drawer));
            if (batched.has(ItemRegistry.SUGAR_CONTENTS_DCTYPE) && !batched
                    .get(ItemRegistry.SUGAR_CONTENTS_DCTYPE)
                    .flavor()
                    .is(Flavors.ORIGINAL)) {
                SugarRefineryBlockEntity.this.refineFlavoredCallback();
            }

            ItemStack output = this.stacks.get(4);
            if (output.isEmpty()) {
                output = batched;
            }
            else {
                output.grow(batched.getCount());
            }

            this.stacks.set(4, drain(output, drawer));
            this.formula = Optional.empty();
        }

        private void acceptRemainder(ItemStack remainder, @Nullable ResourceHandler<ItemResource> drawer) {
            remainder = drain(remainder, drawer);
            for (int i = 5; i < 8; i++) {
                ItemStack stack = this.stacks.get(i);
                if (stack.isEmpty()) {
                    this.stacks.set(i, remainder);
                    return;
                }
                else if (ItemStack.isSameItemSameComponents(stack, remainder)) {
                    int consume = Math.min(stack.getMaxStackSize() - stack.getCount(), remainder.getCount());
                    stack.grow(consume);
                    this.stacks.set(i, stack);
                    remainder.shrink(consume);
                    if (remainder.isEmpty()) {
                        return;
                    }
                }
            }
            if (SugarRefineryBlockEntity.this.level != null) {
                Containers.dropItemStack(SugarRefineryBlockEntity.this.level,
                                         SugarRefineryBlockEntity.this.worldPosition.getX(),
                                         SugarRefineryBlockEntity.this.worldPosition.getY(),
                                         SugarRefineryBlockEntity.this.worldPosition.getZ(),
                                         remainder);
            }
        }

        private ItemStack drain(ItemStack stack, @Nullable ResourceHandler<ItemResource> to) {
            if (to == null) {
                return stack;
            }

            int inserted = 0;
            try (Transaction t = Transaction.openRoot()) {
                inserted = to.insert(ItemResource.of(stack), stack.getCount(), t);
            }

            if (stack.getCount() - inserted <= 0) {
                return ItemStack.EMPTY;
            }
            return stack.copyWithCount(stack.getCount() - inserted);
        }

        public ResourceHandler<ItemResource> getInventoryAccess(Direction facing, @Nullable Direction direction) {
            if (direction == Direction.UP) {
                return RangedResourceHandler.of(this, 0, 2);
            }
            if (direction == Direction.DOWN) {
                return RangedResourceHandler.of(this, 4, 8);
            }
            if (direction == facing.getClockWise()) {
                return RangedResourceHandler.of(this, 2, 3);
            }
            if (direction == facing.getCounterClockWise()) {
                return RangedResourceHandler.of(this, 3, 4);
            }
            return RangedResourceHandler.of(this, 2, 8);
        }

        @Override
        public boolean isValid(int index, ItemResource resource) {
            return switch (index) {
                case 0 -> this.isMilk(resource.toStack());
                case 1 -> this.isSugar(resource.toStack());
                case 2 -> this.isMain(resource.toStack());
                case 3 -> this.isExtra(resource.toStack());
                default -> false;
            };
        }

        @Override
        public void serialize(ValueOutput output) {
            super.serialize(output);
            output.putInt("progress", progress);
        }

        @Override
        public void deserialize(ValueInput input) {
            super.deserialize(input);
            progress = input.getIntOr("progress", 0);
            this.updateFormula();
        }

        @Override
        protected void onContentsChanged(int index, ItemStack previousContents) {
            super.onContentsChanged(index, previousContents);
            changedExternal = true;
        }

        private boolean isMilk(ItemStack stack) {
            return IFormula.isMilk(stack) || testSized(RefiningRecipe::milk, stack);
        }

        private boolean isSugar(ItemStack stack) {
            return IFormula.isSugar(stack) || testSized(RefiningRecipe::sugar, stack);
        }

        private boolean isMain(ItemStack stack) {
            return IFormula.isMain(stack) || testMain(RefiningRecipe::main, stack);
        }

        private boolean isExtra(ItemStack stack) {
            return IFormula.isExtra(stack) || testExtra(RefiningRecipe::extra, stack);
        }

        private boolean testSized(Function<RefiningRecipe, SizedIngredient> ingredientGetter, ItemStack stack) {
            if (SugarRefineryBlockEntity.this.level instanceof ServerLevel serverLevel) {
                return serverLevel.recipeAccess()
                        .getRecipeFor(RecipeRegistry.REFINING.get(), this.getInput(), serverLevel)
                        .isPresent();
            }
            return false;
        }

        private boolean testMain(Function<RefiningRecipe, Ingredient> ingredientGetter, ItemStack stack) {
//            if (SugarRefineryBlockEntity.this.level  instanceof ServerLevel serverLevel) {
//                return serverLevel.recipeAccess()
//                                  .getRecipeFor(RecipeRegistry.REFINING.get(), this.getInput(), serverLevel)
//                                  .isPresent();
//            }
            return true;
        }

        private boolean testExtra(Function<RefiningRecipe, Optional<Ingredient>> ingredientGetter, ItemStack stack) {
            return true;
        }
    }
}
