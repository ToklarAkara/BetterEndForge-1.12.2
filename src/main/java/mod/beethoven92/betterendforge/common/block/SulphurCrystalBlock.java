package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SulphurCrystalBlock extends AttachedBlock {
	private static final EnumMap<EnumFacing, AxisAlignedBB> BOUNDING_SHAPES = Maps.newEnumMap(EnumFacing.class);
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);

	static {
		BOUNDING_SHAPES.put(EnumFacing.UP, new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.5, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.DOWN, new AxisAlignedBB(0.125, 0.5, 0.125, 0.875, 1.0, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.NORTH, new AxisAlignedBB(0.125, 0.125, 0.5, 0.875, 0.875, 1.0));
		BOUNDING_SHAPES.put(EnumFacing.SOUTH, new AxisAlignedBB(0.125, 0.125, 0.0, 0.875, 0.875, 0.5));
		BOUNDING_SHAPES.put(EnumFacing.WEST, new AxisAlignedBB(0.5, 0.125, 0.125, 1.0, 0.875, 0.875));
		BOUNDING_SHAPES.put(EnumFacing.EAST, new AxisAlignedBB(0.0, 0.125, 0.125, 0.5, 0.875, 0.875));
	}

	public SulphurCrystalBlock() {
		super(Material.ROCK);
		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}

	@Override
	protected boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
	{
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return worldIn.getBlockState(blockPos).getBlock() == ModBlocks.BRIMSTONE;
	}


	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}
//	@Override TODO META
//	public IBlockState getStateFromMeta(int meta) {
//		EnumFacing facing = EnumFacing.byIndex(meta & 7);
//		int age = (meta >> 3) & 3;
//		return this.getDefaultState().withProperty(FACING, facing).withProperty(AGE, age);
//	}
//
//	@Override
//	public int getMetaFromState(IBlockState state) {
//		int meta = state.getValue(FACING).getIndex();
//		meta |= state.getValue(AGE) << 3;
//		return meta;
//	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, AGE);
	}

	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		if(state.getValue(SulphurCrystalBlock.AGE)==3) { //TODO CHECK
			return 1 + random.nextInt(3);
		}else {
			return 0;
		}
	}
	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(this, 1, 0);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ModItems.CRYSTALLINE_SULPHUR;
	}
}
