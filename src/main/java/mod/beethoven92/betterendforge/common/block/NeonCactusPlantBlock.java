package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.BlockProperties.CactusBottom;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChorusPlant;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NeonCactusPlantBlock extends Block {
	public static final PropertyEnum<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	public static final PropertyEnum<CactusBottom> CACTUS_BOTTOM = BlockProperties.CACTUS_BOTTOM;
//	public static final PropertyBool WATERLOGGED = PropertyBool.create();
	public static final PropertyEnum<EnumFacing> FACING = PropertyDirection.create("facing");

	private static final EnumMap<EnumFacing, AxisAlignedBB> BIG_SHAPES_OPEN = Maps.newEnumMap(EnumFacing.class);
	private static final EnumMap<EnumFacing, AxisAlignedBB> MEDIUM_SHAPES_OPEN = Maps.newEnumMap(EnumFacing.class);
	private static final EnumMap<EnumFacing, AxisAlignedBB> SMALL_SHAPES_OPEN = Maps.newEnumMap(EnumFacing.class);
	private static final EnumMap<EnumFacing.Axis, AxisAlignedBB> BIG_SHAPES = Maps.newEnumMap(EnumFacing.Axis.class);
	private static final EnumMap<EnumFacing.Axis, AxisAlignedBB> MEDIUM_SHAPES = Maps.newEnumMap(EnumFacing.Axis.class);
	private static final EnumMap<EnumFacing.Axis, AxisAlignedBB> SMALL_SHAPES = Maps.newEnumMap(EnumFacing.Axis.class);
	private static final int MAX_LENGTH = 12;

	public NeonCactusPlantBlock() {
		super(Material.CACTUS);
		setSoundType(SoundType.PLANT);
		setLightLevel(1);
		setTickRandomly(true);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.UP).withProperty(SHAPE, TripleShape.TOP));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE, CACTUS_BOTTOM, FACING);
	}


	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState down = world.getBlockState(pos.offset(facing.getOpposite()));
		IBlockState state = this.getDefaultState().withProperty(FACING, facing);
		if (down.getBlock()==(Blocks.END_STONE) || down.getBlock()==(ModBlocks.ENDSTONE_DUST)) {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (down.getBlock()==(ModBlocks.END_MOSS)) {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		return state;
	}


	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return BlockHelper.rotateHorizontal(state, rot, FACING);
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return BlockHelper.mirrorHorizontal(state, mirrorIn, FACING);
	}


	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		world.scheduleUpdate(pos, this, 2);
		EnumFacing dir = state.getValue(FACING);
		IBlockState downState = world.getBlockState(pos.offset(dir.getOpposite()));
		if (downState.getBlock()==(Blocks.END_STONE) || downState.getBlock()==(ModBlocks.ENDSTONE_DUST)) {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (downState.getBlock()==(ModBlocks.END_MOSS)) {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		world.setBlockState(pos, state);
	}


	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
		if (!state.getBlock().canPlaceBlockAt(worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
			return;
		}

//		if (!this.canSurviveAt(worldIn, pos))
//		{
//			worldIn.scheduleUpdate(pos, this, 1);
//		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess view, BlockPos pos) {
		TripleShape shape = state.getValue(SHAPE);
		EnumFacing dir = state.getValue(FACING);
		IBlockState next = view.getBlockState(pos.offset(dir));
		if (next.getBlock()==(this)) {
			Axis axis = dir.getAxis();
			if (shape == TripleShape.BOTTOM) {
				return BIG_SHAPES.get(axis);
			}
			return shape == TripleShape.MIDDLE ? MEDIUM_SHAPES.get(axis) : SMALL_SHAPES.get(axis);
		} else {
			if (shape == TripleShape.BOTTOM) {
				return BIG_SHAPES_OPEN.get(dir);
			}
			return shape == TripleShape.MIDDLE ? MEDIUM_SHAPES_OPEN.get(dir) : SMALL_SHAPES_OPEN.get(dir);
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return super.getActualState(state, worldIn, pos);
	}

	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return canPlaceBlock(worldIn, pos, side);
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		for (EnumFacing enumfacing : EnumFacing.values())
		{
			if (canPlaceBlock(worldIn, pos, enumfacing))
			{
				return true;
			}
		}

		return false;
	}

	protected boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing dir)
	{
		BlockPos supportPos = pos.offset(dir.getOpposite());
		IBlockState support = worldIn.getBlockState(supportPos);
		return support.getBlock()==(this) || support.getBlock().isSideSolid(support, worldIn, supportPos, dir);
	}

