package com.lnatit.ccw.item.crafting;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, CandyWorkshop.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RepairSuckerRecipe>> REPAIR_SUCKER =
            RECIPE_SERIALIZERS.register("repair_sucker", () -> new CustomRecipe.Serializer<>(RepairSuckerRecipe::new));
}
