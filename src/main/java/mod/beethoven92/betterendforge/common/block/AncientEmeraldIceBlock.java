package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AncientEmeraldIceBlock extends Block
{
	public AncientEmeraldIceBlock()
	{
		super(Material.ICE);
		slipperiness = 0.98f;
		this.setTickRandomly(true);
		setHardness(50.0F);
		setSoundType(SoundType.GLASS);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random random)
	{
		EnumFacing dir = BlockHelper.randomDirection(random);

		if (random.nextBoolean())
		{
			int x = ModMathHelper.randRange(-2, 2, random);
			int y = ModMathHelper.randRange(-2, 2, random);
			int z = ModMathHelper.randRange(-2, 2, random);
			BlockPos p = pos.add(x, y, z);
			if (worldIn.getBlockState(p).getBlock() == Blocks.WATER)
			{
				worldIn.setBlockState(p, ModBlocks.EMERALD_ICE.getDefaultState());
				makeParticles(worldIn, p, random);
			}
		}

		pos = pos.offset(dir);
		state = worldIn.getBlockState(pos);
		if (state.getBlock() == Blocks.WATER)
		{
			worldIn.setBlockState(pos, ModBlocks.EMERALD_ICE.getDefaultState());
			makeParticles(worldIn, pos, random);
		}
		else if (state.getBlock() == ModBlocks.EMERALD_ICE)
		{
			worldIn.setBlockState(pos, ModBlocks.DENSE_EMERALD_ICE.getDefaultState());
			makeParticles(worldIn, pos, random);
		}
	}

	private void makeParticles(World world, BlockPos pos, Random random)
	{
		//TODO PARTICLES
		//Minecraft.getMinecraft().effectRenderer.addEffect(new SnowflakeParticle(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0));
		//world.spawnParticle(ModParticleTypes.SNOWFLAKE_PARTICLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0);
	}
}
