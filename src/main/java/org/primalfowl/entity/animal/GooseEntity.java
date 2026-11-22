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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.Unit;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primal.entity.ai.controls.look.WaterOrLandLookControl;
import org.primal.entity.ai.controls.move.WaterOrLandMoveControl;
import org.primal.util.Primal_Util;
import org.primal.util.mob_types.AttackVillagers;
import org.primal.util.mob_types.DetectsFartherAway;
import org.primalfowl.entity.ai.GooseAi;
import org.primalfowl.entity.ai.controls.navigation.DuckPathNavigation;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_Items;
import org.primalfowl.registry.Primalfowl_Sounds;
import org.primalfowl.registry.Primalfowl_Tags;
import org.primalfowl.util.Primalfowl_Util;

import java.util.UUID;
import java.util.function.IntFunction;

public class GooseEntity extends Animal implements VariantHolder<GooseEntity.Variant>, NeutralMob, DetectsFartherAway, AttackVillagers {
    public int eggTime = this.random.nextInt(6000) + 6000;

    //──────────────────────────────────── Variants ────────────────────────────────────
    public enum Variant implements StringRepresentable {
        WHITE(0, "white"),
        CANADA(1, "canada");

        public static final Codec<GooseEntity.Variant> CODEC = StringRepresentable.fromEnum(GooseEntity.Variant::values);

        private static final IntFunction<GooseEntity.Variant> BY_ID = ByIdMap.continuous(GooseEntity.Variant::getId, values(),
                ByIdMap.OutOfBoundsStrategy.CLAMP);

