package mod.beethoven92.betterendforge.common.entity;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.SilkMothNestBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public class SilkMothEntity extends EntityAnimal implements EntityFlying {
	private BlockPos hivePos;
	private BlockPos entrance;
	private World hiveWorld;

	public SilkMothEntity(World worldIn) {
		super(worldIn);
		setSize(0.6f, 0.6f);
		this.moveHelper = new EntityFlyHelper(this);
		//this.lookHelper = new MothLookHelper(this);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
		this.experienceValue = 1;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue( 16.0);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue( 0.4);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue( 0.1);
	}
	public void setHive(World world, BlockPos hive) {
		this.hivePos = hive;
		this.hiveWorld = world;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		if (hivePos != null) {
			tag.setTag("HivePos", NBTUtil.createPosTag(hivePos));
			tag.setInteger("HiveWorld", hiveWorld.provider.getDimension());
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
		if (tag.hasKey("HivePos")) {
			hivePos = NBTUtil.getPosFromTag(tag.getCompoundTag("HivePos"));
			try {
				hiveWorld = world.getMinecraftServer().getWorld(tag.getInteger("HiveWorld"));
			} catch (Exception e) {
				BetterEnd.LOGGER.warn("Silk Moth Hive World {} is missing!", tag.getInteger("HiveWorld"));
				hivePos = null;
			}
		}
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new ReturnToHiveGoal());
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(5, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(8, new WanderAroundGoal());
		this.tasks.addTask(9, new EntityAISwimming(this));
	}

	public void fall(float distance, float damageMultiplier)
	{
	}

	@Override
	protected PathNavigate createNavigator(World world) {
		PathNavigateFlying birdNavigation = new PathNavigateFlying(this, world) {
			@Override
			public boolean canEntityStandOnPos(BlockPos pos) {
				IBlockState state = this.world.getBlockState(pos);
				return state.getBlock()== Blocks.AIR || !state.getMaterial().blocksMovement();
			}

			@Override
			public void onUpdateNavigation() {
				super.onUpdateNavigation();
			}
		};
		birdNavigation.setCanEnterDoors(false);
		birdNavigation.setCanFloat(false);
		birdNavigation.setCanOpenDoors(true);
		return birdNavigation;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

//	@Override
//	public boolean onLivingFall(float fallDistance, float damageMultiplier) {
//		return false;
//	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new SilkMothEntity(this.world);
	}

	@Override
	public boolean getCanSpawnHere() {
		BlockPos pos = getPosition();
		Chunk chunk = world.getChunk(pos);
		int y = chunk.getHeightValue(pos.getX() & 15, pos.getZ() & 15);
		return y > 0 && pos.getY() >= y;
	}

	class MothLookHelper extends EntityLookHelper {
		public MothLookHelper(EntityLiving entity) {
			super(entity);
		}

//		@Override
//		protected boolean shouldResetPitch() {
//			return true;
//		}
	}

	class WanderAroundGoal extends EntityAIBase {
		WanderAroundGoal() {
			this.setMutexBits(0);
		}

		@Override
		public boolean shouldExecute() {
			return SilkMothEntity.this.navigator.noPath() && SilkMothEntity.this.rand.nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinueExecuting() {
			return !SilkMothEntity.this.navigator.noPath();
		}

		@Override
		public void startExecuting() {
			Vec3d vec3d = this.getRandomLocation();
			if (vec3d != null) {
				try {
					SilkMothEntity.this.navigator.setPath(SilkMothEntity.this.navigator.getPathToPos(new BlockPos(vec3d)), 1.0D);
				} catch (Exception e) {
				}
			}
		}

		@Nullable
		private Vec3d getRandomLocation() {
			Vec3d vec3d3 = SilkMothEntity.this.getLook(0.0F);
			Vec3d vec3d4 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(SilkMothEntity.this, 8, 7, new Vec3d(0,0,0));
			return vec3d4 != null ? vec3d4 : RandomPositionGenerator.getLandPos(SilkMothEntity.this, 8, 4);
		}
	}

	class ReturnToHiveGoal extends EntityAIBase {
		ReturnToHiveGoal() {
			this.setMutexBits(0);
		}

		@Override
		public boolean shouldExecute() {
			return SilkMothEntity.this.hivePos != null && SilkMothEntity.this.hiveWorld == SilkMothEntity.this.world
					&& SilkMothEntity.this.navigator.noPath() && SilkMothEntity.this.rand.nextInt(16) == 0
					&& SilkMothEntity.this.getPositionVector().squareDistanceTo(SilkMothEntity.this.hivePos.getX(),
					SilkMothEntity.this.hivePos.getY(), SilkMothEntity.this.hivePos.getZ()) < 32;
		}

		@Override
		public boolean shouldContinueExecuting() {
			return !SilkMothEntity.this.navigator.noPath() && world.getBlockState(entrance).getBlock()==Blocks.AIR
					&& world.getBlockState(hivePos).getBlock() == ModBlocks.SILK_MOTH_NEST;
		}

		@Override
		public void startExecuting() {
			IBlockState state = SilkMothEntity.this.world.getBlockState(SilkMothEntity.this.hivePos);
			if (state.getBlock() != ModBlocks.SILK_MOTH_NEST) {
				SilkMothEntity.this.hivePos = null;
				SilkMothEntity.this.entrance = null;
			} else {
				SilkMothEntity.this.entrance = SilkMothEntity.this.hivePos.offset(state.getValue(SilkMothNestBlock.FACING));
				SilkMothEntity.this.navigator.setPath(SilkMothEntity.this.navigator.getPathToPos(entrance), 1.0D);
			}
		}

		@Override
		public void updateTask() {
			if (SilkMothEntity.this.hivePos != null && SilkMothEntity.this.entrance != null) {
				super.updateTask();
				double dx = Math.abs(SilkMothEntity.this.entrance.getX() - SilkMothEntity.this.posX);
				double dy = Math.abs(SilkMothEntity.this.entrance.getY() - SilkMothEntity.this.posY);
				double dz = Math.abs(SilkMothEntity.this.entrance.getZ() - SilkMothEntity.this.posZ);
				if (dx + dy + dz < 1) {
					IBlockState state = SilkMothEntity.this.world.getBlockState(hivePos);
					if (state.getBlock() == ModBlocks.SILK_MOTH_NEST) {
						int fullness = state.getValue(BlockProperties.FULLNESS);
						if (fullness < 3 && SilkMothEntity.this.rand.nextBoolean()) {
							fullness++;
							BlockHelper.setWithUpdate(SilkMothEntity.this.hiveWorld, SilkMothEntity.this.hivePos, state.withProperty(BlockProperties.FULLNESS, fullness));
						}
						SilkMothEntity.this.world.playSound(null, SilkMothEntity.this.entrance, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1, 1);
						SilkMothEntity.this.setDead();
					}
				}
			}
		}
	}
}
