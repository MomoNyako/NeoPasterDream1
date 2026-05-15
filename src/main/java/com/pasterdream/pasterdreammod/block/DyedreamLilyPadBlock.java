package com.pasterdream.pasterdreammod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 染梦荷叶方块 —— 只能放置在水面上的水生植物，继承BushBlock行为。
 */
public class DyedreamLilyPadBlock extends BushBlock {
    public static final MapCodec<DyedreamLilyPadBlock> CODEC = simpleCodec(properties -> new DyedreamLilyPadBlock());

    protected static final VoxelShape SHAPE = box(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);

    public DyedreamLilyPadBlock() {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .instabreak()
                .noCollission()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .sound(SoundType.LILY_PAD)
                .pushReaction(PushReaction.DESTROY));
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
        return groundState.is(Blocks.WATER);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState groundState = level.getBlockState(below);
        return this.mayPlaceOn(groundState, level, below);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}