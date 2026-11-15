package org.primalfowl.entity.animal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primal.util.Primal_Util;
import org.primal.util.mob_types.DetectsFartherAway;
import org.primalfowl.entity.ai.DuckAi;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_Tags;

import java.util.Random;
import java.util.function.IntFunction;

public class DuckEntity extends Animal implements VariantHolder<DuckEntity.Variant> {
    //──────────────────────────────────── Variants ────────────────────────────────────
    public enum Variant implements StringRepresentable {
        PEKIN(0, "pekin"),
        MALLARD(1, "mallard");

        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(DuckEntity.Variant::values);

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(DuckEntity.Variant::getId, values(),
                ByIdMap.OutOfBoundsStrategy.CLAMP);

        public static DuckEntity.Variant byId(int id) {
            return BY_ID.apply(id);
        }

        final int id;

        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }

    @Override
    public void setVariant(@NotNull DuckEntity.Variant variant) {
        this.entityData.set(DATA_VARIANT_ID, variant.id);
    }

    @Override
    public @NotNull DuckEntity.Variant getVariant() {
        return DuckEntity.Variant.byId(this.entityData.get(DATA_VARIANT_ID));
    }

    public void setVariantFromBiome(DuckEntity animal, Holder<Biome> holder) {
        if (holder.is(Primalfowl_Tags.Biome.SPAWNS_MALLARD_DUCK)) {
            animal.setVariant(Variant.MALLARD);
        } else {
            animal.setVariant(Variant.PEKIN);
        }
    }

    //──────────────────────────────────── Init ────────────────────────────────────
    public DuckEntity(EntityType<DuckEntity> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new DuckMoveControl(this);
        this.lookControl = new DuckLookControl(this);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6f)
                .add(Attributes.MOVEMENT_SPEED, 0.19f)
                .add(Attributes.ATTACK_DAMAGE, 1.5f)
                .add(Attributes.FOLLOW_RANGE, 48.0F);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(DuckEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, DuckEntity.Variant.PEKIN.id);
    }

    @Override
    public @NotNull SpawnGroupData finalizeSpawn(ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnGroupData) {
        setVariantFromBiome(this, level.getBiome(this.blockPosition()));
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    //──────────────────────────────────── AI & Movement ────────────────────────────────────
    @Override
    protected void registerGoals() {
    }

    @Override
    protected @NotNull Brain.Provider<DuckEntity> brainProvider() {
        return DuckAi.brainProvider();
    }

    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
        return DuckAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Brain<DuckEntity> getBrain() {
        return (Brain<DuckEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        DuckAi.updateActivity(this);

        this.duckAiStep();
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new DuckPathNavigation(this, level);
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
        this.setZza(speed);
    }

    public void stopMoving() {
        this.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        this.getNavigation().stop();
        this.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return isMatingFood(stack);
    }

    public static boolean isMatingFood(@NotNull ItemStack stack){
        return stack.is(Primalfowl_Tags.Item.DUCK_BREED_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        if(otherParent instanceof DuckEntity otherParentCasted)
            return Primal_Util.createFromParents(Primalfowl_Entities.DUCK.get(),
                    this,
                    otherParentCasted, c-> DuckAi.initMemories(c, level.getRandom()));

        return null;
    }

    @Override
    public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mate) {
        this.finalizeSpawnChildFromBreeding(level, mate, null);
        this.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
    }

    //──────────────────────────────────── Sounds ────────────────────────────────────
    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return Primalfowl_Sounds.DUCK_IDLE.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return Primalfowl_Sounds.DUCK_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return Primalfowl_Sounds.DUCK_DEATH.get();
    }

    public @Nullable SoundEvent getEatingSound() {
        return Primalfowl_Sounds.EAGLE_EAT.get();
    }

    protected boolean playEatingSound(){
        //Play the sound
        if (!this.isSilent()) {
            SoundEvent soundEvent = this.getEatingSound();
            if (soundEvent != null) {
                this.level()
                        .playSound(
                                null, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
                        );
            }
        }

        return true;
    }

    //──────────────────────────────────── Visuals ────────────────────────────────────
    public final AnimationState idleAnimationState = new AnimationState();

    public final AnimationState flapAnimationState = new AnimationState();

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlapping(), this.tickCount);
        this.flapAnimationState.animateWhen(this.isFlapping(), this.tickCount);
    }

    //──────────────────────────────────── Misc ────────────────────────────────────
    @Override
    public int getMaxHeadXRot() {
        return 40;
    }

    @Override
    public int getMaxHeadYRot() {
        return 15;
    }

    public static boolean checkDuckSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() >= level.getSeaLevel() - 10
                && isNearWater(level, pos, 5, 2);
    }

    @SuppressWarnings("all")
    protected static boolean isNearWater(LevelAccessor level, BlockPos pos, int lateralDistance, int verticalDistance) {
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();

        for (int dx = -lateralDistance; dx <= lateralDistance; dx++) {
            for (int dz = -lateralDistance; dz <= lateralDistance; dz++) {
                for (int dy = -verticalDistance; dy <= verticalDistance; dy++) {
                    check.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);

                    // Only check if the chunk is actually loaded
                    if (level.isAreaLoaded(check, 1)) {
                        if (level.getFluidState(check).is(FluidTags.WATER)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected float getWaterSlowDown() {
        return super.getWaterSlowDown();
    }

    public boolean refuseToMove() {
        return hasPose(Pose.SITTING);
    }
}
