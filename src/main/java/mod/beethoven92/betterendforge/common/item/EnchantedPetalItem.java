package mod.beethoven92.betterendforge.common.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class EnchantedPetalItem extends Item {

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public IRarity getForgeRarity(ItemStack stack) {
		return EnumRarity.RARE;
	}
}
