package org.primalfowl.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.Primalfowl_Registries;

public class Primalfowl_Sounds {

    //Duck
    public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_IDLE = register("entity.duck.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_HURT = register("entity.duck.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_DEATH = register("entity.duck.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUCK_PLOP = register("entity.duck.plop");

    //Goose
    public static final DeferredHolder<SoundEvent, SoundEvent> GOOSE_IDLE = register("entity.goose.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOOSE_HURT = register("entity.goose.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOOSE_DEATH = register("entity.goose.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> GOOSE_PLOP = register("entity.goose.plop");

    public static void init(){}

    public static DeferredHolder<SoundEvent, SoundEvent> register(final String name) {
        return Primalfowl_Registries.SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, name)));
    }
}
