package com.lnatit.ccw.misc.data;

import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.misc.critereon.CriteriaHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class SugarStat {
    public static final Codec<SugarStat> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.list(Sugar.CODEC).fieldOf("consume_history").forGetter(s -> s.consumeHistory)
    ).apply(ins, SugarStat::new));

    private final List<Sugar> consumeHistory;

    public SugarStat(List<Sugar> consumeHistory) {
        this.consumeHistory = new ArrayList<>(consumeHistory);
    }

    public SugarStat() {
        this.consumeHistory = new ArrayList<>();
    }

    public static AttachmentType<SugarStat> type() {
        return AttachmentType.builder(() -> new SugarStat()).serialize(CODEC).build();
    }

    public void addHistory(Sugar holder, ServerPlayer player) {
        if (!this.consumeHistory.contains(holder)) {
            this.consumeHistory.add(holder);
        }

        if (this.consumeHistory.size() == Sugars.SUGARS.getEntries().size()) {
            CriteriaHandler.COLLECT_ALL_SUGAR.trigger(player);
        }
    }
}
