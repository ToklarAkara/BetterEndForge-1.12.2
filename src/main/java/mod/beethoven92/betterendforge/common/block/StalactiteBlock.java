package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StalactiteBlock extends Block {
	public static final PropertyBool IS_FLOOR = PropertyBool.create("is_floor");
	public static final PropertyInteger SIZE = PropertyInteger.create("size", 0, 7);
	private static final AxisAlignedBB[] SHAPES;

	static {
		float end = 2F / 8F;
		float start = 5F / 8F;
		SHAPES = new AxisAlignedBB[8];
		for (int i = 0; i < 8; i++) {
			int side = MathHelper.floor(AdvMathHelper.lerp(i / 7F, start, end) * 8F + 0.5F);
			SHAPES[i] = new AxisAlignedBB(side / 16.0, 0, side / 16.0, (16 - side) / 16.0, 1, (16 - side) / 16.0);
		}
	}

	public StalactiteBlock() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SIZE, 0).withProperty(IS_FLOOR, true));
		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPES[state.getValue(SIZE)];
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		boolean isFloor = facing != EnumFacing.DOWN && (facing == EnumFacing.UP || pos.getY() < placer.posY);
		return this.getDefaultState().withProperty(IS_FLOOR, isFloor);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		boolean hasUp = isThis(worldIn, pos.up());
		boolean hasDown = isThis(worldIn, pos.down());
		BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
		if (hasUp && hasDown) {
			boolean floor = state.getValue(IS_FLOOR);
			BlockPos second = floor ? pos.up() : pos.down();
			IBlockState bState = worldIn.getBlockState(second);
			worldIn.setBlockState(pos, state.withProperty(SIZE, 1).withProperty(IS_FLOOR, floor));
			worldIn.setBlockState(second, bState.withProperty(SIZE, 1).withProperty(IS_FLOOR, !floor));

			bState = state;
			int startSize = floor ? 1 : 2;
			mut.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
			for (int i = 0; i < 8 && isThis(bState); i++) {
				worldIn.setBlockState(mut, bState.withProperty(SIZE, startSize++).withProperty(IS_FLOOR, false));
				mut.setY(mut.getY() + 1);
				bState = worldIn.getBlockState(mut);
			}

			bState = state;
			startSize = floor ? 2 : 1;
			mut.setPos(pos.getX(), pos.getY() - 1, pos.getZ());
			for (int i = 0; i < 8 && isThis(bState); i++) {
				worldIn.setBlockState(mut, bState.withProperty(SIZE, startSize++).withProperty(IS_FLOOR, true));
				mut.setY(mut.getY() - 1);
				bState = worldIn.getBlockState(mut);
			}
		} else if (hasDown) {
			mut.setPos(pos.getX(), mut.getY(), pos.getZ());
			for (int i = 1; i < 8; i++) {
				mut.setY(pos.getY() - i);
				if (isThis(worldIn, mut)) {
					IBlockState state2 = worldIn.getBlockState(mut);
					int size = state2.getValue(SIZE);
					if (size < i) {
						worldIn.setBlockState(mut, state2.withProperty(SIZE, i).withProperty(IS_FLOOR, true));
					} else {
						break;
					}
				} else {
					break;
				}
			}
		} else if (hasUp) {
			mut.setPos(pos.getX(), mut.getY(), pos.getZ());
			for (int i = 1; i < 8; i++) {
				mut.setY(pos.getY() + i);
				if (isThis(worldIn, mut)) {
					IBlockState state2 = worldIn.getBlockState(mut);
					int size = state2.getValue(SIZE);
					if (size < i) {
						worldIn.setBlockState(mut, state2.withProperty(SIZE, i).withProperty(IS_FLOOR, false));
					} else {
						break;
					}
				} else {
					break;
				}
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SIZE, meta & 7).withProperty(IS_FLOOR, (meta & 8) == 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(SIZE);
		if (!state.getValue(IS_FLOOR)) {
			meta |= 8;
		}
		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SIZE, IS_FLOOR);
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
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!worldIn.isRemote) {
			BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR.getDefaultState());
		}
	}

	private boolean isThis(IBlockAccess world, BlockPos pos) {
		return isThis(world.getBlockState(pos));
	}

	private boolean isThis(IBlockState state) {
		return state.getBlock() instanceof StalactiteBlock;
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
}
