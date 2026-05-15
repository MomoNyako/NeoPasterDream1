package com.pasterdream.pasterdreammod.client.tank;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.capability.MeltDreamEnergyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

/**
 * 染梦能量 HUD 渲染器
 * 在屏幕右上角显示染梦能量条
 * <p>
 * 参考原模组 MeltdreamenergyTank.java，适配 NeoForge 1.21.1 RenderGuiEvent
 * 纹理：textures/screens/pasterdream_hud.png
 * <p>
 * TODO: 集成客户端配置系统（位置偏移、潜行显示等）
 */
public class MeltdreamEnergyTank {

    private static final Minecraft MC = Minecraft.getInstance();

    /**
     * HUD 纹理位置
     */
    public static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath(
            PasterDreamMod.MOD_ID, "textures/screens/pasterdream_hud.png");

    /**
     * 渲染染梦能量条
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
        double energy = MeltDreamEnergyCapability.getEnergy(player);

        MC.getProfiler().push("meltdreamenergy_bar");

        var xBase = screenWidth - 100;
        var yBase = screenHeight - 50;

        // 背景条（80x15，纹理坐标 0,0）
        guiGraphics.blit(ICON, xBase, yBase, 0, 0, 80, 15);
        // 能量填充条（纹理坐标 0,16，宽度根据能量值变化）
        int fillWidth = 11 + Math.round(66.0f / 100 * (float) energy);
        guiGraphics.blit(ICON, xBase, yBase, 0, 16, fillWidth, 15);

        // 潜行时显示数值
        if (player.isShiftKeyDown()) {
            guiGraphics.drawString(MC.font, (int) energy + "/100", xBase + 33, yBase - 5, 0xFFFFFF);
        }

        MC.getProfiler().pop();
    }
}