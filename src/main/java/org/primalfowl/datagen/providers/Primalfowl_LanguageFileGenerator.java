package org.primalfowl.datagen.providers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.registry.Primalfowl_Items;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Primalfowl_LanguageFileGenerator extends LanguageProvider {
    public Primalfowl_LanguageFileGenerator(PackOutput output) {
        super(output, Primalfowl_Main.MOD_ID, "en_us");
    }

    List<String> EXCEPTIONS = List.of(
            Primalfowl_Items.RAW_DUCK.getId().getPath(),
            Primalfowl_Items.RAW_GOOSE.getId().getPath()
    );

    @Override
    protected void addTranslations() {
        Primalfowl_Registries.ENTITIES.getEntries().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(Primalfowl_Main.MOD_ID))
                .forEach((entry)->addEntityTranslation(entry.getKey()));

        Primalfowl_Registries.ITEMS.getEntries().stream()
                .filter(entry -> entry.getKey().location().getNamespace().equals(Primalfowl_Main.MOD_ID) && (!(entry.get() instanceof BlockItem) || entry.get() instanceof ItemNameBlockItem) && !EXCEPTIONS.contains(entry.getKey().location().getPath()))
                .forEach((entry)->addItemTranslation(entry.getKey()));

        add("itemGroup.primalfowl", "Primalfowl");

        //Subtitles
        {

            //Duck
            {
                add("subtitles.primalfowl.entity.duck.idle", "Duck quacks");
                add("subtitles.primalfowl.entity.duck.hurt", "Duck hurts");
                add("subtitles.primalfowl.entity.duck.death", "Duck dies");
            }

            //Goose
            {
                add("subtitles.primalfowl.entity.goose.idle", "Goose honks");
                add("subtitles.primalfowl.entity.goose.hurt", "Goose hurts");
                add("subtitles.primalfowl.entity.goose.death", "Goose dies");
            }

            //Misc
            {
                add("item.primalfowl.raw_duck", "Raw Duck");
                add("item.primalfowl.cooked_duck", "Cooked Duck");
                add("item.primalfowl.raw_goose", "Raw Goose");
                add("item.primalfowl.cooked_goose", "Cooked Goose");
            }
        }

        //Field Guide Descriptions/Titles
        {
            //Animals
            {
                add("entity.primalfowl.duck.description",
                        "A passive mob found near rivers in specific biomes.");
            }

            {
                add("entity.primalfowl.goose.description",
                        "A neutral mob found near rivers in specific biomes. Attacks illagers on sight.");
            }
        }
    }

    private void addEntityTranslation(ResourceKey<EntityType<?>> item) {
        String path = item.location().getPath();
        String translation = translate(path);
        add(BuiltInRegistries.ENTITY_TYPE.get(item.location()), translation);
    }

    private void addItemTranslation(ResourceKey<Item> item) {
        String path = item.location().getPath();
        String translation = translate(path);

            add(BuiltInRegistries.ITEM.get(item.location()), translation);
    }

    private String translate(String path) {
        return Arrays.stream(path.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

}
