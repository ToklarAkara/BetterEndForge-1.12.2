package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.tileentity.EndFurnaceTileEntity;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndFurnaceBlock extends BlockFurnace {
	public EndFurnaceBlock(boolean lit) {
		super(lit);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new EndFurnaceTileEntity();
	}

	public static void setState(boolean p_176446_0_, World p_176446_1_, BlockPos p_176446_2_) {
		IBlockState lvt_3_1_ = p_176446_1_.getBlockState(p_176446_2_);
		TileEntity lvt_4_1_ = p_176446_1_.getTileEntity(p_176446_2_);
		keepInventory = true;
		if (p_176446_0_) {
			p_176446_1_.setBlockState(p_176446_2_, ModBlocks.END_STONE_FURNACE_LIT.getDefaultState().withProperty(FACING, lvt_3_1_.getValue(FACING)), 3);
			p_176446_1_.setBlockState(p_176446_2_, ModBlocks.END_STONE_FURNACE_LIT.getDefaultState().withProperty(FACING, lvt_3_1_.getValue(FACING)), 3);
		} else {
			p_176446_1_.setBlockState(p_176446_2_, ModBlocks.END_STONE_FURNACE.getDefaultState().withProperty(FACING, lvt_3_1_.getValue(FACING)), 3);
			p_176446_1_.setBlockState(p_176446_2_, ModBlocks.END_STONE_FURNACE.getDefaultState().withProperty(FACING, lvt_3_1_.getValue(FACING)), 3);
		}

		keepInventory = false;
		if (lvt_4_1_ != null) {
			lvt_4_1_.validate();
			p_176446_1_.setTileEntity(p_176446_2_, lvt_4_1_);
		}

	}
}
