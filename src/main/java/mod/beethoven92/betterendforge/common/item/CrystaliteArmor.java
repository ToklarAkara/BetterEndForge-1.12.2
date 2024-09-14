package mod.beethoven92.betterendforge.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.client.model.CrystaliteBootsModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteChestplateModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteHelmetModel;
import mod.beethoven92.betterendforge.client.model.CrystaliteLeggingsModel;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class CrystaliteArmor extends ItemArmor {

	private static final UUID[] UUIDS = new UUID[] { UUID.fromString("38c9722b-d905-4b84-940d-551c803100af"),
			UUID.fromString("a7bab7bb-9f3b-4787-9ff7-f138c9c17f6c"),
			UUID.fromString("ccf857e1-86e3-40d5-9f51-fe624d54cc33"),
			UUID.fromString("ed88885e-1882-4989-a4b3-8aa32e2b02d3") };

	@SideOnly(Side.CLIENT)
	CrystaliteBootsModel bootsModel;
	@SideOnly(Side.CLIENT)
	CrystaliteChestplateModel slimChestplateModel;
	@SideOnly(Side.CLIENT)
	CrystaliteChestplateModel chestplateModel;
	@SideOnly(Side.CLIENT)
	CrystaliteHelmetModel helmetModel;
	@SideOnly(Side.CLIENT)
	CrystaliteLeggingsModel leggingsModel;

	public CrystaliteArmor(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);
		ImmutableMultimap.Builder<String, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(modifiers);
		Item item = stack.getItem();
		if (item == ModItems.CRYSTALITE_HELMET && slot == EntityEquipmentSlot.HEAD)
			builder.put(ModAttributes.BLINDNESS_RESISTANCE.getName(), new AttributeModifier(UUIDS[slot.getIndex()],
					"Helmet blindness resistance", 1, 0));
		else if (item == ModItems.CRYSTALITE_LEGGINGS && slot == EntityEquipmentSlot.LEGS)
			builder.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(UUIDS[slot.getIndex()],
					"Armor health boost", 4.0, 0));
		return builder.build();
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (player.ticksExisted % 60 == 0) {
			Item item = stack.getItem();

			if (item == ModItems.CRYSTALITE_BOOTS)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 80, 0, true, false));
			else if (item == ModItems.CRYSTALITE_CHESTPLATE)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 80, 0, true, false));

			if (hasFullSet(player))
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 80, 0, true, false));
		}
	}

	private boolean hasFullSet(EntityPlayer player) {
		for (ItemStack armorStack : player.inventory.armorInventory) {
			if (!(armorStack.getItem() instanceof CrystaliteArmor)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return BetterEnd.MOD_ID + ":textures/models/armor/crystalite_layer_"
				+ (slot == EntityEquipmentSlot.LEGS ? "2" : "1") + ".png";
	}


	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack,
												  EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (bootsModel == null) {
			bootsModel = new CrystaliteBootsModel(1);
			chestplateModel = new CrystaliteChestplateModel(1, false);
			slimChestplateModel = new CrystaliteChestplateModel(1, true);
			helmetModel = new CrystaliteHelmetModel(1);
			leggingsModel = new CrystaliteLeggingsModel(1);
		}

		switch (armorSlot) {
			case HEAD: {
				return helmetModel;
			}
			case CHEST: {
				if (entityLiving instanceof AbstractClientPlayer
						&& ((AbstractClientPlayer) entityLiving).getSkinType().equals("slim")) {
					return slimChestplateModel;
				}
				return chestplateModel;
			}
			case LEGS: {
				return leggingsModel;
			}
			case FEET: {
				return bootsModel;
			}
			default: {
				return _default;
			}
		}
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack) {
		return EnumRarity.UNCOMMON;
	}
}
