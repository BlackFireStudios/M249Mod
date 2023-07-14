package com.m249.items;

import com.m249.M249Mod;
import com.m249.entities.projectiles.EntityCustomBullet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.gametest.BlockPosValueConverter;

public class M249 extends Item {

    public M249(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player p, InteractionHand hand){
        ItemStack i = p.getItemInHand(hand);
        if (i.getDamageValue()<=99) {
            EntityCustomBullet bullet = new EntityCustomBullet(M249Mod.CUSTOM_BULLET.get(), level);
            bullet.setPos(p.getEyePosition());
            bullet.setOwner(p);
            bullet.shoot(p,4);
            level.addFreshEntity(bullet);
            level.playLocalSound(p.getX(), p.getY(),p.getZ(),M249Mod.M249SOUND.get(), SoundSource.PLAYERS,10,10,true);
            if(!p.isCreative()) i.hurt(1, RandomSource.create(),null);
            if (i.getDamageValue()==100){
                ItemStack mag = new ItemStack(M249Mod.M249Magazine.get());
                mag.setDamageValue(100);
                ItemStack cas = new ItemStack(M249Mod.BulletCasing.get());
                cas.setCount(8);
                if(!p.getInventory().add(mag))level.addFreshEntity(new ItemEntity(level, p.getX(),p.getY(),p.getZ(),mag));
                if(!p.getInventory().add(cas))level.addFreshEntity(new ItemEntity(level, p.getX(),p.getY(),p.getZ(),cas));
            }
        }
        return super.use(level,p,hand);
    }
}
