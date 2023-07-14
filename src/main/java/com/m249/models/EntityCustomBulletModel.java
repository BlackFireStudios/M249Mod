package com.m249.models;

import com.m249.M249Mod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class EntityCustomBulletModel extends DefaultedEntityGeoModel{

    public EntityCustomBulletModel() {
        super(new ResourceLocation(M249Mod.MODID,"bullet"));
    }
}
