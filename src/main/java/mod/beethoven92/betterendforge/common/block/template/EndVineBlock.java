package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EndVineBlock extends Block implements IGrowable, IShearable {
	public static final PropertyEnum<TripleShape> SHAPE = BlockProperties.TRIPLE_SHAPE;
	private static final AxisAlignedBB VOXEL_SHAPE = new AxisAlignedBB(2D/16D, 0D/16D, 2D/16D, 14D/16D, 16D/16D, 14D/16D);

	public EndVineBlock(Material material) {
		super(material);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, TripleShape.BOTTOM));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return VOXEL_SHAPE;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return isValidSupport(worldIn.getBlockState(pos.up()), worldIn, pos);
	}

	protected boolean isValidSupport(IBlockState state, IBlockAccess world, BlockPos pos) {
		IBlockState up = world.getBlockState(pos.up());
		return up.getBlock() == this || up.getBlock().isLeaves(up, world, pos.up()) || up.isSideSolid(world, pos.up(), EnumFacing.DOWN);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack){
		if (!canPlaceBlockAt(worldIn, currentPos)) {
			worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState());
		} else {
			if (worldIn.getBlockState(currentPos.down()).getBlock() != this)
				worldIn.setBlockState(currentPos, stateIn.withProperty(SHAPE, TripleShape.BOTTOM));
			else if (worldIn.getBlockState(currentPos.up()).getBlock() != this)
				worldIn.setBlockState(currentPos, stateIn.withProperty(SHAPE, TripleShape.TOP));
			worldIn.setBlockState(currentPos, stateIn.withProperty(SHAPE, TripleShape.MIDDLE));
		}
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		while (worldIn.getBlockState(pos).getBlock() == this) {
			pos = pos.down();
		}
		return worldIn.getBlockState(pos).getBlock()==Blocks.AIR;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		while (worldIn.getBlockState(pos).getBlock() == this) {
			pos = pos.down();
		}
		return worldIn.getBlockState(pos).getBlock()==Blocks.AIR;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		while (worldIn.getBlockState(pos).getBlock() == this) {
			pos = pos.down();
		}
		worldIn.setBlockState(pos, getDefaultState());
		BlockHelper.setWithoutUpdate(worldIn, pos, getDefaultState());
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, TripleShape.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { return true; }
	@Override
	public java.util.List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return com.google.common.collect.Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock(), 1, 0));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}


	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

}
