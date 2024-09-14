package mod.beethoven92.betterendforge.common.block.template;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class PlantBlock extends BlockBush implements IGrowable, IShearable {
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(4D/16D, 0D/16D, 4D/16D, 12D/16D, 14D/16D, 12D/16D);

	public PlantBlock(Material material) {
		super(material);
		setSoundType(SoundType.PLANT);
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
		return isTerrain(down);
	}

	protected boolean isTerrain(IBlockState state) {
		return ModTags.END_GROUND.contains(state.getBlock());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack){
		if (!this.canPlaceBlockAt(worldIn, currentPos)) {
			worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState());
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
		return new BlockStateContainer(this);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { return true; }
	@Override
	public java.util.List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return com.google.common.collect.Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
	}
}
