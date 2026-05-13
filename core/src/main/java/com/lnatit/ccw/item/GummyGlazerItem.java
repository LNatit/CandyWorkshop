package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.item.component.*;
import com.lnatit.ccw.menu.GummyContentMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class GummyGlazerItem extends GummyDeviceItem
{
    public static final String DESC_1_KEY = "item.ccw.gummy_glazer.desc0";
    public static final String DESC_2_KEY = "item.ccw.gummy_glazer.desc1";
    public static final String DESC_UNFOLD_KEY = "item.ccw.unfold";
    public static final String FOLDED_1_KEY = "item.ccw.gummy_glazer.folded0";
    public static final String FOLDED_2_KEY = "item.ccw.gummy_glazer.folded1";
    public static final String FOLDED_3_KEY = "item.ccw.gummy_glazer.folded2";
    public static final String FOLDED_4_KEY = "item.ccw.gummy_glazer.folded3";
    public static final String FOLDED_5_KEY = "item.ccw.gummy_glazer.folded4";
    public static final String FOLDED_6_KEY = "item.ccw.gummy_glazer.folded5";

    public static final Component DESC_1 = Component.translatable(DESC_1_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component DESC_2 = Component.translatable(DESC_2_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component DESC_UNFOLD = Component.translatable(DESC_UNFOLD_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_1 = Component.translatable(FOLDED_1_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_2 = Component.translatable(FOLDED_2_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_3 = Component.translatable(FOLDED_3_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_4 = Component.translatable(FOLDED_4_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_5 = Component.translatable(FOLDED_5_KEY).withStyle(ChatFormatting.GRAY);
    public static final Component FOLDED_6 = Component.translatable(FOLDED_6_KEY).withStyle(ChatFormatting.GRAY);

    private GummyGlazerItem(Properties properties, Tier tier) {
        super(properties, IContents.Type.GLAZER, tier);
    }

    public static GummyGlazerItem create(Tier tier) {
        return new GummyGlazerItem(new Item.Properties().component(IContents.Type.GLAZER.dataComponentType,
                                                                   IContents.Type.GLAZER.defaultContents()), tier);
    }

//    @Override
//    public InteractionResult useOn(UseOnContext context) {
//        LivingEntity source = context.getPlayer();
//        LivingEntity target = context.
//    }

    // TODO check whether to switch between modes
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (player.isShiftKeyDown()) {
            ItemStack itemstack = player.getItemInHand(usedHand);
            if (!level.isClientSide()) {
                int slot = usedHand == InteractionHand.MAIN_HAND ? player.getInventory().getSelectedSlot() : 0;
                GummyContentMenu.Provider provider = GummyContentMenu.provider(this.type,
                                                                               this.getMutable(itemstack),
                                                                               usedHand,
                                                                               slot,
                                                                               itemstack.getHoverName());
                player.openMenu(provider);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(
            ItemStack itemStack,
            TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> builder,
            TooltipFlag tooltipFlag
    ) {

        builder.accept(DESC_1);
        builder.accept(DESC_2);
        // TODO reset styles get each mode
        GlazerMode.getOrDefault(itemStack).addGlazerTooltip(builder);
        if (FMLEnvironment.getDist().isClient() && Minecraft.getInstance().hasShiftDown()) {
            builder.accept(FOLDED_1);
            builder.accept(FOLDED_2);
            builder.accept(FOLDED_3);
            builder.accept(FOLDED_4);
            builder.accept(FOLDED_5);
            builder.accept(FOLDED_6);
        }
        else {
            builder.accept(DESC_UNFOLD);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onAttack(AttackEntityEvent event) {
        // maybe useless
//        if (event.getTarget() instanceof LivingEntity target) {
//            Player player = event.getEntity();
//
//            ItemStack mainHand = player.getMainHandItem();
//            applyGummies(player, mainHand, target);
////            event.setCanceled(true);
//
//            ItemStack offHand = player.getOffhandItem();
//            applyGummies(player, offHand, target);
//        }
    }

    @SubscribeEvent
    public static void onLivingInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof LivingEntity target) {
            Player player = event.getEntity();

            ItemStack mainHand = player.getMainHandItem();
            applyGummies(player, mainHand, target);

            ItemStack offHand = player.getOffhandItem();
            applyGummies(player, offHand, target);
        }
    }

    // TODO check exact requirements
    @SubscribeEvent
    public static void onLivingHurt(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof LivingEntity source) {
            LivingEntity living = event.getEntity();

            ItemStack mainHand = source.getMainHandItem();
            applyGummies(source, mainHand, living);

            ItemStack offHand = source.getOffhandItem();
            applyGummies(source, offHand, living);
        }
    }

    private static void applyGummies(LivingEntity applier, ItemStack stack, LivingEntity target) {
        if (stack.getItem() instanceof GummyGlazerItem glazer) {
            MutableContents contents = glazer.getMutable(stack);
            if (contents.activeSlots().stream().allMatch(ItemStack::isEmpty)) {
                return;
            }
            contents.apply(new Applier(applier, target, GlazerMode.getOrDefault(stack)));
            GummyContents.set(stack, contents);

            if (applier instanceof ServerPlayer player) {
                player.awardStat(Stats.ITEM_USED.get(glazer));
            }
        }
    }

    private record Applier(LivingEntity applier,
                           LivingEntity target,
                           GlazerMode mode) implements Function<ItemStack, ItemStack>
    {
        @Override
        public ItemStack apply(ItemStack stack) {
            if (stack.isEmpty()) return stack;
            stack = stack.copy();
            if (mode == GlazerMode.SAVE && stack.has(ItemRegistry.SUGAR_CONTENTS_DCTYPE)) {
                SugarContents contents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
                assert contents != null;
                Optional<Formula> optional = Formula.getFormulaOptional(contents.sugar(), contents.flavor());
                if (optional.isPresent() && optional.get()
                                                    .effects()
                                                    .stream()
                                                    .allMatch(effect -> target.hasEffect(effect.mobEffect()))) {
                    return stack;
                }
            }
            SugarContents.applySugarEffects(stack, target);
            stack.consume(1, applier);
            return stack;
        }
    }


}
