package com.lnatit.ccw.block.entity;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.block.BlockRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.SugarContents;
import com.lnatit.ccw.item.sugaring.SugarRefining;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class SugarRefineryBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    public static final Component DEFAULT_NAME = Component.translatable("container.sugar_refinery");
    public static final int REFINE_TIME = 160;

    Contents inventory = new Contents();
    int matchTime = 0;
    long startTime = 0;
    boolean changed = true;

    public SugarRefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.SUGAR_REFINERY_BETYPE.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarRefineryBlockEntity blockEntity) {
        blockEntity.tick(level);
    }

    private static int toInt(long value) {
        return (int) value != value ? value > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE : (int) value;
    }

    @Override
    public void setChanged() {
        this.changed = true;
        super.setChanged();
    }

    public void tick(Level level) {
        // RecipeHolder will matchKnown the recipe & store max craft times, and update the data accrodingly
        // BE will store nearest finish time, check it on each tick and update if needed
        // Each time when the contents is changed by external operations (other than recipe update)
        // will trigger a rematch of RecipeHolder
        long delta = level.getGameTime() - this.startTime;
        if (this.changed) {
            this.matchTime = this.inventory.matchRecipe();
            if (this.matchTime > 0) {
                this.startTime = level.getGameTime();
            }
        } else {
            if (matchTime > 0 && delta >= REFINE_TIME) {
                int times = toInt(delta / REFINE_TIME);
                if (times > matchTime) {
                    times = this.matchTime;
                }
                this.matchTime -= times;
                this.startTime += (long) times * REFINE_TIME;
                this.inventory.genOutputs(times);
                // no need to mark flag since it's an internal update
                super.setChanged();
            }
        }
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    // TODO copy BaseContainer
    @Override
    public Component getName() {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return null;
    }

    /**
     * milk, sugar, main, extra, output, misc1, misc2, misc3
     */
    public class Contents extends ItemStackHandler {
        public static final TagKey<Item> EXTRAS =
                TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, "extras"));
        public static final int SUGAR_CONSUMPTION = 8;
        public static final int SUGAR_PRODUCTION = 8;

        public Contents() {
            super(8);
        }

        public int matchRecipe() {
            int count = this.matchKnown();
            if (count > 0) {
                return count;
            }
            // match custom recipes...
            return this.matchCustom();
        }

        private int matchKnown() {
            ItemStack slot1 = this.getStackInSlot(0);
            ItemStack slot2 = this.getStackInSlot(1);
            ItemStack slot3 = this.getStackInSlot(2);
            Holder<Sugar> result = SugarRefining.KNOWN_REFINERS.get(slot3.getItem());
            ItemStack slot4 = this.getStackInSlot(3);
            if (slot1.is(Items.MILK_BUCKET) &&
                    slot2.is(Items.SUGAR) &&
                    result != null &&
                    (slot4.is(EXTRAS) || slot4.isEmpty())
            ) {
                int count = Math.min(slot1.getCount(), slot2.getCount() / SUGAR_CONSUMPTION);
                count = Math.min(count, slot3.getCount());
                count = Math.min(count, slot4.getCount());
                // check output's availability
                ItemStack output = this.getStackInSlot(4);
                SugarContents exist = output.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
                if (exist == null || !exist.is(result)) {
                    return 0;
                }
                count = Math.min(count, output.getMaxStackSize() - output.getCount());
                // check returns' availability
                return count;
            }
            return 0;
        }

        private int matchCustom() {
            // TODO we need to consider the situation when the recipe is reloaded...
            return 0;
        }

        public void genOutputs(int times) {
            this.getStackInSlot(0).shrink(times);

            this.getStackInSlot(1).shrink(times * SUGAR_CONSUMPTION);

            ItemStack main = this.getStackInSlot(2);
            main.shrink(times);

            ItemStack extra = this.getStackInSlot(3);
            Sugar.Type type = Sugar.Type.fromExtra(extra);
            if (type != Sugar.Type.BASE) {
                extra.shrink(times);
            }

            ItemStack output = this.getStackInSlot(4);
            if (output.isEmpty()) {
                output = Sugar.createSugarItem(SugarRefining.KNOWN_REFINERS.get(main.getItem()), type);
                this.setStackInSlot(4, output);
            } else {
                output.grow(times * SUGAR_PRODUCTION);
            }

            // make returns
        }

        @Override
        public void setSize(int size) {
            super.setSize(8);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch (slot) {
                case 0 ->
                    // TODO
                        stack.is(Items.MILK_BUCKET);
                case 1 -> stack.is(Items.SUGAR);
                case 2, 3 -> true;
                default -> false;
            };
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            SugarRefineryBlockEntity.this.changed = true;
        }
    }
}
