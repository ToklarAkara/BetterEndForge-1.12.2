package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndSaplingBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import mod.beethoven92.betterendforge.common.world.feature.DragonTreeFeature;
import mod.beethoven92.betterendforge.common.world.feature.PythadendronFeature;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class PythadendronSaplingBlock extends EndSaplingBlock
{
	public PythadendronSaplingBlock()
	{
		super(Material.PLANTS);
		this.setTickRandomly(true);
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
	{
		return worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.CHORUS_NYLIUM;
	}

	@Override
	protected WorldGenerator getFeature()
	{
		return new PythadendronFeature(true);
	}
}
