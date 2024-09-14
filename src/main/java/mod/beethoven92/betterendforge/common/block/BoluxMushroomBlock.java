package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.SoundType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

public class BoluxMushroomBlock extends PlantBlock
{
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(1.0D / 16.0D, 0.0D, 1.0D / 16.0D, 15.0D / 16.0D, 9.0D / 16.0D, 15.0D / 16.0D);

	public BoluxMushroomBlock()
	{
		super(Material.PLANTS);
		this.setSoundType(SoundType.PLANT);
		this.setLightLevel(0.625F); // Equivalent to light level 10
	}

	@Override
	protected boolean isTerrain(IBlockState state)
	{
		return state.getBlock() == ModBlocks.RUTISCUS;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SHAPE;
	}

	@Override
	public EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.NONE;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return false;
	}
}
