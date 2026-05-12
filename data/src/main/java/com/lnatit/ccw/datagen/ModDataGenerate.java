package com.lnatit.ccw.datagen;

import com.lnatit.ccw.CandyWorkshop;
import com.lnatit.ccw.data.Effect;
import com.lnatit.ccw.item.sugaring.Sugars;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CandyWorkshop.MODID)
public class ModDataGenerate
{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        CoreDataProviders.get()
                         // Overworld blends
                         .register(Sugars.SPEED, Effect.simple(MobEffects.MOVEMENT_SPEED))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.BUNNY, Effect.simple(MobEffects.JUMP))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.HEALING, Effect.instant(MobEffects.HEAL))
                         .defaultExcited()

                         .register(Sugars.POISON, Effect.simple(MobEffects.POISON))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.PUFFERFISH, Effect.simple(MobEffects.WATER_BREATHING))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.NIGHT_VISION, Effect.simple(MobEffects.NIGHT_VISION))
                         .defaultBold()

                         .register(Sugars.STRENGTH, Effect.simple(MobEffects.DAMAGE_BOOST))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.RECOVERY, Effect.simple(MobEffects.REGENERATION))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.TURTLE,
                                   new Effect(MobEffects.MOVEMENT_SLOWDOWN, 100, 3),
                                   new Effect(MobEffects.DAMAGE_RESISTANCE, 100, 2))
                         .excited(new Effect(MobEffects.MOVEMENT_SLOWDOWN, 100, 5),
                                  new Effect(MobEffects.DAMAGE_RESISTANCE, 100, 3))
                         .bold(new Effect(MobEffects.MOVEMENT_SLOWDOWN, 200, 3),
                               new Effect(MobEffects.DAMAGE_RESISTANCE, 200, 2))

                         .register(Sugars.FLUTTER, Effect.simple(MobEffects.SLOW_FALLING))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.SNAIL, Effect.simple(MobEffects.MOVEMENT_SLOWDOWN))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.STINKY, Effect.simple(MobEffects.CONFUSION))
                         .defaultBold()

                         .register(Sugars.BLINDING, Effect.simple(MobEffects.BLINDNESS))
                         .defaultBold()

                         .register(Sugars.WEAKNESS, Effect.simple(MobEffects.WEAKNESS))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.BRIGHTNESS, Effect.simple(MobEffects.GLOWING))
                         .defaultBold()

                         .register(Sugars.DARKNESS, Effect.simple(MobEffects.DARKNESS))
                         .defaultBold()

                         .register(Sugars.HUNGER, Effect.simple(MobEffects.HUNGER))
                         .defaultExcited()
                         .defaultBold()

                         // Nether blends
                         .register(Sugars.INVISIBILITY, Effect.simple(MobEffects.INVISIBILITY))
                         .defaultBold()

                         .register(Sugars.STINGER, Effect.instant(MobEffects.HARM))
                         .defaultExcited()

                         .register(Sugars.BUG, Effect.simple(MobEffects.INFESTED))
                         .defaultBold()

                         .register(Sugars.STICKY, Effect.simple(MobEffects.OOZING))
                         .defaultBold()

                         .register(Sugars.BINDING, Effect.simple(MobEffects.WEAVING))
                         .defaultBold()

                         .register(Sugars.GALE, Effect.simple(MobEffects.WIND_CHARGED))
                         .defaultBold()

                         .register(Sugars.REFRESHING, Effect.simple(MobEffects.DIG_SPEED))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.LAZY, Effect.simple(MobEffects.DIG_SLOWDOWN))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.SOLID, Effect.simple(MobEffects.DAMAGE_RESISTANCE))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.FIREPROOF, Effect.simple(MobEffects.FIRE_RESISTANCE))
                         .defaultBold()

                         .register(Sugars.WITHERING, Effect.simple(MobEffects.WITHER))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.RED_HEART, Effect.simple(MobEffects.HEALTH_BOOST))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.FLOATING, Effect.simple(MobEffects.LEVITATION))
                         .defaultBold()

                         // End blends
                         .register(Sugars.GOLDEN_HEART, Effect.simple(MobEffects.ABSORPTION))
                         .defaultExcited()
                         .defaultBold()

                         .register(Sugars.SATIATING, new Effect(MobEffects.SATURATION, 20, 0))
                         .bold(new Effect(MobEffects.SATURATION, 40, 0))

                         .register(Sugars.LUCKY, Effect.simple(MobEffects.LUCK))
                         .defaultBold()

                         .register(Sugars.UNLUCKY, Effect.simple(MobEffects.UNLUCK))
                         .defaultBold()

                         .register(Sugars.TIDAL, Effect.simple(MobEffects.CONDUIT_POWER))
                         .defaultBold()

                         .register(Sugars.FISH_SWIM, Effect.simple(MobEffects.DOLPHINS_GRACE))
                         .defaultBold()

                         .register(Sugars.TAUNTING, Effect.simple(MobEffects.BAD_OMEN))
                         .defaultBold()

                         .register(Sugars.DISCOUNT, Effect.simple(MobEffects.HERO_OF_THE_VILLAGE))
                         .defaultExcited()
                         .defaultBold()
        ;

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();


        generator.addProvider(
                event.includeClient(),
                new ModModelProvider.Block(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new ModModelProvider.Item(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new ModEN_USProvider(output)
        );
        generator.addProvider(
                event.includeClient(),
                new ModSoundProvider(output, existingFileHelper)
        );

        generator.addProvider(
                event.includeServer(),
                new ModAdvcmtProvider(output, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModRecipeProvider(output, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModLootProvider(output, lookupProvider)
        );
        var blockTags = new ModTagProvider.BlockTags(output, lookupProvider, existingFileHelper);
        generator.addProvider(
                event.includeServer(),
                blockTags

        );
        generator.addProvider(
                event.includeServer(),
                new ModTagProvider.ItemTags(output, lookupProvider, blockTags.contentsGetter(), existingFileHelper)
        );
    }
}
