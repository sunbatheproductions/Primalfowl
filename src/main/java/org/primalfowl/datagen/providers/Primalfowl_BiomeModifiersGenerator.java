package org.primalfowl.datagen.providers;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.biome_modifiers.mobs.DuckNormal_BiomeModifier;
import org.primalfowl.biome_modifiers.mobs.DuckSwamp_BiomeModifier;
import org.primalfowl.biome_modifiers.mobs.GooseGroup_BiomeModifier;
import org.primalfowl.biome_modifiers.mobs.GooseSingle_BiomeModifier;

import java.util.List;
import java.util.function.Supplier;

public class Primalfowl_BiomeModifiersGenerator {

    public static void bootstrap(final BootstrapContext<BiomeModifier> bootstrap) {

        //Animals
        register(bootstrap, "spawn/duck", DuckNormal_BiomeModifier::new);
        register(bootstrap, "spawn/duck_swamp", DuckSwamp_BiomeModifier::new);

        register(bootstrap, "spawn/goose_single", GooseSingle_BiomeModifier::new);
        register(bootstrap, "spawn/goose_group", GooseGroup_BiomeModifier::new);

    }

    public static void register(BootstrapContext<BiomeModifier> bootstrap, String name, Supplier<? extends BiomeModifier> sup){
        bootstrap.register(modifierFor(name), sup.get());
    }

    public static void registerMobSpawn(final BootstrapContext<BiomeModifier> bootstrap, final String biomeModifier, final TagKey<Biome> spawnTag, final EntityType<?> mob, final int weight, final int minGroupSize, final int maxGroupSize){
        registerMobSpawn(bootstrap, modifierFor(biomeModifier), spawnTag, mob, weight, minGroupSize, maxGroupSize);
    }

    public static void registerMobSpawn(final BootstrapContext<BiomeModifier> bootstrap, final ResourceKey<BiomeModifier> biomeModifierResourceKey, final TagKey<Biome> spawnTag, final EntityType<?> mob, final int weight, final int minGroupSize, final int maxGroupSize){
        final HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);

        // Register the biome modifiers.
        bootstrap.register(biomeModifierResourceKey,
                new BiomeModifiers.AddSpawnsBiomeModifier(
                        // The biome(s) to spawn the mobs within
                        biomes.getOrThrow(spawnTag),
                        // The spawners of the entities to add
                        List.of(
                                new MobSpawnSettings.SpawnerData(mob, weight, minGroupSize, maxGroupSize)
                        )
                )
        );
    }

    public static ResourceKey<BiomeModifier> modifierFor(final String id){
        return ResourceKey.create(
                NeoForgeRegistries.Keys.BIOME_MODIFIERS, // The registry this key is for
                ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, id) // The registry variant
        );
    }
}