//	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
//	{
//		if (!this.canSurviveAt(worldIn, pos))
//		{
//			worldIn.destroyBlock(pos, true);
//		}
//	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (!this.canPlaceBlockAt(world, pos) || random.nextInt(8) > 0) {
			return;
		}
		EnumFacing dir = state.getValue(FACING);
		if (!world.isAirBlock(pos.offset(dir))) {
			return;
		}
		int length = getLength(state, world, pos, MAX_LENGTH);
		if (length < 0 || length > MAX_LENGTH - 1) {
			return;
		}
		if (dir.getAxis().isHorizontal()) {
			int horizontal = getHorizontal(state, world, pos, 2);
			if (horizontal > random.nextInt(2)) {
				dir = EnumFacing.UP;
				if (world.getBlockState(pos.up()).getBlock()!=Blocks.AIR) {
					return;
				}
			}
		} else if (length > 1 && world.getBlockState(pos.offset(dir.getOpposite())).getBlock()==(this)) {
			EnumFacing side = getSideDirection(world, pos, state, dir, random);
			BlockPos sidePos = pos.offset(side);
			if (world.isAirBlock(sidePos)) {
				IBlockState placement = state.withProperty(SHAPE, TripleShape.TOP).withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY)
						.withProperty(FACING, side);
				BlockHelper.setWithoutUpdate(world, sidePos, placement);
			}
		}
		IBlockState placement = state.withProperty(SHAPE, TripleShape.TOP).withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY)
				.withProperty(FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos.offset(dir), placement);
		mutateStem(placement, world, pos, MAX_LENGTH);
	}

	public void growPlant(World world, BlockPos pos, Random random) {
		growPlant(world, pos, random, ModMathHelper.randRange(MAX_LENGTH >> 1, MAX_LENGTH, random));
	}

	public void growPlant(World world, BlockPos pos, Random random, int iterations) {
		IBlockState state = getDefaultState();
		IBlockState downState = world.getBlockState(pos.down());
		if (downState.getBlock()==(Blocks.END_STONE) || downState.getBlock()==(ModBlocks.ENDSTONE_DUST)) {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.SAND);
		} else if (downState.getBlock()==(ModBlocks.END_MOSS)) {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.MOSS);
		} else {
			state = state.withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY);
		}
		BlockHelper.setWithoutUpdate(world, pos, state);
		List<Mutable> ends = Lists.newArrayList(new Mutable(pos));
		for (int i = 0; i < iterations; i++) {
			int count = ends.size();
			for (int n = 0; n < count; n++) {
				if (!growIteration(world, ends.get(n), random, ends, i)) {
					ends.remove(n);
					count--;
					n--;
				}
			}
		}
	}

	private boolean growIteration(World world, Mutable pos, Random random, List<Mutable> ends, int length) {
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock()!=(this)) {
			return false;
		}
		EnumFacing dir = state.getValue(FACING);
		if (!world.isAirBlock(pos.offset(dir))) {
			return false;
		}
		if (dir.getAxis().isHorizontal()) {
			int horizontal = getHorizontal(state, world, pos, 2);
			if (horizontal > random.nextInt(2)) {
				dir = EnumFacing.UP;
				if (world.getBlockState(pos.up()).getBlock()!=Blocks.AIR) {
					return false;
				}
			}
		} else if (length > 1 && world.getBlockState(pos.down()).getBlock()==(this)) {
			EnumFacing side = getSideDirection(world, pos, state, dir, random);
			BlockPos sidePos = pos.offset(side);
			if (world.isAirBlock(sidePos)) {
				IBlockState placement = state.withProperty(SHAPE, TripleShape.TOP).withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY)
						.withProperty(FACING, side);
				BlockHelper.setWithoutUpdate(world, sidePos, placement);
				ends.add(new Mutable(sidePos));
			}
		}
		IBlockState placement = state.withProperty(SHAPE, TripleShape.TOP).withProperty(CACTUS_BOTTOM, CactusBottom.EMPTY)
				.withProperty(FACING, dir);
		BlockHelper.setWithoutUpdate(world, pos.offset(dir), placement);
		mutateStem(placement, world, pos, MAX_LENGTH);
		pos.move(dir);
		return true;
	}

	private EnumFacing getSideDirection(World world, BlockPos pos, IBlockState iterState, EnumFacing dir,
			Random random) {
		Mutable iterPos = new Mutable(pos);
		EnumFacing startDir = dir;
		EnumFacing lastDir = null;
		while (iterState.getBlock()==(this) && startDir.getAxis().isVertical()) {
			startDir = iterState.getValue(FACING);
			if (lastDir == null) {
				for (EnumFacing side : BlockHelper.HORIZONTAL_DIRECTIONS) {
					IBlockState sideState = world.getBlockState(iterPos.offset(side));
					if (sideState.getBlock()==(this)) {
						EnumFacing sideDir = sideState.getValue(FACING);
						if (sideDir != side) {
							continue;
						}
						lastDir = sideDir;
					}
				}
			}
			iterPos.move(dir);
			iterState = world.getBlockState(iterPos);
		}

		EnumFacing side = lastDir == null ? BlockHelper.randomHorizontal(random) : lastDir.rotateY();
		if (side.getOpposite() == startDir) {
			side = side.getOpposite();
		}
		return side;
	}

