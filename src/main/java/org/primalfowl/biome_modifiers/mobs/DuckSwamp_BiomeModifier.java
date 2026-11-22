package org.primalfowl.biome_modifiers.mobs;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_Tags;
import org.primalfowl.util.Primalfowl_Util;

public class DuckSwamp_BiomeModifier implements BiomeModifier {
    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase == Phase.ADD) {
            Primalfowl_Util.Generation.createBiomeModifier(biome, builder, Primalfowl_Tags.Biome.SPAWNS_MALLARD_DUCK,
                    Primalfowl_Main.COMMON_CONFIG.duckSwampExtraBiomes.get().stream().map(Object::toString).toList(),
                    Primalfowl_Main.COMMON_CONFIG.enableDuckSwampSpawn.get(),
                    Primalfowl_Main.COMMON_CONFIG.duckSwampSpawnWeight.get(),
                    Primalfowl_Main.COMMON_CONFIG.duckSwampMinGroup.get(),
                    Primalfowl_Main.COMMON_CONFIG.duckSwampMaxGroup.get(),
                    Primalfowl_Entities.DUCK.get());
        }
    }

    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return Primalfowl_Util.Generation.createBiomeModifierSerializer("duck_swamp_spawn").get();
    }

    public static void register() {
        Primalfowl_Registries.BIOME_MODIFIERS.register(
                "duck_swamp_spawn",
                () -> MapCodec.unit(new DuckSwamp_BiomeModifier()));
    }
}
