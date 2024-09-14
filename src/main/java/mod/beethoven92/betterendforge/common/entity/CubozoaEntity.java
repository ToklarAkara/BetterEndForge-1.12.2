package mod.beethoven92.betterendforge.common.entity;

import java.util.List;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CubozoaEntity extends EntityWaterMob {
	public static final int VARIANTS = 2;
	private static final DataParameter<Byte> VARIANT = EntityDataManager.createKey(CubozoaEntity.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> SCALE = EntityDataManager.createKey(CubozoaEntity.class, DataSerializers.BYTE);

	public CubozoaEntity(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(VARIANT, (byte) 0);
		this.dataManager.register(SCALE, (byte) this.rand.nextInt(16));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setByte("Variant", (byte) getVariant());
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

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue( 16.0);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue( 0.5);
	}

	public int getVariant() {
		return (int) this.dataManager.get(VARIANT);
	}

	public float getScale() {
		return this.dataManager.get(SCALE) / 32F + 0.75F;
	}

	@Override
	public boolean getCanSpawnHere() {
		AxisAlignedBB box = new AxisAlignedBB(getPosition()).grow(16);
		List<CubozoaEntity> list = world.getEntitiesWithinAABB(CubozoaEntity.class, box, (entity) -> true);
		return list.size() < 9;
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}

//	@Override
//	protected ItemStack getFishBucket() {
//		ItemStack bucket = new ItemStack(ModItems.BUCKET_CUBOZOA);
//		NBTTagCompound tag = bucket.getOrCreateTag();
//		tag.setByte("variant", dataManager.get(VARIANT));
//		tag.setByte("scale", dataManager.get(SCALE));
//		return bucket;
//	}
//
//	@Override
//	protected SoundEvent getFlopSound() {
//		return SoundEvents.ENTITY_SALMON_FLOP;
//	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if (player instanceof EntityPlayerMP && player.attackEntityFrom(DamageSource.causeMobDamage(this), 0.5F)) {
			if (!this.isSilent()) {
				((EntityPlayerMP) player).connection.sendPacket(new SPacketChangeGameState(10, 0.0F));
			}
			if (rand.nextBoolean()) {
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 20, 0)); // 19 is the ID for Poison
			}
		}
	}
}
