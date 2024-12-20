package mod.beethoven92.betterendforge.common.entity;

import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EndFishEntity extends EntityAnimal implements EntityFlying {
	public static final int VARIANTS_NORMAL = 5;
	public static final int VARIANTS_SULPHUR = 3;
	public static final int VARIANTS = VARIANTS_NORMAL + VARIANTS_SULPHUR;

	private static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(EndFishEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> SCALE = EntityDataManager.createKey(EndFishEntity.class, DataSerializers.BYTE);

	public EndFishEntity(World worldIn) {
		super(worldIn);
		setSize(0.5f, 0.5f);
	}


	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EndFishEntity(ageable.world);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(VARIANT, (byte) this.rand.nextInt(VARIANTS));
		this.dataManager.register(SCALE, (byte) this.rand.nextInt(16));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setByte("Variant", (byte) this.getVariant());
		compound.setByte("Scale", dataManager.get(SCALE));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (compound.hasKey("Variant")) {
			this.dataManager.set(VARIANT, compound.getByte("Variant"));
		}
		if (compound.hasKey("Scale")) {
			this.dataManager.set(SCALE, compound.getByte("Scale"));
		}
	}

//	@Override
//	protected ItemStack getFishBucket() {
//		ItemStack bucket = new ItemStack(ModItems.BUCKET_END_FISH);
//		NBTTagCompound tag = bucket.getTagCompound();
//		tag.setByte("variant", dataManager.get(VARIANT));
//		tag.setByte("scale", dataManager.get(SCALE));
//		return bucket;
//	} TODO FISH BUCKET

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (rand.nextInt(8) == 0 && this.isInWater()) {
			double x = this.posX + rand.nextGaussian() * 0.2;
			double y = this.posY + rand.nextGaussian() * 0.2;
			double z = this.posZ + rand.nextGaussian() * 0.2;
			this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, x, y, z, 0, 0, 0);
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue( 16.0);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue( 0.75);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	public int getVariant() {
		return this.dataManager.get(VARIANT);
	}

	public float getScale() {
		return this.dataManager.get(SCALE) / 32F + 0.75F;
	}

	@Override
	public boolean getCanSpawnHere() {
		AxisAlignedBB box = new AxisAlignedBB(getPosition()).grow(16);
		List<EndFishEntity> list = world.getEntitiesWithinAABB(EndFishEntity.class, box, (entity) -> true);
		return list.size() < 9;
	}
}
