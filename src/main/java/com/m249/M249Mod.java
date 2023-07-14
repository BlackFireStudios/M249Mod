package com.m249;

import com.m249.entities.projectiles.EntityCustomBullet;
import com.m249.items.M249;
import com.m249.renderer.EntityCustomBulletRenderer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
// The value here should match an entry in the META-INF/mods.toml file
@Mod(M249Mod.MODID)
public class M249Mod
{
    public static boolean hasregistered;
    // Define mod id in a common place for everything to reference
    public static final String MODID = "m249mod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    //Creates a Deferred Register to hold EntityTypes
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE,MODID);
    // Creates a Deferred Register to hold SoundEvents
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, MODID);
    // Creates a new sound for shooting the M249
    public static final RegistryObject<SoundEvent> M249SOUND = SOUNDS.register("m249",()-> SoundEvent.createFixedRangeEvent(new ResourceLocation(MODID, "m249"),200));
    // Creates a new DamageType for guns
    public static final ResourceKey<DamageType> GUNDMG = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MODID,"gun"));
    // Create new Items for use in crafting
    public static final RegistryObject<Item> M249Magazine = ITEMS.register("m249_magazine", () -> new Item(new Item.Properties().durability(100)));
    public static final RegistryObject<Item> BulletCasing = ITEMS.register("bullet_casing", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> Bullet = ITEMS.register("bullet", () -> new Item(new Item.Properties()));
    //creates the M249 item
    public static final RegistryObject<Item> M249 = ITEMS.register("m249", () -> new M249(new Item.Properties().stacksTo(1).durability(100).defaultDurability(100)));
    public static final RegistryObject<EntityType<EntityCustomBullet>> CUSTOM_BULLET = ENTITY_TYPES.register("bullet",
            () -> EntityType.Builder.of(EntityCustomBullet::new, MobCategory.MISC)
                    .sized(0.1F, 0.1F)
                    .fireImmune()
                    .build("m249mod:bullet"));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> M249_TAB = CREATIVE_MODE_TABS.register("m249", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> M249.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(M249.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                output.accept(M249Magazine.get());
                output.accept(Bullet.get());
                output.accept(BulletCasing.get());
            }).build());
    public M249Mod()
    {
        hasregistered = false;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
        ENTITY_TYPES.register(modEventBus);
        SOUNDS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, M249Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        M249Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
            event.accept(M249);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(CUSTOM_BULLET.get(), EntityCustomBulletRenderer::new);
        }
    }
}
