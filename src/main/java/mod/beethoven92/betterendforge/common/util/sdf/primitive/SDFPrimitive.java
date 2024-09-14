package mod.beethoven92.betterendforge.common.util.sdf.primitive;

import java.util.function.Function;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public abstract class SDFPrimitive extends SDF
{
	protected Function<BlockPos, IBlockState> placerFunction;
	
	public SDFPrimitive setBlock(Function<BlockPos, IBlockState> placerFunction)
	{
		this.placerFunction = placerFunction;
		return this;
	}
	
	public SDFPrimitive setBlock(IBlockState state)
	{
		this.placerFunction = (pos) -> {
			return state;
		};
		return this;
	}
	
	public SDFPrimitive setBlock(Block block) 
	{
		this.placerFunction = (pos) -> {
			return block.getDefaultState();
		};
		return this;
	}
	
	public IBlockState getBlockState(BlockPos pos)
	{
		return placerFunction.apply(pos);
	}
}
