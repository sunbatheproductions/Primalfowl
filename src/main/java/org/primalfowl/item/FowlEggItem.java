package org.primalfowl.item;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FowlEggItem extends Item implements ProjectileItem {

    private final BiFunction<Level, LivingEntity, ? extends ThrowableItemProjectile> fromOwner;
    private final ProjectileCreator fromPos;

    @FunctionalInterface
    public interface ProjectileCreator {
        ThrowableItemProjectile create(Level level, double x, double y, double z);
    }

    public FowlEggItem(
            BiFunction<Level, LivingEntity, ? extends ThrowableItemProjectile> fromOwner,
            ProjectileCreator fromPos,
            Properties properties
    ) {
        super(properties);
        this.fromOwner = fromOwner;
        this.fromPos = fromPos;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.EGG_THROW,
                SoundSource.PLAYERS,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );

        if (!level.isClientSide) {
            ThrowableItemProjectile egg = this.fromOwner.apply(level, player);
            egg.setItem(stack);
            egg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(egg);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        stack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack, @NotNull Direction direction) {
        ThrowableItemProjectile egg = this.fromPos.create(level, pos.x(), pos.y(), pos.z());
        egg.setItem(stack);
        return egg;
    }
}