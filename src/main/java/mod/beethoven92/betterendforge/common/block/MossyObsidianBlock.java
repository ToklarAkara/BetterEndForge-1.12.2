package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MossyObsidianBlock extends Block {
	public MossyObsidianBlock() {
		super(Material.ROCK);
		this.setHardness(50.0F).setResistance(2000.0F);
		this.setTickRandomly(true);
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
		if (random.nextInt(16) == 0 && !canSurvive(state, world, pos)) {
			world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
		}
	}

	public static boolean canSurvive(IBlockState state, World world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() == Blocks.SNOW && blockState.getValue(BlockSnow.LAYERS) == 1) {
			return true;
		} else if (blockState.getMaterial() == Material.WATER && blockState.getValue(BlockLiquid.LEVEL) == 0) {
			return false;
		} else {
			int lightLevel = world.getLightFor(EnumSkyBlock.SKY, pos);
			return lightLevel < 5;
		}
	}

	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(this, 1, 0);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemBlock.getItemFromBlock(Blocks.OBSIDIAN);
	}
}
