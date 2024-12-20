package mod.beethoven92.betterendforge.common.entity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public class EndSlimeEntity extends EntitySlime {
	private static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EndSlimeEntity.class, DataSerializers.BYTE);
	private static final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();

	public EndSlimeEntity(World worldIn) {
		super(worldIn);
		setSize(2f, 2f);
		this.moveHelper = new EndSlimeMoveHelper(this);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new SwimmingGoal());
		this.tasks.addTask(2, new FaceTowardTargetGoal());
		this.tasks.addTask(3, new RandomLookGoal());
		this.tasks.addTask(5, new MoveGoal());
		this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
		this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(VARIANT, (byte) 0);
	}

	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		IEntityLivingData data = super.onInitialSpawn(difficulty, livingdata);
		BetterEndBiome biome = ModBiomes.getFromBiome(world.getBiome(getPosition()));
		if (biome == ModBiomes.FOGGY_MUSHROOMLAND) {
			this.setMossy();
		}
		else if (biome == ModBiomes.MEGALAKE || biome == ModBiomes.MEGALAKE_GROVE) {
			this.setLake();
		}
		else if (biome == ModBiomes.AMBER_LAND) {
			this.setAmber(true);
		}
		return data;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setByte("Variant", (byte) getSlimeType());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("Variant")) {
			this.dataManager.set(VARIANT, compound.getByte("Variant"));
		}
	}

	@Override
	protected EnumParticleTypes getParticleType() {
		return EnumParticleTypes.PORTAL;
	}

	@Override
	public void setDead() {
		int i = this.getSlimeSize();
		if (!this.world.isRemote && i > 1 && !this.isDead) {
			String text = this.getCustomNameTag();
			boolean bl = this.isAIDisabled();
			float f = (float) i / 4.0F;
			int j = i / 2;
			int k = 2 + this.rand.nextInt(3);
			int type = this.getSlimeType();

			for (int l = 0; l < k; ++l) {
				float g = ((float) (l % 2) - 0.5F) * f;
				float h = ((float) (l / 2) - 0.5F) * f;
				EndSlimeEntity slimeEntity = new EndSlimeEntity(this.world);
				if (this.isNoDespawnRequired()) {
					slimeEntity.enablePersistence();
				}

				slimeEntity.setSlimeType(type);
				slimeEntity.setCustomNameTag(text);
				slimeEntity.setNoAI(bl);
				slimeEntity.setEntityInvulnerable(this.getIsInvulnerable());
				slimeEntity.setSlimeSize(j, true);
				slimeEntity.setLocationAndAngles(this.posX + (double) g, this.posY + 0.5D, this.posZ + (double) h, this.rand.nextFloat() * 360.0F, 0.0F);
				this.world.spawnEntity(slimeEntity);
			}
		}
		this.isDead = true;
	}

	public int getSlimeType() {
		return this.dataManager.get(VARIANT).intValue();
	}

	public void setSlimeType(int value) {
		this.dataManager.set(VARIANT, (byte) value);
	}

	protected void setMossy() {
		setSlimeType(1);
	}

	public boolean isMossy() {
		return getSlimeType() == 1;
	}

	protected void setLake() {
		setSlimeType(2);
	}

	public boolean isLake() {
		return getSlimeType() == 2;
	}

	protected void setAmber(boolean mossy) {
		this.dataManager.set(VARIANT, (byte) 3);
	}

	public boolean isAmber() {
		return this.dataManager.get(VARIANT) == 3;
	}

	public boolean isChorus() {
		return this.dataManager.get(VARIANT) == 0;
	}

	@Override
	public boolean getCanSpawnHere() {
		BlockPos pos = getPosition();
		return isPermanentBiome(world, pos) || (notManyEntities(world, pos, 32, 3) && isWaterNear(world, pos, 32, 8));
	}

	private static boolean isPermanentBiome(World world, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		return ModBiomes.getFromBiome(biome) == ModBiomes.CHORUS_FOREST;
	}

	private static boolean notManyEntities(World world, BlockPos pos, int radius, int maxCount) {
		AxisAlignedBB box = new AxisAlignedBB(pos).grow(radius);
		List<EndSlimeEntity> list = world.getEntitiesWithinAABB(EndSlimeEntity.class, box, (entity) -> true);
		return list.size() <= maxCount;
	}

	private static boolean isWaterNear(World world, BlockPos pos, int radius, int radius2) {
		for (int x = pos.getX() - radius; x <= pos.getX() + radius; x++) {
			POS.setPos(x, pos.getY(), pos.getZ());
			for (int z = pos.getZ() - radius; z <= pos.getZ() + radius; z++) {
				POS.setPos(x, pos.getY(), z);
				for (int y = pos.getY() - radius2; y <= pos.getY() + radius2; y++) {
					POS.setPos(x, y, z);
					if (world.getBlockState(POS).getBlock() == Blocks.WATER) {
						return true;
					}
				}
			}
		}
		return false;
	}

	class MoveGoal extends EntityAIBase {
		public MoveGoal() {
			this.setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			if (EndSlimeEntity.this.isBeingRidden()) {
				return false;
			}

			float yaw = EndSlimeEntity.this.rotationYawHead;
			float speed = EndSlimeEntity.this.getAIMoveSpeed();
			if (speed > 0.1) {
				float dx = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
				float dz = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
				BlockPos pos = EndSlimeEntity.this.getPosition().add(dx * speed * 4, 0, dz * speed * 4);
				int down = BlockHelper.downRay(EndSlimeEntity.this.world, pos, 16);
				return down < 5;
			}

			return true;
		}

		@Override
		public void updateTask() {
			((EndSlimeMoveHelper) EndSlimeEntity.this.getMoveHelper()).move(1.0D);
		}
	}

	class SwimmingGoal extends EntityAIBase {
		public SwimmingGoal() {
			this.setMutexBits(2);
			if (EndSlimeEntity.this.getNavigator() instanceof PathNavigateGround)
			{
				((PathNavigateGround)EndSlimeEntity.this.getNavigator()).setCanSwim(true);
			}
			else if (EndSlimeEntity.this.getNavigator() instanceof PathNavigateFlying)
			{
				((PathNavigateFlying)EndSlimeEntity.this.getNavigator()).setCanFloat(true);
			}
		}

		@Override
		public boolean shouldExecute() {
			return (EndSlimeEntity.this.isInWater() || EndSlimeEntity.this.isInLava()) && EndSlimeEntity.this.getMoveHelper() instanceof EndSlimeMoveHelper;
		}

		@Override
		public void updateTask() {
			if (EndSlimeEntity.this.getRNG().nextFloat() < 0.8F) {
				EndSlimeEntity.this.getJumpHelper().setJumping();
			}

			((EndSlimeMoveHelper) EndSlimeEntity.this.getMoveHelper()).move(1.2D);
		}
	}


	class RandomLookGoal extends EntityAIBase {
		private float targetYaw;
		private int timer;

		public RandomLookGoal() {
			this.setMutexBits(1);
		}

		@Override
		public boolean shouldExecute() {
			return EndSlimeEntity.this.getAttackTarget() == null
					&& (EndSlimeEntity.this.onGround || EndSlimeEntity.this.isInWater()
					|| EndSlimeEntity.this.isInLava());
		}

		@Override
		public void updateTask() {
			if (--this.timer <= 0) {
				this.timer = 40 + EndSlimeEntity.this.getRNG().nextInt(60);
				this.targetYaw = (float) EndSlimeEntity.this.getRNG().nextInt(360);
			}

			((EndSlimeMoveHelper) EndSlimeEntity.this.getMoveHelper()).look(this.targetYaw, false);
		}
	}

	class FaceTowardTargetGoal extends EntityAIBase {
		private int ticksLeft;

		public FaceTowardTargetGoal() {
			this.setMutexBits(1);
		}

		@Override
		public boolean shouldExecute() {
			EntityLivingBase target = EndSlimeEntity.this.getAttackTarget();
			return target != null && target.isEntityAlive();
		}

		@Override
		public void startExecuting() {
			this.ticksLeft = 300;
			super.startExecuting();
		}

		@Override
		public boolean shouldContinueExecuting() {
			EntityLivingBase target = EndSlimeEntity.this.getAttackTarget();
			return target != null && target.isEntityAlive() && --this.ticksLeft > 0;
		}

		@Override
		public void updateTask() {
			EndSlimeEntity.this.faceEntity(EndSlimeEntity.this.getAttackTarget(), 10.0F, 10.0F);
			((EndSlimeMoveHelper) EndSlimeEntity.this.getMoveHelper()).look(EndSlimeEntity.this.rotationYaw, EndSlimeEntity.this.canDamagePlayer());
		}
	}

	class EndSlimeMoveHelper extends EntityMoveHelper {
		private float targetYaw;
		private int ticksUntilJump;
		private boolean jumpOften;

		public EndSlimeMoveHelper(EndSlimeEntity slime) {
			super(slime);
			this.targetYaw = 180.0F * slime.rotationYaw / (float) Math.PI;
		}

		public void look(float targetYaw, boolean jumpOften) {
			this.targetYaw = targetYaw;
			this.jumpOften = jumpOften;
		}

		public void move(double speed) {
			this.speed = speed;
			this.action = EntityMoveHelper.Action.MOVE_TO;
		}

		@Override
		public void onUpdateMoveHelper() {
			this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.targetYaw, 90.0F);
			this.entity.rotationYawHead = this.entity.rotationYaw;
			this.entity.renderYawOffset = this.entity.rotationYaw;
			if (this.action != EntityMoveHelper.Action.MOVE_TO) {
				this.entity.setMoveForward(0.0F);
			} else {
				this.action = EntityMoveHelper.Action.WAIT;
				if (this.entity.onGround) {
					this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
					if (this.ticksUntilJump-- <= 0) {
						this.ticksUntilJump = EndSlimeEntity.this.getJumpDelay();
						if (this.jumpOften) {
							this.ticksUntilJump /= 3;
						}

						EndSlimeEntity.this.getJumpHelper().setJumping();
						if (EndSlimeEntity.this.makesSoundOnJump()) {
							EndSlimeEntity.this.playSound(EndSlimeEntity.this.getJumpSound(), EndSlimeEntity.this.getSoundVolume(), getJumpSoundPitch());
						}
					} else {
						EndSlimeEntity.this.moveStrafing = 0.0F;
						EndSlimeEntity.this.moveForward = 0.0F;
						this.entity.setAIMoveSpeed(0.0F);
					}
				} else {
					this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
				}
			}
		}

		private float getJumpSoundPitch() {
			float f = EndSlimeEntity.this.isSmallSlime() ? 1.4F : 0.8F;
			return ((EndSlimeEntity.this.rand.nextFloat() - EndSlimeEntity.this.rand.nextFloat()) * 0.2F + 1.0F) * f;
		}
	}
}
