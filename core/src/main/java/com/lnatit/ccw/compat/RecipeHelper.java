package com.lnatit.ccw.compat;


import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility for recipe lookups against the new 26.1 RecipeMap API.
 * <p>
 * Uses {@link net.minecraft.world.item.crafting.RecipeManager#recipeMap()} → {@link net.minecraft.world.item.crafting.RecipeMap#byType(RecipeType)}
 * which is the approach used by Mekanism in {@code MekanismRecipeType.getRecipesUncached()}.
 * <p>
 * In 1.21, recipes are server-side only. For client access, this helper maintains a
 * client-side cache populated via NeoForge's {@code OnDatapackSyncEvent} →
 * {@code RecipesReceivedEvent} pipeline.
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = CandyWorkshop.MODID)
public interface RecipeHelper
{
    /** Client-side recipe cache populated by RecipesReceivedEvent. */
    Map<RecipeType<?>, List<RecipeHolder<?>>> CLIENT_RECIPES = new ConcurrentHashMap<>();

    /**
     * Returns all registered recipes of the given type, or an empty collection if on client
     * and the cache hasn't been populated yet.
     *
     * @param level the current world
     * @param type  the recipe type to query
     * @param <I>   the RecipeInput subtype
     * @param <T>   the Recipe subtype
     * @return typed collection of recipe holders; never null
     */
    @SuppressWarnings("unchecked")
    static <I extends RecipeInput, T extends Recipe<I>> Collection<RecipeHolder<T>> getRecipesByType(
            Level level, RecipeType<T> type)
    {
        if (level instanceof ServerLevel serverLevel)
        {
            return serverLevel.recipeAccess().recipeMap().byType(type);
        }
        // Client side: check local cache (populated by RecipesReceivedEvent)
        List<RecipeHolder<?>> cached = CLIENT_RECIPES.get(type);
        if (cached != null)
        {
            return (Collection<RecipeHolder<T>>) (Collection<?>) cached;
        }
        return Collections.emptyList();
    }

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "rawtypes"})
    static void onRecipesReceived(RecipesReceivedEvent event)
    {
        var map = event.getRecipeMap();
        for (var type : event.getRecipeTypes())
        {
            Collection<RecipeHolder<?>> recipes = map.byType((net.minecraft.world.item.crafting.RecipeType) type);
            CLIENT_RECIPES.put(type, List.copyOf(new ArrayList<>(recipes)));
        }
    }
}
