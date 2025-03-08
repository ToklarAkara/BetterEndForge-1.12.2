package mod.beethoven92.betterendforge.common.init;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.*;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.material.*;
import mod.beethoven92.betterendforge.common.block.template.*;
import mod.beethoven92.betterendforge.common.item.ModArmorMaterial;
import mod.beethoven92.betterendforge.common.item.ModItemTier;
import net.minecraft.block.*;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ModBlocks {
    public static final ArrayList<Block> BLOCKS = new ArrayList<>(); //DeferredRegister.create(ForgeRegistries.BLOCKS, BetterEnd.MOD_ID);

    // TERRAINS
    public static final Block CRYSTAL_MOSS = registerBlockWithDefaultItem("crystal_moss", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block END_MYCELIUM = registerBlockWithDefaultItem("end_mycelium", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block END_MOSS = registerBlockWithDefaultItem("end_moss", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block CHORUS_NYLIUM = registerBlockWithDefaultItem("chorus_nylium", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block CAVE_MOSS = registerBlockWithDefaultItem("cave_moss", () -> new TripleTerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block SHADOW_GRASS = registerBlockWithDefaultItem("shadow_grass", () -> new ShadowGrassBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block PINK_MOSS = registerBlockWithDefaultItem("pink_moss", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block AMBER_MOSS = registerBlockWithDefaultItem("amber_moss", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block JUNGLE_MOSS = registerBlockWithDefaultItem("jungle_moss", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block SANGNUM = registerBlockWithDefaultItem("sangnum", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block RUTISCUS = registerBlockWithDefaultItem("rutiscus", () -> new TerrainBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block PALLIDIUM_FULL = registerBlockWithDefaultItem("pallidium_full", () -> new PallidiumBlock("full", null));

    public static final Block PALLIDIUM_HEAVY = registerBlockWithDefaultItem("pallidium_heavy", () -> new PallidiumBlock("heavy", PALLIDIUM_FULL));

    public static final Block PALLIDIUM_THIN = registerBlockWithDefaultItem("pallidium_thin", () -> new PallidiumBlock("thin", PALLIDIUM_HEAVY));

    public static final Block PALLIDIUM_TINY = registerBlockWithDefaultItem("pallidium_tiny", () -> new PallidiumBlock("tiny", PALLIDIUM_THIN));


    public static final Block FLAMMALIX = registerBlockWithDefaultItem("flammalix", () -> new FlammalixBlock());


    public static final Block ENDSTONE_DUST = registerBlockWithDefaultItem("endstone_dust", () -> new EndstoneDustBlock(Material.SAND).setHardness(0.5f).setResistance(0.5f));

    public static final Block SILK_MOTH_NEST = registerBlockWithDefaultItem("silk_moth_nest", () -> new SilkMothNestBlock().setHardness(0.5f).setResistance(0.1f).setTickRandomly(true));

    //PATHS
    public static final Block CRYSTAL_MOSS_PATH = registerBlockWithDefaultItem("crystal_moss_path", () -> new PathBlock(CRYSTAL_MOSS));

    public static final Block END_MYCELIUM_PATH = registerBlockWithDefaultItem("end_mycelium_path", () -> new PathBlock(END_MYCELIUM));

    public static final Block END_MOSS_PATH = registerBlockWithDefaultItem("end_moss_path", () -> new PathBlock(END_MOSS));

    public static final Block CHORUS_NYLIUM_PATH = registerBlockWithDefaultItem("chorus_nylium_path", () -> new PathBlock(CHORUS_NYLIUM));

    public static final Block CAVE_MOSS_PATH = registerBlockWithDefaultItem("cave_moss_path", () -> new PathBlock(CAVE_MOSS));

    public static final Block SHADOW_GRASS_PATH = registerBlockWithDefaultItem("shadow_grass_path", () -> new PathBlock(SHADOW_GRASS));

    public static final Block PINK_MOSS_PATH = registerBlockWithDefaultItem("pink_moss_path", () -> new PathBlock(PINK_MOSS));

    public static final Block AMBER_MOSS_PATH = registerBlockWithDefaultItem("amber_moss_path", () -> new PathBlock(AMBER_MOSS));

    public static final Block JUNGLE_MOSS_PATH = registerBlockWithDefaultItem("jungle_moss_path", () -> new PathBlock(JUNGLE_MOSS));

    public static final Block SANGNUM_PATH = registerBlockWithDefaultItem("sangnum_path", () -> new PathBlock(SANGNUM));

    public static final Block RUTISCUS_PATH = registerBlockWithDefaultItem("rutiscus_path", () -> new PathBlock(RUTISCUS));


    public static final Block MOSSY_OBSIDIAN = registerBlockWithDefaultItem("mossy_obsidian", () -> new MossyObsidianBlock());

    public static final Block DRAGON_BONE_BLOCK = registerBlockWithDefaultItem("dragon_bone_block", () -> new BlockRotatedPillar(Material.ROCK, MapColor.SAND) {
    }.setHardness(2.0f).setResistance(2.0F));

    public static final BlockStairs DRAGON_BONE_STAIRS = registerBlockWithDefaultItem("dragon_bone_stairs", () -> new CustomBlockStairs(DRAGON_BONE_BLOCK.getDefaultState()));

	public static final Block DRAGON_BONE_SLAB = registerBlockWithDefaultItem("dragon_bone_slab",
			() -> new CustomBlockSlab(Material.ROCK).setHardness(2.0f).setResistance(2.0F));

    public static final Block MOSSY_DRAGON_BONE = registerBlockWithDefaultItem("mossy_dragon_bone", () -> new MossyDragonBoneBlock());

    // MATERIALS
    public static final Block AETERNIUM_BLOCK = registerBlockWithDefaultItem("aeternium_block", () -> new Block(Material.IRON, MapColor.GRAY).setHardness(65F).setResistance(1200F));

    public static final Block ENDER_BLOCK = registerBlockWithDefaultItem("ender_block", () -> new Block(Material.ROCK).setHardness(5F).setResistance(6F));

    public static final Block AURORA_CRYSTAL = registerBlockWithDefaultItem("aurora_crystal", () -> new AuroraCrystalBlock() {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "pickaxe";
        }

        @Override
        public boolean causesSuffocation(IBlockState state) {
            return false;
        }

        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }

        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }
    }.setHardness(1f).setResistance(1f).setLightLevel(1));

    public static final Block AMBER_BLOCK = registerBlockWithDefaultItem("amber_block", () -> new Block(Material.IRON, MapColor.YELLOW).

            setHardness(5.0F).setResistance(6.0F));

    public static final Block SMARAGDANT_CRYSTAL = registerBlockWithDefaultItem("smaragdant_crystal", () -> new BlockRotatedPillar(Material.ROCK, MapColor.GREEN) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "pickaxe";
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.GLASS;
        }
    }.setHardness(1f).setResistance(1f).setLightLevel(1));

    public static final Block SMARAGDANT_CRYSTAL_SHARD = registerBlockWithDefaultItem("smaragdant_crystal_shard", () -> new SmaragdantCrystalShardBlock() {
                @Nullable
                @Override
                public String getHarvestTool(IBlockState state) {
                    return "pickaxe";
                }

                @Override
                public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
                    return SoundType.GLASS;
                }
            }.setLightLevel(1)

    );

    // LANTERNS
    public static final Block ANDESITE_LANTERN = registerBlockWithDefaultItem("andesite_lantern", () -> new ModLanternBlock().setLightLevel(1));
    public static final Block DIORITE_LANTERN = registerBlockWithDefaultItem("diorite_lantern", () -> new ModLanternBlock().setLightLevel(1));
    public static final Block GRANITE_LANTERN = registerBlockWithDefaultItem("granite_lantern", () -> new ModLanternBlock().setLightLevel(1));
    public static final Block QUARTZ_LANTERN = registerBlockWithDefaultItem("quartz_lantern", () -> new ModLanternBlock().setLightLevel(1));
    public static final Block PURPUR_LANTERN = registerBlockWithDefaultItem("purpur_lantern", () -> new ModLanternBlock().setLightLevel(1));
    public static final Block END_STONE_LANTERN = registerBlockWithDefaultItem("end_stone_lantern", () -> new ModLanternBlock().setLightLevel(1));
    public static final Block BLACKSTONE_LANTERN = registerBlockWithDefaultItem("blackstone_lantern", () -> new ModLanternBlock().setLightLevel(1));

    public static final Block IRON_BULB_LANTERN = registerBlockWithDefaultItem("iron_bulb_lantern", () -> new BulbVineLanternBlock());

    public static final Block IRON_CHANDELIER = registerBlockWithDefaultItem("iron_chandelier", () -> new ChandelierBlock(Material.IRON).


            setLightLevel(1));

    public static final Block GOLD_CHANDELIER = registerBlockWithDefaultItem("gold_chandelier", () -> new ChandelierBlock(Material.IRON).


            setLightLevel(1));

    // ORES
    public static final Block ENDER_ORE = registerBlockWithDefaultItem("ender_ore", () -> new Block(Material.ROCK, MapColor.SAND) {
        public int quantityDroppedWithBonus(int fortune, Random random) {
            return this.quantityDropped(random) + random.nextInt(fortune + 1);
        }

        protected ItemStack getSilkTouchDrop(IBlockState state) {
            return new ItemStack(this, 1, 0);
        }

        public Item getItemDropped(IBlockState state, Random rand, int fortune) {
            return ModItems.ENDER_SHARD;
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.STONE;
        }
    }.setHardness(3F).setResistance(9F));

    public static final Block AMBER_ORE = registerBlockWithDefaultItem("amber_ore", () -> new Block(Material.ROCK, MapColor.SAND) {
        public int quantityDroppedWithBonus(int fortune, Random random) {
            return this.quantityDropped(random) + random.nextInt(fortune + 1);
        }

        protected ItemStack getSilkTouchDrop(IBlockState state) {
            return new ItemStack(this, 1, 0);
        }

        public Item getItemDropped(IBlockState state, Random rand, int fortune) {
            return ModItems.RAW_AMBER;
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.STONE;
        }
    }.setHardness(3F).setResistance(9F));

    // ROCKS	
    public static final Block BRIMSTONE = registerBlockWithDefaultItem("brimstone", () -> new BrimstoneBlock().

            setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    public static final Block SULPHUR_CRYSTAL = registerBlockWithDefaultItem("sulphur_crystal", () -> new SulphurCrystalBlock());

    public static final Block HYDROTHERMAL_VENT = registerBlockWithDefaultItem("hydrothermal_vent", () -> new HydrothermalVentBlock().

            setTickRandomly(true));

    public static final Block FLAVOLITE_RUNED = registerBlockWithDefaultItem("flavolite_runed", () -> new RunedFlavoliteBlock().setHardness(3.0F).setResistance(2000f));

    public static final Block FLAVOLITE_RUNED_ETERNAL = registerBlockWithDefaultItem("flavolite_runed_eternal", () -> new RunedFlavoliteBlock() {
        @Override
        public int quantityDropped(Random random) {
            return 0;
        }
    }.setHardness(-1.0F).setResistance(3600000.0F));

    public static final Block QUARTZ_PEDESTAL = registerBlockWithDefaultItem("quartz_pedestal", () -> new PedestalBlock());

    public static final Block PURPUR_PEDESTAL = registerBlockWithDefaultItem("purpur_pedestal", () -> new PedestalBlock());

    public static final Block ANDESITE_PEDESTAL = registerBlockWithDefaultItem("andesite_pedestal", () -> new PedestalBlock());

    public static final Block DIORITE_PEDESTAL = registerBlockWithDefaultItem("diorite_pedestal", () -> new PedestalBlock());

    public static final Block GRANITE_PEDESTAL = registerBlockWithDefaultItem("granite_pedestal", () -> new PedestalBlock());

    public static final Block END_STONE_STALACTITE = registerBlockWithDefaultItem("end_stone_stalactite", () -> new StalactiteBlock() {
        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }
    }.setHardness(3.0F).setResistance(15.0F));

    public static final Block END_STONE_STALACTITE_CAVEMOSS = registerBlockWithDefaultItem("end_stone_stalactite_cavemoss", () -> new StalactiteBlock() {
        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }
    }.setHardness(3.0f).setResistance(9.0f).setTickRandomly(true));

    // PLANTS
    public static final Block UMBRELLA_MOSS = registerBlockWithDefaultItem("umbrella_moss", () -> new UmbrellaMossBlock().setHardness(0).setResistance(0).setLightLevel(11f/15f));

    public static final Block UMBRELLA_MOSS_TALL = registerBlockWithDefaultItem("umbrella_moss_tall", () -> new UmbrellaMossTallBlock() {
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(UmbrellaMossTallBlock.TOP) ? 12 : 0;
        }
    }.setHardness(0).setResistance(0));

    public static final Block CREEPING_MOSS = registerBlockWithDefaultItem("creeping_moss", () -> new GlowingMossBlock().setHardness(0).setResistance(0).setLightLevel(11f/15f));

    public static final Block CHORUS_GRASS = registerBlockWithDefaultItem("chorus_grass", () -> new ChorusGrassBlock().setHardness(0).setResistance(0));

    public static final Block CHARCOAL_BLOCK = registerBlockWithNoItem("charcoal_block", () -> new CharcoalBlock(Material.ROCK).setHardness(3.0F).setResistance(5.0F));

    public static final Block CAVE_GRASS = registerBlockWithDefaultItem("cave_grass", () -> new TerrainPlantBlock(CAVE_MOSS));

    public static final Block CRYSTAL_GRASS = registerBlockWithDefaultItem("crystal_grass", () -> new TerrainPlantBlock(CRYSTAL_MOSS));

    public static final Block AMBER_GRASS = registerBlockWithDefaultItem("amber_grass", () -> new TerrainPlantBlock(AMBER_MOSS));

    public static final Block SHADOW_PLANT = registerBlockWithDefaultItem("shadow_plant", () -> new TerrainPlantBlock(SHADOW_GRASS));

    public static final Block BUSHY_GRASS = registerBlockWithDefaultItem("bushy_grass", () -> new TerrainPlantBlock(PINK_MOSS));

    public static final Block JUNGLE_GRASS = registerBlockWithDefaultItem("jungle_grass", () -> new TerrainPlantBlock(JUNGLE_MOSS));

    public static final Block BLOOMING_COOKSONIA = registerBlockWithDefaultItem("blooming_cooksonia", () -> new TerrainPlantBlock(END_MOSS));

    public static final Block SALTEAGO = registerBlockWithDefaultItem("salteago", () -> new TerrainPlantBlock(END_MOSS));

    public static final Block VAIOLUSH_FERN = registerBlockWithDefaultItem("vaiolush_fern", () -> new TerrainPlantBlock(END_MOSS));

    public static final Block FRACTURN = registerBlockWithDefaultItem("fracturn", () -> new TerrainPlantBlock(END_MOSS));

    public static final Block GLOBULAGUS = registerBlockWithDefaultItem("globulagus", () -> new TerrainPlantBlock(SANGNUM, MOSSY_OBSIDIAN, MOSSY_DRAGON_BONE));

    public static final Block INFLEXIA = registerBlockWithDefaultItem("inflexia", () -> new TerrainPlantBlock(PALLIDIUM_FULL, PALLIDIUM_HEAVY, PALLIDIUM_THIN, PALLIDIUM_TINY));

    public static final Block CLAWFERN = registerBlockWithDefaultItem("clawfern", () -> new TerrainPlantBlock(SANGNUM, MOSSY_OBSIDIAN, MOSSY_DRAGON_BONE));

    public static final Block AERIDIUM = registerBlockWithDefaultItem("aeridium", () -> new TerrainPlantBlock(RUTISCUS));

    public static final Block ORANGO = registerBlockWithDefaultItem("orango", () -> new TerrainPlantBlock(RUTISCUS));

    public static final Block LUTEBUS = registerBlockWithDefaultItem("lutebus", () -> new TerrainPlantBlock(RUTISCUS));

    public static final Block LAMELLARIUM = registerBlockWithDefaultItem("lamellarium", () -> new TerrainPlantBlock(RUTISCUS));

    public static final Block BOLUX_MUSHROOM = registerBlockWithDefaultItem("bolux_mushroom", () -> new BoluxMushroomBlock());


    public static final Block BLUE_VINE_SEED = registerBlockWithDefaultItem("blue_vine_seed", () -> new BlueVineSeedBlock(Material.PLANTS).setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block BLUE_VINE = registerBlock("blue_vine", () -> new BlueVineBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block BLUE_VINE_LANTERN = registerBlockWithDefaultItem("blue_vine_lantern", () -> new BlueVineLanternBlock(Material.WOOD) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    }.setHardness(0).setResistance(0).setLightLevel(1));

    public static final Block BLUE_VINE_FUR = registerBlockWithDefaultItem("blue_vine_fur", () -> new FurBlock(Material.PLANTS).setHardness(0).setResistance(0).setLightLevel(1)

    );

    public static final Block CAVE_BUSH = registerBlockWithDefaultItem("cave_bush", () -> new Block(Material.LEAVES, MapColor.MAGENTA) {
        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }

        @Override
        public boolean causesSuffocation(IBlockState state) {
            return false;
        }

        @Override
        public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
            return false;
        }


        public int quantityDropped(Random random) {
            return 0;
        }

        public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
            if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
                spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock(), 1, 0));
            } else {
                super.harvestBlock(worldIn, player, pos, state, te, stack);
            }
        }

        @SideOnly(Side.CLIENT)
        public BlockRenderLayer getRenderLayer()
        {
            return BlockRenderLayer.CUTOUT_MIPPED;
        }

        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }

        @Override
        public boolean isFullCube(IBlockState state) {
            return false;
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.PLANT;
        }
    }.setHardness(0.2F).setResistance(0.2f).setTickRandomly(true));


    public static final Block END_LILY_SEED = registerBlockWithDefaultItem("end_lily_seed", () -> new EndLilySeedBlock().setTickRandomly(true).setHardness(0).setResistance(0));

    public static final Block END_LILY = registerBlock("end_lily", () -> new EndLilyBlock().setHardness(0).setResistance(0));

    public static final Block END_LOTUS_SEED = registerBlockWithDefaultItem("end_lotus_seed", () -> new EndLotusSeedBlock().setTickRandomly(true).setHardness(0).setResistance(0));

    public static final Block END_LOTUS_STEM = registerBlockWithDefaultItem("end_lotus_stem", () -> new EndLotusStemBlock(Material.WOOD) {
        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.WOOD;
        }
    }.setHardness(2.0F).setResistance(5.0F));

    public static final Block END_LOTUS_LEAF = registerBlock("end_lotus_leaf", () -> new EndLotusLeafBlock(Material.PLANTS) {
        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }

        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }
    });

    public static final Block END_LOTUS_FLOWER = registerBlock("end_lotus_flower", () -> new EndLotusFlowerBlock(Material.PLANTS) {
        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }
    }.setLightLevel(1));

    public static final Block BUBBLE_CORAL = registerBlockWithDefaultItem("bubble_coral", () -> new BubbleCoralBlock() {
        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }
    }.setHardness(0).setResistance(0).setHardness(0).setResistance(0));

    public static final Block MURKWEED = registerBlockWithDefaultItem("murkweed", () -> new MurkweedBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block NEEDLEGRASS = registerBlockWithDefaultItem("needlegrass", () -> new NeedlegrassBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block MENGER_SPONGE = registerBlockWithDefaultItem("menger_sponge", () -> new MengerSpongeBlock() {
        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }
    }.setHardness(0.6F));

    public static final Block MENGER_SPONGE_WET = registerBlockWithDefaultItem("menger_sponge_wet", () -> new MengerSpongeWetBlock() {
        @Override
        public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
            return false;
        }

        @Override
        public boolean isTopSolid(IBlockState state) {
            return false;
        }
    }.setHardness(0.6F));

    public static final Block CHARNIA_RED = registerBlockWithDefaultItem("charnia_red", () -> new CharniaBlock().setHardness(0).setResistance(0));

    public static final Block CHARNIA_PURPLE = registerBlockWithDefaultItem("charnia_purple", () -> new CharniaBlock().setHardness(0).setResistance(0));

    public static final Block CHARNIA_ORANGE = registerBlockWithDefaultItem("charnia_orange", () -> new CharniaBlock().setHardness(0).setResistance(0));

    public static final Block CHARNIA_LIGHT_BLUE = registerBlockWithDefaultItem("charnia_light_blue", () -> new CharniaBlock().setHardness(0).setResistance(0));

    public static final Block CHARNIA_CYAN = registerBlockWithDefaultItem("charnia_cyan", () -> new CharniaBlock().setHardness(0).setResistance(0));

    public static final Block CHARNIA_GREEN = registerBlockWithDefaultItem("charnia_green", () -> new CharniaBlock().setHardness(0).setResistance(0));

    public static final Block FLAMAEA = registerBlock("flamaea", () -> new FlamaeaBlock());

    public static final Block HYDRALUX_SAPLING = registerBlockWithDefaultItem("hydralux_sapling", () -> new HydraluxSaplingBlock().setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block HYDRALUX = registerBlock("hydralux", () -> new HydraluxBlock() {
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(HydraluxBlock.SHAPE).hasGlow() ? 1 : 0;
        }
    }.setHardness(0).setResistance(0));

    public static final Block HYDRALUX_PETAL_BLOCK = registerBlockWithDefaultItem("hydralux_petal_block", () -> new HydraluxPetalBlock() {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    }.setHardness(1F).setResistance(1f));

    public static final Block LANCELEAF_SEED = registerBlockWithDefaultItem("lanceleaf_seed", () -> new LanceleafSeedBlock(Material.PLANTS).setHardness(0).setResistance(0).

            setTickRandomly(true));

    public static final Block LANCELEAF = registerBlock("lanceleaf", () -> new LanceleafBlock(Material.PLANTS).setHardness(0).setResistance(0)

    );

    public static final Block LUMECORN_SEED = registerBlockWithDefaultItem("lumecorn_seed", () -> new LumecornSeedBlock(Material.PLANTS).setHardness(0).setResistance(0).

            setTickRandomly(true));

    public static final Block LUMECORN = registerBlock("lumecorn", () -> new LumecornBlock(Material.WOOD) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }

        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(LumecornBlock.SHAPE).getLight();
        }
    }.setHardness(0.5f).setResistance(0.5F));

    public static final Block GLOWING_PILLAR_SEED = registerBlockWithDefaultItem("glowing_pillar_seed", () -> new GlowingPillarSeedBlock() {
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(PlantBlockWithAge.AGE) * 3 + 3;
        }
    }.setHardness(0).setResistance(0));

    public static final Block GLOWING_PILLAR_ROOTS = registerBlock("glowing_pillar_roots", () -> new GlowingPillarRootsBlock().setHardness(0).setResistance(0));

    public static final Block GLOWING_PILLAR_LUMINOPHOR = registerBlockWithDefaultItem("glowing_pillar_luminophor", () -> new GlowingPillarLuminophorBlock().setHardness(0.2f).setResistance(0.2f).setLightLevel(1));

    public static final Block GLOWING_PILLAR_LEAVES = registerBlockWithDefaultItem("glowing_pillar_leaves", () -> new FurBlock(Material.PLANTS).setHardness(0).setResistance(0).setLightLevel(1));

    public static final Block TWISTED_UMBRELLA_MOSS = registerBlockWithDefaultItem("twisted_umbrella_moss", () -> new TwistedUmbrellaMossBlock().setHardness(0).setResistance(0).setLightLevel(11f/15f));

    public static final Block TWISTED_UMBRELLA_MOSS_TALL = registerBlockWithDefaultItem("twisted_umbrella_moss_tall", () -> new TwistedUmbrellaMossTallBlock(){
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(TwistedUmbrellaMossTallBlock.TOP) ? 12 : 0;
        }
    }.setHardness(0).setResistance(0));

    public static final Block SMALL_JELLYSHROOM = registerBlockWithDefaultItem("small_jellyshroom", () -> new SmallJellyshroomBlock().setHardness(0).setResistance(0));

    public static final Block NEON_CACTUS = registerBlockWithDefaultItem("neon_cactus", () -> new NeonCactusPlantBlock());

    public static final Block NEON_CACTUS_BLOCK = registerBlockWithDefaultItem("neon_cactus_block", () -> new BlockRotatedPillar(Material.CACTUS){

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.CLOTH;
        }
    }.setHardness(0.4F).setLightLevel(1));

    public static final Block NEON_CACTUS_BLOCK_STAIRS = registerBlockWithDefaultItem("neon_cactus_stairs", () -> new CustomBlockStairs(NEON_CACTUS_BLOCK.getDefaultState()).setLightLevel(1));

    public static final Block NEON_CACTUS_BLOCK_SLAB = registerBlockWithDefaultItem("neon_cactus_slab", () -> new CustomBlockSlab(Material.CACTUS){

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.CLOTH;
        }
    }.setHardness(0.4F).setLightLevel(1));
    // CROPS
    public static final Block SHADOW_BERRY = registerBlockWithDefaultItem("shadow_berry", () -> new ShadowBerryBlock().setHardness(0).setResistance(0).

            setTickRandomly(true));


    public static final Block BLOSSOM_BERRY = registerBlockWithDefaultItem("blossom_berry_seed", () -> new EndCropBlock(Material.PLANTS, PINK_MOSS){
        @Override
        public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
            if(state.getValue(EndCropBlock.AGE)==3){
                ArrayList<ItemStack> drops = new ArrayList<>();
                drops.add(new ItemStack(ModItems.BLOSSOM_BERRY, 1, 0));
                return drops;
            }
            return super.getDrops(world, pos, state, fortune);
        }
    }.setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block AMBER_ROOT = registerBlockWithDefaultItem("amber_root_seed", () -> new EndCropBlock(Material.PLANTS, AMBER_MOSS){
        @Override
        public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
            if(state.getValue(EndCropBlock.AGE)==3){
                ArrayList<ItemStack> drops = new ArrayList<>();
                drops.add(new ItemStack(ModItems.AMBER_ROOT_RAW, 1, 0));
                return drops;
            }
            return super.getDrops(world, pos, state, fortune);
        }
    }.setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block CHORUS_MUSHROOM = registerBlockWithDefaultItem("chorus_mushroom_seed", () -> new EndCropBlock(Material.PLANTS, CHORUS_NYLIUM){
        @Override
        public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
            if(state.getValue(EndCropBlock.AGE)==3){
                ArrayList<ItemStack> drops = new ArrayList<>();
                drops.add(new ItemStack(ModItems.CHORUS_MUSHROOM_RAW, 1, 0));
                return drops;
            }
            return super.getDrops(world, pos, state, fortune);
        }
    }.setHardness(0).setResistance(0).setTickRandomly(true));

