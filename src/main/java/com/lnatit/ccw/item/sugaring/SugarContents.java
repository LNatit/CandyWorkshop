package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.misc.data.AttachmentRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;
import java.util.function.Consumer;

public record SugarContents(Optional<Sugar> sugar, Sugar.Flavor flavor)
{
    public static final Codec<SugarContents> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                              Sugar.CODEC.optionalFieldOf("sugar").forGetter(SugarContents::sugar),
                              SingleEffectSugar.Flavor.CODEC.fieldOf("flavor").forGetter(SugarContents::flavor)
                      )
                      .apply(ins, SugarContents::new));
//    public static final StreamCodec<FriendlyByteBuf, SugarContents> STREAM_CODEC = StreamCodec.composite(
//            Sugar.STREAM_CODEC.apply(ByteBufCodecs::optional),
//            SugarContents::sugar,
//            SingleEffectSugar.Flavor.STREAM_CODEC,
//            SugarContents::flavor,
//            SugarContents::new
//    );

    public static final SugarContents VANILLA = new SugarContents(Optional.empty(), SingleEffectSugar.Flavor.ORIGINAL);

    public boolean is(Sugar holder) {
        return this.sugar.isPresent() && holder.equals(this.sugar.get());
    }

    public boolean is(Sugar holder, SingleEffectSugar.Flavor type) {
        return is(holder) && this.flavor.equals(type);
    }

    public Component getName(String descriptionId) {
        // temporary fix
        Component name = Component.translatable(
                descriptionId.concat(".").concat(this.sugar.map(Sugar::name).orElse("vanilla"))
        ).withStyle(ChatFormatting.WHITE);
        MutableComponent flavor = Sugar.Flavor.nameOf(this.flavor);
        return flavor == null ? name : flavor.append(" ").append(name);
    }

    public void addSugarTooltip(Consumer<Component> tooltipAdder) {
        if (this.sugar.isPresent()) {
            this.sugar.get().addSugarTooltip(tooltipAdder, this.flavor);
            Component desc = Sugar.Flavor.descriptionOf(this.flavor);
            if (desc != null) {
                tooltipAdder.accept(desc);
            }
        }
    }

    public void onConsume(LivingEntity entity) {
        if (this.sugar.isPresent()) {
            Sugar sugar = this.sugar.get();
            sugar.applyOn(entity, this.flavor);
            if (entity instanceof ServerPlayer player) {
                player.getData(AttachmentRegistry.SUGAR_STAT).addHistory(sugar, player);
            }
        }
    }
}
