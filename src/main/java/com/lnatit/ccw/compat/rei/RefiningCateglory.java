package com.lnatit.ccw.compat.rei;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.item.ItemRegistry;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.impl.ClientInternals;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RefiningCateglory implements DisplayCategory<RefiningDisplay>
{
    public static final CategoryIdentifier<RefiningDisplay> REFINING = CategoryIdentifier.of(CandyWorkshop.MODID, "refining");

    // TODO dg
    public static final Component TITLE = Component.translatable("compat.ccw.rei.title");
    public static final Renderer ICON = EntryStacks.of(ItemRegistry.SUGAR_REFINERY);
    // Note: it's a SPRITE!
    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
            CandyWorkshop.MODID,
            "rei/refining"
    );

    @Override
    public CategoryIdentifier<? extends RefiningDisplay> getCategoryIdentifier() {
        return REFINING;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public int getDisplayHeight() {
        return DisplayCategory.super.getDisplayHeight();
    }

    @Override
    public List<Widget> setupDisplay(RefiningDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        Point startingPoint = new Point(bounds.x, bounds.y);
        // Background
        widgets.add(createRecipeBase(bounds));
        // Progress Bar
        // TODO add prog & animation
//        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
//            int width = Mth.ceil(System.currentTimeMillis() / 250d % 18d);
//            graphics.blit(RenderType::guiTextured, texture, startingPoint.x + 44, startingPoint.y + 28, 103, 163, width, 4, 256, 256);
//        }));
        // Inputs
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 13, startingPoint.y + 1))
                       .entries(display.getInputEntries().get(0))
                       .disableBackground()
                       .markInput()
        );
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 38, startingPoint.y + 1))
                       .entries(display.getInputEntries().get(1))
                       .disableBackground()
                       .markInput()
        );
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 97, startingPoint.y + 1))
                       .entries(display.getInputEntries().get(2))
                       .disableBackground()
                       .markInput()
        );
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 121, startingPoint.y + 1))
                       .entries(display.getInputEntries().get(3))
                       .disableBackground()
                       .markInput()
        );
        // Output(s)
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 68, startingPoint.y + 21))
                       .entries(display.getOutputEntries().get(0))
                       .disableBackground()
                       .markOutput()
        );
        return widgets;
    }

    private static Widget createRecipeBase(Rectangle bounds) {
        return ClientInternals.getWidgetsProvider()
                       .createPanelWidget(bounds)
                       .texture(BACKGROUND, BACKGROUND);
    }
}
