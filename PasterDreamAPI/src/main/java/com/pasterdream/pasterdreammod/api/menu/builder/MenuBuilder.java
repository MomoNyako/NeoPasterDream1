package com.pasterdream.pasterdreammod.api.menu.builder;

import com.pasterdream.pasterdreammod.api.menu.MenuAPI;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

/**
 * 菜单类型构建器 —— 简化 MenuType 的注册流程
 * <p>
 * 使用示例：
 * <pre>{@code
 * MenuAPI.createMenu("shadow_chest")
 *     .factory(ShadowChestMenu::new)
 *     .build();
 * }</pre>
 *
 * @param <T> 菜单类型参数
 */
public class MenuBuilder<T extends AbstractContainerMenu> {

    private final String name;
    private IContainerFactory<T> factory;

    /**
     * 构造菜单构建器
     *
     * @param name 菜单注册名
     */
    public MenuBuilder(String name) {
        this.name = name;
    }

    /**
     * 设置菜单工厂
     * <p>
     * 工厂通常为菜单类的构造方法引用，签名需为 {@code (int, Inventory, FriendlyByteBuf)}。
     *
     * @param factory 菜单工厂
     * @return 当前构建器实例
     */
    public MenuBuilder<T> factory(IContainerFactory<T> factory) {
        this.factory = factory;
        return this;
    }

    /**
     * 执行构建并注册菜单类型
     *
     * @return 注册后的 DeferredHolder
     * @throws NullPointerException 如果工厂未设置
     */
    public DeferredHolder<MenuType<?>, MenuType<T>> build() {
        Objects.requireNonNull(factory, "[MenuBuilder] factory 不能为空");
        return MenuAPI.register(name, () -> IMenuTypeExtension.create(factory));
    }
}
