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

public class GooseGroup_BiomeModifier implements BiomeModifier {
    @Override
    public void modify(@NotNull Holder<Biome> biome, @NotNull BiomeModifier.Phase phase, ModifiableBiomeInfo.BiomeInfo.@NotNull Builder builder) {
        if (phase == Phase.ADD) {
            Primalfowl_Util.Generation.createBiomeModifier(biome, builder, Primalfowl_Tags.Biome.SPAWNS_GOOSE,
                    Primalfowl_Main.COMMON_CONFIG.gooseGroupExtraBiomes.get().stream().map(Object::toString).toList(),
                    Primalfowl_Main.COMMON_CONFIG.enableGooseGroupSpawn.get(),
                    Primalfowl_Main.COMMON_CONFIG.gooseGroupSpawnWeight.get(),
                    Primalfowl_Main.COMMON_CONFIG.gooseGroupMinGroup.get(),
                    Primalfowl_Main.COMMON_CONFIG.gooseGroupMaxGroup.get(),
                    Primalfowl_Entities.GOOSE.get());
        }
    }

    @Override
    public @NotNull MapCodec<? extends BiomeModifier> codec() {
        return Primalfowl_Util.Generation.createBiomeModifierSerializer("goose_group_spawn").get();
    }

    public static void register() {
        Primalfowl_Registries.BIOME_MODIFIERS.register(
                "goose_group_spawn",
                () -> MapCodec.unit(new GooseGroup_BiomeModifier()));
    }
}
