package com.pasterdream.pasterdreammod.api.blockentity.builder;

import com.pasterdream.pasterdreammod.api.blockentity.BlockEntityAPI;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 方块实体构建器 —— 简化 BlockEntityType 的注册流程
 * <p>
 * 使用示例：
 * <pre>{@code
 * BlockEntityAPI.createBlockEntity("dream_accumulator")
 *     .factory(DreamAccumulatorBlockEntity::new)
 *     .validBlock(PDBlocks.DREAM_ACCUMULATOR)
 *     .build();
 * }</pre>
 *
 * @param <T> 方块实体类型参数
 */
public class BlockEntityBuilder<T extends BlockEntity> {

    private final String name;
    private BlockEntityType.BlockEntitySupplier<T> factory;
    private final List<Supplier<? extends Block>> validBlockSuppliers = new ArrayList<>();

    /**
     * 构造方块实体构建器
     *
     * @param name 方块实体注册名
     */
    public BlockEntityBuilder(String name) {
        this.name = name;
    }

    /**
     * 设置方块实体工厂
     * <p>
     * 工厂通常为方块实体类的构造方法引用，签名需为 {@code (BlockPos, BlockState)}。
     *
     * @param factory 方块实体工厂
     * @return 当前构建器实例
     */
    public BlockEntityBuilder<T> factory(BlockEntityType.BlockEntitySupplier<T> factory) {
        this.factory = factory;
        return this;
    }

    /**
     * 添加一个有效方块
     * <p>
     * 只有在此列表中的方块才会被识别为该方块实体的宿主。
     *
     * @param block 有效方块
     * @return 当前构建器实例
     */
    public BlockEntityBuilder<T> validBlock(Block block) {
        this.validBlockSuppliers.add(() -> block);
        return this;
    }

    /**
     * 添加一个有效方块 Supplier
     * <p>
     * 通过 Supplier 延迟获取方块实例，避免在注册事件触发前访问未绑定的 DeferredHolder。
     *
     * @param blockSupplier 有效方块 Supplier
     * @return 当前构建器实例
     */
    public BlockEntityBuilder<T> validBlock(Supplier<? extends Block> blockSupplier) {
        this.validBlockSuppliers.add(blockSupplier);
        return this;
    }

    /**
     * 批量添加有效方块
     *
     * @param blocks 有效方块数组
     * @return 当前构建器实例
     */
    public BlockEntityBuilder<T> validBlocks(Block... blocks) {
        for (Block block : blocks) {
            this.validBlockSuppliers.add(() -> block);
        }
        return this;
    }

    /**
     * 批量添加有效方块 Supplier
     *
     * @param blockSuppliers 方块 Supplier 数组
     * @return 当前构建器实例
     */
    @SafeVarargs
    public final BlockEntityBuilder<T> validBlocks(Supplier<? extends Block>... blockSuppliers) {
        this.validBlockSuppliers.addAll(Arrays.asList(blockSuppliers));
        return this;
    }

    /**
     * 执行构建并注册方块实体类型
     *
     * @return 注册后的 DeferredHolder
     * @throws NullPointerException  如果工厂未设置
     * @throws IllegalStateException 如果没有添加任何有效方块
     */
    public DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> build() {
        Objects.requireNonNull(factory, "[BlockEntityBuilder] factory 不能为空");
        if (validBlockSuppliers.isEmpty()) {
            throw new IllegalStateException("[BlockEntityBuilder] 至少需要一个有效方块");
        }
        return BlockEntityAPI.register(name, () -> {
            Block[] blocks = validBlockSuppliers.stream()
                    .map(Supplier::get)
                    .toArray(Block[]::new);
            return BlockEntityType.Builder.of(factory, blocks).build(null);
        });
    }
}
