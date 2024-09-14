package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.interfaces.TeleportingEntity;
import mod.beethoven92.betterendforge.common.teleporter.BetterEndTeleporter;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EndPortalBlock extends BlockPortal
{

	public static final PropertyInteger PORTAL = BlockProperties.PORTAL;
	public EndPortalBlock()
	{
		super();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(100) == 0)
		{
			worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		double x = pos.getX() + rand.nextDouble();
		double y = pos.getY() + rand.nextDouble();
		double z = pos.getZ() + rand.nextDouble();
		int k = rand.nextInt(2) * 2 - 1;
		if (!worldIn.getBlockState(pos.west()).getBlock().equals(this) && !worldIn.getBlockState(pos.east()).getBlock().equals(this))
		{
			x = pos.getX() + 0.5D + 0.25D * k;
		}
		else
		{
			z = pos.getZ() + 0.5D + 0.25D * k;
		}

		worldIn.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, 0, 0, 0);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state;
	}

	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (!worldIn.isRemote && !entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
		{
			TeleportingEntity teleEntity = (TeleportingEntity) entityIn;

			if (entityIn.timeUntilPortal > 0) return;

			boolean isOverworld = worldIn.provider.getDimensionType() == DimensionType.OVERWORLD;
			MinecraftServer server = worldIn.getMinecraftServer();
			WorldServer destination = isOverworld ? server.getWorld(DimensionType.THE_END.getId()) : EndPortals.getWorld(server, state.getValue(PORTAL));

			if (destination == null)
			{
				return;
			}

			BlockPos exitPos = this.findExitPos(destination, pos, entityIn);

			if (exitPos == null)
			{
				return;
			}

			if (entityIn instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP) entityIn;
				player.changeDimension(destination.provider.getDimension(), new BetterEndTeleporter(exitPos));
				player.timeUntilPortal = player.capabilities.isCreativeMode ? 50 : 300;
			}
			else
			{
				teleEntity.beSetExitPos(exitPos);
				entityIn.changeDimension(destination.provider.getDimension());
				entityIn.timeUntilPortal = 300;
			}
		}
	}

	private BlockPos findExitPos(WorldServer world, BlockPos pos, Entity entity)
	{
		double mult = world.provider.getMovementFactor();
		BlockPos.MutableBlockPos basePos;

		if (world.provider.getDimensionType() != DimensionType.THE_END)
		{
			basePos = new BlockPos.MutableBlockPos((int) (pos.getX() / mult), pos.getY(), (int) (pos.getZ() / mult));
		}
		else
		{
			basePos = new BlockPos.MutableBlockPos((int) (pos.getX() * mult), pos.getY(), (int) (pos.getZ() * mult));
		}

		EnumFacing direction = EnumFacing.EAST;
		BlockPos.MutableBlockPos checkPos = basePos;

		for (int step = 1; step < 128; step++)
		{
			for (int i = 0; i < (step >> 1); i++)
			{
				Chunk chunk = world.getChunk(checkPos);

				if (chunk != null)
				{
					int ceil = chunk.getHeightValue(checkPos.getX() & 15, checkPos.getZ() & 15);
					if (ceil > 5)
					{
						checkPos.setY(ceil);
						while (checkPos.getY() > 5)
						{
							IBlockState state = world.getBlockState(checkPos);
							if (state.getBlock() == this)
							{
								Axis axis = state.getValue(AXIS);
								checkPos = this.findCenter(world, checkPos, axis);

								EnumFacing frontDir = EnumFacing.getFacingFromAxis(AxisDirection.POSITIVE, axis).rotateY();
								EnumFacing entityDir = entity.getHorizontalFacing();
								if (entityDir.getAxis().isVertical())
								{
									entityDir = frontDir;
								}

								if (frontDir == entityDir || frontDir.getOpposite() == entityDir)
								{
									return checkPos.offset(entityDir);
								}
								else
								{
									entity.rotationYaw = entity.rotationYaw + 90;
									entityDir = entityDir.rotateY();
									return checkPos.offset(entityDir);
								}
							}
							checkPos.move(EnumFacing.DOWN);
						}
					}
				}
				checkPos.move(direction);
			}
			direction = direction.rotateY();
		}
		return null;
	}

	private BlockPos.MutableBlockPos findCenter(World world, BlockPos.MutableBlockPos pos, EnumFacing.Axis axis)
	{
		return this.findCenter(world, pos, axis, 1);
	}

	private BlockPos.MutableBlockPos findCenter(World world, BlockPos.MutableBlockPos pos, EnumFacing.Axis axis, int step)
	{
		if (step > 8) return pos;

		IBlockState right, left;
		EnumFacing rightDir, leftDir;

		rightDir = EnumFacing.getFacingFromAxis(AxisDirection.POSITIVE, axis);
		leftDir = rightDir.getOpposite();
		right = world.getBlockState(pos.offset(rightDir));
		left = world.getBlockState(pos.offset(leftDir));

		IBlockState down = world.getBlockState(pos.down());

		if (down.getBlock() == this)
		{
			return findCenter(world, pos.move(EnumFacing.DOWN), axis, step);
		}
		else if (right.getBlock() == this && left.getBlock() == this)
		{
			return pos;
		}
		else if (right.getBlock() == this)
		{
			return findCenter(world, pos.move(rightDir), axis, ++step);
		}
		else if (left.getBlock() == this)
		{
			return findCenter(world, pos.move(leftDir), axis, ++step);
		}
		return pos;
	}
}