        public static GooseEntity.Variant byId(int id) {
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
    public void setVariant(@NotNull GooseEntity.Variant variant) {
        this.entityData.set(DATA_VARIANT_ID, variant.id);
    }

    @Override
    public @NotNull GooseEntity.Variant getVariant() {
        return GooseEntity.Variant.byId(this.entityData.get(DATA_VARIANT_ID));
    }

    public void setVariantFromBiome(GooseEntity animal, Holder<Biome> holder) {
        if (holder.is(Primalfowl_Tags.Biome.SPAWNS_CANADA_GOOSE)) {
            animal.setVariant(GooseEntity.Variant.CANADA);
        } else {
            animal.setVariant(GooseEntity.Variant.WHITE);
        }
    }

    //──────────────────────────────────── Init ────────────────────────────────────
    public GooseEntity(EntityType<GooseEntity> entityType, Level level) {
        super(entityType, level);
        this.getNavigation().setCanFloat(true);
        this.moveControl = new WaterOrLandMoveControl<>(this, 85, 50, 0.58f, 1.0f, false, goose -> !goose.refuseToMove());
        this.lookControl = new WaterOrLandLookControl<>(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6f)
                .add(Attributes.MOVEMENT_SPEED, 0.21f)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.FOLLOW_RANGE, 48.0F);
    }

    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(GooseEntity.class, EntityDataSerializers.INT);

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, GooseEntity.Variant.WHITE.id);
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
    protected @NotNull Brain.Provider<GooseEntity> brainProvider() {
        return GooseAi.brainProvider();
    }

    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
        return GooseAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull Brain<GooseEntity> getBrain() {
        return (Brain<GooseEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();

        Brain<GooseEntity> brain = this.getBrain();
        brain.tick((ServerLevel) this.level(), this);
        GooseAi.updateActivity(this);

        if (!this.level().isClientSide && this.isAlive() && !this.isBaby() && --this.eggTime <= 0) {
            this.playSound(Primalfowl_Sounds.GOOSE_PLOP.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Primalfowl_Items.GOOSE_EGG.get());
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }

    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new DuckPathNavigation(this, level);
    }

    @Override
    public void setSpeed(float speed) {
        super.setSpeed(speed);
        this.setZza(speed);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.floatOnWater();
    }

    /**
     * Keeps the goose buoyant near the water surface instead of sinking to the bottom.
     */
    protected void floatOnWater() {
        if (this.isInWater()) {
            double fluidHeight = this.getFluidHeight(FluidTags.WATER);
            double submergeThreshold = this.getBbHeight() * 0.45D;

            if (fluidHeight > submergeThreshold || this.isUnderWater()) {
                Vec3 delta = this.getDeltaMovement();
                double targetUpward = this.isUnderWater() ? 0.08D : 0.04D;
                this.setDeltaMovement(delta.x, Math.max(delta.y, targetUpward), delta.z);
                this.hasImpulse = true;
            }
        }
    }

    public boolean isSwimmingOnWater() {
        return this.isInWaterOrBubble();
    }


    // ──────────────────────────────────── Feeding & Interaction ────────────────────────────────────
    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stackInHand = player.getItemInHand(hand);

        if (this.isFood(stackInHand)) {
            boolean wasFed = this.handleEating(player, stackInHand);
            if (wasFed) {
                if (!this.level().isClientSide) {
                    this.usePlayerItem(player, hand, stackInHand);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(player, hand);
    }

    public boolean handleEating(@NotNull Player player, @NotNull ItemStack stack) {
        if (!this.isFood(stack)) {
            return false;
        }

        if (isMatingFood(stack) && this.getAge() == 0 && this.canFallInLove()) {
            if (!this.level().isClientSide) {
                this.setInLove(player);
            }
            return playEatingSound();
        }

        if (this.isBaby()) {
            if (!this.level().isClientSide) {
                int currentAge = this.getAge();
                this.ageUp(getSpeedUpSecondsWhenFeeding(-currentAge), true);
            }
            return playEatingSound();
        }

        return false;
    }

    protected boolean playEatingSound() {
        if (!this.level().isClientSide) {
            this.level().playSound(
                    null,
                    this,
                    SoundEvents.GENERIC_EAT,
                    this.getSoundSource(),
                    1.0F,
                    (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F
            );
        }
        return true;
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.getBrain().hasMemoryValue(MemoryModuleType.IS_PREGNANT);
    }

    @Override
    public boolean isFood(@NotNull ItemStack stack) {
        return isMatingFood(stack);
    }

    public static boolean isMatingFood(@NotNull ItemStack stack) {
        return stack.is(Primalfowl_Tags.Item.GOOSE_BREED_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
        if (otherParent instanceof GooseEntity otherParentCasted)
            return Primal_Util.createFromParents(Primalfowl_Entities.GOOSE.get(),
                    this,
                    otherParentCasted, c -> GooseAi.initMemories(c, level.getRandom()));

        return null;
    }

    @Override
    public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mate) {
        this.finalizeSpawnChildFromBreeding(level, mate, null);
        this.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
    }

    //──────────────────────────────────── Combat ────────────────────────────────────
    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getTargetFromBrain();
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity target) {
        // To stop attacking
        if (target.getType().equals(this.getType()) && !target.isBaby() && target.getHealth() <= target.getMaxHealth() * 0.30) {
            return false;
        }

        if (this.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target) && super.canAttack(target))
            return true;

        return super.canAttack(target) &&
                // Hunts
                (target.getType().is(Primalfowl_Tags.Entity.GOOSE_HUNTABLE))
                && !this.isPacified()
                && Primalfowl_Util.isNotNeverAttack(target, Primalfowl_Tags.Entity.GOOSE_NEVER_ATTACK);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.getEntity() != null && source.getEntity().getType().equals(this.getType()) && this.random.nextBoolean())
            amount = amount + this.getRandom().nextIntBetweenInclusive(1, 2);

        boolean hurt = super.hurt(source, amount);

        if (hurt) {
            if (source.getEntity() instanceof LivingEntity target && !(target instanceof Player player && player.isCreative()))
                Primal_Util.Ai.wasHurtByAndAttacks(this, target, GooseEntity.class, false, true, true);
        }

        if (source.getEntity() != null && source.getEntity().getType().equals(this.getType()) && this.getHealth() < this.getMaxHealth() * 0.31) {
            this.getBrain().eraseMemory(MemoryModuleType.HOME);
            this.level().broadcastEntityEvent(this, (byte) 13);
        }

        return hurt;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        var hurt = super.doHurtTarget(target);
        if (hurt) {
            if (target instanceof LivingEntity livingTarget) {
                // If it was another goose the last hurt, then it's the winner
                if (target.getType().equals(this.getType()) && livingTarget.getHealth() < livingTarget.getMaxHealth() * 0.31) {
                    this.level().broadcastEntityEvent(this, (byte) 14);
                    this.heal(this.getRandom().nextIntBetweenInclusive(4, 6));
                }
            }
        }
        return hurt;
    }

    public boolean isPacified() {
        return this.getBrain().hasMemoryValue(MemoryModuleType.PACIFIED);
    }

    @Override
    protected int getBaseExperienceReward() {
        return this.xpReward;
    }

    //──────────────────────────────────── Sounds ────────────────────────────────────
    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return Primalfowl_Sounds.GOOSE_IDLE.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return Primalfowl_Sounds.GOOSE_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return Primalfowl_Sounds.GOOSE_DEATH.get();
    }