//	public static final Block PEARLBERRY = registerBlockWithDefaultItem("pearlberry_seed",
//			() -> new EndCropBlock(Material.PLANTS).
//                                                            setHardness(0).setResistance(0).
//                                                            
//                                                            setTickRandomly(true).
//                                                            sound(SoundType.PLANT),
//                                                            END_MOSS, END_MYCELIUM));


    // WALL PLANTS
    public static final Block PURPLE_POLYPORE = registerBlockWithDefaultItem("purple_polypore", () -> new WallMushroomBlock() {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.WOOD;
        }
    }.setHardness(0.2F).setResistance(0.2f).setLightLevel(13f/15f));

    public static final Block AURANT_POLYPORE = registerBlockWithDefaultItem("aurant_polypore", () -> new WallMushroomBlock() {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    }.setHardness(0.2F).setResistance(0.2f).setLightLevel(13f/15f));

    public static final Block TAIL_MOSS = registerBlockWithDefaultItem("tail_moss", () -> new WallPlantBlock().setHardness(0).setResistance(0));

    public static final Block CYAN_MOSS = registerBlockWithDefaultItem("cyan_moss", () -> new WallPlantBlock().setHardness(0).setResistance(0));

    public static final Block TWISTED_MOSS = registerBlockWithDefaultItem("twisted_moss", () -> new WallPlantBlock().setHardness(0).setResistance(0));

    public static final Block BULB_MOSS = registerBlockWithDefaultItem("bulb_moss", () -> new WallPlantBlock().setHardness(0).setResistance(0).setLightLevel(12f/15f));

    public static final Block TUBE_WORM = registerBlockWithDefaultItem("tube_worm", () -> new UnderwaterWallPlantBlock().setHardness(0).setResistance(0));

    public static final Block JUNGLE_FERN = registerBlockWithDefaultItem("jungle_fern", () -> new WallPlantBlock().setHardness(0).setResistance(0));

    public static final Block RUSCUS = registerBlockWithDefaultItem("ruscus", () -> new WallPlantBlock().setHardness(0).setResistance(0));


    public static final Block POND_ANEMONE = registerBlockWithDefaultItem("pond_anemone", () -> new PondAnemoneBlock());

    // VINES
    public static final Block DENSE_VINE = registerBlockWithDefaultItem("dense_vine", () -> new EndVineBlock(Material.PLANTS) {
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;
        }
    }.setHardness(0).setResistance(0));

    public static final Block TWISTED_VINE = registerBlockWithDefaultItem("twisted_vine", () -> new EndVineBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block BULB_VINE = registerBlockWithDefaultItem("bulb_vine", () -> new BulbVineBlock() {
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(EndVineBlock.SHAPE) == TripleShape.BOTTOM ? 15 : 0;
        }
    }.setHardness(0).setResistance(0));

    public static final Block BULB_VINE_SEED = registerBlockWithDefaultItem("bulb_vine_seed", () -> new BulbVineSeedBlock().setHardness(0).setResistance(0).

            setTickRandomly(true));

    public static final Block JUNGLE_VINE = registerBlockWithDefaultItem("jungle_vine", () -> new EndVineBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block RUBINEA = registerBlockWithDefaultItem("rubinea", () -> new EndVineBlock(Material.PLANTS).setHardness(0).setResistance(0));

    // TREES
    public static final Block MOSSY_GLOWSHROOM_SAPLING = registerBlockWithDefaultItem("mossy_glowshroom_sapling", () -> new MossyGlowshroomSaplingBlock().setHardness(0).setResistance(0).setLightLevel(7f/15f).setTickRandomly(true));

    public static final Block MOSSY_GLOWSHROOM_CAP = registerBlockWithDefaultItem("mossy_glowshroom_cap", () -> new MossyGlowshroomCapBlock() {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    });

    public static final Block MOSSY_GLOWSHROOM_HYMENOPHORE = registerBlockWithDefaultItem("mossy_glowshroom_hymenophore", () -> new Block(Material.WOOD) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    }.setLightLevel(1));

    public static final Block MOSSY_GLOWSHROOM_FUR = registerBlockWithDefaultItem("mossy_glowshroom_fur", () -> new FurBlock(Material.PLANTS).setHardness(0).setResistance(0).setLightLevel(1));

    public static final Block LACUGROVE_SAPLING = registerBlockWithDefaultItem("lacugrove_sapling", () -> new LacugroveSaplingBlock(Material.PLANTS).setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block LACUGROVE_LEAVES = registerBlockWithDefaultItem("lacugrove_leaves", () -> new CustomBlockLeaves(){
        @Override
        public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
            return Item.getItemFromBlock(ModBlocks.LACUGROVE_SAPLING);
        }
    });

    public static final Block PYTHADENDRON_SAPLING = registerBlockWithDefaultItem("pythadendron_sapling", () -> new PythadendronSaplingBlock().setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block PYTHADENDRON_LEAVES = registerBlockWithDefaultItem("pythadendron_leaves", () -> new CustomBlockLeaves(){
        @Override
        public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
            return Item.getItemFromBlock(ModBlocks.PYTHADENDRON_SAPLING);
        }
    });

    public static final Block DRAGON_TREE_SAPLING = registerBlockWithDefaultItem("dragon_tree_sapling", () -> new DragonTreeSaplingBlock().setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block DRAGON_TREE_LEAVES = registerBlockWithDefaultItem("dragon_tree_leaves", () -> new CustomBlockLeaves(){
        @Override
        public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
            return Item.getItemFromBlock(ModBlocks.DRAGON_TREE_SAPLING);
        }
    });

    public static final Block TENANEA_SAPLING = registerBlockWithDefaultItem("tenanea_sapling", () -> new TenaneaSaplingBlock().setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block TENANEA_LEAVES = registerBlockWithDefaultItem("tenanea_leaves", () -> new CustomBlockLeaves(){
        @Override
        public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
            return Item.getItemFromBlock(ModBlocks.TENANEA_SAPLING);
        }
    });

    public static final Block TENANEA_FLOWERS = registerBlockWithDefaultItem("tenanea_flowers", () -> new TenaneaFlowersBlock().setHardness(0).setResistance(0).setLightLevel(1));

    public static final Block TENANEA_OUTER_LEAVES = registerBlockWithDefaultItem("tenanea_outer_leaves", () -> new FurBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block HELIX_TREE_SAPLING = registerBlockWithDefaultItem("helix_tree_sapling", () -> new HelixTreeSaplingBlock(Material.PLANTS).setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block HELIX_TREE_LEAVES = registerBlockWithDefaultItem("helix_tree_leaves", () -> new HelixTreeLeavesBlock(Material.LEAVES, MapColor.ADOBE).setHardness(0.2f).setResistance(0.2f).setTickRandomly(true));

    public static final Block UMBRELLA_TREE_SAPLING = registerBlockWithDefaultItem("umbrella_tree_sapling", () -> new UmbrellaTreeSaplingBlock().setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block UMBRELLA_TREE_MEMBRANE = registerBlockWithDefaultItem("umbrella_tree_membrane", () -> new UmbrellaTreeMembraneBlock());

    public static final Block UMBRELLA_TREE_CLUSTER = registerBlockWithDefaultItem("umbrella_tree_cluster", () -> new UmbrellaTreeClusterBlock().setHardness(1.0f).setResistance(1.0f).setLightLevel(1));

    public static final Block UMBRELLA_TREE_CLUSTER_EMPTY = registerBlockWithDefaultItem("umbrella_tree_cluster_empty", () -> new UmbrellaTreeClusterEmptyBlock().setHardness(1.0f).setResistance(1.0f).setTickRandomly(true));

    public static final Block SMALL_AMARANITA_MUSHROOM = registerBlockWithDefaultItem("small_amaranita_mushroom", () -> new SmallAmaranitaBlock());

    public static final Block LARGE_AMARANITA_MUSHROOM = registerBlock("large_amaranita_mushroom", () -> new LargeAmaranitaBlock(Material.PLANTS));

    public static final Block AMARANITA_STEM = registerBlockWithDefaultItem("amaranita_stem", () -> new BlockRotatedPillar(Material.WOOD, MapColor.LIME){
        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.WOOD;
        }
    }.setHardness(2.0F).setResistance(3.0F));

    public static final Block AMARANITA_HYPHAE = registerBlockWithDefaultItem("amaranita_hyphae", () -> new BlockRotatedPillar(Material.WOOD, MapColor.LIME){
        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.WOOD;
        }
    }.setHardness(2.0F).setResistance(3.0F));

    public static final Block AMARANITA_HYMENOPHORE = registerBlockWithDefaultItem("amaranita_hymenophore", () -> new Block(Material.WOOD) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.WOOD;
        }
    });

    public static final Block AMARANITA_LANTERN = registerBlockWithDefaultItem("amaranita_lantern", () -> new Block(Material.WOOD) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    }.setLightLevel(1));

    public static final Block AMARANITA_FUR = registerBlockWithDefaultItem("amaranita_fur", () -> new FurBlock(Material.PLANTS).setHardness(0).setResistance(0).setLightLevel(1));

    public static final Block AMARANITA_CAP = registerBlockWithDefaultItem("amaranita_cap", () -> new Block(Material.WOOD) {
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.WOOD;
        }
    });


    public static final Block JELLYSHROOM_CAP_PURPLE = registerBlockWithDefaultItem("jellyshroom_cap_purple", () -> new JellyshroomCapBlock(217, 142, 255, 164, 0, 255));

    // BLOCKS WITH TILE ENTITY
    public static final Block ETERNAL_PEDESTAL = registerBlockWithDefaultItem("eternal_pedestal", () -> new EternalPedestal().setHardness(-1.0F).setResistance(3600000.0F));

    public static final Block INFUSION_PEDESTAL = registerBlockWithDefaultItem("infusion_pedestal", () -> new InfusionPedestal().setHardness(50.0F).setResistance(2000.0F));

    public static final Block END_STONE_SMELTER = registerBlockWithDefaultItem("end_stone_smelter", () -> new EndStoneSmelter(Material.ROCK){
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return state.getValue(EndStoneSmelter.LIT) ? 13 : 0;
        }
    }.setHardness(4F).setResistance(100F));

    public static final Block END_STONE_FURNACE = registerBlockWithDefaultItem("end_stone_furnace", () -> new EndFurnaceBlock(false).setHardness(3.0F).setResistance(15.0F));
    public static final Block END_STONE_FURNACE_LIT = registerBlock("end_stone_furnace_lit", () -> new EndFurnaceBlock(true).setHardness(3.0F).setResistance(15.0F));

    // MISC
