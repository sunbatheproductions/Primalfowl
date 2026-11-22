package org.primalfowl.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.primal.client.renderer.defaulted.MobRendererWithCustomBaby;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.client.model.entity.GooseModel;
import org.primalfowl.entity.animal.GooseEntity;

public class GooseRenderer extends MobRendererWithCustomBaby.WithVariants<GooseEntity, GooseModel<GooseEntity>, GooseEntity.Variant> {

    public GooseRenderer(EntityRendererProvider.Context context) {
        super(context,
                ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, "goose"),
                new GooseModel.Adult<>(context.bakeLayer(GooseModel.Adult.LAYER_LOCATION)),
                new GooseModel.Baby<>(context.bakeLayer(GooseModel.Baby.LAYER_LOCATION)),
                0.65f,
                true, Primalfowl_Main.COMMON_CONFIG.gooseBabyCustomModel.get());
    }

    @Override
    protected float getShadowRadius(@NotNull GooseEntity entity) {
        float f = super.getShadowRadius(entity);
        return entity.isBaby() ? f * 0.25F : f;
    }

}
