package com.m249.items;

import com.m249.M249Mod;
import com.m249.entities.projectiles.EntityCustomBullet;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class M249 extends Item {

    public M249(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player p, InteractionHand hand){
        if (!Minecraft.getInstance().level.isClientSide) {
            EntityCustomBullet bullet = new EntityCustomBullet(M249Mod.CUSTOM_BULLET, level);
            bullet.setPos(p.getX(), p.getEyeY(), p.getZ());
            bullet.shootFromRotation(p, p.getXRot(), p.getYRot(), 0.0F, 3.0F, 1.0F); ;
            level.addFreshEntity(bullet);
        }
        this.damageItem(1)
        return ;
    }
}