//    public static final Block AETERNIUM_ANVIL = registerBlock("aeternium_anvil", () -> new AeterniumAnvil().setHardness(5.0F).setResistance(1200.0F));

    public static final Block DENSE_SNOW = registerBlockWithDefaultItem("dense_snow", () -> new CustomBlock(Material.SNOW).setSoundType(SoundType.SNOW).setHardness(0.2F).setResistance(0.2F));

    public static final Block EMERALD_ICE = registerBlockWithDefaultItem("emerald_ice", () -> new EmeraldIceBlock().setHardness(0.5F).setLightOpacity(3));

    public static final Block DENSE_EMERALD_ICE = registerBlockWithDefaultItem("dense_emerald_ice", () -> new CustomBlock(Material.ICE){

        @Override
        public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
            return 0.98f;
        }

        @Override
        public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
            return SoundType.GLASS;
        }
    }.setHardness(0.5f));

    public static final Block ANCIENT_EMERALD_ICE = registerBlockWithDefaultItem("ancient_emerald_ice", () -> new AncientEmeraldIceBlock().setTickRandomly(true));

    public static final Block END_PORTAL_BLOCK = registerBlock("end_portal_block", () -> new EndPortalBlock(){
        @Override
        public int quantityDropped(Random random) {
            return 0;
        }
    }.setTickRandomly(true).setResistance(-1.0F).setHardness(-1.0F).setLightLevel(12f/15f));

    public static final Block RESPAWN_OBELISK = registerBlockWithDefaultItem("respawn_obelisk", () -> new RespawnObeliskBlock(){
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
            return (state.getValue(RespawnObeliskBlock.SHAPE) == TripleShape.BOTTOM) ? 0 : 1;
        }
    });

    public static final Block VENT_BUBBLE_COLUMN = registerBlock("vent_bubble_column", () -> new VentBubbleColumnBlock(){
        @Override
        public boolean isOpaqueCube(IBlockState state) {
            return false;
        }

        @Override
        public int quantityDropped(Random random) {
            return 0;
        }
    });

    public static final Block MISSING_TILE = registerBlockWithDefaultItem("missing_tile", () -> new Block(Material.ROCK).setHardness(3.0F).setResistance(15.0F));

