package com.m249.renderer;

import com.m249.entities.projectiles.EntityCustomBullet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;

public class EntityCustomBulletRenderer extends EntityRenderer<EntityCustomBullet> {
    protected EntityCustomBulletRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCustomBullet p_114482_) {
        return new ResourceLocation("");
    }
}
