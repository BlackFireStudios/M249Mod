package com.m249.renderer;

import com.m249.M249Mod;
import com.m249.entities.projectiles.EntityCustomBullet;
import com.m249.models.EntityCustomBulletModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;

public class EntityCustomBulletRenderer extends EntityRenderer<EntityCustomBullet> {
    EntityCustomBulletModel<EntityCustomBullet> model;
    public EntityCustomBulletRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        model = new EntityCustomBulletModel<EntityCustomBullet>();
    }
    @Override
    public ResourceLocation getTextureLocation(EntityCustomBullet b) {
        return new ResourceLocation("entity/bullet.png");
    }
    public void render(EntityCustomBullet b, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay){
        pPoseStack.pushPose();
        model.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(b))), pPackedLight,pPackedOverlay, 1,1,1,1);
        pPoseStack.popPose();
    }
}