    //──────────────────────────────────── Visuals ────────────────────────────────────
    public final AnimationState idleAnimationState = new AnimationState();

    public final AnimationState swimAnimationState = new AnimationState();

    public final AnimationState flapAnimationState = new AnimationState();

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        boolean swimming = this.isSwimmingOnWater() && !this.isFlapping();
        this.swimAnimationState.animateWhen(swimming, this.tickCount);
        this.idleAnimationState.animateWhen(!swimming && !this.isFlapping(), this.tickCount);
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

    @SuppressWarnings("all")
    protected static boolean isNearWater(LevelAccessor level, BlockPos pos, int lateralDistance, int verticalDistance) {
        BlockPos.MutableBlockPos check = new BlockPos.MutableBlockPos();

        for (int dx = -lateralDistance; dx <= lateralDistance; dx++) {
            for (int dz = -lateralDistance; dz <= lateralDistance; dz++) {
                for (int dy = -verticalDistance; dy <= verticalDistance; dy++) {
                    check.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);

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
        return 0.96F;
    }

    public boolean refuseToMove() {
        return hasPose(Pose.SITTING);
    }

    @Override
    public boolean canBeLeashed() {
        return super.canBeLeashed() && !hasPose(Pose.SITTING);
    }

    @Override
    public boolean isEntityTargetable(LivingEntity attacker, LivingEntity target) {
        return attacker.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
                ? Primal_Util.Ai.getTargetConditions(48, true).test(attacker, target)
                : Primal_Util.Ai.getTargetConditions(48, false).test(attacker, target);
    }

    @Override
    public boolean isEntityAttackable(LivingEntity attacker, LivingEntity target) {
        return attacker.getBrain().isMemoryValue(MemoryModuleType.ATTACK_TARGET, target)
                ? Primal_Util.Ai.getTargetConditions(48, true).test(attacker, target)
                : Primal_Util.Ai.getTargetConditions(48, false).test(attacker, target);
    }

    @Override
    protected @NotNull Vec3 getLeashOffset() {
        return new Vec3(0.0, this.getEyeHeight() * 0.6f, this.getBbWidth() * 0.2F);
    }

    public static boolean checkGooseSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() >= level.getSeaLevel() - 10
                && isNearWater(level, pos, 5, 2);
    }

    @Override
    public float getScale() {
        return this.isBaby() ? 0.5f : 1.0f;
    }

    @Override
    public boolean isTargetingVillager() {
        return this.getTarget() instanceof Villager
                || this.getLastHurtMob() instanceof Villager;
    }

    //──────────────────────────────────── Neutral Behavior ────────────────────────────────────
    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
    }

    @Override
    public void startPersistentAngerTimer() {
    }
}