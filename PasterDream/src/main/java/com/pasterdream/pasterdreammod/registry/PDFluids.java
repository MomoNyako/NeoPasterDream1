package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.fluid.MeltdreamLiquidFluid;
import com.pasterdream.pasterdreammod.api.fluid.FluidAPI;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * 流体注册器
 * <p>
 * 使用 {@link FluidAPI} 统一注册所有自定义流体，避免维护独立的 DeferredRegister。
 */
public class PDFluids {

    /**
     * 融梦涌泉流体源（meltdream_liquid）
     * 静止状态的流体源方块
     */
    public static final DeferredHolder<Fluid, MeltdreamLiquidFluid.Source> MELTDREAM_LIQUID =
            FluidAPI.register("meltdream_liquid", MeltdreamLiquidFluid.Source::new);

    /**
     * 融梦涌泉流体流动（flowing_meltdream_liquid）
     * 流动状态的流体
     */
    public static final DeferredHolder<Fluid, MeltdreamLiquidFluid.Flowing> FLOWING_MELTDREAM_LIQUID =
            FluidAPI.register("flowing_meltdream_liquid", MeltdreamLiquidFluid.Flowing::new);
}
