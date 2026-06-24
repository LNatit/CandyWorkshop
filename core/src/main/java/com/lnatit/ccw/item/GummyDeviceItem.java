package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshopClient;
import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.item.component.GummyContents;
import com.lnatit.ccw.item.component.IContents;
import com.lnatit.ccw.item.component.MutableContents;
import com.lnatit.ccw.misc.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.function.Consumer;

public abstract class GummyDeviceItem extends Item
{
    public static final String DESC_UNFOLD_KEY = "item.ccw.unfold";
    public static final Component DESC_UNFOLD = Component.translatable(DESC_UNFOLD_KEY).withStyle(ChatFormatting.GRAY);
    protected final IContents.Type type;
    protected final Tier tier;

    public GummyDeviceItem(Properties properties, IContents.Type type, Tier tier) {
        super(properties.stacksTo(1));
        this.type = type;
        this.tier = tier;
    }

    // TODO Add notification
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            if (level.getBlockEntity(pos) instanceof DrawerTableBlockEntity table) {
                if (level.isClientSide()) {
                    return InteractionResult.SUCCESS;
                }

                ItemStack stack = context.getItemInHand();
                MutableContents mutable = this.getMutable(stack);
                ResourceHandler<ItemResource> contents = table.accessInventory(context.getClickedFace());

                boolean changed = false;
                int i;

                /*
                 * 第一阶段：补充已有内容的槽位
                 * 遍历设备的全部槽位，对每个非空槽位调用 doFill，
                 * 从抽屉中取出同类物品并尽可能填满该槽位。
                 */
                for (i = 0; i < mutable.size(); i++) {
                    ItemResource template = mutable.getResource(i);
                    if (template.isEmpty()) continue;
                    if (doFill(contents, template, mutable, i) != 0) changed = true;
                }

                /*
                 * 第二阶段：补充空白非活跃槽位（仅在第一阶段无任何变化时执行）
                 *
                 * 第一次交互时触发空槽的补货，第二次交互时触发非活跃区域按活跃区域顺序填充。
                 * 本阶段以活跃槽位（索引 0..activeSize-1）作为类型模板，按顺序轮询，
                 * 为非活跃区域（索引 activeSize..getSlots-1）的空白槽位从抽屉补货。
                 *
                 * 模板有效条件：非空且已满（count >= 有效上限）。
                 * 若模板未满，说明第一阶段已尝试补充该类型但抽屉中没有库存，故跳过。
                 *
                 * 终止条件：k 转满一整轮仍未有任何槽位被填充（cycleProgress 为 false），
                 * 说明所有有效模板均无法继续补货，退出循环。
                 */
                if (!changed) {
                    ItemResource template;
                    boolean cycleProgress = false;
                    int k = 0;
                    i = mutable.activeSize();
                    while (i < mutable.size()) {
                        // 跳过已有内容的非活跃槽位，找到下一个空白目标
                        while (i < mutable.size() && !mutable.getResource(i).isEmpty()) i++;
                        if (i >= mutable.size()) break;

                        template = mutable.getResource(k);
                        // 模板必须非空且已满：未满说明第一阶段已确认抽屉中无此类型库存
                        if (!template.isEmpty() && mutable.getAmountAsInt(k) >= mutable.getCapacityAsInt(k, template)) {
                            int filled = doFill(contents, template, mutable, i);
                            if (filled != 0) {
                                i++;             // 槽位已获得物品，推进到下一个空白槽位
                                changed = true;
                                cycleProgress = true;
                            }
                        }

                        k = (k + 1) % mutable.activeSize();  // 循环推进模板指针
                        // k 归零表示一轮模板恰好遍历完毕，检查本轮是否有任何进展
                        if (k == 0) {
                            if (!cycleProgress) break;  // 整轮无进展，抽屉已无可用库存
                            cycleProgress = false;
                        }
                    }
                }

                // 仅在内容发生变化时才将 mutable 写回物品的 DataComponent，避免无效更新
                if (changed) {
                    GummyContents.set(stack, mutable);
                }

                // 客户端返回 SUCCESS，服务端必须返回 CONSUME 保持一致
                return InteractionResult.CONSUME;
            }
        }
        return super.useOn(context);
    }

    public MutableContents getMutable(ItemStack stack) {
        if (!stack.has(this.type.dataComponentType)) {
            stack.set(this.type.dataComponentType, this.type.defaultContents());
        }
        GummyContents contents = stack.get(this.type.dataComponentType);
        assert contents != null;
        return new MutableContents(contents, this.tier);
    }

    /**
     * 从 {@code from} 中寻找与 {@code template} 类型匹配的物品，尽可能填充至 {@code to} 的 {@code slotId} 槽位。
     * <p>
     * 若 {@code template} 为空，则改以目标槽位的现有物品作为模板；
     * 若目标槽位非空且与 {@code template} 类型不同，则直接返回 {@link ItemStack#EMPTY}（不同类型无法共存于同一槽位）。
     *
     * @param from     来源库存（如抽屉）
     * @param template 填充类型模板，决定从 {@code from} 中取哪种物品
     * @param to       目标 {@link MutableContents}
     * @param slotId   目标槽位索引
     * @return 实际填入槽位的填充总量
     */
    public static int doFill(ResourceHandler<ItemResource> from, ItemResource template, ResourceHandler<ItemResource> to, int slotId) {
        ItemResource dest = to.getResource(slotId);
        // 目标槽位已有不同类型的物品：模板为空时改用现有物品作为模板，否则类型冲突无法填充
        if (template.isEmpty()) {
            if (dest.isEmpty()) return 0;
            template = dest;
        }
        else if (!dest.isEmpty() && !template.equals(dest)) {
            return 0;
        }
        
        // 目标槽位的可用空间（受槽位上限与物品堆叠上限的双重约束）
        int pulled = to.getCapacityAsInt(slotId, template) - to.getAmountAsInt(slotId);
        if (pulled <= 0) return 0;
        int extracted;

        try (Transaction transaction = Transaction.openRoot()) {
            extracted = from.extract(template, pulled, transaction);
            to.insert(slotId, template, extracted, transaction);
        }

        return extracted;
    }

    @Override
    public void appendHoverText(
            ItemStack itemStack,
            TooltipContext context,
            TooltipDisplay display,
            Consumer<Component> builder,
            TooltipFlag tooltipFlag
    ) {
        this.appendCommonTooltips(itemStack, builder);
        if (FMLEnvironment.getDist().isClient()) {
            if (Minecraft.getInstance().hasShiftDown()) {
                if (CandyWorkshopClient.folded()) {
                    CandyWorkshopClient.playSound(SoundRegistry.UNFOLD_DESC);
                    CandyWorkshopClient.setFolded(false);
                }
                appendFoldedTooltips(builder);
            } else {
                if (!CandyWorkshopClient.folded()) {
                    CandyWorkshopClient.playSound(SoundRegistry.FOLD_DESC);
                    CandyWorkshopClient.setFolded(true);
                }
                appendUnfoldNotification(builder);
            }
        }
    }

    private void appendUnfoldNotification(Consumer<Component> builder) {
        builder.accept(DESC_UNFOLD);
    }

    protected abstract void appendFoldedTooltips(Consumer<Component> builder);

    protected abstract void appendCommonTooltips(ItemStack stack, Consumer<Component> builder);
}
