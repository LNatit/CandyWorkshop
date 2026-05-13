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
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public record SugarContents(Holder<Sugar> sugar, Holder<Flavor> flavor) implements ConsumableListener, TooltipProvider
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
                                                                                                     .identifier()
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
        itemStack.set(DataComponents.ITEM_MODEL, Sugar.getModelId(sugar));
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
        Component name = Component.translatable(descriptionId + "." + this.sugar.getKey().identifier().getPath())
                                  .withStyle(ChatFormatting.WHITE);
        return this.flavor.is(Flavors.ORIGINAL) ? name : Flavor.prefix(this.flavor).append(" ").append(name);
    }

    @Override
    public void addToTooltip(
            Item.TooltipContext context,
            Consumer<Component> consumer,
            TooltipFlag flag,
            DataComponentGetter components
    ) {
        float ticksPerSecond = context.tickRate();
        Formula.getFormulaOptional(this.sugar, this.flavor)
               .map(Formula::effects)
               .orElse(List.of())
               .forEach(effect -> consumer.accept(effect.getDescription(ticksPerSecond)));

        if (!flavor.is(Flavors.ORIGINAL)) {
            consumer.accept(Flavor.description(this.flavor));
        }
    }

    @Override
    public void onConsume(Level level, LivingEntity user, ItemStack stack, Consumable consumable) {
        this.onConsume(user);
    }

    public void onConsume(LivingEntity entity) {
        if (!entity.level().isClientSide()) {
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
