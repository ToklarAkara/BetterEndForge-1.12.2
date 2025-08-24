package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.LumecornShape;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class LumecornBlock extends Block {
	public static final PropertyEnum<LumecornShape> SHAPE = PropertyEnum.create("shape", LumecornShape.class);
	private static final AxisAlignedBB SHAPE_BOTTOM = new AxisAlignedBB(6D/16D, 0D/16D, 6D/16D, 10D/16D, 16D/16D, 10D/16D);
	private static final AxisAlignedBB SHAPE_TOP = new AxisAlignedBB(6D/16D, 0D/16D, 6D/16D, 10D/16D, 8D/16D, 10D/16D);

	public LumecornBlock(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, LumecornShape.BOTTOM_BIG));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(SHAPE) == LumecornShape.LIGHT_TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		LumecornShape shape = state.getValue(SHAPE);
		if (shape == LumecornShape.BOTTOM_BIG || shape == LumecornShape.BOTTOM_SMALL) {
			return ModTags.END_GROUND.contains(worldIn.getBlockState(pos.down()).getBlock());
		} else if (shape == LumecornShape.LIGHT_TOP) {
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		} else {
			return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() == this;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!canPlaceBlockAt((World) worldIn, pos)) {
			worldIn.destroyBlock(pos, true);
		} else {
			worldIn.setBlockState(pos, state);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(SHAPE, LumecornShape.values()[meta]);
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
	public void getDrops(NonNullList<ItemStack> p_getDrops_1_, IBlockAccess p_getDrops_2_, BlockPos p_getDrops_3_, IBlockState p_getDrops_4_, int p_getDrops_5_) {
		super.getDrops(p_getDrops_1_, p_getDrops_2_, p_getDrops_3_, p_getDrops_4_, p_getDrops_5_);
	}

	@Override
	public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
		if(p_180660_1_.getValue(SHAPE)!=LumecornShape.BOTTOM_SMALL && p_180660_1_.getValue(SHAPE)!=LumecornShape.BOTTOM_BIG && p_180660_1_.getValue(SHAPE)!=LumecornShape.MIDDLE){
			return ModItems.LUMECORN_ROD;
		}
		if(p_180660_2_.nextInt(2)==0){
			return Item.getItemFromBlock(ModBlocks.LUMECORN_SEED);
		}
		return super.getItemDropped(p_180660_1_, p_180660_2_, p_180660_3_);
	}
}
