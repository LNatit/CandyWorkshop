package com.lnatit.ccw.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;

public class ExtraTooltipItem extends Item
{
    private final List<Component> extraTooltip = new ArrayList<>();

    public ExtraTooltipItem(Properties properties, int lines) {
        super(properties);
        lines = 0;
        for (int i = 0; i < lines; i++) {
            extraTooltip.add(
                    Component.translatable(
                            "%s.desc%d".formatted(
                                    this.getDescriptionId(),
                                    i
                            )
                    )
            );
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.addAll(extraTooltip);
    }
}
