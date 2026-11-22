package org.primalfowl.biome_modifiers.mobs;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.NotNull;
import org.primalfowl.util.Primalfowl_Util;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_Tags;

public class GooseSingle_BiomeModifier implements BiomeModifier {
    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase == BiomeModifier.Phase.ADD) {
            Primalfowl_Util.Generation.createBiomeModifier(biome, builder, Primalfowl_Tags.Biome.SPAWNS_GOOSE,
                    Primalfowl_Main.COMMON_CONFIG.gooseSingleExtraBiomes.get().stream().map(Object::toString).toList(),
                    Primalfowl_Main.COMMON_CONFIG.enableGooseSingleSpawn.get(),
                    Primalfowl_Main.COMMON_CONFIG.gooseSingleSpawnWeight.get(),
                    Primalfowl_Main.COMMON_CONFIG.gooseSingleMinGroup.get(),
                    Primalfowl_Main.COMMON_CONFIG.gooseSingleMaxGroup.get(),
                    Primalfowl_Entities.GOOSE.get());
        }
    }

    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return Primalfowl_Util.Generation.createBiomeModifierSerializer("goose_single_spawn").get();
    }

    public static void register() {
        Primalfowl_Registries.BIOME_MODIFIERS.register(
                "goose_single_spawn",
                () -> MapCodec.unit(new GooseSingle_BiomeModifier()));
    }
}
