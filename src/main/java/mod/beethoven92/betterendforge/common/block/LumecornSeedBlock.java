package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlockWithAge;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LumecornSeedBlock extends PlantBlockWithAge {

	public LumecornSeedBlock(Material materialIn) {
		super(materialIn);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public void growAdult(World world, Random random, BlockPos pos) {
		ModFeatures.LUMECORN.generate(world, random, pos);
	}

	@Override
	protected boolean isTerrain(IBlockState state) {
		return state.getBlock() == ModBlocks.END_MOSS;
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.NONE;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}
}
