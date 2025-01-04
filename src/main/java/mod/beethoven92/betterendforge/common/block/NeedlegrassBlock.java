package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NeedlegrassBlock extends BlockBush
{
	public NeedlegrassBlock(Material mat)
	{
		super(mat);
		this.setTickRandomly(true);
		setSoundType(SoundType.PLANT);
	}


	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (entityIn instanceof EntityLivingBase)
		{
			entityIn.attackEntityFrom(DamageSource.CACTUS, 0.1F);
		}
	}

	@Override
	protected boolean canSustainBush(IBlockState state)
	{
		return state.getBlock() == ModBlocks.SHADOW_GRASS;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
	}
}
