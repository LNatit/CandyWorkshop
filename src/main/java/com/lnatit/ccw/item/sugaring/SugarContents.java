package com.lnatit.ccw.item.sugaring;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Consumer;

public record SugarContents(Optional<Holder<Sugar>> sugar,
                            SingleEffectSugar.Flavor flavor) implements ConsumableListener
{
    public static final Codec<SugarContents> CODEC = RecordCodecBuilder.create(
            ins -> ins.group(
                              Sugar.CODEC.optionalFieldOf("sugar").forGetter(SugarContents::sugar),
                              SingleEffectSugar.Flavor.CODEC.fieldOf("flavor").forGetter(SugarContents::flavor)
                      )
                      .apply(ins, SugarContents::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SugarContents> STREAM_CODEC = StreamCodec.composite(
            Sugar.STREAM_CODEC.apply(ByteBufCodecs::optional),
            SugarContents::sugar,
            SingleEffectSugar.Flavor.STREAM_CODEC,
            SugarContents::flavor,
            SugarContents::new
    );

    public static final SugarContents VANILLA = new SugarContents(Optional.empty(), SingleEffectSugar.Flavor.ORIGINAL);

    public boolean is(Holder<Sugar> holder) {
        return this.sugar.isPresent() && holder.equals(this.sugar.get());
    }

    public boolean is(Holder<Sugar> holder, SingleEffectSugar.Flavor type) {
        return is(holder) && this.flavor.equals(type);
    }

    public Component getName(String descriptionId) {
        // temporary fix
        Component name = Component.translatable(
                descriptionId.concat(".").concat(this.sugar.map(s -> s.value().name()).orElse("vanilla"))
        ).withStyle(ChatFormatting.WHITE);
        MutableComponent flavor = Sugar.Flavor.nameOf(this.flavor);
        return flavor == null ? name : flavor.append(" ").append(name);
    }

    public void addSugarTooltip(Consumer<Component> tooltipAdder, float ticksPerSecond) {
        if (this.sugar.isPresent()) {
            this.sugar.get().value().addSugarTooltip(tooltipAdder, this.flavor, ticksPerSecond);
            Component desc = Sugar.Flavor.descriptionOf(this.flavor);
            if (desc != null) {
                tooltipAdder.accept(desc);
            }
        }
    }

    @Override
    public void onConsume(Level level, LivingEntity entity, ItemStack stack, Consumable consumable) {
        this.sugar.ifPresent(holder -> holder.value().applyOn(entity, this.flavor));
    }
}
