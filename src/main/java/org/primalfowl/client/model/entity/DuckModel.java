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
import org.primal.util.Primal_Util;
import org.primalfowl.Primalfowl_Main;
import org.primalfowl.client.animation.entity.DuckAnimations;
import org.primalfowl.entity.animal.DuckEntity;
import org.primalfowl.util.Primalfowl_Util;

@SuppressWarnings("FieldCanBeLocal, unused")
@OnlyIn(Dist.CLIENT)
public abstract class DuckModel<T extends DuckEntity> extends AgeableHierarchicalModel<T> {

    public DuckModel(float youngScaleFactor, float bodyYOffset) {
        super(youngScaleFactor, bodyYOffset);
    }

    public static class Adult<T extends DuckEntity> extends DuckModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, "duck"), "main");
        private final ModelPart root;
        private final ModelPart duck;
        private final ModelPart tail;
        private final ModelPart wing_right;
        private final ModelPart wing_left;
        private final ModelPart neck;
        private final ModelPart head;
        private final ModelPart leg_right;
        private final ModelPart leg_left;

        public Adult(ModelPart root) {
            super(1.0f, 0.0F);
            this.root = root.getChild("root");
            this.duck = this.root.getChild("duck");
            this.tail = this.duck.getChild("tail");
            this.wing_right = this.duck.getChild("wing_right");
            this.wing_left = this.duck.getChild("wing_left");
            this.neck = this.duck.getChild("neck");
            this.head = this.neck.getChild("head");
            this.leg_right = this.root.getChild("leg_right");
            this.leg_left = this.root.getChild("leg_left");
        }

        public static LayerDefinition createLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition duck = root.addOrReplaceChild("duck", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 22).addBox(-3.0F, -2.5F, -5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

            PartDefinition tail = duck.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 5.0F));

            PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(16, 14).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

            PartDefinition wing_right = duck.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(30, 10).addBox(-1.0F, 0.5F, 2.4F, 0.0F, 3.5F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 27).addBox(-1.0F, 0.5F, -1.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.5F, -2.0F));

            PartDefinition wing_left = duck.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.5F, -1.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(10, 32).addBox(1.0F, 0.4F, 2.5F, 0.0F, 3.5F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.5F, -2.0F));

            PartDefinition neck = duck.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(16, 19).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.0F));

            PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 25).addBox(-2.0F, -4.25F, 2.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 14).addBox(-2.0F, -4.25F, -2.0F, 4.0F, 4.25F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(14, 26).addBox(-1.5F, -2.99F, -5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

            PartDefinition leg_right = root.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(10, 27).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(30, 5).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(28, 19).addBox(-1.5F, 3.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -3.0F, 0.5F));

            PartDefinition leg_left = root.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(16, 32).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(28, 22).addBox(-1.5F, 3.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, 0.5F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);

            //──────────────────────────────────── Head Rotations ────────────────────────────────────
            this.head.xRot = Mth.clamp(headPitch, -entity.getMaxHeadXRot(), entity.getMaxHeadXRot()) * (float) (Math.PI / 180.0);
            this.head.yRot = Mth.clamp(netHeadYaw, -entity.getMaxHeadYRot(), entity.getMaxHeadYRot()) * (float) (Math.PI / 180.0);

            //──────────────────────────────────── Movement ────────────────────────────────────
            if(entity.isInWaterOrBubble())
                this.animate(entity.idleAnimationState, DuckAnimations.SWIM, ageInTicks, Primalfowl_Util.Visuals.getSwimFactor(entity, 15f, 0.3f, 4f));
            else
                this.animate(entity.idleAnimationState, DuckAnimations.WALK, ageInTicks, 1.0f);

            //──────────────────────────────────── Idle ────────────────────────────────────
            if(entity.isInWaterOrBubble())
                this.animate(entity.idleAnimationState, DuckAnimations.SWIM, ageInTicks, Primalfowl_Util.Visuals.getSwimFactor(entity, 15f, 0.3f, 4f));
            else
                this.animate(entity.idleAnimationState, DuckAnimations.IDLE, ageInTicks, 1.0f);

        }

        @Override
        public @NotNull ModelPart root() {
            return this.root;
        }
    }

    public static class Baby<T extends DuckEntity> extends DuckModel<T> {
        public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Primalfowl_Main.MOD_ID, "duckling"), "main");
        private final ModelPart root;
        private final ModelPart duckling;
        private final ModelPart tail;
        private final ModelPart wing_right;
        private final ModelPart wing_left;
        private final ModelPart neck;
        private final ModelPart head;
        private final ModelPart leg_right;
        private final ModelPart leg_left;

        public Baby(ModelPart root) {
            super(1.0f, 0.0F);
            this.root = root.getChild("root");
            this.duckling = this.root.getChild("duckling");
            this.tail = this.duckling.getChild("tail");
            this.wing_right = this.duckling.getChild("wing_right");
            this.wing_left = this.duckling.getChild("wing_left");
            this.neck = this.duckling.getChild("neck");
            this.head = this.neck.getChild("head");
            this.leg_right = this.root.getChild("leg_right");
            this.leg_left = this.root.getChild("leg_left");
        }

        public static LayerDefinition createLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition duckling = root.addOrReplaceChild("duckling", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -4.0F, 6.0F, 5.0F, 9.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 22).addBox(-3.0F, -2.5F, -5.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

            PartDefinition tail = duckling.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 5.0F));

            PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(16, 14).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

            PartDefinition wing_right = duckling.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(30, 10).addBox(-1.0F, 0.5F, 2.4F, 0.0F, 3.5F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 27).addBox(-1.0F, 0.5F, -1.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -2.5F, -2.0F));

            PartDefinition wing_left = duckling.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(26, 26).addBox(0.0F, 0.5F, -1.5F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(10, 32).addBox(1.0F, 0.4F, 2.5F, 0.0F, 3.5F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -2.5F, -2.0F));

            PartDefinition neck = duckling.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(16, 19).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.0F));

            PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 25).addBox(-2.0F, -4.25F, 2.0F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 14).addBox(-2.0F, -4.25F, -2.0F, 4.0F, 4.25F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(14, 26).addBox(-1.5F, -2.99F, -5.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

            PartDefinition leg_right = root.addOrReplaceChild("leg_right", CubeListBuilder.create().texOffs(10, 27).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(30, 5).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(28, 19).addBox(-1.5F, 3.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -3.0F, 0.5F));

            PartDefinition leg_left = root.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(30, 0).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                    .texOffs(16, 32).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                    .texOffs(28, 22).addBox(-1.5F, 3.0F, -3.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -3.0F, 0.5F));

            return LayerDefinition.create(meshdefinition, 64, 64);
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

        @Override
        public @NotNull ModelPart root() {
            return this.root;
        }
    }
}