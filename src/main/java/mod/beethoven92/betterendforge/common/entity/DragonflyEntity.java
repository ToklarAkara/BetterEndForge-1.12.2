package mod.beethoven92.betterendforge.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class DragonflyEntity extends EntityAnimal implements EntityFlying {
	public DragonflyEntity(World worldIn) {
		super(worldIn);
		setSize(0.6f, 0.5f);
		this.moveHelper = new EntityFlyHelper(this);
		//this.lookHelper = new DragonflyLookHelper(this);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
		this.experienceValue = 1;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(2, new EntityAIFollowParent(this, 1.0D));
		this.tasks.addTask(3, new WanderAroundGoal());
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue( 16.0);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue( 0.1);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue( 1.0);
	}


	@Override
	public float getBlockPathWeight(BlockPos pos) {
		return this.world.getBlockState(pos).getBlock()==Blocks.AIR ? 10.0F : 0.0F;
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateFlying flyingNavigator = new PathNavigateFlying(this, worldIn) {
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
		flyingNavigator.setCanEnterDoors(false);
		//flyingNavigator.setCanSwim(false);
		flyingNavigator.setCanOpenDoors(false);
		return flyingNavigator;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

//	@Override
//	public boolean onLivingFall(float distance, float damageMultiplier) {
//		return false;
//	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSoundEvents.ENTITY_DRAGONFLY;
	}

	@Override
	protected float getSoundVolume() {
		return ModMathHelper.randRange(0.25F, 0.5F, rand);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new DragonflyEntity(this.world);
	}

	@Override
	public boolean getCanSpawnHere() {
		BlockPos pos = getPosition();
		AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
		List<DragonflyEntity> list = world.getEntitiesWithinAABB(DragonflyEntity.class, box, (entity) -> true);
		Chunk chunk = world.getChunk(pos);
		int y = chunk.getHeightValue(pos.getX() & 15, pos.getZ() & 15);

		// FIX dragonfly spawning too much and preventing other entities to spawn
		return y > 0 && pos.getY() >= y && list.size() < 9;
	}
	public class DragonflyLookHelper extends EntityLookHelper {
		public DragonflyLookHelper(EntityLiving entity) {
			super(entity);
		}

//
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
			return DragonflyEntity.this.navigator.noPath() && DragonflyEntity.this.rand.nextInt(10) == 0;
		}

		@Override
		public boolean shouldContinueExecuting() {
			return !DragonflyEntity.this.navigator.noPath();
		}

		@Override
		public void startExecuting() {
			Vec3d vec3d = this.getRandomLocation();

			if (vec3d != null) {
				BlockPos pos = new BlockPos(vec3d);
				if (!pos.equals(DragonflyEntity.this.getPosition())) {
					Path path = DragonflyEntity.this.navigator.getPathToPos(new BlockPos(vec3d));
					if (path != null) {
						DragonflyEntity.this.navigator.setPath(path, 1.0D);
					}
				}
			}
			super.startExecuting();
		}

		private Vec3d getRandomLocation() {
			int h = BlockHelper.downRay(DragonflyEntity.this.world, DragonflyEntity.this.getPosition(), 16);

			Vec3d rotation = DragonflyEntity.this.getLook(0.0F);

			Vec3d airPos = RandomPositionGenerator.findRandomTargetBlockAwayFrom(DragonflyEntity.this, 8, 7, new Vec3d(0,0,0));

			if (airPos != null) {
				if (isInVoid(airPos)) {
					for (int i = 0; i < 8; i++) {
						airPos = RandomPositionGenerator.findRandomTargetBlockAwayFrom(DragonflyEntity.this, 16, 7, new Vec3d(0,0,0));
						if (airPos != null && !isInVoid(airPos)) {
							return airPos;
						}
					}
					return null;
				}
				if (h > 5 && airPos.y >= DragonflyEntity.this.getPosition().getY()) {
					airPos = new Vec3d(airPos.x, airPos.y - h * 0.5, airPos.z);
				}
				return airPos;
			}
			return RandomPositionGenerator.getLandPos(DragonflyEntity.this, 8, 4);
		}

		private boolean isInVoid(Vec3d pos) {
			int h = BlockHelper.downRay(DragonflyEntity.this.world, new BlockPos(pos), 128);
			return h > 100;
		}
	}
}
