package mod.beethoven92.betterendforge.common.block;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RespawnObeliskBlock extends Block {
	private static final AxisAlignedBB VOXEL_SHAPE_BOTTOM = new AxisAlignedBB(1 / 16.0, 0, 1 / 16.0, 15 / 16.0, 16 / 16.0, 15 / 16.0);
	private static final AxisAlignedBB VOXEL_SHAPE_MIDDLE_TOP = new AxisAlignedBB(2 / 16.0, 0, 2 / 16.0, 14 / 16.0, 16 / 16.0, 14 / 16.0);

	public static final PropertyEnum<TripleShape> SHAPE = PropertyEnum.create("shape", TripleShape.class);

	public RespawnObeliskBlock() {
		super(Material.ROCK);
		this.setHardness(3.0F);
		this.setResistance(9.0F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return (state.getValue(SHAPE) == TripleShape.BOTTOM) ? VOXEL_SHAPE_BOTTOM : VOXEL_SHAPE_MIDDLE_TOP;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		for (int i = 0; i < 3; i++) {
			if (!worldIn.getBlockState(pos.up(i)).getMaterial().isReplaceable()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, @Nullable EntityLivingBase placer, ItemStack stack) {
		state = this.getDefaultState();
		BlockHelper.setWithUpdate(worldIn, pos, state.withProperty(SHAPE, TripleShape.BOTTOM));
		BlockHelper.setWithUpdate(worldIn, pos.up(), state.withProperty(SHAPE, TripleShape.MIDDLE));
		BlockHelper.setWithUpdate(worldIn, pos.up(2), state.withProperty(SHAPE, TripleShape.TOP));
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos currentPos) {
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			if (worldIn.getBlockState(currentPos.up()).getBlock() == this) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		} else if (shape == TripleShape.MIDDLE) {
			if (worldIn.getBlockState(currentPos.down()).getBlock() == this && worldIn.getBlockState(currentPos.down()).getBlock() == this) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		} else {
			if (worldIn.getBlockState(currentPos.down()).getBlock() == this) {
				return state;
			} else {
				return Blocks.AIR.getDefaultState();
			}
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.MIDDLE) {
			BlockHelper.setWithUpdate(worldIn, pos.down(), Blocks.AIR.getDefaultState());
			BlockHelper.setWithUpdate(worldIn, pos.up(), Blocks.AIR.getDefaultState());
		} else if (shape == TripleShape.TOP) {
			BlockHelper.setWithUpdate(worldIn, pos.down(), Blocks.AIR.getDefaultState());
			BlockHelper.setWithUpdate(worldIn, pos.down(2), Blocks.AIR.getDefaultState());
		} else if (shape == TripleShape.BOTTOM) {
			BlockHelper.setWithUpdate(worldIn, pos.up(), Blocks.AIR.getDefaultState());
			BlockHelper.setWithUpdate(worldIn, pos.up(2), Blocks.AIR.getDefaultState());
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemStack = player.getHeldItem(hand);
		boolean canActivate = itemStack.getItem() == ModItems.AMBER_GEM && itemStack.getCount() > 5;
		if (hand != EnumHand.MAIN_HAND || !canActivate) {
			if (!worldIn.isRemote && !(itemStack.getItem() instanceof ItemBlock) && !player.capabilities.isCreativeMode) {
				EntityPlayerMP serverPlayerEntity = (EntityPlayerMP) player;
				serverPlayerEntity.sendMessage(new TextComponentTranslation("message.betterendforge.fail_spawn"));
			}
			return false;
		} else if (!worldIn.isRemote) {
			EntityPlayerMP serverPlayerEntity = (EntityPlayerMP) player;
			serverPlayerEntity.setSpawnPoint(pos, false);
			serverPlayerEntity.sendMessage(new TextComponentTranslation("message.betterendforge.set_spawn"));
			double px = pos.getX() + 0.5;
			double py = pos.getY() + 0.5;
			double pz = pos.getZ() + 0.5;
			if (worldIn instanceof WorldServer) {
				double py1 = py;
				double py2 = py - 0.2;
				if (state.getValue(SHAPE) == TripleShape.BOTTOM) {
					py1 += 1;
					py2 += 2;
				} else if (state.getValue(SHAPE) == TripleShape.MIDDLE) {
					py1 += 0;
					py2 += 1;
				} else {
					py1 -= 2;
				}
				((WorldServer) worldIn).spawnParticle(EnumParticleTypes.END_ROD, px, py1, pz, 20, 0.14, 0.5, 0.14, 0.1);
				((WorldServer) worldIn).spawnParticle(EnumParticleTypes.END_ROD, px, py2, pz, 20, 0.14, 0.3, 0.14, 0.1);
			}
			worldIn.playSound(null, px, py, pz, SoundEvents.BLOCK_PORTAL_TRIGGER, SoundCategory.BLOCKS, 1F, 1F);
			if (!player.capabilities.isCreativeMode) {
				itemStack.shrink(6);
			}
		}
		return !player.capabilities.isCreativeMode;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(SHAPE, TripleShape.values()[meta]);
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
