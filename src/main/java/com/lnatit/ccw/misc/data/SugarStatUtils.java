package com.lnatit.ccw.misc.data;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.misc.critereon.CriteriaHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

// TODO copy on clone needed?
public class SugarStatUtils
{
    public static final String TAG_ROOT = CandyWorkshop.MODID + ".sugar_stat";
    public static final String TAG_COUNT = "ConsumeCount";
    public static final String TAG_HISTORY = "ConsumeHistory";

    private static CompoundTag getSugarStats(ServerPlayer player) {
        CompoundTag tag = player.getPersistentData();
        if (tag.contains(TAG_ROOT, Tag.TAG_COMPOUND)) {
            return tag.getCompound(TAG_ROOT);
        }
        return new CompoundTag();
    }

    private static int getConsumeCount(CompoundTag sugarStats) {
        if (sugarStats.contains(TAG_COUNT, Tag.TAG_INT)) {
            return sugarStats.getInt(TAG_COUNT);
        }
        return 0;
    }

    private static int growConsumeCount(CompoundTag sugarStats) {
        int count = getConsumeCount(sugarStats) + 1;
        sugarStats.putInt(TAG_COUNT, count);
        return count;
    }

    public static int getConsumeCount(ServerPlayer player) {
        return getConsumeCount(getSugarStats(player));
    }

    private static ListTag getConsumeHistory(CompoundTag sugarStats) {
        if (!sugarStats.contains(TAG_HISTORY, Tag.TAG_LIST)) {
            sugarStats.put(TAG_HISTORY, new ListTag());
        }
        return sugarStats.getList(TAG_HISTORY, Tag.TAG_LIST);
    }

    private static int getConsumeHistories(CompoundTag sugarStats) {
        return sugarStats.contains(TAG_HISTORY, Tag.TAG_LIST) ?
                sugarStats.getList(TAG_HISTORY, Tag.TAG_STRING).size() : 0;
    }

    public static int getConsumeHistories(ServerPlayer player) {
        return getConsumeHistories(getSugarStats(player));
    }

    public static void record(ServerPlayer player, Sugar sugar) {
        CompoundTag sugarStats = getSugarStats(player);
        ResourceLocation sugarId = Sugars.SUGAR_SUPPLIER.get().getKey(sugar);
        if (sugarId != null) {
            ListTag history = getConsumeHistory(sugarStats);
            history.add(StringTag.valueOf(sugarId.toString()));
            if (history.size() == Sugars.SUGAR_SUPPLIER.get().getEntries().size()) {
                CriteriaHandler.COLLECT_ALL_SUGAR.trigger(player);
            }
        }
        CriteriaHandler.DEVELOP_DIABETES.trigger(player, growConsumeCount(sugarStats));
    }
}
