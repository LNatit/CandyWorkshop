package com.lnatit.ccw.item.component;

import com.lnatit.ccw.data.Effect;
import com.lnatit.ccw.data.Formula;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.sugaring.Flavor;
import com.lnatit.ccw.item.sugaring.Flavors;
import com.lnatit.ccw.item.sugaring.Sugar;
import com.lnatit.ccw.item.sugaring.Sugars;
import com.lnatit.ccw.misc.attachment.AttachmentRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record SugarContents(Holder<Sugar> sugar, Holder<Flavor> flavor)
{
    public static final Codec<SugarContents> CODEC =
            Codec.withAlternative(RecordCodecBuilder.create(ins -> ins.group(Sugar.CODEC.fieldOf("sugar")
                                                                                        .forGetter(SugarContents::sugar),
                                                                             Flavor.CODEC.fieldOf("flavor")
                                                                                         .forGetter(SugarContents::flavor))
                                                                      .apply(ins, SugarContents::new)),
                                  RecordCodecBuilder.create(ins -> ins.group(Sugar.CODEC.optionalFieldOf("sugar")
                                                                                        .xmap(o -> o.orElse(Sugars.SPEED),
                                                                                              Optional::of)
                                                                                        .forGetter(SugarContents::sugar),
                                                                             Codec.STRING.fieldOf("flavor")
                                                                                         .xmap(Flavors::byName,
                                                                                               f -> f.getKey()
                                                                                                     .location()
                                                                                                     .getPath())
                                                                                         .forGetter(SugarContents::flavor))
                                                                      .apply(ins, SugarContents::new))

            );
    public static final StreamCodec<RegistryFriendlyByteBuf, SugarContents> STREAM_CODEC =
            StreamCodec.composite(Sugar.STREAM_CODEC,
                                  SugarContents::sugar,
                                  Flavor.STREAM_CODEC,
                                  SugarContents::flavor,
                                  SugarContents::new);

    public static ItemStack createSugar(Holder<Sugar> sugar, Holder<Flavor> flavor) {
        ItemStack itemStack = ItemRegistry.GUMMY.toStack();
        flavor.value().onApply(itemStack);
        itemStack.set(ItemRegistry.SUGAR_CONTENTS_DCTYPE, new SugarContents(sugar, flavor));
        return itemStack;
    }

    public static ItemStack createOriginalSugar(Holder<Sugar> sugar) {
        return createSugar(sugar, Flavors.ORIGINAL);
    }

    public static void applySugarEffects(ItemStack stack, LivingEntity livingEntity) {
        SugarContents sugarContents = stack.get(ItemRegistry.SUGAR_CONTENTS_DCTYPE);
        if (sugarContents != null) {
            sugarContents.onConsume(livingEntity);
        }
    }

    public boolean is(Holder<Sugar> sugar) {
        return sugar.equals(this.sugar);
    }

    public boolean is(Holder<Sugar> sugar, Holder<Flavor> flavor) {
        return is(sugar) && flavor.equals(this.flavor);
    }

    public Component getName(String descriptionId) {
        // temporary fix
        Component name = Component.translatable(descriptionId + "." + this.sugar.getKey().location().getPath())
                                  .withStyle(ChatFormatting.WHITE);
        return this.flavor.is(Flavors.ORIGINAL) ? name : Flavor.prefix(this.flavor).append(" ").append(name);
    }

    public void addSugarTooltip(Consumer<Component> tooltipAdder, float ticksPerSecond) {
        Formula.getFormulaOptional(this.sugar, this.flavor)
               .map(Formula::effects)
               .orElse(List.of())
               .forEach(effect -> tooltipAdder.accept(effect.getDescription(ticksPerSecond)));

        if (!flavor.is(Flavors.ORIGINAL)) {
            tooltipAdder.accept(Flavor.description(this.flavor));
        }
    }

    public void onConsume(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            Optional<Formula> optional = Formula.getFormulaOptional(this.sugar, this.flavor);
            optional.ifPresent(formula -> applyOn(formula, entity));

            if (entity instanceof ServerPlayer player) {
                player.getData(AttachmentRegistry.SUGAR_STAT).addHistory(this.sugar, player);
            }
        }
    }

    public SugarContents cycle() {
        Holder<Flavor> next = this.flavor;
        do {
            next = Flavor.next(next);
        } while (Formula.getFormulaOptional(this.sugar, next).isEmpty());
        return new SugarContents(this.sugar, next);
    }

    private static void applyOn(Formula formula, LivingEntity entity) {
        List<Flavor> flavors = List.of(formula.flavor().value());
        List<Effect> effects = formula.effects();

        flavors.forEach(m -> m.preConsume(entity, effects));
        effects.forEach(e -> e.extendEffect(entity));
        flavors.forEach(m -> m.postConsume(entity, effects));
    }
}
