package mod.beethoven92.betterendforge.common.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EndVeilEnchantment extends Enchantment
{
	public EndVeilEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
	{
		super(rarityIn, typeIn, slots);
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}
}
