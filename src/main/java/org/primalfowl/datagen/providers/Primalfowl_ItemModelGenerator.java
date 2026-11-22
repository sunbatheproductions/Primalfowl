package org.primalfowl.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.registry.Primalfowl_Items;

import java.util.Objects;

public class Primalfowl_ItemModelGenerator extends ItemModelProvider {

    public Primalfowl_ItemModelGenerator(final PackOutput output, final ExistingFileHelper existingFileHelper) {
        super(output, Primalfowl_Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        eggItem(Primalfowl_Items.DUCK_SPAWN_EGG.get());
        basicItem(Primalfowl_Items.RAW_DUCK.get());
        basicItem(Primalfowl_Items.COOKED_DUCK.get());

        eggItem(Primalfowl_Items.GOOSE_SPAWN_EGG.get());
        basicItem(Primalfowl_Items.RAW_GOOSE.get());
        basicItem(Primalfowl_Items.COOKED_GOOSE.get());
    }

    protected void eggItem(final Item eggItem) {
        getBuilder(
                Objects.requireNonNull(Primalfowl_Registries.ITEMS.getRegistry().get().getKey(eggItem)).toString())
                .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}
