package org.primalfowl.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.registry.Primalfowl_Sounds;

public class Primalfowl_SoundsJsonGenerator extends SoundDefinitionsProvider {

    public Primalfowl_SoundsJsonGenerator(final PackOutput output, final ExistingFileHelper helper) {
        super(output, Primalfowl_Main.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

        addSoundEntity(Primalfowl_Sounds.DUCK_IDLE, 3);
        addSoundEntity(Primalfowl_Sounds.DUCK_HURT, 2);
        addSoundEntity(Primalfowl_Sounds.DUCK_DEATH, 1);
        addSpecificSound(Primalfowl_Sounds.DUCK_PLOP,
                "minecraft:mob/chicken/plop"
        );

        addSoundEntity(Primalfowl_Sounds.GOOSE_IDLE, 1);
        addSoundEntity(Primalfowl_Sounds.GOOSE_HURT, 1);
        addSoundEntity(Primalfowl_Sounds.GOOSE_DEATH, 1);
        addSpecificSound(Primalfowl_Sounds.GOOSE_PLOP,
                "minecraft:mob/chicken/plop"
        );

    }

    private void addSpecificSound(final DeferredHolder<SoundEvent, SoundEvent> sound, SoundDefinition.Sound... soundData) {
        final SoundDefinition definition = definition().subtitle("subtitles." + Primalfowl_Main.MOD_ID + "." + sound.getId().getPath());

        for (SoundDefinition.Sound soundDatum : soundData) {
            definition.with(soundDatum);
        }

        this.add(sound.get(), definition);
    }

    private void addSpecificSound(final DeferredHolder<SoundEvent, SoundEvent> sound, final String... sounds) {
        final SoundDefinition definition = definition().subtitle("subtitles." + Primalfowl_Main.MOD_ID + "." + sound.getId().getPath());

        for(final String name: sounds){
            definition.with(sound(name));
        }

        this.add(sound.get(), definition);
    }

    private void addSoundEntity(final DeferredHolder<SoundEvent, SoundEvent> sound, final int soundVariationAmount) {
        final SoundDefinition definition = definition().subtitle("subtitles." + Primalfowl_Main.MOD_ID + "." + sound.getId().getPath());

        String[] soundPathSplitted= sound.getId().getPath().split("\\.");

        String mobType  = soundPathSplitted[1];
        String soundType= soundPathSplitted[2];

        String finalPath=mobType+"_"+soundType;

        for (int i = 1; i <= soundVariationAmount; i++)
            definition.with(sound(Primalfowl_Main.MOD_ID + ":entity/" + mobType + "/" + finalPath + i));

        this.add(sound.get(), definition);
    }

}
