package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.tileentity.HydrothermalVentTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HydrothermalVentBlock extends BlockBush {
	public static final PropertyBool WATERLOGGED = PropertyBool.create("waterlogged");
	public static final PropertyBool ACTIVATED = PropertyBool.create("activated");
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(1 / 16.0, 1 / 16.0, 1 / 16.0, 15 / 16.0, 1, 15 / 16.0);

	public HydrothermalVentBlock() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(WATERLOGGED, true).withProperty(ACTIVATED, false));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new HydrothermalVentTileEntity();
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (worldIn instanceof WorldServer && state.getValue(WATERLOGGED) && worldIn.getBlockState(pos.up()).getBlock() == Blocks.WATER) {
			randomTick(worldIn, pos, state, worldIn.rand);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		return state.getBlock() == ModBlocks.SULPHURIC_ROCK.stone;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(WATERLOGGED, worldIn.getBlockState(pos).getBlock() == Blocks.WATER);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state;
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			worldIn.setBlockToAir(pos);
		} else if (state.getValue(WATERLOGGED) && worldIn.getBlockState(pos.up()).getBlock() == Blocks.WATER) {
			worldIn.scheduleUpdate(pos, this, 20);
		}
	}


	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		BlockPos up = pos.up();
		if (worldIn.getBlockState(up).getBlock() == Blocks.WATER) {
			BlockHelper.setWithoutUpdate(worldIn, up, ModBlocks.VENT_BUBBLE_COLUMN);
			worldIn.scheduleUpdate(up, ModBlocks.VENT_BUBBLE_COLUMN, 5);
		}
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (!stateIn.getValue(ACTIVATED) && rand.nextBoolean()) {
			double x = pos.getX() + rand.nextDouble();
			double y = pos.getY() + 0.9 + rand.nextDouble() * 0.3;
			double z = pos.getZ() + rand.nextDouble();
			if (stateIn.getValue(WATERLOGGED)) {
				//worldIn.spawnParticle(ModParticleTypes.GEYSER_PARTICLE, x, y, z, 0, 0, 0); TODO PARTICLES
			} else {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0, 0, 0);
			}
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, WATERLOGGED, ACTIVATED);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(WATERLOGGED) ? 1 : 0) | (state.getValue(ACTIVATED) ? 2 : 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(WATERLOGGED, (meta & 1) != 0).withProperty(ACTIVATED, (meta & 2) != 0);
	}
}
