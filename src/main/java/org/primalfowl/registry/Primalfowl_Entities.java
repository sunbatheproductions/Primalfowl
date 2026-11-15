package org.primalfowl.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.entity.animal.DuckEntity;

public class Primalfowl_Entities {
    public static DeferredHolder<EntityType<?>, EntityType<DuckEntity>> DUCK = Primalfowl_Registries.ENTITIES.register(
            "duck",
            () -> EntityType.Builder.of(DuckEntity::new, MobCategory.CREATURE)
                    //Hitbox
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(8)
                    .build("duck"));

    public static DeferredHolder<EntityType<?>, EntityType<GooseEntity>> GOOSE = Primalfowl_Registries.ENTITIES.register(
            "goose",
            () -> EntityType.Builder.of(GooseEntity::new, MobCategory.CREATURE)
                    //Hitbox
                    .sized(0.5f, 0.6f)
                    .clientTrackingRange(8)
                    .build("goose"));

    public static DeferredHolder<EntityType<?>, EntityType<SwanEntity>> SWAN = Primalfowl_Registries.ENTITIES.register(
            "swan",
            () -> EntityType.Builder.of(SwanEntity::new, MobCategory.CREATURE)
                    //Hitbox
                    .sized(0.5f, 0.6f)
                    .clientTrackingRange(8)
                    .build("swan"));
}