//    // FLOWER POT BLOCKS
//    public static final Block POTTED_MOSSY_GLOWSHROOM_SAPLING = registerFlowerPotBlock("potted_mossy_glowshroom_sapling", MOSSY_GLOWSHROOM_SAPLING);
//    public static final Block POTTED_LACUGROVE_SAPLING = registerFlowerPotBlock("potted_lacugrove_sapling", LACUGROVE_SAPLING);
//    public static final Block POTTED_PYTHADENDRON_SAPLING = registerFlowerPotBlock("potted_pythadendron_sapling", PYTHADENDRON_SAPLING);
//    public static final Block POTTED_DRAGON_TREE_SAPLING = registerFlowerPotBlock("potted_dragon_tree_sapling", DRAGON_TREE_SAPLING);
//    public static final Block POTTED_TENANEA_SAPLING = registerFlowerPotBlock("potted_tenanea_sapling", TENANEA_SAPLING);
//    public static final Block POTTED_HELIX_TREE_SAPLING = registerFlowerPotBlock("potted_helix_tree_sapling", HELIX_TREE_SAPLING);
//    public static final Block POTTED_UMBRELLA_TREE_SAPLING = registerFlowerPotBlock("potted_umbrella_tree_sapling", UMBRELLA_TREE_SAPLING);

    // WOODEN MATERIALS
    private static List<WoodenMaterial> woodenMaterials;

    public static List<WoodenMaterial> getWoodenMaterials() {
        return ImmutableList.copyOf(woodenMaterials);
    }

    public static final WoodenMaterial MOSSY_GLOWSHROOM = createWoodenMaterial("mossy_glowshroom", MapColor.GRAY, MapColor.WOOD);
    public static final WoodenMaterial LACUGROVE = createWoodenMaterial("lacugrove", MapColor.BROWN, MapColor.YELLOW);
    public static final WoodenMaterial END_LOTUS = createWoodenMaterial("end_lotus", MapColor.LIGHT_BLUE, MapColor.CYAN);
    public static final WoodenMaterial PYTHADENDRON = createWoodenMaterial("pythadendron", MapColor.MAGENTA, MapColor.PURPLE);
    public static final WoodenMaterial DRAGON_TREE = createWoodenMaterial("dragon_tree", MapColor.BLACK, MapColor.MAGENTA);
    public static final WoodenMaterial TENANEA = createWoodenMaterial("tenanea", MapColor.BROWN, MapColor.PINK);
    public static final WoodenMaterial HELIX_TREE = createWoodenMaterial("helix_tree", MapColor.GRAY, MapColor.ADOBE);
    public static final WoodenMaterial UMBRELLA_TREE = createWoodenMaterial("umbrella_tree", MapColor.BLUE, MapColor.GREEN);
    public static final WoodenMaterial JELLYSHROOM = createWoodenMaterial("jellyshroom", MapColor.PURPLE, MapColor.LIGHT_BLUE);
    public static final WoodenMaterial LUCERNIA = createWoodenMaterial("lucernia", MapColor.ADOBE, MapColor.ADOBE);

    public static final Block LUCERNIA_SAPLING = registerBlockWithDefaultItem("lucernia_sapling", () -> new LucerniaSaplingBlock(Material.PLANTS).setHardness(0).setResistance(0).setTickRandomly(true));

    public static final Block LUCERNIA_LEAVES = registerBlockWithDefaultItem("lucernia_leaves", () -> new CustomBlockLeaves(){
        @Override
        public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
            return Item.getItemFromBlock(ModBlocks.LUCERNIA_SAPLING);
        }
    });

    public static final Block LUCERNIA_OUTER_LEAVES = registerBlockWithDefaultItem("lucernia_outer_leaves", () -> new FurBlock(Material.PLANTS).setHardness(0).setResistance(0));

    public static final Block FILALUX = registerBlockWithDefaultItem("filalux", () -> new FilaluxBlock());
    public static final Block FILALUX_WINGS = registerBlockWithDefaultItem("filalux_wings", () -> new FilaluxWingsBlock());
    public static final Block FILALUX_LANTERN = registerBlockWithDefaultItem("filalux_lantern", () -> new Block(Material.WOOD){
        @Nullable
        @Override
        public String getHarvestTool(IBlockState state) {
            return "axe";
        }
    }.setLightLevel(1));


    // STONE MATERIALS
    private static List<StoneMaterial> stoneMaterials;

    public static List<StoneMaterial> getStoneMaterials() {
        return ImmutableList.copyOf(stoneMaterials);
    }

    public static final StoneMaterial FLAVOLITE = createStoneMaterial("flavolite", MapColor.SAND);
    public static final StoneMaterial VIOLECITE = createStoneMaterial("violecite", MapColor.PURPLE);
    public static final StoneMaterial SULPHURIC_ROCK = createStoneMaterial("sulphuric_rock", MapColor.BROWN);
    public static final StoneMaterial VIRID_JADESTONE = createStoneMaterial("virid_jadestone", MapColor.GREEN);
    public static final StoneMaterial AZURE_JADESTONE = createStoneMaterial("azure_jadestone", MapColor.LIGHT_BLUE);
    public static final StoneMaterial SANDY_JADESTONE = createStoneMaterial("sandy_jadestone", MapColor.YELLOW);
    public static final StoneMaterial UMBRALITH = new StoneMaterial("umbralith", MapColor.BLACK);


    // METAL MATERIALS
    private static List<MetalMaterial> metalMaterials;

    public static List<MetalMaterial> getMetalMaterials() {
        return ImmutableList.copyOf(metalMaterials);
    }

    public static final MetalMaterial THALLASIUM = createMetalMaterial("thallasium", true, Material.IRON,ModItemTier.THALLASIUM,ModArmorMaterial.THALLASIUM);
    public static final MetalMaterial TERMINITE = createMetalMaterial("terminite", false, Material.IRON,ModItemTier.TERMINITE,ModArmorMaterial.TERMINITE);


    // COLORED MATERIALS
    public static final ColoredMaterial HYDRALUX_PETAL_BLOCK_COLORED = new ColoredMaterial("hydralux_petal_block", () -> new HydraluxPetalBlockColored(), HYDRALUX_PETAL_BLOCK, true);

    public static final ColoredMaterial IRON_BULB_LANTERN_COLORED = new ColoredMaterial("iron_bulb_lantern", () -> new BulbVineLanternBlock(), IRON_BULB_LANTERN, false);

    public final static Item FLAMAEA_ITEM = ModItems.registerItem("flamaea", () -> new ItemLilyPad(ModBlocks.FLAMAEA).setCreativeTab(ModCreativeTabs.CREATIVE_TAB));
    public final static Item CHARCOAL_BLOCK_ITEM = ModItems.registerItem("charcoal_block", () -> new ItemBlock(ModBlocks.CHARCOAL_BLOCK)
    {
        @Override
        public int getItemBurnTime(ItemStack itemStack) {
            return 14400;
        }
    }.setCreativeTab(ModCreativeTabs.CREATIVE_TAB));

    //////////////////////////////////////////////////////
    //
    // Block registration helpers
    //
    /////////////////////////////////////////////////////

    public static <T extends Block> T registerBlock(String name, Supplier<? extends T> blockSupplier) {
        T block = (T) blockSupplier.get().setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name).setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
        BLOCKS.add(block);
        return block;
    }

    public static <T extends Block> T registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier) {
        T block = (T) blockSupplier.get().setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name).setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
        BLOCKS.add(block);
        ModItems.ITEMS.add(new ItemBlock(block).setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name));
        return block;
    }

    public static <T extends CustomBlockDoor> T registerBlockWithDoorItem(String name, Supplier<? extends T> blockSupplier) {
        T block = (T) blockSupplier.get().setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name).setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
        BLOCKS.add(block);
        ItemDoor itemDoor = (ItemDoor) new ItemDoor(block).setCreativeTab(ModCreativeTabs.CREATIVE_TAB).setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name);
        ModItems.ITEMS.add(itemDoor);
        block.setDoorItem(itemDoor);
        return block;
    }

    public static Block registerBlockWithDefaultItem(Block block, String name) {
        return registerBlockWithDefaultItem(name, ()->block);
    }

    public static <T extends Block> T registerBlockWithNoItem(String name, Supplier<? extends T> blockSupplier) {
        T block = (T) blockSupplier.get().setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name).setCreativeTab(ModCreativeTabs.CREATIVE_TAB);
        BLOCKS.add(block);
        return block;
    }


    public static <T extends Block> T registerBlockWithDefaultItem(String name, Supplier<? extends T> blockSupplier, CreativeTabs group) {
        T block = (T) blockSupplier.get().setRegistryName(BetterEnd.MOD_ID, name).setTranslationKey(BetterEnd.MOD_ID+"."+name).setCreativeTab(group);
        BLOCKS.add(block);
        ModItems.ITEMS.add(new ItemBlock(block).setCreativeTab(group).setRegistryName(BetterEnd.MOD_ID, name));
        return block;
    }

