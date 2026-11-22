package org.primalfowl.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.Level;
import org.primalfowl.entity.animal.DuckEntity;
import org.primalfowl.entity.animal.GooseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fox.class)
public class FoxMixin extends Mob {

    protected FoxMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("RETURN"))
    private void registerGoals(CallbackInfo ci) {
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((Fox) (Object) this, DuckEntity.class, 10,
                true, true, null));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>((Fox) (Object) this, GooseEntity.class, 10,
                true, true, LivingEntity::isBaby));
    }
}
