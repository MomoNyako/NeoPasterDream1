package com.pasterdream.pasterdreammod.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

/**
 * 染梦树苗 (Dyedream Sapling)
 * 简化版，继承 FlowerBlock，无 EntityBlock
 * 注意：这是简化实现，原版有复杂的生长逻辑和 BlockEntity
 */
public class DyedreamSaplingBlock extends FlowerBlock {
    /**
     * 构造一个染梦树苗方块
     */
    public DyedreamSaplingBlock() {
        super(MobEffects.LUCK, 80,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .randomTicks()
                .sound(SoundType.GRASS)
                .instabreak()
                .noCollission()
                .offsetType(BlockBehaviour.OffsetType.NONE)
                .pushReaction(PushReaction.DESTROY));
    }
}