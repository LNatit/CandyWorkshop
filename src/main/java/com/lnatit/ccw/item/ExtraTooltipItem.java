package com.lnatit.ccw.item;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.util.Lazy;

import java.util.ArrayList;
import java.util.List;

public class ExtraTooltipItem extends Item
{
    private final Lazy<List<Component>> extraTooltip;

    public ExtraTooltipItem(Properties properties, int lines) {
        super(properties);
        extraTooltip = Lazy.of(
                () -> {
                    List<Component> tooltip = new ArrayList<>();
                    for (int i = 0; i < lines; i++) {
                        tooltip.add(
                                Component.translatable(
                                        "item.%s.%s.desc%d".formatted(
                                                CandyWorkshop.MODID,
                                                builtInRegistryHolder().getRegisteredName(),
                                                i
                                        )
                                )
                        );
                    }
                    return tooltip;
                }
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.addAll(extraTooltip.get());
    }
}
