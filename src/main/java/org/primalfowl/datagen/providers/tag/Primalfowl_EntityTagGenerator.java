package org.primalfowl.datagen.providers.tag;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_Tags;

import java.util.concurrent.CompletableFuture;

public class Primalfowl_EntityTagGenerator extends EntityTypeTagsProvider {

    public Primalfowl_EntityTagGenerator(PackOutput output, CompletableFuture<Provider> provider,
                                     @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, Primalfowl_Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(@NotNull Provider provider) {
        this.tag(Primalfowl_Tags.Entity.GOOSE_HUNTABLE)
                .add(EntityType.PLAYER)
                .add(EntityType.VILLAGER, EntityType.WANDERING_TRADER)
                .addTag(EntityTypeTags.ILLAGER);

        this.tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                .add(Primalfowl_Entities.DUCK.get(),
                        Primalfowl_Entities.GOOSE.get());
    }
}
