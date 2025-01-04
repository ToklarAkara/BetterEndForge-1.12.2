package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EmeraldIceBlock extends BlockIce
{
	public EmeraldIceBlock()
	{
		super();
		this.slipperiness = 0.98F;
		this.setTickRandomly(true);
		setSoundType(SoundType.GLASS);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0)
		{
			if (worldIn.provider.doesWaterVaporize())
			{
				worldIn.setBlockToAir(pos);
				return;
			}

			Material material = worldIn.getBlockState(pos.down()).getMaterial();
			if (material.blocksMovement() || material.isLiquid())
			{
				worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
			}
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11 - state.getLightOpacity(worldIn, pos))
		{
			this.melt(worldIn, pos);
		}
	}

	protected void melt(World world, BlockPos pos)
	{
		if (world.provider.doesWaterVaporize())
		{
			world.setBlockToAir(pos);
		}
		else
		{
			world.setBlockState(pos, Blocks.WATER.getDefaultState());
			world.notifyNeighborsOfStateChange(pos, Blocks.WATER, false);
		}
	}
}
