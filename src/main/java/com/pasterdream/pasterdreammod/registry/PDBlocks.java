package com.pasterdream.pasterdreammod.registry;

import com.pasterdream.pasterdreammod.PasterDreamMod;
import com.pasterdream.pasterdreammod.block.DreamAccumulatorBlock;
import com.pasterdream.pasterdreammod.block.DyedreamCrackBlock;
import com.pasterdream.pasterdreammod.block.DyedreamDeskBlock;
import com.pasterdream.pasterdreammod.block.DyedreamLilyPadBlock;
import com.pasterdream.pasterdreammod.block.DyedreamLotusBlock;
import com.pasterdream.pasterdreammod.block.DyedreamPlanksPaneBlock;
import com.pasterdream.pasterdreammod.block.DyedreamSaplingBlock;
import com.pasterdream.pasterdreammod.block.DyedreamSeagrassBlock;
import com.pasterdream.pasterdreammod.block.DyedreamBudBlock;
import com.pasterdream.pasterdreammod.block.IceBudBlock;
import com.pasterdream.pasterdreammod.block.LifeCrystalBlock;
import com.pasterdream.pasterdreammod.block.PinkagaricBlock;
import com.pasterdream.pasterdreammod.block.ShadowChestBlock;
import net.minecraft.world.item.BlockItem;
                import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * 方块注册类
 * 使用 DeferredRegister 模式注册所有方块
 */
public class PDBlocks {

    /**
     * 方块注册器
     */
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(PasterDreamMod.MOD_ID);

    /**
     * 蓄梦池方块 (dream_accumulator)
     * 核心功能方块，用于收集梦境能量
     * 原模组使用 TESR 特殊渲染，简化版使用普通方块 + 自定义模型
     */
    public static final DeferredBlock<DreamAccumulatorBlock> DREAM_ACCUMULATOR = BLOCKS.register("dream_accumulator",
            () -> new DreamAccumulatorBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.CALCITE)
                    .strength(1.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    /**
     * 染梦书桌方块 (dyedream_desk)
     * 方向性方块，玩家放置时根据朝向旋转
     * 原模组有 GUI 和 TileEntity，简化版暂不包含
     */
    public static final DeferredBlock<DyedreamDeskBlock> DYEDREAM_DESK = BLOCKS.register("dyedream_desk",
            () -> new DyedreamDeskBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.WOOD)
                    .strength(1.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    /**
     * 生命水晶方块 (life_crystal)
     * 站在附近可以缓慢恢复生命值
     * 发光等级12，无TileEntity简化版
     */
    public static final DeferredBlock<LifeCrystalBlock> LIFE_CRYSTAL = BLOCKS.register("life_crystal",
            () -> new LifeCrystalBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.GLASS)
                    .strength(1.0f)
                    .lightLevel(state -> 12)
                    .noOcclusion()));

