package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TerrainBlock extends Block {
	private Block pathBlock;

	public TerrainBlock() {
		super(Material.ROCK);
		this.setTickRandomly(true);
		setSoundType(SoundType.GROUND);
	}

	public void setPathBlock(Block block) {
		pathBlock = block;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (pathBlock != null && playerIn.getHeldItemMainhand().getItem() instanceof ItemSpade) {
			worldIn.playSound(playerIn, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isRemote) {
				worldIn.setBlockState(pos, pathBlock.getDefaultState());
				if (!playerIn.capabilities.isCreativeMode) {
					playerIn.getHeldItemMainhand().damageItem(1, playerIn);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (rand.nextInt(16) == 0 && !canSurvive(state, worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
		}
	}

	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(this, 1, 0);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ItemBlock.getItemFromBlock(Blocks.END_STONE);
	}

	public static boolean canSurvive(IBlockState state, IBlockAccess world, BlockPos pos) {
		BlockPos blockPos = pos.up();
		IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() == Blocks.SNOW && blockState.getValue(BlockSnow.LAYERS) == 1) {
			return true;
		} else if (blockState.getMaterial() == Material.WATER && blockState.getValue(BlockLiquid.LEVEL) == 0) {
			return false;
		} else {
			return true;
		}
	}
}
