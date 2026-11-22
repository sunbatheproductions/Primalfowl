package org.primalfowl.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.registry.Primalfowl_Tags;

import java.util.concurrent.CompletableFuture;

public class Primalfowl_BiomeTagGenerator extends BiomeTagsProvider {

    public Primalfowl_BiomeTagGenerator(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable final ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Primalfowl_Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider arg) {

        //Animals
        {
            //Duck
            this.tag(Primalfowl_Tags.Biome.SPAWNS_DUCK)
                    .add(Biomes.PLAINS)
                    .add(Biomes.MEADOW)
                    .addTag(BiomeTags.IS_FOREST)
                    .add(Biomes.TAIGA)
                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA).add(Biomes.OLD_GROWTH_PINE_TAIGA)
                    .add(Biomes.WINDSWEPT_FOREST)
                    .add(Biomes.CHERRY_GROVE)
                    .addTag(Primalfowl_Tags.Biome.SPAWNS_MALLARD_DUCK);

            this.tag(Primalfowl_Tags.Biome.SPAWNS_MALLARD_DUCK)
                    .add(Biomes.SWAMP)
                    .add(Biomes.MANGROVE_SWAMP)
                    .add(Biomes.JUNGLE)
                    .add(Biomes.SPARSE_JUNGLE)
                    .add(Biomes.BAMBOO_JUNGLE);

            //Goose
            this.tag(Primalfowl_Tags.Biome.SPAWNS_GOOSE)
                    .add(Biomes.PLAINS)
                    .addTag(BiomeTags.IS_FOREST)
                    .addTag(Primalfowl_Tags.Biome.SPAWNS_CANADA_GOOSE);

            this.tag(Primalfowl_Tags.Biome.SPAWNS_CANADA_GOOSE)
                    .add(Biomes.MEADOW)
                    .add(Biomes.TAIGA)
                    .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                    .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                    .add(Biomes.WINDSWEPT_FOREST)
                    .add(Biomes.WINDSWEPT_GRAVELLY_HILLS)
                    .add(Biomes.WINDSWEPT_HILLS);

        }
    }
}
