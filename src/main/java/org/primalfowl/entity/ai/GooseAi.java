package org.primalfowl.entity.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.UnknownNullability;
import org.primal.entity.ai.behavior.generic.*;
import org.primal.util.Primal_Util;
import org.primalfowl.entity.animal.GooseEntity;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_MemoryModuleTypes;
import org.primalfowl.registry.Primalfowl_Sensors;

import java.util.function.Predicate;

public class GooseAi {

    private static final Predicate<GooseEntity> CAN_MOVE = goose -> !goose.refuseToMove();
    private static final Predicate<GooseEntity> IS_ADULT = goose -> !goose.isBaby();

    private static final ImmutableList<SensorType<? extends Sensor<? super GooseEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.HURT_BY,
            Primalfowl_Sensors.NEAREST_BABY.get(),
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ADULT,
            SensorType.NEAREST_ITEMS,
            Primalfowl_Sensors.GOOSE_TEMPTATIONS_SENSOR.get(),
            Primalfowl_Sensors.GOOSE_SCARE_SENSOR.get(),
            Primalfowl_Sensors.GOOSE_HOSTILE_SENSOR.get());

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
            Primalfowl_MemoryModuleTypes.IS_STUNNED.get(),
            Primalfowl_MemoryModuleTypes.IS_GRABBING.get(),
            Primalfowl_MemoryModuleTypes.AMOUNT_ATTACKED.get(),
            Primalfowl_MemoryModuleTypes.NEAREST_SCARED.get(),
            Primalfowl_MemoryModuleTypes.HURT_RECENTLY.get(),

            MemoryModuleType.HOME,
            MemoryModuleType.RAM_COOLDOWN_TICKS,

            Primalfowl_MemoryModuleTypes.ATTACKED_LIST.get(),
            Primalfowl_MemoryModuleTypes.LAST_ATTACK_TARGET.get(),
            Primalfowl_MemoryModuleTypes.WAS_TRIGGER_ANIMATION.get()
    );

    public static Predicate<ItemStack> getTemptations() {
        return GooseEntity::isMatingFood;
    }

    public static void initMemories(GooseEntity goose, RandomSource random) {
    }

    public static Brain.Provider<GooseEntity> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    public static Brain<?> makeBrain(Brain<GooseEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    //──────────────────────────────────── Activities ────────────────────────────────────
    private static void initCoreActivity(@UnknownNullability Brain<GooseEntity> brain) {
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

    private static void initIdleActivity(Brain<GooseEntity> brain) {
        brain.addActivity(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        new AnimalMakeLove(Primalfowl_Entities.GOOSE.get()),
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
                                (OneShot<? super GooseEntity>) SetWalkTargetFromPackMemberSometimes.create(
                                        1.0F, 10, 2,
                                        UniformInt.of(Primal_Util.toTicks(3), Primal_Util.toTicks(10)),
                                        IS_ADULT
                                )
                        ),
                        createIdleBehaviors()
                )
        );
    }

    private static RunOne<GooseEntity> createIdleBehaviors() {
        return new RunOne<>(
                ImmutableList.of(
                        Pair.of(
                                BehaviorBuilder.triggerIf(
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

    private static void initFightActivity(Brain<GooseEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        EraseMemoryIf.create(GooseEntity::isBaby, MemoryModuleType.ATTACK_TARGET),
                        BecomePassiveIfMemoryPresent.create(MemoryModuleType.NEAREST_REPELLENT, 200),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
                        MeleeAttack.create(20),
                        SetEntityLookTarget.create(50),
                        StopAttackingIfTargetInvalid.create(),
                        EraseMemoryIf.create(
                                goose -> goose.isBaby() || goose.isInLove() || Primal_Util.Ai.lessThanMinAirSlow(goose),
                                MemoryModuleType.ATTACK_TARGET
                        )
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    //──────────────────────────────────── Misc ────────────────────────────────────
    public static void updateActivity(GooseEntity goose) {
        Brain<GooseEntity> brain = goose.getBrain();

        if (goose.isBaby()) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
            brain.eraseMemory(MemoryModuleType.NEAREST_ATTACKABLE);
            goose.setAggressive(false);
            brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
            return;
        }

        brain.setActiveActivityToFirstValid(ImmutableList.of(
                Activity.FIGHT,
                Activity.IDLE
        ));
        goose.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }
}