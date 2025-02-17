package com.lnatit.ccw.item.sugaring;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.Optional;
import java.util.function.Consumer;

public record SugarContents(Optional<Holder<Sugar>> sugar, Sugar.Type type) {
    public static final SugarContents VANILLA = new SugarContents(Optional.empty(), Sugar.Type.BASE);

    public static final Codec<SugarContents> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                            Sugar.CODEC.optionalFieldOf("sugar").forGetter(SugarContents::sugar),
                            Sugar.Type.CODEC.fieldOf("type").forGetter(SugarContents::type)
                    )
                    .apply(ins, SugarContents::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SugarContents> STREAM_CODEC = StreamCodec.composite(
            Sugar.STREAM_CODEC.apply(ByteBufCodecs::optional),
            SugarContents::sugar,
            Sugar.Type.STREAM_CODEC,
            SugarContents::type,
            SugarContents::new
    );

    public void applyOn(ServerLevel level, LivingEntity entity) {
        this.sugar.ifPresent(holder -> holder.value().applySugarOn(level, entity, this.type));
    }

    // *.speed.base
    public Component getName(String descriptionId) {
        return Component.translatable(descriptionId + "." +
                this.sugar.map(s -> s.value().name()).orElse("vanilla") + "." +
                this.type.getSerializedName()
        );
    }

    public void addSugarTooltip(Consumer<Component> tooltipAdder, float durationFactor, float ticksPerSecond) {
        if (this.sugar.isPresent()) {
            //TODO
        }
    }
}
