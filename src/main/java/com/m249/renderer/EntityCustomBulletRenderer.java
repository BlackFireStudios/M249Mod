package com.m249.renderer;

import com.m249.entities.projectiles.EntityCustomBullet;
import com.m249.models.EntityCustomBulletModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class EntityCustomBulletRenderer extends GeoEntityRenderer<EntityCustomBullet> {

    public EntityCustomBulletRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EntityCustomBulletModel());
    }
    @Override
    public void preRender(PoseStack poseStack, EntityCustomBullet animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
                          float alpha) {
        RenderUtils.faceRotation(poseStack, animatable, partialTick);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
