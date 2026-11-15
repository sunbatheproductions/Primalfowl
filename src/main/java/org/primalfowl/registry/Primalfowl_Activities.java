package org.primalfowl.registry;

import net.minecraft.world.entity.schedule.Activity;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.primalfowl.Primalfowl_Registries;

public class Primalfowl_Activities {

    public static final DeferredHolder<Activity, Activity> SIT = register("primalfowl_sit");
    public static final DeferredHolder<Activity, Activity> FOLLOW = register("primalfowl_follow");
    public static final DeferredHolder<Activity, Activity> JOCKEY = register("primalfowl_jockey");
    public static final DeferredHolder<Activity, Activity> CAUTIOUS = register("primalfowl_cautious");

    public static void init() {}

    private static @NotNull DeferredHolder<Activity, Activity> register(String key) {
        return Primalfowl_Registries.ACTIVITIES.register(key, ()->new Activity(key));
    }
}
