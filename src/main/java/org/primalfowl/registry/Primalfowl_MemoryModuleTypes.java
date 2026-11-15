package org.primalfowl.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primal.registry.Primal_Codecs;
import org.primalfowl.Primalfowl_Registries;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Primalfowl_MemoryModuleTypes {

    //Generic
    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_VISIBLE_BABY =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_visible_baby", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> IS_STUNNED =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("animal_stunned", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> LAST_ATTACK_TARGET =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("last_attack_target", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockPos>> NEAREST_IMPORTANT_BLOCK =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_important_block", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_SCARED =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_scared", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> AMOUNT_ATTACKED =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("amount_attacked", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> LUNGE_COOLDOWN =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("lunge_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> WAS_IDLE_ANIMATION =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("was_idle_animation", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> WAS_TRIGGER_ANIMATION =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("was_trigger_animation", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> WAS_TOWARDS_IMPORTANT_BLOCK =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("was_towards_important_block", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> HURT_RECENTLY =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("hurt_recently", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> HAS_INSTRUMENT =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("has_instrument", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> STALK_TARGET =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("stalk_target", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> STALK_COOLDOWN =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("stalk_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_LEADER =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_leader", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_PACK_MEMBER =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_pack_member", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> GO_TO_PACK_MEMBER_COOLDOWN =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("go_to_pack_member_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Map<String, Long>>> CAUTIOUS_LIST =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("cautious_targets_list", () -> new MemoryModuleType<>(Optional.of(Primal_Codecs.CAUTIOUS_LIST)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_CAUTIOUS =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_cautious", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_ATTACKABLE_CAUTIOUS =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_attackable_cautious", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> IS_GRABBING =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("animal_grab", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockPos>> MUSIC_BLOCK =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_music_block", () -> new MemoryModuleType<>(Optional.empty()));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> HOLLOW_LOG_ENTER_COOLDOWN =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("hollow_log_enter_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> MATE_VARIANT =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("mate_variant", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<LivingEntity>> NEAREST_PLAY_MOB =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("nearest_play_mob", () -> new MemoryModuleType<>(Optional.empty()));


    //Note: Need codec to being able to store it inside the nbt
    public static DeferredHolder<MemoryModuleType<?>, MemoryModuleType<List<UUID>>> ATTACKED_LIST =
            Primalfowl_Registries.MEMORY_MODULE_TYPES.register("mobs_attacked", () -> new MemoryModuleType<>(Optional.of(Codec.list(UUIDUtil.CODEC))));

    public static void init() {}
}
