package com.m249.entities.projectiles;

import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EntityCustomBullet extends AbstractHurtingProjectile {
    public EntityCustomBullet(EntityType<EntityCustomBullet> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }
    @Override

}