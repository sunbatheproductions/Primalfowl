package org.primalfowl.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.entity.animal.DuckEntity;
import org.primalfowl.entity.animal.GooseEntity;
import org.primalfowl.entity.misc.DuckEgg;
import org.primalfowl.entity.misc.GooseEgg;

public class Primalfowl_Entities {
    public static DeferredHolder<EntityType<?>, EntityType<DuckEntity>> DUCK = Primalfowl_Registries.ENTITIES.register(
            "duck",
            () -> EntityType.Builder.of(DuckEntity::new, MobCategory.CREATURE)
                    //Hitbox
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(8)
                    .build("duck"));

    public static DeferredHolder<EntityType<?>, EntityType<DuckEgg>> THROWN_DUCK_EGG = Primalfowl_Registries.ENTITIES.register(
            "duck_egg", () -> EntityType.Builder.<DuckEgg>of(DuckEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).build("duck_egg"));

    public static DeferredHolder<EntityType<?>, EntityType<GooseEntity>> GOOSE = Primalfowl_Registries.ENTITIES.register(
            "goose",
            () -> EntityType.Builder.of(GooseEntity::new, MobCategory.CREATURE)
                    //Hitbox
                    .sized(0.5f, 0.6f)
                    .clientTrackingRange(8)
                    .build("goose"));

    public static DeferredHolder<EntityType<?>, EntityType<GooseEgg>> THROWN_GOOSE_EGG = Primalfowl_Registries.ENTITIES.register(
            "goose_egg", () -> EntityType.Builder.<GooseEgg>of(GooseEgg::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).build("goose_egg"));


    public static void init() {}
}
