package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.RegistryRegistry;
import com.lnatit.ccw.item.ItemRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;

import java.util.List;

/**
 * Combine Sugar Registry and Item data component together
 * if split needed (e.g. a SugarContents similar to PotionContents)
 * createSugarItem need to be port to the new record class
 */
public record Sugar(String name, Holder<Potion> potion, int duration)
{
    public static final Codec<Holder<Sugar>> CODEC = RegistryRegistry.SUGAR.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Sugar>> STREAM_CODEC =
            ByteBufCodecs.holderRegistry(RegistryRegistry.SUGAR_KEY);

//    public static final Sugar VANILLA = new Sugar("vanilla", null, 0);

    public static ItemStack createSugarItem(Holder<Sugar> sugar) {
        ItemStack itemStack = ItemRegistry.GUMMY_ITEM.toStack(1);
        itemStack.set(ItemRegistry.SUGAR_DCTYPE, sugar);
//        if (sugar != Su)
            itemStack.set(DataComponents.ITEM_MODEL, sugar.value().toModelId());
        return itemStack;
    }

    public void applySugarOn(ServerLevel level, LivingEntity entity) {
        if (this.potion != null) {
            List<MobEffectInstance> effects = this.potion.value().getEffects();
            for (MobEffectInstance effect : effects) {
                Holder<MobEffect> apply = effect.getEffect();
                // Instantenous effect behaves differently
                if (apply.value().isInstantenous()) {
                    apply.value().applyInstantenousEffect(level, entity, entity, entity, effect.getAmplifier(), 0.5);
                }
                else {
                    MobEffectInstance exist = entity.getEffect(apply);
                    int duration = this.duration;
//                    while (exist != null && exist.getAmplifier() != effect.getAmplifier()) {
//                        exist = exist.getEffect()
//                    }
                    if (exist != null && !exist.isAmbient() && exist.getAmplifier() <= effect.getAmplifier()) {
                        duration += exist.getDuration();
                    }
                    entity.addEffect(new MobEffectInstance(apply, duration, effect.getAmplifier()));
                }
            }
        }
    }

    public ResourceLocation toModelId() {
        return ResourceLocation.fromNamespaceAndPath(CandyWorkshop.MODID, this.name)
                               .withSuffix("_gummy");
    }
}
