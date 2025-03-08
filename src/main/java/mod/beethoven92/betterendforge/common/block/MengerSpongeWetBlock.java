package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MengerSpongeWetBlock extends Block
{
	public MengerSpongeWetBlock()
	{
		super(Material.SPONGE);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (worldIn.provider.doesWaterVaporize())
		{
			worldIn.setBlockState(pos, ModBlocks.MENGER_SPONGE.getDefaultState(), 3);
			worldIn.playEvent(2009, pos, Block.getStateId(Blocks.WATER.getDefaultState()));
			worldIn.playSound((EntityPlayer)null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, (1.0F + worldIn.rand.nextFloat() * 0.2F) * 0.7F);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		BlockHelper.setWithUpdate(worldIn, pos, Blocks.AIR.getDefaultState());
		super.breakBlock(worldIn, pos, state);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
