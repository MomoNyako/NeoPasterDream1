package com.pasterdream.pasterdreammod.client;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.client.model.Modelslime;
import com.pasterdream.pasterdreammod.client.particle.LifeCrystalParticle;
import com.pasterdream.pasterdreammod.client.renderer.block.DreamAccumulatorBlockRenderer;
import com.pasterdream.pasterdreammod.client.renderer.block.LifeCrystalBlockRenderer;
import com.pasterdream.pasterdreammod.client.renderer.block.ShadowChestBlockRenderer;
import com.pasterdream.pasterdreammod.client.renderer.entity.PinkSlimeRenderer;
import com.pasterdream.pasterdreammod.client.renderer.entity.ShadowGolemRenderer;
import com.pasterdream.pasterdreammod.client.screen.ShadowChestScreen;
import com.pasterdream.pasterdreammod.client.tank.ClientTankEvents;
import com.pasterdream.pasterdreammod.registry.PDBlockEntities;
import com.pasterdream.pasterdreammod.registry.PDEntities;
import com.pasterdream.pasterdreammod.registry.PDMenus;
import com.pasterdream.pasterdreammod.registry.PDParticles;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * 客户端设置类
 * 负责注册客户端特有的渲染器和事件处理
 *
 * 注意：此类仅在客户端加载（Dist.CLIENT）
 */
@EventBusSubscriber(modid = PasterDreamMod.MOD_ID, value = Dist.CLIENT)
public class ClientSetup {

    /**
     * 注册渲染器
     * 在 EntityRenderersEvent.RegisterRenderers 事件时调用
     *
     * @param event 渲染器注册事件
     */
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // 注册蓄梦池方块实体渲染器
        event.registerBlockEntityRenderer(
                PDBlockEntities.DREAM_ACCUMULATOR.get(),
                context -> new DreamAccumulatorBlockRenderer()
        );

        // 注册生命水晶方块实体渲染器
        event.registerBlockEntityRenderer(
                PDBlockEntities.LIFE_CRYSTAL.get(),
                LifeCrystalBlockRenderer::new
        );

        // 注册影之箱方块实体渲染器
        event.registerBlockEntityRenderer(
                PDBlockEntities.SHADOW_CHEST.get(),
                ShadowChestBlockRenderer::new
        );

        // 注册暗影魔像实体渲染器
        event.registerEntityRenderer(
                PDEntities.SHADOW_GOLEM.get(),
                ShadowGolemRenderer::new
        );

        // 注册粉色史莱姆实体渲染器
        event.registerEntityRenderer(
                PDEntities.PINK_SLIME.get(),
                PinkSlimeRenderer::new
        );
    }

    /**
     * 注册模型层
     * 在 EntityRenderersEvent.RegisterLayerDefinitions 事件时调用
     *
     * @param event 模型层注册事件
     */
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelslime.LAYER_LOCATION, Modelslime::createBodyLayer);
    }

    /**
     * 注册 GUI 屏幕
     * 在 RegisterMenuScreensEvent 事件时调用
     *
     * @param event 菜单屏幕注册事件
     */
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(PDMenus.SHADOW_CHEST.get(), ShadowChestScreen::new);
    }

    /**
     * 注册粒子提供器
     * 在 RegisterParticleProvidersEvent 事件时调用
     *
     * @param event 粒子提供器注册事件
     */
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PDParticles.MELTDREAM_CRYSTAL_PARTICLE.get(), LifeCrystalParticle.Provider::new);
    }

    /**
     * 注册染梦世界维度特殊效果（天空、雾色）
     * 对应 dimension_type JSON 中的 "effects": "pasterdream:dyedream_world"
     */
    @SubscribeEvent
    public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
        event.register(
                ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world"),
                new DimensionSpecialEffects(
                        192.0f,
                        true,
                        DimensionSpecialEffects.SkyType.NORMAL,
                        false,
                        false
                ) {
                    @Override
                    public Vec3 getBrightnessDependentFogColor(
                            Vec3 fogColor, float sunHeight) {
                        return fogColor.multiply(sunHeight * 0.94 + 0.06,
                                sunHeight * 0.94 + 0.06,
                                sunHeight * 0.91 + 0.09);
                    }

                    @Override
                    public boolean isFoggyAt(int x, int y) {
                        return false;
                    }
                }
        );
    }

    /**
     * 客户端初始化
     * 在 FMLClientSetupEvent 时注册自定义 HUD 覆盖层事件监听器
     * <p>
     * RenderGuiEvent 走 NeoForge 事件总线而非 Mod 事件总线，
     * 因此需要在此处手动注册到 NeoForge.EVENT_BUS
     *
     * @param event 客户端设置事件
     */
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(ClientTankEvents::onRenderGuiPost);
    }
}
