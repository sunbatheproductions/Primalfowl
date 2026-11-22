package org.primalfowl.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.primalfowl.registry.Primalfowl_Items;

import java.util.concurrent.CompletableFuture;

public class Primalfowl_RecipesGenerator  extends RecipeProvider {

    public Primalfowl_RecipesGenerator(final PackOutput output, final CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    public void buildRecipes(final @NotNull RecipeOutput exporter) {

        createCookFood(exporter, Primalfowl_Items.RAW_DUCK.get(), Primalfowl_Items.COOKED_DUCK.get());
        createCookFood(exporter, Primalfowl_Items.RAW_GOOSE.get(), Primalfowl_Items.COOKED_GOOSE.get());

    }

    protected static void createCookFood(RecipeOutput exporter,ItemLike rawItem, ItemLike cookedItem) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(rawItem), RecipeCategory.FOOD, cookedItem, 0.35F, 200)
                .unlockedBy(getHasName(rawItem), has(rawItem))
                .save(exporter);

        simpleCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100, rawItem, cookedItem, 0.35F);
        simpleCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600, rawItem, cookedItem, 0.35F);
    }

}
