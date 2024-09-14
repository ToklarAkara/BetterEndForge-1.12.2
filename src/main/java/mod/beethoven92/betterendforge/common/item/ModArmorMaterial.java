package mod.beethoven92.betterendforge.common.item;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ModArmorMaterial
{
	public static final ItemArmor.ArmorMaterial THALLASIUM = EnumHelper.addArmorMaterial("THALLASIUM",BetterEnd.MOD_ID + ":thallasium", 17, new int[] { 1, 4, 5, 2 }, 12,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F);
	public static final ItemArmor.ArmorMaterial TERMINITE = EnumHelper.addArmorMaterial("TERMINITE",BetterEnd.MOD_ID + ":terminite", 26, new int[] {3, 6, 7, 3}, 14,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	public static final ItemArmor.ArmorMaterial AETERNIUM = EnumHelper.addArmorMaterial("AETERNIUM",BetterEnd.MOD_ID + ":aeternium", 40, new int[] { 4, 7, 9, 4 }, 18,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5F);
	public static final ItemArmor.ArmorMaterial CRYSTALITE = EnumHelper.addArmorMaterial("CRYSTALITE",BetterEnd.MOD_ID + ":crystalite", 30, new int[] { 3, 6, 8, 3 }, 24,
			SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.2F);


//	private static final int[] MAX_DAMAGE_ARRAY = { 11, 16, 15, 13 };
//	private final String name;
//	private final int maxDamageFactor;
//	private final int[] damageReductionAmountArray;
//	private final int enchantability;
//	private final SoundEvent soundEvent;
//	private final float toughness;
//	private final Supplier<ItemStack> repairMaterial;
//	private final float knockbackResistance;
//
//	ModArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability,
//					 SoundEvent soundEvent, float toughness, Supplier<ItemStack> repairMaterial, float knockbackResistance)
//	{
//		this.name = name;
//		this.maxDamageFactor = maxDamageFactor;
//		this.damageReductionAmountArray = damageReductionAmountArray;
//		this.enchantability = enchantability;
//		this.soundEvent = soundEvent;
//		this.toughness = toughness;
//		this.repairMaterial = repairMaterial;
//		this.knockbackResistance = knockbackResistance;
//	}
//
//	@Override
//	public int getDurability(EntityEquipmentSlot slotIn)
//	{
//		return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
//	}
//
//	@Override
//	public int getDamageReductionAmount(EntityEquipmentSlot slotIn)
//	{
//		return damageReductionAmountArray[slotIn.getIndex()];
//	}
//
//	@Override
//	public int getEnchantability()
//	{
//		return enchantability;
//	}
//
//	@Override
//	public SoundEvent getSoundEvent()
//	{
//		return soundEvent;
//	}
//
//	@Override
//	public ItemStack getRepairItemStack()
//	{
//		return repairMaterial.get();
//	}
//
//	@Override
//	public String getName()
//	{
//		return name;
//	}
//
//	@Override
//	public float getToughness()
//	{
//		return toughness;
//	}
//
////	@Override
////	public float getKnockbackResistance()
////	{
////		return knockbackResistance;
////	}
}
