package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.enchantment.EndVeilEnchantment;
import mod.beethoven92.betterendforge.common.potion.EndVeilEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ModEnchantments 
{
	public static final Enchantment END_VEIL = new EndVeilEnchantment(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_HEAD,
			new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD });

	@SubscribeEvent
	public static void registerEnchants(RegistryEvent.Register<Enchantment> evt)
	{
		evt.getRegistry().register(END_VEIL.setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, "end_veil")));
	}
}
