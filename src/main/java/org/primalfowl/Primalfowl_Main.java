package org.primalfowl;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.primalfowl.entity.animal.DuckEntity;
import org.primalfowl.registry.Primalfowl_Activities;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_MemoryModuleTypes;
import org.primalfowl.registry.Primalfowl_Sensors;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = Primalfowl_Main.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
@Mod(Primalfowl_Main.MOD_ID)
public class Primalfowl_Main {
    public static final String MOD_ID = "primalfowl";
    public static final Primalfowl_Config COMMON_CONFIG;
    public static final ModConfigSpec COMMON_SPEC;

    static {
        Pair<Primalfowl_Config, ModConfigSpec> pair = new ModConfigSpec.Builder()
                .configure(Primalfowl_Config::new);
        COMMON_CONFIG = pair.getLeft();
        COMMON_SPEC = pair.getRight();
    }

    public Primalfowl_Main(IEventBus modEventBus) {
        ModList.get().getModContainerById(Primalfowl_Main.MOD_ID)
                .ifPresent(
                        modContainer ->
                                modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC)
                );

        //AI
        Primalfowl_Sensors.init(); Primalfowl_Registries.SENSOR_TYPES.register(modEventBus);
        Primalfowl_MemoryModuleTypes.init(); Primalfowl_Registries.MEMORY_MODULE_TYPES.register(modEventBus);
        Primalfowl_Activities.init(); Primalfowl_Registries.ACTIVITIES.register(modEventBus);

        //Entities
        Primalfowl_Entities.init(); Primalfowl_Registries.ENTITIES.register(modEventBus);

        //Sounds
        Primalfowl_Sounds.init(); Primalfowl_Registries.SOUNDS.register(modEventBus);

        //Items
        Primalfowl_Items.initItems(); Primalfowl_Registries.ITEMS.register(modEventBus);
        Primalfowl_Items.initGroups(); Primalfowl_Registries.CREATIVE_MODE_TABS.register(modEventBus);


        //Biome Modifiers
        createBiomeModifiers(); Primalfowl_Registries.BIOME_MODIFIERS.register(modEventBus);
    }

    public static void createBiomeModifiers() {
        //Mobs
            DuckNormal_BiomeModifier.register();
            DuckSwamp_BiomeModifier.register();
            GooseSingle_BiomeModifier.register();
            GooseGroup_BiomeModifier.register();
            SwanSingle_BiomeModifier.register();
            SwanGroup_BiomeModifier.register();
    }

    @SubscribeEvent
        public static void registerAttribute (@NotNull EntityAttributeCreationEvent event){
            event.put(Primalfowl_Entities.DUCK.get(), DuckEntity.createAttributes().build());
            event.put(Primalfowl_Entities.GOOSE.get(), GooseEntity.createAttributes().build());
            event.put(Primalfowl_Entities.SWAN.get(), SwanEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerSpawnPlacements (final @NotNull RegisterSpawnPlacementsEvent event){
            //Duck
            event.register(Primalfowl_Entities.DUCK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    DuckEntity::checkDuckSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            //Goose
            event.register(Primalfowl_Entities.GOOSE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    GooseEntity::checkWaterfowlSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            //Swan
            event.register(Primalfowl_Entities.Swan.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    SwanEntity::checkWaterfowlSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
}

