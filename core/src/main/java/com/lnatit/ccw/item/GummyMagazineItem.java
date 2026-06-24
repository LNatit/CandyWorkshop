package com.lnatit.ccw.item;

import com.lnatit.ccw.item.component.GummyContents;
import com.lnatit.ccw.item.component.IContents;
import com.lnatit.ccw.item.component.MutableContents;
import com.lnatit.ccw.menu.GummyContentMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;
import java.util.function.Function;

public class GummyMagazineItem extends GummyDeviceItem
{
    public static final String DESC_1_KEY = "item.ccw.gummy_magazine.desc0";
    public static final String DESC_2_KEY = "item.ccw.gummy_magazine.desc1";
    public static final String FOLDED_1_KEY = "item.ccw.gummy_magazine.folded0";
    public static final String FOLDED_2_KEY = "item.ccw.gummy_magazine.folded1";
    public static final String FOLDED_3_KEY = "item.ccw.gummy_magazine.folded2";

    public static final Component DESC_1 = Component.translatable(DESC_1_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component DESC_2 = Component.translatable(DESC_2_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_1 = Component.translatable(FOLDED_1_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_2 = Component.translatable(FOLDED_2_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_3 = Component.translatable(FOLDED_3_KEY).withStyle(ChatFormatting.GRAY);

    private GummyMagazineItem(Properties properties, Tier tier) {
        super(properties, IContents.Type.MAGAZINE, tier);
    }

    public static GummyMagazineItem create(Tier tier) {
        return new GummyMagazineItem(new Item.Properties().component(IContents.Type.MAGAZINE.dataComponentType,
                                                                     IContents.Type.MAGAZINE.defaultContents()), tier);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        MutableContents magazine = this.getMutable(itemstack);
        boolean client = level.isClientSide();

        if (player.isShiftKeyDown()) {
            if (!client) {
                int slot = usedHand == InteractionHand.MAIN_HAND ? player.getInventory().getSelectedSlot() : 0;
                GummyContentMenu.Provider provider =
                        GummyContentMenu.provider(this.type, magazine, usedHand, slot, itemstack.getHoverName());
                player.openMenu(provider);
            }
        }
        else {
            if (magazine.activeSlots().stream().allMatch(ItemStack::isEmpty) || !eatGummies(level, player, magazine)) {
                return InteractionResult.FAIL;
            }
            GummyContents.set(itemstack, magazine);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS_SERVER;
    }

    public static boolean eatGummies(Level level, Player player, MutableContents magazine) {
        return magazine.apply(new Applier(level, player));
    }

    @Override
    protected void appendFoldedTooltips(Consumer<Component> builder) {
        builder.accept(FOLDED_1);
        builder.accept(FOLDED_2);
        builder.accept(FOLDED_3);
    }

    @Override
    protected void appendCommonTooltips(ItemStack stack, Consumer<Component> builder) {
        builder.accept(DESC_1);
        builder.accept(DESC_2);
    }

    private record Applier(Level level, LivingEntity entity) implements Function<ItemStack, ItemStack>
    {
        @Override
        public ItemStack apply(ItemStack stack) {
            if (stack.isEmpty()) return stack;
            return stack.copy().finishUsingItem(level, entity);
        }
    }
}
