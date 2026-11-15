package org.primalfowl.registry;

import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primal.entity.ai.sensors.generic.BabySensor;
import org.primal.entity.ai.sensors.generic.ScaredSensor;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.entity.ai.DuckAi;

public class Primalfowl_Sensors {

    //Duck
    public static DeferredHolder<SensorType<?>, SensorType<DuckEntitySensor>> DUCK_ENTITY_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("duck_entity_sensor",
                    () -> new SensorType<>(DuckEntitySensor::new));

    public static final DeferredHolder<SensorType<?>, SensorType<TemptingSensor>> DUCK_TEMPTATIONS_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("duck_temptations",
                    () -> new SensorType<>(() -> new TemptingSensor(DuckAi.getTemptations())));

    //Goose
    public static DeferredHolder<SensorType<?>, SensorType<GooseEntitySensor>> GOOSE_ENTITY_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("goose_entity_sensor",
                    () -> new SensorType<>(GooseEntitySensor::new));

    public static final DeferredHolder<SensorType<?>, SensorType<TemptingSensor>> GOOSE_TEMPTATIONS_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("goose_temptations",
                    () -> new SensorType<>(() -> new TemptingSensor(GooseAi.getTemptations())));

    public static final DeferredHolder<SensorType<?>, SensorType<ScaredSensor<GooseEntity>>> GOOSE_SCARE_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("goose_scare_sensor",
                    () -> new SensorType<>(() -> new ScaredSensor<>(
                            target -> !(target instanceof Animal) && target.isSprinting())));

    public static DeferredHolder<SensorType<?>, SensorType<GooseNearestHostile>> GOOSE_HOSTILE_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("goose_nearest_hostile_sensor",
                    () -> new SensorType<>(GooseNearestHostile::new));

    //Swan
    public static DeferredHolder<SensorType<?>, SensorType<SwanEntitySensor>> SWAN_ENTITY_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("swan_entity_sensor",
                    () -> new SensorType<>(SwanEntitySensor::new));

    public static final DeferredHolder<SensorType<?>, SensorType<TemptingSensor>> SWAN_TEMPTATIONS_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("swan_temptations",
                    () -> new SensorType<>(() -> new TemptingSensor(SwanAi.getTemptations())));

    public static final DeferredHolder<SensorType<?>, SensorType<ScaredSensor<SwanEntity>>> SWAN_SCARE_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("swan_scare_sensor",
                    () -> new SensorType<>(() -> new ScaredSensor<>(
                            target -> !(target instanceof Animal) && target.isSprinting())));

    public static DeferredHolder<SensorType<?>, SensorType<SwanNearestHostile>> SWAN_HOSTILE_SENSOR =
            Primalfowl_Registries.SENSOR_TYPES.register("swan_nearest_hostile_sensor",
                    () -> new SensorType<>(SwanNearestHostile::new));

    //Generic
    public static DeferredHolder<SensorType<?>, SensorType<BabySensor>> NEAREST_BABY =
            Primalfowl_Registries.SENSOR_TYPES.register("nearest_baby",
                    () -> new SensorType<>(BabySensor::new));

    public static void init() {}
}