//	@Override
//	public boolean allowsMovement(IBlockState state, World worldIn, BlockPos pos, PathType type) {
//		return false;
//	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
	}

	private int getLength(IBlockState state, World world, BlockPos pos, int max) {
		int length = 0;
		EnumFacing dir = state.getValue(FACING).getOpposite();
		BlockPos.MutableBlockPos mut = new Mutable().setPos(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (state.getBlock()!=(this)) {
				if (!ModTags.END_GROUND.contains(state.getBlock())) {
					length = -1;
				}
				break;
			}
			dir = state.getValue(FACING).getOpposite();
			length++;
		}
		return length;
	}

	private int getHorizontal(IBlockState state, World world, BlockPos pos, int max) {
		int count = 0;
		EnumFacing dir = state.getValue(FACING).getOpposite();
		BlockPos.MutableBlockPos mut = new Mutable().setPos(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (state.getBlock()!=(this)) {
				break;
			}
			dir = state.getValue(FACING).getOpposite();
			if (dir.getYOffset() != 0) {
				break;
			}
			count++;
		}
		return count;
	}

	private void mutateStem(IBlockState state, World world, BlockPos pos, int max) {
		EnumFacing dir = state.getValue(FACING).getOpposite();
		BlockPos.MutableBlockPos mut = new Mutable().setPos(pos);
		for (int i = 0; i < max; i++) {
			mut.move(dir);
			state = world.getBlockState(mut);
			if (state.getBlock()!=(this)) {
				return;
			}
			int size = (i + 2) * 3 / max;
			int src = state.getValue(SHAPE).getIndex();
			dir = state.getValue(FACING).getOpposite();
			if (src < size) {
				TripleShape shape = TripleShape.fromIndex(size);
				BlockHelper.setWithoutUpdate(world, mut, state.withProperty(SHAPE, shape));
			}
		}
	}

	static {
		BIG_SHAPES.put(Axis.X, new AxisAlignedBB(0D/16D, 2D/16D, 2D/16D, 16D/16D, 14D/16D, 14D/16D));
		BIG_SHAPES.put(Axis.Y, new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 16D/16D, 14D/16D));
		BIG_SHAPES.put(Axis.Z, new AxisAlignedBB(2D/16D, 2D/16D, 0D/16D, 14D/16D, 14D/16D, 16D/16D));

		MEDIUM_SHAPES.put(Axis.X, new AxisAlignedBB(0D/16D, 3D/16D, 3D/16D, 16D/16D, 13D/16D, 13D/16D));
		MEDIUM_SHAPES.put(Axis.Y, new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 16D/16D, 13D/16D));
		MEDIUM_SHAPES.put(Axis.Z, new AxisAlignedBB(3D/16D, 3D/16D, 0D/16D, 13D/16D, 13D/16D, 16D/16D));

		SMALL_SHAPES.put(Axis.X, new AxisAlignedBB(0D/16D, 4D/16D, 4D/16D, 16D/16D, 12D/16D, 12D/16D));
		SMALL_SHAPES.put(Axis.Y, new AxisAlignedBB(4D/16D, 0D/16D, 4D/16D, 12D/16D, 16D/16D, 12D/16D));
		SMALL_SHAPES.put(Axis.Z, new AxisAlignedBB(4D/16D, 4D/16D, 0D/16D, 12D/16D, 12D/16D, 16D/16D));

		BIG_SHAPES_OPEN.put(EnumFacing.UP, new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 14D/16D, 14D/16D));
		BIG_SHAPES_OPEN.put(EnumFacing.DOWN, new AxisAlignedBB(2D/16D, 2D/16D, 2D/16D, 14D/16D, 16D/16D, 14D/16D));
		BIG_SHAPES_OPEN.put(EnumFacing.NORTH, new AxisAlignedBB(2D/16D, 2D/16D, 2D/16D, 14D/16D, 14D/16D, 16D/16D));
		BIG_SHAPES_OPEN.put(EnumFacing.SOUTH, new AxisAlignedBB(2D/16D, 2D/16D, 0D/16D, 14D/16D, 14D/16D, 14D/16D));
		BIG_SHAPES_OPEN.put(EnumFacing.WEST, new AxisAlignedBB(2D/16D, 2D/16D, 2D/16D, 16D/16D, 14D/16D, 14D/16D));
		BIG_SHAPES_OPEN.put(EnumFacing.EAST, new AxisAlignedBB(0D/16D, 2D/16D, 2D/16D, 14D/16D, 1D/16D, 14D/16D));

		MEDIUM_SHAPES_OPEN.put(EnumFacing.UP, new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 13D/16D, 13D/16D));
		MEDIUM_SHAPES_OPEN.put(EnumFacing.DOWN, new AxisAlignedBB(3D/16D, 3D/16D, 3D/16D, 13D/16D, 16D/16D, 13D/16D));
		MEDIUM_SHAPES_OPEN.put(EnumFacing.NORTH, new AxisAlignedBB(3D/16D, 3D/16D, 3D/16D, 13D/16D, 13D/16D, 16D/16D));
		MEDIUM_SHAPES_OPEN.put(EnumFacing.SOUTH, new AxisAlignedBB(3D/16D, 3D/16D, 0D/16D, 13D/16D, 13D/16D, 13D/16D));
		MEDIUM_SHAPES_OPEN.put(EnumFacing.WEST, new AxisAlignedBB(3D/16D, 3D/16D, 3D/16D, 16D/16D, 13D/16D, 13D/16D));
		MEDIUM_SHAPES_OPEN.put(EnumFacing.EAST, new AxisAlignedBB(0D/16D, 3D/16D, 3D/16D, 13D/16D, 13D/16D, 13D/16D));

		SMALL_SHAPES_OPEN.put(EnumFacing.UP, new AxisAlignedBB(4D/16D, 0D/16D, 4D/16D, 12D/16D, 12D/16D, 12D/16D));
		SMALL_SHAPES_OPEN.put(EnumFacing.DOWN, new AxisAlignedBB(4D/16D, 4D/16D, 4D/16D, 12D/16D, 16D/16D, 12D/16D));
		SMALL_SHAPES_OPEN.put(EnumFacing.NORTH, new AxisAlignedBB(4D/16D, 4D/16D, 4D/16D, 12D/16D, 12D/16D, 16D/16D));
		SMALL_SHAPES_OPEN.put(EnumFacing.SOUTH, new AxisAlignedBB(4D/16D, 4D/16D, 0D/16D, 12D/16D, 12D/16D, 12D/16D));
		SMALL_SHAPES_OPEN.put(EnumFacing.WEST, new AxisAlignedBB(4D/16D, 4D/16D, 4D/16D, 16D/16D, 12D/16D, 12D/16D));
		SMALL_SHAPES_OPEN.put(EnumFacing.EAST, new AxisAlignedBB(0D/16D, 4D/16D, 4D/16D, 12D/16D, 12D/16D, 12D/16D));
	}

//	public boolean canSurviveAt(World wordIn, BlockPos pos)
//	{
//		boolean flag = wordIn.isAirBlock(pos.up());
//		boolean flag1 = wordIn.isAirBlock(pos.down());
//
//		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
//		{
//			BlockPos blockpos = pos.offset(enumfacing);
//			Block block = wordIn.getBlockState(blockpos).getBlock();
//
//			if (block == this)
//			{
//				Block block1 = wordIn.getBlockState(blockpos.down()).getBlock();
//
//				if (block1 == this || ModTags.END_GROUND.contains(block1))
//				{
//					return true;
//				}
//			}
//		}
//
//		Block block2 = wordIn.getBlockState(pos.down()).getBlock();
//		return block2 == this || ModTags.END_GROUND.contains(block2);
//	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
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
