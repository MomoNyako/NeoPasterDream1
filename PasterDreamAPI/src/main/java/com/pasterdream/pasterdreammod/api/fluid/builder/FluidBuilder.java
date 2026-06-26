package com.pasterdream.pasterdreammod.api.fluid.builder;

import com.pasterdream.pasterdreammod.api.fluid.FluidAPI;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 流体构建器 —— 简化 Fluid 的注册流程
 * <p>
 * 使用示例：
 * <pre>{@code
 * FluidAPI.createFluid("meltdream_liquid")
 *     .factory(MeltdreamLiquidFluid.Source::new)
 *     .build();
 * }</pre>
 *
 * @param <T> 流体类型参数
 */
public class FluidBuilder<T extends Fluid> {

    private final String name;
    private Supplier<T> factory;

    /**
     * 构造流体构建器
     *
     * @param name 流体注册名
     */
    public FluidBuilder(String name) {
        this.name = name;
    }

    /**
     * 设置流体工厂
     * <p>
     * 工厂通常为流体子类的无参构造方法引用。
     *
     * @param factory 流体工厂
     * @return 当前构建器实例
     */
    public FluidBuilder<T> factory(Supplier<T> factory) {
        this.factory = factory;
        return this;
    }

    /**
     * 执行构建并注册流体
     *
     * @return 注册后的 DeferredHolder
     * @throws NullPointerException 如果工厂未设置
     */
    public DeferredHolder<Fluid, T> build() {
        Objects.requireNonNull(factory, "[FluidBuilder] factory 不能为空");
        return FluidAPI.register(name, factory);
    }
}
