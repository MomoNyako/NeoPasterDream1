package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

/**
 * 维度注册类 —— 定义染梦维度的 ResourceKey 引用
 * <p>
 * 维度类型和维度实例的 JSON 定义存放在
 * {@code data/pasterdream/dimension_type/dyedream_world.json} 和
 * {@code data/pasterdream/dimension/dyedream_world.json} 中，
 * 由数据驱动自动加载，此处提供 Java 侧的引用键用于事件监听与传送。
 * <p>
 * 维度特殊效果（天空/雾气渲染）在 {@link com.pasterdream.pasterdreammod.event.ClientEventHandler} 中注册。
 */
public class PDDimensions {

    public static final ResourceKey<Level> DYEDREAM_WORLD_LEVEL_KEY = ResourceKey.create(
            Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world")
    );

    public static final ResourceKey<DimensionType> DYEDREAM_WORLD_TYPE_KEY = ResourceKey.create(
            Registries.DIMENSION_TYPE,
            ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "dyedream_world")
    );

    /**
     * 判断当前维度是否为染梦维度
     */
    public static boolean isDyedreamWorld(Level level) {
        return level.dimension().equals(DYEDREAM_WORLD_LEVEL_KEY);
    }

    /**
     * 判断当前维度是否为主世界
     */
    public static boolean isOverworld(Level level) {
        return level.dimension().equals(Level.OVERWORLD);
    }

    // ==================== 未来维度预留 ====================
    // - 灯影世界（lamp_shadow_world）：更深层梦境
    // - 暗影地牢（shadow_dungeon）：灯影深处的随机地牢
    // - 风之旅维度（wind_journey_world）：天空浮岛世界
}