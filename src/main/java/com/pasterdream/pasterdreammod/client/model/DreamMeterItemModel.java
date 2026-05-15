package com.pasterdream.pasterdreammod.client.model;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.item.DreamMeterItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * 忆梦魔导透镜模型 (Dream Meter Model)
 * 用于加载 GeckoLib 3D 模型资源
 *
 * 资源文件路径：
 * - 模型：assets/pasterdream/geo/dream_meter.geo.json
 * - 纹理：assets/pasterdream/textures/item/dream_meter.png
 * - 动画：assets/pasterdream/animations/dream_meter.animation.json
 */
public class DreamMeterItemModel extends GeoModel<DreamMeterItem> {

    @Override
    public ResourceLocation getAnimationResource(DreamMeterItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "animations/dream_meter.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(DreamMeterItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "geo/dream_meter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DreamMeterItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(PasterDreamMod.MOD_ID, "textures/item/dream_meter.png");
    }
}