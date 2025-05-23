package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.block.SugarRefineryBlock;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.SingleEffectSugar;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import com.lnatit.ccw.menu.SugarRefineryMenu;
import com.lnatit.ccw.misc.critereon.CriteriaRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.Nullable;

public class SugarRefineryBlockEntity extends BlockEntity implements MenuProvider, Nameable, IItemStackHandlerContainer {
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    private final Data data = new Data();
    @Nullable
    private Component name;

    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity refinery) {
        if (level.isClientSide()) return;

        if (refinery.data.tick())
            refinery.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("data", this.data.serializeNBT());
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.data.deserializeNBT(tag.getCompound("data"));
        if (tag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    public IItemHandler accessInventory(@Nullable Direction direction) {
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
        return new SugarRefineryMenu(
                containerId,
                playerInventory,
                this.data,
                this.data.getDataAccess(),
                ContainerLevelAccess.create(this.level, this.worldPosition)
        );
    }

    @Override
    public ItemStackHandler getInventory() {
        return this.data;
    }

    private void refineFlavoredCallback() {
        int i = worldPosition.getX();
        int j = worldPosition.getY();
        int k = worldPosition.getZ();
        for (ServerPlayer serverplayer : level.getEntitiesOfClass(
                ServerPlayer.class, new AABB(i, j, k, i, j - 4, k).inflate(10.0, 5.0, 10.0)
        ))
            CriteriaRegistry.REFINE_FLAVORED_SUGAR.get().trigger(serverplayer);
    }

    public class Data extends ItemStackHandler
    {
        public static final int COMMON_MILK_CONSUMPTION = 1;
        public static final int CARTON_MILK_CONSUMPTION = 8;
        public static final int SUGAR_CONSUMPTION = 8;
        public static final int SUGAR_PRODUCTION = 8;

        private boolean changedExternal = true;
        private int progress = 0;
        private ItemStack scheduledOutput = ItemStack.EMPTY;

        private Data() {
            super(8);
        }

        private DataSlot getDataAccess() {
            return new DataSlot() {
                @Override
                public int get() {
                    return scheduledOutput.isEmpty() ? ~progress : progress;
                }

                @Override
                public void set(int value) {
                    if (value < 0) {
                        scheduledOutput = ItemStack.EMPTY;
                        progress = 0;
                    } else {
                        changedExternal = true;
                    }
                }
            };
        }

        private boolean tick() {
            boolean flag = false;
            if (changedExternal) {
                // match recipe
                if (updateRecipe())
                    progress = 0;
                changedExternal = false;
                flag = true;
            }

            if (!scheduledOutput.isEmpty()) {
                progress++;
                flag = true;
            }

            if (progress >= SugarRefining.REFINE_TIME) {
                progress = 0;
                // generate the outputs
                generateOutputs();
                // the flag is set during output generation
                changedExternal = true;
                flag = true;
            }

            return flag;
        }

        /**
         * @return true if new matched output is different from the old one
         */
        private boolean updateRecipe() {
            if (!hasEnoughMilkAndSugar()) {
                scheduledOutput = ItemStack.EMPTY;
                return false;
            }

            ItemStack newOutput = SugarRefining.sugarRefining.makeSugar(this.stacks.get(1), this.stacks.get(2), this.stacks.get(3));
            ItemStack output = this.stacks.get(4);

            if (!output.isEmpty() && (!ItemStack.isSameItemSameTags(output, newOutput) ||
                    output.getCount() + newOutput.getCount() > output.getMaxStackSize())) {
                newOutput = ItemStack.EMPTY;
            }

            if (!ItemStack.isSameItemSameTags(scheduledOutput, newOutput)) {
                scheduledOutput = newOutput;
                return true;
            }
            return false;
        }

        private boolean hasEnoughMilkAndSugar() {
            ItemStack milk = this.stacks.get(0);
            ItemStack sugar = this.stacks.get(1);
            if (milk.isEmpty() || sugar.isEmpty())
                return false;
            if (!milk.is(ItemRegistry.MILK_TAG) || !SugarRefining.sugarRefining.isSugar(sugar))
                return false;

            int milkCount = getMilkConsumption(milk);
            int sugarCount = SUGAR_CONSUMPTION;

            return milk.getCount() >= milkCount && sugar.getCount() >= sugarCount;
        }

        private void generateOutputs() {
            // consume ingredients
            ItemStack milk = this.stacks.get(0);
            int milkConsumption = getMilkConsumption(milk);
            acceptRemainder(milk.getCraftingRemainingItem(), milkConsumption);
            milk.shrink(milkConsumption);

            ItemStack sugar = this.stacks.get(1);
            sugar.shrink(SUGAR_CONSUMPTION);

            ItemStack main = this.stacks.get(2);
            acceptRemainder(main.getCraftingRemainingItem(), 1);
            main.shrink(1);

            ItemStack extra = this.stacks.get(3);
            SingleEffectSugar.Flavor flavor = SingleEffectSugar.Flavor.fromExtra(extra);
            if (flavor != SingleEffectSugar.Flavor.ORIGINAL) {
                acceptRemainder(extra.getCraftingRemainingItem(), 1);
                extra.shrink(1);
                SugarRefineryBlockEntity.this.refineFlavoredCallback();
            }

            ItemStack output = this.stacks.get(4);
            if (output.isEmpty()) {
                scheduledOutput.setCount(SUGAR_PRODUCTION);
                this.stacks.set(4, scheduledOutput);
            } else {
                output.grow(SUGAR_PRODUCTION);
            }
            scheduledOutput = ItemStack.EMPTY;
        }

        private void acceptRemainder(ItemStack remainder, int count) {
            remainder.setCount(count);
            for (int i = 5; i < 8; i++) {
                ItemStack stack = this.stacks.get(i);
                if (stack.isEmpty()) {
                    this.stacks.set(i, remainder);
                    return;
                } else if (ItemStack.isSameItemSameTags(stack, remainder)) {
                    int consume = Math.min(stack.getMaxStackSize() - stack.getCount(), count);
                    stack.grow(consume);
                    this.stacks.set(i, stack);
                    remainder.shrink(consume);
                    if (remainder.isEmpty()) {
                        return;
                    }
                }
            }
            if (SugarRefineryBlockEntity.this.level != null) {
                Containers.dropItemStack(
                        SugarRefineryBlockEntity.this.level,
                        SugarRefineryBlockEntity.this.worldPosition.getX(),
                        SugarRefineryBlockEntity.this.worldPosition.getY(),
                        SugarRefineryBlockEntity.this.worldPosition.getZ(),
                        remainder
                );
            }
        }

        public IItemHandler getInventoryAccess(Direction facing, @Nullable Direction direction) {
            if (direction == Direction.UP)
                return new RangedWrapper(this, 0, 2);
            if (direction == Direction.DOWN)
                return new RangedWrapper(this, 4, 8);
            if (direction == facing.getClockWise())
                return new RangedWrapper(this, 2, 3);
            if (direction == facing.getCounterClockWise())
                return new RangedWrapper(this, 3, 4);
            return new RangedWrapper(this, 2, 8);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.is(ItemRegistry.MILK_TAG);
                case 1 -> SugarRefining.sugarRefining.isSugar(stack);
                case 2 -> SugarRefining.sugarRefining.isMain(stack);
                case 3 -> SugarRefining.sugarRefining.isExtra(stack);
                default -> false;
            };
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = super.serializeNBT();
            tag.putInt("progress", progress);
            if (!scheduledOutput.isEmpty()) {
                tag.put("scheduled_output", scheduledOutput.save(new CompoundTag()));
            }
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            progress = nbt.getInt("progress");
            scheduledOutput = ItemStack.of(nbt.getCompound("scheduled_output"));
//            changedExternal = true;
        }

        @Override
        protected void onContentsChanged(int slot) {
            changedExternal = true;
        }

        private static int getMilkConsumption(ItemStack milk) {
            return milk.is(ItemRegistry.CARTON_MILK_TAG) ? CARTON_MILK_CONSUMPTION : COMMON_MILK_CONSUMPTION;
        }
    }
}
