package org.primalfowl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.primalfowl.client.model.entity.DuckModel;
import org.primalfowl.client.model.entity.GooseModel;
import org.primalfowl.client.renderer.entity.DuckRenderer;
import org.primalfowl.client.renderer.entity.GooseRenderer;
import org.primalfowl.registry.Primalfowl_Entities;
import org.primalfowl.registry.Primalfowl_Items;

@EventBusSubscriber(modid = Primalfowl_Main.MOD_ID, value = Dist.CLIENT)
@Mod(value = Primalfowl_Main.MOD_ID, dist = Dist.CLIENT)
public class Primalfowl_Client {

    @SubscribeEvent
    public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DuckModel.Adult.LAYER_LOCATION, DuckModel.Adult::createLayer);
        event.registerLayerDefinition(DuckModel.Baby.LAYER_LOCATION, DuckModel.Baby::createLayer);

        event.registerLayerDefinition(GooseModel.Adult.LAYER_LOCATION, GooseModel.Adult::createLayer);
        event.registerLayerDefinition(GooseModel.Baby.LAYER_LOCATION, GooseModel.Baby::createLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Primalfowl_Entities.DUCK.get(),      DuckRenderer::new);
        event.registerEntityRenderer(Primalfowl_Entities.GOOSE.get(),     GooseRenderer::new);

        event.registerEntityRenderer(Primalfowl_Entities.THROWN_DUCK_EGG.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(Primalfowl_Entities.THROWN_GOOSE_EGG.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerColorItems(final RegisterColorHandlersEvent.Item event){
        registerEgg(event, Primalfowl_Items.DUCK_SPAWN_EGG.get());
        registerEgg(event, Primalfowl_Items.GOOSE_SPAWN_EGG.get());
    }

    private static void registerEgg(final RegisterColorHandlersEvent.Item event, Item egg){
        event.register((stack, tintIndex) ->
                //Disables the tint if modern eggs texture pack is active
                Minecraft.getInstance().getResourceManager().listPacks()
                        .anyMatch(packResources -> packResources.packId().equals("mod/primalfowl:resourcepacks/modern_spawn_eggs"))?
                        -1
                        : FastColor.ARGB32.opaque(((SpawnEggItem) (egg)).getColor(tintIndex)), egg);
    }

}
