package org.primalfowl.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.Primalfowl_Registries;
import org.primalfowl.entity.misc.DuckEgg;
import org.primalfowl.entity.misc.GooseEgg;
import org.primalfowl.item.FowlEggItem;

import java.util.function.Supplier;

public class Primalfowl_Items {

    public static DeferredHolder<Item, Item> PRIMALFOWL;

    //Duck
    public static DeferredHolder<Item, Item> DUCK_SPAWN_EGG;
    public static DeferredHolder<Item, Item> DUCK_EGG;
    public static DeferredHolder<Item, Item> RAW_DUCK;
    public static DeferredHolder<Item, Item> COOKED_DUCK;

    //Goose
    public static DeferredHolder<Item, Item> GOOSE_SPAWN_EGG;
    public static DeferredHolder<Item, Item> GOOSE_EGG;
    public static DeferredHolder<Item, Item> RAW_GOOSE;
    public static DeferredHolder<Item, Item> COOKED_GOOSE;

    public static void initItems() {
        PRIMALFOWL = register("primalfowl",
                () -> new Item(new Item.Properties().stacksTo(1)));

        //Spawn eggs
        {
            DUCK_SPAWN_EGG = register("duck_spawn_egg",
                    () -> new DeferredSpawnEggItem(Primalfowl_Entities.DUCK, 0x36924d, 0xfffff0, new Item.Properties()));

            GOOSE_SPAWN_EGG = register("goose_spawn_egg",
                    () -> new DeferredSpawnEggItem(Primalfowl_Entities.GOOSE, 0xd3dedd, 0xfffff0, new Item.Properties()));
        }
        //Food
        {
            RAW_DUCK = register("raw_duck",
                    () -> new Item(new Item.Properties().food(Primalfowl_Food.RAW_DUCK)));

            COOKED_DUCK = register("cooked_duck",
                    () -> new Item(new Item.Properties().food(Primalfowl_Food.COOKED_DUCK)));

            RAW_GOOSE = register("raw_goose",
                    () -> new Item(new Item.Properties().food(Primalfowl_Food.RAW_GOOSE)));

            COOKED_GOOSE = register("cooked_goose",
                    () -> new Item(new Item.Properties().food(Primalfowl_Food.COOKED_GOOSE)));
        }
        //Eggs
        {
            DUCK_EGG = register("duck_egg",
                    () -> new FowlEggItem(DuckEgg::new, DuckEgg::new, new Item.Properties().stacksTo(16)));

            GOOSE_EGG = register("goose_egg",
                    () -> new FowlEggItem(GooseEgg::new, GooseEgg::new, new Item.Properties().stacksTo(16)));
        }
    }

    public static void initGroups(){
        Primalfowl_Registries.CREATIVE_MODE_TABS.register(
                "item_group." + Primalfowl_Main.MOD_ID,
                () -> CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup." + Primalfowl_Main.MOD_ID))
                        .icon(() -> new ItemStack(Items.EGG))
                        .displayItems((params, output) -> {
                            // ───────── Spawn Eggs ─────────
                            output.accept(DUCK_SPAWN_EGG.get());
                            output.accept(RAW_DUCK.get());
                            output.accept(COOKED_DUCK.get());
                            output.accept(DUCK_EGG.get());
                            output.accept(GOOSE_SPAWN_EGG.get());
                            output.accept(RAW_GOOSE.get());
                            output.accept(COOKED_GOOSE.get());
                            output.accept(GOOSE_EGG.get());
                        })
                        .build()
        );
    }

    public static DeferredHolder<Item, Item> register(final String name, final Supplier<Item> item) {
        return Primalfowl_Registries.ITEMS.register(name, item);
    }
}