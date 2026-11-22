package org.primalfowl.datagen.providers.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.registry.Primalfowl_Items;
import org.primalfowl.registry.Primalfowl_Tags;

import java.util.concurrent.CompletableFuture;

public class Primalfowl_ItemTagsGenerator extends ItemTagsProvider {

    public Primalfowl_ItemTagsGenerator(final PackOutput arg, final CompletableFuture<HolderLookup.Provider> completableFuture, final CompletableFuture<TagLookup<Block>> completableFuture2, @Nullable final ExistingFileHelper existingFileHelper) {
        super(arg, completableFuture, completableFuture2, Primalfowl_Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(final HolderLookup.@NotNull Provider lookup) {
        this.tag(Tags.Items.EGGS)
                .add(Primalfowl_Items.DUCK_EGG.get())
                .add(Primalfowl_Items.GOOSE_EGG.get());

        this.tag(Primalfowl_Tags.Item.DUCK_BREED_FOOD)
                .add(Items.WHEAT_SEEDS)
                .add(Items.MELON_SEEDS)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.PUMPKIN_SEEDS)
                .add(Items.TORCHFLOWER_SEEDS);

        this.tag(Primalfowl_Tags.Item.GOOSE_BREED_FOOD)
                .add(Items.WHEAT_SEEDS)
                .add(Items.MELON_SEEDS)
                .add(Items.BEETROOT_SEEDS)
                .add(Items.PUMPKIN_SEEDS)
                .add(Items.TORCHFLOWER_SEEDS);
    }
}
