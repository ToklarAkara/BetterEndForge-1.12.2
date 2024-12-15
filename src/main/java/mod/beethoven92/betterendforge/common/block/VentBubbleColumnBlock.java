package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VentBubbleColumnBlock extends Block {
	public VentBubbleColumnBlock() {
		super(Material.AIR);
		this.setTickRandomly(true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return Block.NULL_AABB;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState blockState = worldIn.getBlockState(pos.down());
		return blockState.getBlock() == this || blockState.getBlock() == ModBlocks.HYDROTHERMAL_VENT;
	}



	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (!this.canPlaceBlockAt(worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
		} else {
			BlockPos up = pos.up();
			if (worldIn.getBlockState(up).getBlock() == Blocks.WATER) {
				BlockHelper.setWithoutUpdate(worldIn, up, this.getDefaultState());
				worldIn.scheduleUpdate(up, this, 5);
			}
			worldIn.setBlockState(pos, this.getDefaultState());
		}
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		IBlockState blockState = worldIn.getBlockState(pos.up());
		if (blockState.getBlock().isAir(blockState, worldIn, pos.up())) {
			//entityIn.onEnterBubbleColumnWithAirAbove(false); TODO BUBBLES
			if (!worldIn.isRemote) {
				for (int i = 0; i < 2; ++i) {
					worldIn.spawnParticle(EnumParticleTypes.WATER_SPLASH, (double) pos.getX() + worldIn.rand.nextDouble(), (double) (pos.getY() + 1), (double) pos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, (double) pos.getX() + worldIn.rand.nextDouble(), (double) (pos.getY() + 1), (double) pos.getZ() + worldIn.rand.nextDouble(), 0.0D, 0.01D, 0.0D);
				}
			}
		} else {
			//entityIn.onEnterBubbleColumn(false); TODO BUBBLES
		}
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(4) == 0) {
			double px = pos.getX() + rand.nextDouble();
			double py = pos.getY() + rand.nextDouble();
			double pz = pos.getZ() + rand.nextDouble();
			worldIn.spawnParticle(EnumParticleTypes.WATER_BUBBLE, px, py, pz, 0, 0.04, 0);
		}
		if (rand.nextInt(200) == 0) {
			worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		return false;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(Blocks.WATER);
	}

}
