package com.pasterdream.pasterdreammod.client.tank;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.SanCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

/**
 * San 理智值 HUD 渲染器
 * 在屏幕右上角显示 San 值状态条
 * <p>
 * 参考原模组 SanTank.java，适配 NeoForge 1.21.1 RenderGuiEvent
 * 纹理：textures/screens/pasterdream_hud.png
 * <p>
 * TODO:
 * - 集成客户端配置系统
 * - San 值波动指示器（参考原版 SAN_VARIABILITY 属性）
 * - 低 San 值预警效果
 */
public class SanTank {

    private static final Minecraft MC = Minecraft.getInstance();

    /**
     * HUD 纹理位置
     */
    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/screens/pasterdream_hud.png");

    /**
     * 渲染 San 值条
     * 由 ClientTankEvents 在 RenderGuiEvent.Post 时调用
     *
     * @param guiGraphics  GUI 绘图对象
     * @param screenWidth  屏幕宽度
     * @param screenHeight 屏幕高度
     */
    public static void render(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        if (MC.player == null || MC.options.hideGui) return;
        if (MC.player.getVehicle() instanceof LivingEntity) return;

        var player = MC.player;
        if (!SanCapability.getSanCheck(player)) return;

        MC.getProfiler().push("san_bar");

        var xBase = screenWidth - 100;
        var yBase = screenHeight - 70;

        double sanValue = SanCapability.getSan(player);
        float fillRatio = 20.0f / 100;

        // San 背景条（32x32，纹理坐标 0,32）
        guiGraphics.blit(ICON, xBase, yBase, 0, 32, 32, 32);
        // San 填充条（纹理坐标 0,70，高度根据 San 值变化）
        int fillHeight = 20 - Math.round((float) sanValue * fillRatio);
        guiGraphics.blit(ICON, xBase, yBase + 6, 0, 70, 32, fillHeight);

        // 潜行时显示数值
        if (player.isShiftKeyDown()) {
            guiGraphics.drawString(MC.font, (int) sanValue + "/100", xBase + 4, yBase - 4, 0xFFFFFF);
        }

        MC.getProfiler().pop();
    }
}