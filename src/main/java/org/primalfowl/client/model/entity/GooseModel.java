package org.primalfowl.client.model.entity;

import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.client.animation.entity.DuckAnimations;
import org.primalfowl.entity.animal.GooseEntity;
import org.primalfowl.util.Primalfowl_Util;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public abstract class GooseModel<T extends GooseEntity> extends AgeableHierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart head;

    protected GooseModel(ModelPart root, float youngScaleFactor, float bodyYOffset) {
        super(youngScaleFactor, bodyYOffset);
        this.root = root;
        ModelPart bodyRoot = root.getChild("root");
        ModelPart body = bodyRoot.hasChild("goose") ? bodyRoot.getChild("goose") : bodyRoot.getChild("gosling");
        ModelPart neckLower = body.getChild("neck_lower");
        ModelPart neckUpper = neckLower.getChild("neck_upper");
        this.head = neckUpper.getChild("head");
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        //──────────────────────────────────── Head Rotations ────────────────────────────────────
        this.head.xRot = Mth.clamp(headPitch, -entity.getMaxHeadXRot(), entity.getMaxHeadXRot()) * (float) (Math.PI / 180.0);
        this.head.yRot = Mth.clamp(netHeadYaw, -entity.getMaxHeadYRot(), entity.getMaxHeadYRot()) * (float) (Math.PI / 180.0);

        //──────────────────────────────────── Animations ────────────────────────────────────
        if (entity.isSwimmingOnWater()) {
            this.animate(entity.swimAnimationState, DuckAnimations.SWIM, ageInTicks,
                    Primalfowl_Util.Visuals.getSwimFactor(entity, 15f, 0.3f, 4f));
        } else if (limbSwingAmount > 0.01f) {
            this.animateWalk(DuckAnimations.WALK, limbSwing, limbSwingAmount, 2.5f, 3.0f);
        } else {
            this.animate(entity.idleAnimationState, DuckAnimations.IDLE, ageInTicks, 1.0f);
        }
    }

    public static class Adult<T extends GooseEntity> extends GooseModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, "goose"), "main");

        private final ModelPart bodyRoot;
        private final ModelPart goose;
        private final ModelPart tail;
        private final ModelPart wing_right;
        private final ModelPart wing_left;
        private final ModelPart neck_lower;
        private final ModelPart neck_upper;
        private final ModelPart leg_right;
        private final ModelPart leg_left;

        public Adult(ModelPart root) {
            super(root, 0.5f, 24.0f);
            this.bodyRoot = root.getChild("root");
            this.goose = this.bodyRoot.getChild("goose");
            this.tail = this.goose.getChild("tail");
            this.wing_right = this.goose.getChild("wing_right");
            this.wing_left = this.goose.getChild("wing_left");
            this.neck_lower = this.goose.getChild("neck_lower");
            this.neck_upper = this.neck_lower.getChild("neck_upper");
            this.leg_right = this.bodyRoot.getChild("leg_right");
            this.leg_left = this.bodyRoot.getChild("leg_left");
        }

        public static LayerDefinition createLayer() {
            return buildGooseLayer("goose");
        }
    }

    public static class Baby<T extends GooseEntity> extends GooseModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
                ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, "gosling"), "main");

        private final ModelPart bodyRoot;
        private final ModelPart gosling;
        private final ModelPart tail;
        private final ModelPart wing_right;
        private final ModelPart wing_left;
        private final ModelPart neck_lower;
        private final ModelPart neck_upper;
        private final ModelPart leg_right;
        private final ModelPart leg_left;

        public Baby(ModelPart root) {
            super(root, 0.5f, 24.0f);
            this.bodyRoot = root.getChild("root");
            this.gosling = this.bodyRoot.getChild("gosling");
            this.tail = this.gosling.getChild("tail");
            this.wing_right = this.gosling.getChild("wing_right");
            this.wing_left = this.gosling.getChild("wing_left");
            this.neck_lower = this.gosling.getChild("neck_lower");
            this.neck_upper = this.neck_lower.getChild("neck_upper");
            this.leg_right = this.bodyRoot.getChild("leg_right");
            this.leg_left = this.bodyRoot.getChild("leg_left");
        }

        public static LayerDefinition createLayer() {
            return buildGooseLayer("gosling");
        }
    }

    private static LayerDefinition buildGooseLayer(String bodyName) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild(bodyName, CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-3.5F, -2.5F, -5.0F, 7.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 17).addBox(-3.5F, -3.5F, -4.0F, 7.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 27).addBox(-2.5125F, -2.0F, -6.7F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 6.0F));

        tail.addOrReplaceChild("tail_r1", CubeListBuilder.create()
                        .texOffs(0, 27).addBox(-2.5F, -0.5F, -2.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 3.425F, 0.0F, -0.5672F, 0.0F, 0.0F));

        body.addOrReplaceChild("wing_right", CubeListBuilder.create()
                        .texOffs(30, 33).addBox(-1.0F, 1.5F, 3.5F, 0.0F, 4.5F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 6).addBox(-1.0F, 1.5F, -0.5F, 1.0F, 3.5F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, -3.5F, -3.0F));

        body.addOrReplaceChild("wing_left", CubeListBuilder.create()
                        .texOffs(10, 34).addBox(0.0F, 2.0F, -0.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 34).addBox(1.0F, 1.5F, 3.5F, 0.0F, 4.5F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.5F, -3.5F, -3.0F));

        PartDefinition neckLower = body.addOrReplaceChild("neck_lower", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -2.5F, -4.5F, 0.2094F, 0.0F, 0.0F));

        PartDefinition neckUpper = neckLower.addOrReplaceChild("neck_upper", CubeListBuilder.create()
                        .texOffs(20, 33).addBox(-1.0F, -2.5F, -0.8F, 2.0F, 8.5F, 3.05F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, -0.2094F, 0.0F, 0.0F));

        neckUpper.addOrReplaceChild("head", CubeListBuilder.create()
                        .texOffs(32, 17).addBox(-1.5F, -2.0F, -1.55F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 0).addBox(-1.0F, 0.0F, -5.55F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 25).addBox(-0.75F, -0.5F, -2.55F, 1.5F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -4.0F, 0.0F));

        root.addOrReplaceChild("leg_right", CubeListBuilder.create()
                        .texOffs(40, 28).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 13).addBox(-1.5F, 5.0F, -2.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -5.0F, 0.5F));

        root.addOrReplaceChild("leg_left", CubeListBuilder.create()
                        .texOffs(40, 34).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 25).addBox(-1.5F, 5.0F, -2.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, -5.0F, 0.5F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}