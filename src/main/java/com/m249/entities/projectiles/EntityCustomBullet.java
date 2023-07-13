package com.m249.entities.projectiles;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
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
    public void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_20139_) {

    }
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
