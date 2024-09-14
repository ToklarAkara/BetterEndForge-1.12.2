package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PedestalBlock;
import mod.beethoven92.betterendforge.common.rituals.InfusionRitual;
import mod.beethoven92.betterendforge.common.tileentity.InfusionPedestalTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class InfusionPedestal extends PedestalBlock
{
	private static final AxisAlignedBB SHAPE_DEFAULT;
	private static final AxisAlignedBB SHAPE_PEDESTAL_TOP;

	static {
		AxisAlignedBB basinUp = new AxisAlignedBB(2D/16D, 3D/16D, 2D/16D, 14D/16D, 4D/16D, 14D/16D);
		AxisAlignedBB basinDown = new AxisAlignedBB(0D/16D, 0D/16D, 0D/16D, 16D/16D, 3D/16D, 16D/16D);
		AxisAlignedBB pedestalTop = new AxisAlignedBB(1D/16D, 9D/16D, 1D/16D, 15D/16D, 11D/16D, 15D/16D);
		AxisAlignedBB pedestalDefault = new AxisAlignedBB(1D/16D, 13D/16D, 1D/16D, 15D/16D, 15D/16D, 15D/16D);
		AxisAlignedBB pillar = new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 9D/16D, 13D/16D);
		AxisAlignedBB pillarDefault = new AxisAlignedBB(3D/16D, 4D/16D, 3D/16D, 13D/16D, 13D/16D, 13D/16D);
		AxisAlignedBB eyeDefault = new AxisAlignedBB(4D/16D, 15D/16D, 4D/16D, 12D/16D, 16D/16D, 12D/16D);
		AxisAlignedBB eyeTop = new AxisAlignedBB(4D/16D, 11D/16D, 4D/16D, 12D/16D, 12D/16D, 12D/16D);
		AxisAlignedBB basin = basinDown.union(basinUp);
		SHAPE_DEFAULT = basin.union(pillarDefault).union(pedestalDefault).union(eyeDefault);
		SHAPE_PEDESTAL_TOP = pillar.union(pedestalTop).union(eyeTop);
	}

	public InfusionPedestal()
	{
		super();
		this.height = 1.08F;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getBlock() == this)
		{
			switch(state.getValue(STATE))
			{
				case PEDESTAL_TOP:
				{
					return SHAPE_PEDESTAL_TOP;
				}
				case DEFAULT:
				{
					return SHAPE_DEFAULT;
				}
				default:
				{
					return super.getBoundingBox(state, source, pos);
				}
			}
		}
		return super.getBoundingBox(state, source, pos);
	}


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote || state.getBlock() != this) return true;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		InfusionPedestalTileEntity pedestal = null;
		if (tileEntity instanceof InfusionPedestalTileEntity)
		{
			pedestal = (InfusionPedestalTileEntity) tileEntity;
			if (!pedestal.isEmpty() && pedestal.hasRitual())
			{
				if (pedestal.getRitual().hasRecipe())
				{
					pedestal.getRitual().stop();
					return true;
				}
				else if (pedestal.getRitual().checkRecipe())
				{
					return true;
				}
			}
		}

		boolean result = false;
		if (hand != null)
		{
			result = super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
		}

		if (result == true)
		{
			if (pedestal != null)
			{
				if (pedestal.hasRitual())
				{
					pedestal.getRitual().checkRecipe();
				}
				else
				{
					InfusionRitual ritual = new InfusionRitual(worldIn, pos);
					pedestal.linkRitual(ritual);
					ritual.checkRecipe();
				}
			}
		}
		return result;
	}


	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new InfusionPedestalTileEntity();
	}
}
