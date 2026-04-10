package com.lnatit.ccw.item;

import com.lnatit.ccw.block.entity.DrawerTableBlockEntity;
import com.lnatit.ccw.item.component.GummyContents;
import com.lnatit.ccw.item.component.IContents;
import com.lnatit.ccw.item.component.MutableContents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;

public abstract class GummyDeviceItem extends Item
{
    protected final IContents.Type type;
    protected final Tier tier;

    public GummyDeviceItem(Properties properties, IContents.Type type, Tier tier) {
        super(properties);
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
                IItemHandler contents = table.accessInventory(context.getClickedFace());

                boolean changed = false;
                int i;

                /*
                 * 第一阶段：补充已有内容的槽位
                 * 遍历设备的全部槽位，对每个非空槽位调用 doFill，
                 * 从抽屉中取出同类物品并尽可能填满该槽位。
                 */
                for (i = 0; i < mutable.getSlots(); i++) {
                    ItemStack template = mutable.getStackInSlot(i);
                    if (template.isEmpty()) continue;
                    if (!doFill(contents, template, mutable, i).isEmpty()) changed = true;
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
                    ItemStack template;
                    boolean cycleProgress = false;
                    int k = 0;
                    i = mutable.activeSize();
                    while (i < mutable.getSlots()) {
                        // 跳过已有内容的非活跃槽位，找到下一个空白目标
                        while (i < mutable.getSlots() && !mutable.getStackInSlot(i).isEmpty()) i++;
                        if (i >= mutable.getSlots()) break;

                        template = mutable.getStackInSlot(k);
                        // 模板必须非空且已满：未满说明第一阶段已确认抽屉中无此类型库存
                        if (!template.isEmpty() && template.getCount() >= Math.min(template.getMaxStackSize(),
                                                                                   mutable.getSlotLimit(k))) {
                            ItemStack filled = doFill(contents, template, mutable, i);
                            if (!filled.isEmpty()) {
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
     * @return 实际填入槽位的 {@link ItemStack}（其 count 为本次填充总量），
     *         若未填入任何物品则返回 {@link ItemStack#EMPTY}
     */
    public static ItemStack doFill(IItemHandler from, ItemStack template, IItemHandler to, int slotId) {
        ItemStack dest = to.getStackInSlot(slotId);
        // 目标槽位已有不同类型的物品：模板为空时改用现有物品作为模板，否则类型冲突无法填充
        if (template.isEmpty()) {
            if (dest.isEmpty()) return ItemStack.EMPTY;
            template = dest;
        }
        else if (!dest.isEmpty() && !ItemStack.isSameItemSameComponents(template, dest)) {
            return ItemStack.EMPTY;
        }
        
        // 目标槽位的可用空间（受槽位上限与物品堆叠上限的双重约束）
        int pulled = Math.min(to.getSlotLimit(slotId), template.getMaxStackSize()) - dest.getCount();
        if (pulled <= 0) return ItemStack.EMPTY;
        int totalFilled = pulled;  // 复用：先存快照，循环后原地求差得填充量

        for (int j = 0; j < from.getSlots(); j++) {
            ItemStack current = from.getStackInSlot(j);
            if (current.isEmpty()) continue;

            if (ItemStack.isSameItemSameComponents(current, template)) {
                // 先模拟提取与插入，确定实际可转移数量，再执行真实操作，避免多余的回滚
                ItemStack topped = from.extractItem(j, pulled, true);
                int toFill = topped.getCount() - to.insertItem(slotId, topped, true).getCount();
                if (toFill > 0) {
                    to.insertItem(slotId, from.extractItem(j, toFill, false), false);
                    pulled -= toFill;
                    if (pulled <= 0) break;  // 槽位已满，无需继续遍历
                }
            }
        }

        totalFilled -= pulled;  // 初始可用空间 - 剩余可用空间 = 实际填充量
        return totalFilled > 0 ? template.copyWithCount(totalFilled) : ItemStack.EMPTY;
    }
}