//    public static Block registerFlowerPotBlock(String name, Block plant) {
//        Block flowerPot = BLOCKS.register(name, () -> new BlockFlowerPot(plant, Material.MISCELLANEOUS).setHardness(0).setResistance(0).notSolid()))
//        ;
//        return flowerPot;
//    }

    public static WoodenMaterial createWoodenMaterial(String name, MapColor woodColor, MapColor planksColor) {
        if (woodenMaterials == null) woodenMaterials = new ArrayList<>();

        WoodenMaterial material = new WoodenMaterial(name, woodColor, planksColor);
        woodenMaterials.add(material);
        return material;
    }

    public static StoneMaterial createStoneMaterial(String name, MapColor color) {
        if (stoneMaterials == null) stoneMaterials = new ArrayList<>();

        StoneMaterial material = new StoneMaterial(name, color);
        stoneMaterials.add(material);
        return material;
    }

    public static MetalMaterial createMetalMaterial(String name, boolean hasOre, Material blockMaterial, Item.ToolMaterial toolMaterial, ItemArmor.ArmorMaterial armor) {
        if (metalMaterials == null) metalMaterials = new ArrayList<>();

        MetalMaterial material = new MetalMaterial(name, hasOre, blockMaterial, toolMaterial, armor);
        metalMaterials.add(material);
        return material;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> evt) {
        for(Block block : BLOCKS) {
            try {
                evt.getRegistry().register(block);
            } catch (Exception e) {
                System.out.println(block.getRegistryName());
                e.printStackTrace();
            }
        }
    }
}
