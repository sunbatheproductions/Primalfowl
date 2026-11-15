package org.primalfowl.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.primalfowl.Primalfowl_Main;

public class Primalfowl_Tags {

    public static class Item {
        //──────────────────────────────────── Breed Food ────────────────────────────────────
        public static final TagKey<net.minecraft.world.item.Item> DUCK_BREED_FOOD = createTag(Registries.ITEM, "duck_breed_food");
        public static final TagKey<net.minecraft.world.item.Item> GOOSE_BREED_FOOD = createTag(Registries.ITEM, "goose_breed_food");
        public static final TagKey<net.minecraft.world.item.Item> SWAN_BREED_FOOD = createTag(Registries.ITEM, "swan_breed_food");

    }

    public static class Biome {
        public static final TagKey<net.minecraft.world.level.biome.Biome> SPAWNS_DUCK = createTag(Registries.BIOME, "has_mob/duck");
        public static final TagKey<net.minecraft.world.level.biome.Biome> SPAWNS_MALLARD_DUCK = createTag(Registries.BIOME, "has_mob/mallard_duck");
        public static final TagKey<net.minecraft.world.level.biome.Biome> SPAWNS_GOOSE = createTag(Registries.BIOME, "has_mob/goose");
        public static final TagKey<net.minecraft.world.level.biome.Biome> SPAWNS_CANADA_GOOSE = createTag(Registries.BIOME, "has_mob/canada_goose");
        public static final TagKey<net.minecraft.world.level.biome.Biome> SPAWNS_SWAN = createTag(Registries.BIOME, "has_mob/swan");
    }

    private static <T> TagKey<T> createTag(ResourceKey<Registry<T>> registryResourceKey, String name) {
        return createTag(registryResourceKey, ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, name));
    }

    private static <T> TagKey<T> createTag(ResourceKey<Registry<T>> registryResourceKey, ResourceLocation resourceLocation) {
        return TagKey.create(registryResourceKey, resourceLocation);
    }
}
