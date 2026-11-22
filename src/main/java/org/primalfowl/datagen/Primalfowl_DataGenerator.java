package org.primalfowl.datagen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.datagen.providers.*;
import org.primalfowl.datagen.providers.tag.Primalfowl_BiomeTagGenerator;
import org.primalfowl.datagen.providers.tag.Primalfowl_BlockTagsGenerator;
import org.primalfowl.datagen.providers.tag.Primalfowl_EntityTagGenerator;
import org.primalfowl.datagen.providers.tag.Primalfowl_ItemTagsGenerator;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = Primalfowl_Main.MOD_ID)
public class Primalfowl_DataGenerator {

    private static final RegistrySetBuilder BUILDER =
            new RegistrySetBuilder()
                    .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Primalfowl_BiomeModifiersGenerator::bootstrap);

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        //Client
        {

            //Item Model
            generator.addProvider(event.includeClient(), new Primalfowl_ItemModelGenerator(output, existingFileHelper));

            //Sounds
            generator.addProvider(event.includeClient(), new Primalfowl_SoundsJsonGenerator(output, existingFileHelper));

            //Language File
            generator.addProvider(event.includeClient(), new Primalfowl_LanguageFileGenerator(output));
        }


        //Server
        {
            //World Gen
            generator.addProvider(true, new DatapackBuiltinEntriesProvider(output, lookupProvider, BUILDER, Set.of("minecraft", Primalfowl_Main.MOD_ID)));

            //Entity Tags
            generator.addProvider(event.includeServer(), new Primalfowl_EntityTagGenerator(output, lookupProvider, existingFileHelper));
            //Block Tags
            final var blockTagGenerator = generator.addProvider(event.includeServer(), new Primalfowl_BlockTagsGenerator(output, lookupProvider, existingFileHelper));
            //Biome Tags
            generator.addProvider(event.includeServer(), new Primalfowl_BiomeTagGenerator(output, lookupProvider, existingFileHelper));
            //Item Tags
            generator.addProvider(event.includeServer(), new Primalfowl_ItemTagsGenerator(output, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

            //Recipes
            generator.addProvider(event.includeServer(), new Primalfowl_RecipesGenerator(output, lookupProvider));

            //Loot Tables
            generator.addProvider(event.includeServer(), createLootTableProviders(output, lookupProvider));

        }
    }

    public static LootTableProvider createLootTableProviders (final PackOutput output,
                                                              final CompletableFuture<HolderLookup.Provider> lookupProvider){
        return new LootTableProvider(output, Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(Primalfowl_LootTablesEntitiesGenerator::new, LootContextParamSets.ENTITY)),
                lookupProvider
        );
    }
}