    /**
     * 影之箱子方块 (shadow_chest)
     * 装饰性方块，无存储功能（简化版）
     * 具有暗影主题的粒子效果
     */
    public static final DeferredBlock<ShadowChestBlock> SHADOW_CHEST = BLOCKS.register("shadow_chest",
            () -> new ShadowChestBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.DEEPSLATE_TILES)
                    .strength(1.0f, 0.5f)
                    .noOcclusion()));

    // ==================== 染梦世界方块批量移植 ====================

    public static final DeferredBlock<Block> DYEDREAM_BLOCK = BLOCKS.registerSimpleBlock("dyedream_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> DYEDREAM_DIRT = BLOCKS.registerSimpleBlock("dyedream_dirt",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final DeferredBlock<Block> DYEDREAM_SAND = BLOCKS.registerSimpleBlock("dyedream_sand",
            BlockBehaviour.Properties.ofFullCopy(Blocks.SAND));
    public static final DeferredBlock<Block> DYEDREAM_PLANKS = BLOCKS.registerSimpleBlock("dyedream_planks",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    public static final DeferredBlock<Block> DYEDREAM_GLASS = BLOCKS.registerSimpleBlock("dyedream_glass",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final DeferredBlock<Block> DYEDREAM_ICE = BLOCKS.registerSimpleBlock("dyedream_ice",
            BlockBehaviour.Properties.ofFullCopy(Blocks.ICE));
    public static final DeferredBlock<Block> DYEDREAM_PACKED_ICE = BLOCKS.registerSimpleBlock("dyedream_packed_ice",
            BlockBehaviour.Properties.ofFullCopy(Blocks.PACKED_ICE));
    public static final DeferredBlock<Block> DYEDREAMQUARTZ_BLOCK = BLOCKS.registerSimpleBlock("dyedreamquartz_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> SMOOTH_DYEDREAMQUARTZ_BLOCK = BLOCKS.registerSimpleBlock("smooth_dyedreamquartz_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> BRICKS_DYEDREAMQUARTZ_BLOCK = BLOCKS.registerSimpleBlock("bricks_dyedreamquartz_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> CHISELED_DYEDREAMQUARTZ_BLOCK = BLOCKS.registerSimpleBlock("chiseled_dyedreamquartz_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> DYEDREAM_BUD_BLOCK = BLOCKS.registerSimpleBlock("dyedream_bud_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> PINKSLIME_BLOCK = BLOCKS.registerSimpleBlock("pinkslime_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK));
    public static final DeferredBlock<Block> ICESTONE = BLOCKS.registerSimpleBlock("icestone",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));
    public static final DeferredBlock<Block> DYEDREAM_LEAVES = BLOCKS.registerSimpleBlock("dyedream_leaves",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<Block> DYEDREAM_WORLDTREE_LEAVES = BLOCKS.registerSimpleBlock("dyedream_worldtree_leaves",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES));
    public static final DeferredBlock<Block> DYEDREAMQUARTZ_ORE = BLOCKS.registerSimpleBlock("dyedreamquartz_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<Block> DYEDREAMDUST_ORE = BLOCKS.registerSimpleBlock("dyedreamdust_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<Block> AMBER_CANDY_ORE = BLOCKS.registerSimpleBlock("amber_candy_ore",
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_ORE));
    public static final DeferredBlock<Block> CARVE_DYEDREAM_GLASS = BLOCKS.registerSimpleBlock("carve_dyedream_glass",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final DeferredBlock<Block> GOLD_CARVE_DYEDREAM_GLASS = BLOCKS.registerSimpleBlock("gold_carve_dyedream_glass",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS));
    public static final DeferredBlock<Block> DYEDREAM_GRASS = BLOCKS.registerSimpleBlock("dyedream_grass",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK));

    public static final DeferredBlock<RotatedPillarBlock> DYEDREAM_LOG = BLOCKS.registerBlock("dyedream_log",
            RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final DeferredBlock<RotatedPillarBlock> DYEDREAM_WOOD = BLOCKS.registerBlock("dyedream_wood",
            RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG));
    public static final DeferredBlock<RotatedPillarBlock> PILLAR_DYEDREAMQUARTZ_BLOCK = BLOCKS.registerBlock("pillar_dyedreamquartz_block",
            RotatedPillarBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

    public static final DeferredBlock<StairBlock> DYEDREAM_PLANKS_STAIRS = BLOCKS.registerBlock("dyedream_planks_stairs",
            p -> new StairBlock(DYEDREAM_PLANKS.get().defaultBlockState(), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS));
    public static final DeferredBlock<StairBlock> DYEDREAM_BUD_STAIRS = BLOCKS.registerBlock("dyedream_bud_stairs",
            p -> new StairBlock(DYEDREAM_BUD_BLOCK.get().defaultBlockState(), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS));
    public static final DeferredBlock<StairBlock> DYEDREAMQUARTZ_BLOCK_STAIRS = BLOCKS.registerBlock("dyedreamquartz_block_stairs",
            p -> new StairBlock(DYEDREAMQUARTZ_BLOCK.get().defaultBlockState(), p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_STAIRS));

    public static final DeferredBlock<SlabBlock> DYEDREAM_PLANKS_SLAB = BLOCKS.registerBlock("dyedream_planks_slab",
            SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB));
    public static final DeferredBlock<SlabBlock> DYEDREAM_BUD_SLAB = BLOCKS.registerBlock("dyedream_bud_slab",
            SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));
    public static final DeferredBlock<SlabBlock> DYEDREAMQUARTZ_BLOCK_SLAB = BLOCKS.registerBlock("dyedreamquartz_block_slab",
            SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_SLAB));

    public static final DeferredBlock<WallBlock> DYEDREAM_BUD_WALL = BLOCKS.registerBlock("dyedream_bud_wall",
            WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));
    public static final DeferredBlock<WallBlock> DYEDREAMQUARTZ_BLOCK_WALL = BLOCKS.registerBlock("dyedreamquartz_block_wall",
            WallBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE_WALL));

    public static final DeferredBlock<FenceBlock> DYEDREAM_PLANKS_FENCE = BLOCKS.registerBlock("dyedream_planks_fence",
            FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE));
    public static final DeferredBlock<FenceGateBlock> DYEDREAM_PLANKS_FENCEGATE = BLOCKS.registerBlock("dyedream_planks_fencegate",
            p -> new FenceGateBlock(WoodType.OAK, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE));

    public static final DeferredBlock<DoorBlock> DYEDREAM_PLANKS_DOOR = BLOCKS.registerBlock("dyedream_planks_door",
            p -> new DoorBlock(BlockSetType.OAK, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR));
    public static final DeferredBlock<TrapDoorBlock> DYEDREAM_PLANKS_TRAPDOOR = BLOCKS.registerBlock("dyedream_planks_trapdoor",
            p -> new TrapDoorBlock(BlockSetType.OAK, p), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR));

    public static final DeferredBlock<PressurePlateBlock> DYEDREAM_PLANKS_PRESSURE_PLATE = BLOCKS.registerBlock("dyedream_planks_pressure_plate",
            p -> new PressurePlateBlock(BlockSetType.OAK, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE));
    public static final DeferredBlock<ButtonBlock> DYEDREAM_PLANKS_BUTTON = BLOCKS.registerBlock("dyedream_planks_button",
            p -> new ButtonBlock(BlockSetType.OAK, 30, p),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON));

    public static final DeferredBlock<IronBarsBlock> DYEDREAM_GLASSPANE = BLOCKS.registerBlock("dyedream_glasspane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final DeferredBlock<IronBarsBlock> CARVE_DYEDREAM_GLASSPANE = BLOCKS.registerBlock("carve_dyedream_glasspane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));
    public static final DeferredBlock<IronBarsBlock> GOLD_CARVE_DYEDREAM_GLASSPANE = BLOCKS.registerBlock("gold_carve_dyedream_glasspane",
            IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE));

    public static final DeferredBlock<LanternBlock> DYEDREAM_LARTERN = BLOCKS.registerBlock("dyedream_lartern",
            LanternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN));

    // ==================== 自定义模型方块 ====================

    /**
     * 木板屏风 (dyedream_planks_pane)
     * 继承 IronBarsBlock，类似玻璃板的连接逻辑，木质纹理
     */
    public static final DeferredBlock<DyedreamPlanksPaneBlock> DYEDREAM_PLANKS_PANE = BLOCKS.registerBlock("dyedream_planks_pane",
            p -> new DyedreamPlanksPaneBlock());

    /**
     * 粉丁菇 0~3 号变种 (pinkagaric_0/1/2/3)
     * 粉色蘑菇，不同变种有不同的发光等级
     */
    public static final DeferredBlock<Block> PINKAGARIC_0 = BLOCKS.registerBlock("pinkagaric_0",
            p -> new PinkagaricBlock(p, () -> 0), pinkagaricProps());
    public static final DeferredBlock<Block> PINKAGARIC_1 = BLOCKS.registerBlock("pinkagaric_1",
            p -> new PinkagaricBlock(p, () -> 8), pinkagaricProps());
    public static final DeferredBlock<Block> PINKAGARIC_2 = BLOCKS.registerBlock("pinkagaric_2",
            p -> new PinkagaricBlock(p.noOcclusion(), () -> 0), pinkagaricProps());
    public static final DeferredBlock<Block> PINKAGARIC_3 = BLOCKS.registerBlock("pinkagaric_3",
            p -> new PinkagaricBlock(p.lightLevel(s -> 15), () -> 15), pinkagaricProps());

    private static BlockBehaviour.Properties pinkagaricProps() {
        return BlockBehaviour.Properties.of()
                .ignitedByLava()
                .instrument(NoteBlockInstrument.BASS)
                .sound(SoundType.WART_BLOCK)
                .strength(0.3f, 0.1f)
                .jumpFactor(1.2f);
    }

    /**
     * 花蕾 0~2 号变种 (dyedream_bud_0/1/2)
     * SimpleWaterloggedBlock，AXIS 轴向旋转，发光等级10
     */
    public static final DeferredBlock<DyedreamBudBlock> DYEDREAM_BUD_0 = BLOCKS.registerBlock("dyedream_bud_0",
            p -> new DyedreamBudBlock(p, 0), budProps());
    public static final DeferredBlock<DyedreamBudBlock> DYEDREAM_BUD_1 = BLOCKS.registerBlock("dyedream_bud_1",
            p -> new DyedreamBudBlock(p, 1), budProps());
    public static final DeferredBlock<DyedreamBudBlock> DYEDREAM_BUD_2 = BLOCKS.registerBlock("dyedream_bud_2",
            p -> new DyedreamBudBlock(p, 2), budProps());

    private static BlockBehaviour.Properties budProps() {
        return BlockBehaviour.Properties.of()
                .sound(SoundType.AMETHYST_CLUSTER)
                .strength(1f, 0f)
                .lightLevel(s -> 10)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .hasPostProcess((bs, br, bp) -> true)
                .emissiveRendering((bs, br, bp) -> true)
                .isRedstoneConductor((bs, br, bp) -> false);
    }

    /**
     * 冰蕾 (ice_bud_0)
     * SimpleWaterloggedBlock，FACING 六面朝向，发光等级9
     */
    public static final DeferredBlock<IceBudBlock> ICE_BUD_0 = BLOCKS.registerBlock("ice_bud_0",
            IceBudBlock::new, BlockBehaviour.Properties.of()
                    .sound(SoundType.AMETHYST_CLUSTER)
                    .strength(1f, 0f)
                    .lightLevel(s -> 9)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .hasPostProcess((bs, br, bp) -> true)
                    .emissiveRendering((bs, br, bp) -> true)
                    .isRedstoneConductor((bs, br, bp) -> false));

    /**
     * 染梦荷叶 (dyedream_lily_pad)
     * 水面植物，继承 FlowerBlock，只能放在水上
     */
    public static final DeferredBlock<DyedreamLilyPadBlock> DYEDREAM_LILY_PAD = BLOCKS.registerBlock("dyedream_lily_pad",
            p -> new DyedreamLilyPadBlock());

    /**
     * 染梦莲花 (dyedream_lotus)
     * 水面植物，继承 FlowerBlock，只能放在水上
     */
    public static final DeferredBlock<DyedreamLotusBlock> DYEDREAM_LOTUS = BLOCKS.registerBlock("dyedream_lotus",
            p -> new DyedreamLotusBlock());

    /**
     * 染梦海草 (dyedream_seagrass)
     * SimpleWaterloggedBlock，水下植物，XZ 偏移
     */
    public static final DeferredBlock<DyedreamSeagrassBlock> DYEDREAM_SEAGRASS = BLOCKS.registerBlock("dyedream_seagrass",
            p -> new DyedreamSeagrassBlock());

    /**
     * 染梦树苗 (dyedream_sapling)
     * 简化版，继承 FlowerBlock，无 EntityBlock
     */
    public static final DeferredBlock<DyedreamSaplingBlock> DYEDREAM_SAPLING = BLOCKS.registerBlock("dyedream_sapling",
            p -> new DyedreamSaplingBlock());

    /**
     * 染梦裂纹 (dyedream_crack)
     * 简化版，保留 FACING+WATERLOGGED 属性，发光等级14，无 EntityBlock
     */
    public static final DeferredBlock<DyedreamCrackBlock> DYEDREAM_CRACK = BLOCKS.registerBlock("dyedream_crack",
            p -> new DyedreamCrackBlock());

}
