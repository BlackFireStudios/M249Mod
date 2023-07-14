package com.m249.entities.projectiles;

import com.m249.M249Mod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class EntityCustomBullet extends AbstractHurtingProjectile implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private double force;

    public EntityCustomBullet(EntityType<EntityCustomBullet> entityType, Level level) {
        super(entityType, level);
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

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }
    public void shoot(Entity p,double force){
        this.setOwner(p);
        this.force=force;
        this.setRot(p.getYRot(),p.getXRot());
        this.setDeltaMovement(p.getDeltaMovement().add(p.getLookAngle().multiply(force,force,force)));
    }
    @Override
    public void onHitEntity(EntityHitResult h){
        Entity e  = h.getEntity();
        Vec3 epos = new Vec3(e.getX(),e.getY()+e.getEyeHeight(),e.getZ());
        Vec3 v = this.getDeltaMovement();
        Vec3 p = new Vec3(this.getX(), this.getY(), this.getZ());
        double dis = (epos.subtract(p).cross(v).length())/v.length();
        //M249Mod.LOGGER.debug("Distance:"+dis);
        if(dis<=0.4)h.getEntity().hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(M249Mod.GUNDMG)), (float) (24*(this.getDeltaMovement().length()/force)));
        else h.getEntity().hurt(new DamageSource(this.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(M249Mod.GUNDMG)),(float) (6*(this.getDeltaMovement().length()/force)));
    }
    @Override
    public void onHitBlock(BlockHitResult h){
        Vec3 mid = h.getBlockPos().getCenter();
        this.setDeltaMovement(this.getDeltaMovement().multiply(0.5,0.5,0.5));
        if(this.getDeltaMovement().length()<0.1)this.setDeltaMovement(Vec3.ZERO);
    }
    private double axis(Vec3 in, int a){
        switch(a){
            case 0:
                return in.x;
            case 1:
                return in.y;
            case 2:
                return in.z;
            default:
                return 0;
        }
    }
    private int getLargestAxis(Vec3 in){
        if(in.x>=in.y&&in.x>=in.z) return 0;
        else if (in.y>=in.z&&in.y>=in.x) return 1;
        else return 2;
    }
    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }
            Vec3 vec3 = this.getDeltaMovement();
            this.checkInsideBlocks();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }
    @Override
    public boolean hurt(DamageSource d, float dmg){
        return false;
    }
}
