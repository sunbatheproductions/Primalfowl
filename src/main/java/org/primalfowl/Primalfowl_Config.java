package org.primalfowl;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class Primalfowl_Config {

    // --- Duck ---
    public final ModConfigSpec.BooleanValue enableDuckNormalSpawn;
    public final ModConfigSpec.IntValue duckNormalSpawnWeight;
    public final ModConfigSpec.IntValue duckNormalMinGroup;
    public final ModConfigSpec.IntValue duckNormalMaxGroup;
    public final ModConfigSpec.ConfigValue<List<? extends String>> duckNormalExtraBiomes;

    public final ModConfigSpec.BooleanValue enableDuckSwampSpawn;
    public final ModConfigSpec.IntValue duckSwampSpawnWeight;
    public final ModConfigSpec.IntValue duckSwampMinGroup;
    public final ModConfigSpec.IntValue duckSwampMaxGroup;
    public final ModConfigSpec.ConfigValue<List<? extends String>> duckSwampExtraBiomes;
    
    // --- Goose ---
    public final ModConfigSpec.BooleanValue enableGooseSingleSpawn;
    public final ModConfigSpec.IntValue gooseSingleSpawnWeight;
    public final ModConfigSpec.IntValue gooseSingleMinGroup;
    public final ModConfigSpec.IntValue gooseSingleMaxGroup;
    public final ModConfigSpec.ConfigValue<List<? extends String>> gooseSingleExtraBiomes;

    public final ModConfigSpec.BooleanValue enableGooseGroupSpawn;
    public final ModConfigSpec.IntValue gooseGroupSpawnWeight;
    public final ModConfigSpec.IntValue gooseGroupMinGroup;
    public final ModConfigSpec.IntValue gooseGroupMaxGroup;
    public final ModConfigSpec.ConfigValue<List<? extends String>> gooseGroupExtraBiomes;

    // --- Swan ---
    public final ModConfigSpec.BooleanValue enableSwanSingleSpawn;
    public final ModConfigSpec.IntValue swanSingleSpawnWeight;
    public final ModConfigSpec.IntValue swanSingleMinGroup;
    public final ModConfigSpec.IntValue swanSingleMaxGroup;
    public final ModConfigSpec.ConfigValue<List<? extends String>> swanSingleExtraBiomes;

    public final ModConfigSpec.BooleanValue enableSwanGroupSpawn;
    public final ModConfigSpec.IntValue swanGroupSpawnWeight;
    public final ModConfigSpec.IntValue swanGroupMinGroup;
    public final ModConfigSpec.IntValue swanGroupMaxGroup;
    public final ModConfigSpec.ConfigValue<List<? extends String>> swanGroupExtraBiomes;

    @SuppressWarnings("deprecation")
    public Primalfowl_Config(ModConfigSpec.Builder builder) {

        // --- Duck ---
        builder.push("crocodile");
        {
            builder.push("normal");
            {
                builder.comment("""
                            Enable or disable duck spawning in non-swampy biomes
                            Default=true""");
                enableDuckNormalSpawn = builder.define("enableDuckNormalSpawn", true);

                builder.comment("""
                            Spawn weight for ducks in non-swampy biomes (higher = more common)
                            Default=20""");
                duckNormalSpawnWeight = builder.defineInRange("duckNormalSpawnWeight", 5, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Minimum group size for ducks in non-swampy biomes, it MUST be lesser than max group size
                            Default=1""");
                duckNormalMinGroup = builder.defineInRange("duckNormalMinGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Maximum group size for ducks in non-swampy biomes, it MUST be greater than min group size
                            Default=1""");
                duckNormalMaxGroup = builder.defineInRange("duckNormalMaxGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Extra biomes (separated from biome tag) where normal ducks can spawn.
                            Must be written as full biome IDs (e.g. "minecraft:plains", "nomansland:maple_forest")
                            You can use biome tags too, just put a # before (e.g. "#minecraft:is_forest", "#nomansland:is_river")
                            Default = []""");
                duckNormalExtraBiomes = builder.defineList("duckNormalExtraBiomes",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );
            }
            builder.pop();

            builder.push("swamp");
            {
                builder.comment("""
                            Enable or disable duck spawning in swampy biomes
                            Default=true""");
                enableDuckSwampSpawn = builder.define("enableduckSwampSpawn", true);

                builder.comment("""
                            Spawn weight for ducks in swampy biomes (higher = more common)
                            Default=3""");
                duckSwampSpawnWeight = builder.defineInRange("duckSwampSpawnWeight", 10, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Minimum group size for ducks in swampy biomes, it MUST be lesser than max group size
                            Default=1""");
                duckSwampMinGroup = builder.defineInRange("duckSwampMinGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Maximum group size for ducks in swampy biomes, it MUST be greater than min group size
                            Default=1""");
                duckSwampMaxGroup = builder.defineInRange("duckSwampMaxGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Extra biomes (separated from biome tag) where Swamp ducks can spawn
                            Must be written as full biome IDs (e.g. "minecraft:plains", "nomansland:maple_forest")
                            You can use biome tags too, just put a # before (e.g. "#minecraft:is_forest", "#nomansland:is_river")
                            Default = []""");
                duckSwampExtraBiomes = builder.defineList("duckSwampExtraBiomes",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );
            }
            builder.pop();
        }
        builder.pop();

        // --- Goose ---
        builder.push("goose");
        {
            builder.push("single");
            {
                builder.comment("""
                            Enable or disable goose single spawning
                            Default=true""");
                enableGooseSingleSpawn = builder.define("enableGooseSingleSpawn", true);

                builder.comment("""
                            Spawn weight for single geese (higher = more common)
                            Default=15""");
                gooseSingleSpawnWeight = builder.defineInRange("gooseSingleSpawnWeight", 8, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Minimum group size for single geese, it MUST be lesser than max group size
                            Default=1""");
                gooseSingleMinGroup = builder.defineInRange("gooseSingleMinGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Maximum group size for single geese, it MUST be greater than min group size
                            Default=1""");
                gooseSingleMaxGroup = builder.defineInRange("gooseSingleMaxGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Extra biomes (separated from biome tag) where single geese can spawn.
                            Must be written as full biome IDs (e.g. "minecraft:plains", "nomansland:maple_forest")
                            You can use biome tags too, just put a # before (e.g. "#minecraft:is_forest", "#nomansland:is_river")
                            Default = []""");
                gooseSingleExtraBiomes = builder.defineList("gooseSingleExtraBiomes",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );
            }
            builder.pop();

            builder.push("group");
            {
                builder.comment("""
                            Enable or disable goose group spawning (with babies)
                            Default=true""");
                enableGooseGroupSpawn = builder.define("enableGooseGroupSpawn", true);

                builder.comment("""
                            Spawn weight for goose groups (higher = more common)
                            Default=5""");
                gooseGroupSpawnWeight = builder.defineInRange("gooseGroupSpawnWeight", 5, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Minimum group size for goose groups, it MUST be lesser than max group size
                            Default=2""");
                gooseGroupMinGroup = builder.defineInRange("gooseGroupMinGroup", 2, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Maximum group size for goose groups, it MUST be greater than min group size
                            Default=2""");
                gooseGroupMaxGroup = builder.defineInRange("gooseGroupMaxGroup", 2, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Extra biomes (separated from biome tag) where goose groups can spawn.
                            Must be written as full biome IDs (e.g. "minecraft:plains", "nomansland:maple_forest")
                            You can use biome tags too, just put a # before (e.g. "#minecraft:is_forest", "#nomansland:is_river")
                            Default = []""");
                gooseGroupExtraBiomes = builder.defineList("gooseGroupExtraBiomes",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );
            }
            builder.pop();
        }
        builder.pop();

        // --- Swan ---
        builder.push("swan");
        {
            builder.push("single");
            {
                builder.comment("""
                            Enable or disable swan single spawning
                            Default=true""");
                enableSwanSingleSpawn = builder.define("enableSwanSingleSpawn", true);

                builder.comment("""
                            Spawn weight for single swan (higher = more common)
                            Default=15""");
                swanSingleSpawnWeight = builder.defineInRange("swanSingleSpawnWeight", 8, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Minimum group size for single swan, it MUST be lesser than max group size
                            Default=1""");
                swanSingleMinGroup = builder.defineInRange("swanSingleMinGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Maximum group size for single swan, it MUST be greater than min group size
                            Default=1""");
                swanSingleMaxGroup = builder.defineInRange("swanSingleMaxGroup", 1, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Extra biomes (separated from biome tag) where single swan can spawn.
                            Must be written as full biome IDs (e.g. "minecraft:plains", "nomansland:maple_forest")
                            You can use biome tags too, just put a # before (e.g. "#minecraft:is_forest", "#nomansland:is_river")
                            Default = []""");
                swanSingleExtraBiomes = builder.defineList("swanSingleExtraBiomes",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );
            }
            builder.pop();

            builder.push("group");
            {
                builder.comment("""
                            Enable or disable swan group spawning (with babies)
                            Default=true""");
                enableSwanGroupSpawn = builder.define("enableSwanGroupSpawn", true);

                builder.comment("""
                            Spawn weight for swan groups (higher = more common)
                            Default=5""");
                swanGroupSpawnWeight = builder.defineInRange("swanGroupSpawnWeight", 4, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Minimum group size for swan groups, it MUST be lesser than max group size
                            Default=2""");
                swanGroupMinGroup = builder.defineInRange("swanGroupMinGroup", 2, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Maximum group size for swan groups, it MUST be greater than min group size
                            Default=2""");
                swanGroupMaxGroup = builder.defineInRange("swanGroupMaxGroup", 2, 1, Integer.MAX_VALUE);

                builder.comment("""
                            Extra biomes (separated from biome tag) where swan groups can spawn.
                            Must be written as full biome IDs (e.g. "minecraft:plains", "nomansland:maple_forest")
                            You can use biome tags too, just put a # before (e.g. "#minecraft:is_forest", "#nomansland:is_river")
                            Default = []""");
                swanGroupExtraBiomes = builder.defineList("swanGroupExtraBiomes",
                        List.of(),
                        o -> o instanceof String s && s.contains(":")
                );
            }
            builder.pop();
        }
        builder.pop();
    }
}
