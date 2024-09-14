package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class WallMushroomBlock extends WallPlantBlock
{
	@Override
	public boolean isValidSupport(IBlockAccess world, BlockPos pos, IBlockState blockState, EnumFacing direction)
	{
		return blockState.getMaterial().isSolid() && blockState.isSideSolid(world, pos, direction);
	}
}
