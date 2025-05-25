package com.lnatit.ccw.item.sugaring;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.misc.RegRegistry;
import com.lnatit.ccw.misc.data.SugarStatUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class SugarUtils
{
    public static final String TAG_ROOT = CandyWorkshop.MODID + ".sugar";
    public static final String TAG_SUGAR = "Sugar";
    public static final String TAG_FLAVOR = "Flavor";

    public static CompoundTag getSugarContents(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(TAG_ROOT, Tag.TAG_COMPOUND)) {
            return tag.getCompound(TAG_SUGAR);
        }
        return new CompoundTag();
    }

    private static Optional<Sugar> getSugar(ResourceLocation id) {
        return Optional.ofNullable(RegRegistry.SUGAR.getValue(id));
    }

    private static Optional<Sugar> getSugar(String id) {
        return getSugar(ResourceLocation.parse(id));
    }

    private static Optional<Sugar> getSugar(CompoundTag sugarContents) {
        if (sugarContents.contains(TAG_SUGAR, Tag.TAG_STRING)) {
            return getSugar(sugarContents.getString(TAG_SUGAR));
        }
        return Optional.empty();
    }

    public static Optional<Sugar> getSugar(ItemStack stack) {
        return getSugar(getSugarContents(stack));
    }

    private static Sugar.Flavor getFlavor(String flavor) {
        return Sugar.Flavor.valueOf(flavor);
    }

    private static Sugar.Flavor getFlavor(CompoundTag sugarContents) {
        if (sugarContents.contains(TAG_SUGAR, Tag.TAG_STRING)) {
            return getFlavor(sugarContents.getString(TAG_FLAVOR));
        }
        return Sugar.Flavor.ORIGINAL;
    }

    public static Sugar.Flavor getFlavor(ItemStack stack) {
        return getFlavor(getSugarContents(stack));
    }

    public static Component getName(String descriptionId, ItemStack stack) {
        CompoundTag sugaContents = getSugarContents(stack);
        // temporary fix
        Component name = Component.translatable(
                descriptionId.concat(".").concat(getSugar(sugaContents).map(Sugar::name).orElse("vanilla"))
        ).withStyle(ChatFormatting.WHITE);
        MutableComponent flavor = Sugar.Flavor.nameOf(getFlavor(sugaContents));
        return flavor == null ? name : flavor.append(" ").append(name);
    }

    public static void consume(LivingEntity entity, ItemStack stack) {
        CompoundTag sugarContents = getSugarContents(stack);
        var oSugar = getSugar(sugarContents);
        var flavor = getFlavor(sugarContents);
        if (oSugar.isPresent()) {
            Sugar sugar = oSugar.get();
            sugar.applyOn(entity, flavor);
            if (entity instanceof ServerPlayer player) {
                SugarStatUtils.record(player, sugar);
            }
        }
    }

    public static void addSugarTooltip(ItemStack stack, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag sugarContents = getSugarContents(stack);
        var sugar = getSugar(sugarContents);
        var flavor = getFlavor(sugarContents);
        if (sugar.isPresent()) {
            sugar.get().addSugarTooltip(tooltip::add, flavor);
            Component desc = Sugar.Flavor.descriptionOf(flavor);
            if (desc != null) {
                tooltip.add(desc);
            }
        }
    }

    public static Tag of(@Nullable Sugar sugar, Sugar.Flavor flavor) {
        CompoundTag tag = new CompoundTag();
        ResourceLocation sugarId = RegRegistry.SUGAR.getKey(sugar);
        if (sugar != null && sugarId != null) {
            tag.putString(TAG_SUGAR, sugarId.toString());
        }
        tag.putString(TAG_FLAVOR, flavor.name());
        return tag;
    }

    public static ItemStack createSugar(@Nullable Sugar sugar, Sugar.Flavor flavor) {
        if (sugar == null) {
            return ItemStack.EMPTY;
        }
        ItemStack itemStack = new ItemStack(ItemRegistry.GUMMY_ITEM.get());
        itemStack.getOrCreateTag().put(TAG_ROOT, of(sugar, flavor));
        return itemStack;
    }
}
