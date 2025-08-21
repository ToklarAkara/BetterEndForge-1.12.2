package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "git.jbredwards.fluidlogged_api.api.block.IFluidloggable", modid = "fluidlogged_api", striprefs = true)
public class UnderwaterWallPlantBlock extends WallPlantBlock
{
	public static final PropertyBool WATERLOGGED = PropertyBool.create("waterlogged");

	public UnderwaterWallPlantBlock()
	{
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(WATERLOGGED, true));
	}

	public boolean isValidPosition(IBlockState state, World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos).getMaterial() == Material.WATER && super.isValidPosition(state, worldIn, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos).getMaterial() == Material.WATER && super.canPlaceBlockAt(worldIn, pos);
	}

//	@Override
//	public boolean isValidPosition(IBlockState state, IBlockAccess worldIn, BlockPos pos)
//	{
//		return worldIn.getBlockState(pos).getMaterial() == Material.WATER && super.isValidPosition(state, worldIn, pos);
//	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(WATERLOGGED, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(WATERLOGGED) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, WATERLOGGED);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canPlaceBlockAt(worldIn, pos))
		{
			worldIn.setBlockToAir(pos);
		}
	}
}
