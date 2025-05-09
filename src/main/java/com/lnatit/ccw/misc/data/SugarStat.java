package com.lnatit.ccw.misc.data;

import com.lnatit.ccw.item.sugaring.Sugar;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.ArrayList;
import java.util.Collection;

public class SugarStat
{
    public static final Codec<SugarStat> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.INT.fieldOf("consume_count").forGetter(o -> o.consume_count),
//            Codec.fieldOf("consume_history").forGetter(o -> o.consume_history)
    ).apply(ins, SugarStat::new));

    private int consume_count = 0;
    private Collection<Holder<Sugar>> consume_history = new ArrayList<>();

    public static AttachmentType<SugarStat> type() {
        return AttachmentType.builder(SugarStat::new).serialize(CODEC).build();
    }

    public void increaseCount() {
        this.consume_count++;
    }

    public void addHistory(Holder<Sugar> holder) {
        this.consume_history.add(holder);
    }
}
