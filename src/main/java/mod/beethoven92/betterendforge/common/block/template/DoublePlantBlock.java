package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DoublePlantBlock extends BlockBush implements IGrowable {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(4D/16D, 2D/16D, 4D/16D, 12D/16D, 16D/16D, 12D/16D);
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 3);
	public static final PropertyBool TOP = PropertyBool.create("top");

	public DoublePlantBlock(Material material) {
		super(material);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TOP, false));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SHAPE;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState down = worldIn.getBlockState(pos.down());
		IBlockState up = worldIn.getBlockState(pos.up());
		return down.getBlock() == this || (isTerrain(down) && up.getMaterial().isReplaceable());
	}

	protected boolean isTerrain(IBlockState state) {
		return ModTags.END_GROUND.contains(state.getBlock());
	}

	public boolean canStayAt(IBlockState state, IBlockAccess world, BlockPos pos) {
		IBlockState down = world.getBlockState(pos.down());
		IBlockState up = world.getBlockState(pos.up());
		return state.getValue(TOP) ? down.getBlock() == this : isTerrain(down) && up.getBlock() == this;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int rot = worldIn.rand.nextInt(4);
		IBlockState bs = this.getDefaultState().withProperty(ROTATION, rot);
		BlockHelper.setWithoutUpdate(worldIn, pos, bs);
		BlockHelper.setWithoutUpdate(worldIn, pos.up(), bs.withProperty(TOP, true));
	}

	public void updateTick(World worldIn, BlockPos currentPos, IBlockState stateIn, Random rand) {
		if (!worldIn.isAreaLoaded(currentPos, 1)) return;

		if (!canStayAt(stateIn, worldIn, currentPos)) {
			worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState());
		}
	}

	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canStayAt(worldIn.getBlockState(pos), worldIn, pos))
		{
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{ROTATION, TOP});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(ROTATION, meta & 3).withProperty(TOP, (meta & 4) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = state.getValue(ROTATION);
		if (state.getValue(TOP)) {
			i |= 4;
		}
		return i;
	}

	@Override
	public boolean requiresUpdates() {
		return true;
	}
}
