package org.primalfowl.registry;

import net.minecraft.world.food.FoodProperties;

public class Primalfowl_Food {
    public static final FoodProperties RAW_DUCK = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.6F)
            .build();

    public static final FoodProperties COOKED_DUCK = new FoodProperties.Builder()
            .nutrition(8)
            .saturationModifier(1.2F)
            .build();

    public static final FoodProperties RAW_GOOSE = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.6F)
            .build();

    public static final FoodProperties COOKED_GOOSE = new FoodProperties.Builder()
            .nutrition(8)
            .saturationModifier(1.2F)
            .build();
}
