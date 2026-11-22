package org.primalfowl.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.primal.client.renderer.defaulted.MobRendererWithCustomBaby;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.client.model.entity.DuckModel;
import org.primalfowl.entity.animal.DuckEntity;

public class DuckRenderer extends MobRendererWithCustomBaby.WithVariants<DuckEntity, DuckModel<DuckEntity>, DuckEntity.Variant> {

    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context,
                ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, "duck"),
                new DuckModel.Adult<>(context.bakeLayer(DuckModel.Adult.LAYER_LOCATION)),
                new DuckModel.Baby<>(context.bakeLayer(DuckModel.Baby.LAYER_LOCATION)),
                0.65f,
                true, Primalfowl_Main.COMMON_CONFIG.duckBabyCustomModel.get());
    }

    @Override
    protected float getShadowRadius(@NotNull DuckEntity entity) {
        float f = super.getShadowRadius(entity);
        return entity.isBaby() ? f * 0.25F : f;
    }

}
