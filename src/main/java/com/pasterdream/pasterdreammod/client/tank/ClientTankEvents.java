package com.pasterdream.pasterdreammod.client.tank;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

/**
 * 客户端 HUD 事件监听器
 * 在游戏渲染 GUI 时绘制自定义覆盖层（染梦能量条 + San 理智值条）
 * <p>
 * 不使用废弃的 @EventBusSubscriber，改为在 PasterDreamMod 构造器中手动注册
 */
public class ClientTankEvents {

    /**
     * GUI 渲染完成后的回调
     * 在此处绘制自定义 HUD 覆盖层
     *
     * @param event GUI 渲染事件
     */
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        var guiGraphics = event.getGuiGraphics();
        var mc = net.minecraft.client.Minecraft.getInstance();
        var window = mc.getWindow();

        MeltdreamEnergyTank.render(guiGraphics, window.getGuiScaledWidth(), window.getGuiScaledHeight());
        SanTank.render(guiGraphics, window.getGuiScaledWidth(), window.getGuiScaledHeight());
    }
}