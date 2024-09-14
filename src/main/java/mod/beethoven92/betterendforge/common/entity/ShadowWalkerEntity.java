package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShadowWalkerEntity extends EntityCreature
{
	public ShadowWalkerEntity(World worldIn)
	{
		super(worldIn);
		this.setSize(0.6F, 1.95F);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.5D);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
//		world.spawnParticle(EnumParticleTypes.ASH,
//				posX + rand.nextGaussian() * 0.2,
//				posY + rand.nextGaussian() * 0.5 + 1,
//				posZ + rand.nextGaussian() * 0.2,
//				0, 0, 0);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
				posX + rand.nextGaussian() * 0.2,
				posY + rand.nextGaussian() * 0.5 + 1,
				posZ + rand.nextGaussian() * 0.2,
				0, 0, 0);
		world.spawnParticle(EnumParticleTypes.SPELL_MOB,
				posX + rand.nextGaussian() * 0.2,
				posY + rand.nextGaussian() * 0.5 + 1,
				posZ + rand.nextGaussian() * 0.2,
				0, 0, 0);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ModSoundEvents.ENTITY_SHADOW_WALKER;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return ModSoundEvents.ENTITY_SHADOW_WALKER_DAMAGE;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ModSoundEvents.ENTITY_SHADOW_WALKER_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {

	}

	@Override
	protected float getSoundVolume()
	{
		return ModMathHelper.randRange(0.25F, 0.5F, rand);
	}

	@Override
	protected float getSoundPitch()
	{
		return ModMathHelper.randRange(0.75F, 1.25F, rand);
	}

	@Override
	public boolean getCanSpawnHere() {
		if (world.getLight(getPosition()) < 8)
		{
			AxisAlignedBB box = new AxisAlignedBB(getPosition()).grow(16);
			List<ShadowWalkerEntity> entities = world.getEntitiesWithinAABB(ShadowWalkerEntity.class, box);
			return entities.size() < 6;
		}
		return false;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		boolean attack = super.attackEntityAsMob(entityIn);
		if (attack && entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entityIn;
			if (!living.isPotionActive(MobEffects.BLINDNESS))
			{
				living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60));
			}
		}
		return attack;
	}

	private final class AttackGoal extends EntityAIAttackMelee
	{
		private final ShadowWalkerEntity walker;
		private int ticks;

		public AttackGoal(ShadowWalkerEntity walker, double speed, boolean pauseWhenMobIdle)
		{
			super(walker, speed, pauseWhenMobIdle);
			this.walker = walker;
		}

		@Override
		public void startExecuting()
		{
			super.startExecuting();
			this.ticks = 0;
		}

		@Override
		public void resetTask()
		{
			super.resetTask();
			//this.walker.setAggroed(false);
		}

		@Override
		public void updateTask()
		{
			super.updateTask();
			++this.ticks;
			if (this.ticks >= 5 && this.attackTick < this.attackInterval / 2)
			{
				//this.walker.setAggroed(true);
			}
			else
			{
				//this.walker.setAggroed(false);
			}
		}
	}
}
