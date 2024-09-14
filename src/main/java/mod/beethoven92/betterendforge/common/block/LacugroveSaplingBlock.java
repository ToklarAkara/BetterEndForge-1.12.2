package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.world.feature.LacugroveFeature;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class LacugroveSaplingBlock extends EndSaplingBlock
{
	public LacugroveSaplingBlock(Material materialIn)
	{
		super(materialIn);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		return worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.END_MOSS
				|| worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.ENDSTONE_DUST;
	}

	@Override
	protected WorldGenerator getFeature()
	{
		return new LacugroveFeature(true);
	}
}
