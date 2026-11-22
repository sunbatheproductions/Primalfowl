package org.primalfowl.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import org.primal.entity.ai.behavior.generic.*;
import org.primal.util.Primal_Util;
import org.primalfowl.entity.animal.DuckEntity;
import org.primalfowl.registry.Primalfowl_Activities;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_MemoryModuleTypes;
import org.primalfowl.registry.Primalfowl_Sensors;

import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class DuckAi {

    private static final Predicate<DuckEntity> CAN_MOVE = duck -> !duck.refuseToMove();
    private static final Predicate<DuckEntity> IS_ADULT_DUCK = duck -> !duck.isBaby();

    //──────────────────────────────────── Init ────────────────────────────────────
    private static final ImmutableList<SensorType<? extends Sensor<? super DuckEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.HURT_BY,
            Primalfowl_Sensors.NEAREST_BABY.get(),
            Primalfowl_Sensors.DUCK_TEMPTATIONS_SENSOR.get(),
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ADULT,
            SensorType.NEAREST_ITEMS);

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.GAZE_COOLDOWN_TICKS,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.ROAR_TARGET,

            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.BREED_TARGET,

            MemoryModuleType.IS_PANICKING,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.TEMPTING_PLAYER,

            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.HAS_HUNTING_COOLDOWN,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,

            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.IS_PREGNANT,
            MemoryModuleType.PACIFIED,

            MemoryModuleType.NEAREST_HOSTILE,

            Primalfowl_MemoryModuleTypes.NEAREST_VISIBLE_BABY.get(),
            Primalfowl_MemoryModuleTypes.NEAREST_IMPORTANT_BLOCK.get(),

            MemoryModuleType.HOME,

            Primalfowl_MemoryModuleTypes.WAS_IDLE_ANIMATION.get(),
            Primalfowl_MemoryModuleTypes.WAS_TRIGGER_ANIMATION.get(),

            Primalfowl_MemoryModuleTypes.HURT_RECENTLY.get(),

            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
            MemoryModuleType.ADMIRING_ITEM,

            Primalfowl_MemoryModuleTypes.NEAREST_SCARED.get(),
            MemoryModuleType.SNIFF_COOLDOWN,
            Primalfowl_MemoryModuleTypes.NEAREST_CAUTIOUS.get(),
            Primalfowl_MemoryModuleTypes.NEAREST_PACK_MEMBER.get(),
            Primalfowl_MemoryModuleTypes.GO_TO_PACK_MEMBER_COOLDOWN.get(),

            Primalfowl_MemoryModuleTypes.NEAREST_PLAY_MOB.get(),
            Primalfowl_MemoryModuleTypes.LUNGE_COOLDOWN.get()
    );

    public static Predicate<ItemStack> getTemptations() {
        return DuckEntity::isMatingFood;
    }

    public static void initMemories(DuckEntity duck, RandomSource random) {
    }

    public static Brain.Provider<DuckEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    public static Brain<?> makeBrain(Brain<DuckEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRetreatActivity(brain);
        initCautiousActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    //──────────────────────────────────── Activities ────────────────────────────────────
    private static void initCoreActivity(Brain<DuckEntity> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new Swim(0.85F),
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        TryFindWaterSurface.create(32, 1, 0.10f)
                )
        );
    }

    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(2, 12);

    private static void initIdleActivity(Brain<DuckEntity> brain) {
        brain.addActivity(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        new AnimalMakeLove(Primalfowl_Entities.DUCK.get()),
                        new RunOne<>(
                                ImmutableList.of(
                                        Pair.of(new FollowTemptation(livingEntity -> 1.1F, livingEntity -> livingEntity.isBaby() ? 2.5 : 3.5), 4),
                                        Pair.of(
                                                BehaviorBuilder.triggerIf(
                                                        CAN_MOVE,
                                                        BabyFollowAdult.create(ADULT_FOLLOW_RANGE, 1.15F)
                                                ),
                                                3
                                        )
                                )
                        ),
                        SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60)),
                        BehaviorBuilder.triggerIf(
                                CAN_MOVE,
                                (OneShot<? super DuckEntity>) SetWalkTargetFromPackMemberSometimes.create(
                                        1.0F, 10, 2,
                                        UniformInt.of(Primal_Util.toTicks(3), Primal_Util.toTicks(10)),
                                        IS_ADULT_DUCK
                                )
                        ),
                        createIdleBehaviors()
                )
        );
    }

    private static RunOne<DuckEntity> createIdleBehaviors() {
        return new RunOne<>(
                ImmutableList.of(
                        Pair.of(
                                BehaviorBuilder.<DuckEntity>triggerIf(
                                        CAN_MOVE,
                                        RandomStroll.stroll(1.0F)
                                ),
                                6
                        ),
                        Pair.of(new RandomLookAround(UniformInt.of(100, 180), 30.0F, 0.0F, 0.0F), 2),
                        Pair.of(new DoNothing(20, 40), 1)
                )
        );
    }

    public static final UniformInt RETREAT_DURATION = TimeUtil.rangeOfSeconds(15, 30);

    private static void initRetreatActivity(Brain<DuckEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.AVOID,
                10,
                ImmutableList.of(
                        SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.3F, 30, false),
                        RandomStroll.stroll(1.0F),
                        new RandomLookAround(UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F)
                ),
                MemoryModuleType.AVOID_TARGET
        );
    }

    private static void initCautiousActivity(Brain<DuckEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Primalfowl_Activities.CAUTIOUS.get(),
                10,
                ImmutableList.of(
                        SetLookTarget.fromCautiousTarget(),
                        RandomStroll.stroll(0.75F)
                ),
                Primalfowl_MemoryModuleTypes.NEAREST_CAUTIOUS.get()
        );
    }

    //──────────────────────────────────── Misc ────────────────────────────────────
    public static void updateActivity(DuckEntity duck) {
        Brain<DuckEntity> brain = duck.getBrain();

        if (duck.isBaby()) {
            brain.setActiveActivityToFirstValid(ImmutableList.of(
                    Primalfowl_Activities.ESCAPE.get(),
                    Activity.AVOID,
                    Activity.IDLE
            ));
        } else {
            brain.setActiveActivityToFirstValid(ImmutableList.of(
                    Primalfowl_Activities.ESCAPE.get(),
                    Activity.AVOID,
                    Primalfowl_Activities.CAUTIOUS.get(),
                    Activity.IDLE
            ));
        }
    }
}