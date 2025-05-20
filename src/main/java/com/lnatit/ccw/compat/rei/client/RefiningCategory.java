package com.lnatit.ccw.compat.rei.client;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.compat.rei.RefiningDisplay;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.menu.client.SugarRefineryScreen;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.impl.client.gui.widget.basewidgets.PanelWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RefiningCategory implements DisplayCategory<RefiningDisplay>
{

    public static final Component TITLE = Component.translatable("compat.ccw.rei.title");
    public static final Renderer ICON = EntryStacks.of(ItemRegistry.SUGAR_REFINERY);
    // Note: it's a SPRITE!
    public static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(
            CandyWorkshop.MODID,
            "rei/refining"
    );

    @Override
    public CategoryIdentifier<? extends RefiningDisplay> getCategoryIdentifier() {
        return CandyWorkshopPlugin.REFINING;
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
        widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
            graphics.blitSprite(
                    SugarRefineryScreen.ANIMATION_SPRITE,
                    startingPoint.x + 61,
                    startingPoint.y + 5,
                    28, 19
            );
//            int width = Mth.ceil(System.currentTimeMillis() / 250d % 18d);
//            graphics.blit(
//                    RenderType::guiTextured,
//                    SugarRefineryScreen.PROGRESS_SPRITE,
//                    startingPoint.x + 62,
//                    startingPoint.y + 24,
//                    103,
//                    163, width, 4, 256, 256);
        }));
        // Inputs
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 13, startingPoint.y + 7))
                       .entries(display.getMilk())
                       .disableBackground()
                       .markInput()
        );
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 38, startingPoint.y + 7))
                       .entries(display.getSugar())
                       .disableBackground()
                       .markInput()
        );
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 96, startingPoint.y + 7))
                       .entries(display.getMain())
                       .disableBackground()
                       .markInput()
        );
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 120, startingPoint.y + 7))
                       .entries(display.getExtra())
                       .disableBackground()
                       .markInput()
        );
        // Output(s)
        widgets.add(
                Widgets.createSlot(new Point(startingPoint.x + 67, startingPoint.y + 39))
                       .entries(display.getOutput())
                       .disableBackground()
                       .markOutput()
        );
        return widgets;
    }

    private static Widget createRecipeBase(Rectangle bounds) {
        PanelWidget base = new PanelWidget(bounds);
        base.setTexture(BACKGROUND, BACKGROUND);
        return base;
    }
}
