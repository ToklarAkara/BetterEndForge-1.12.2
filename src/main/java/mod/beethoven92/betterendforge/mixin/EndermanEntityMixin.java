package mod.beethoven92.betterendforge.mixin;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModEffects;
import mod.beethoven92.betterendforge.common.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;

@Mixin(EntityEnderman.class)
public abstract class EndermanEntityMixin 
{
	@Inject(at = @At("HEAD"), method = "shouldAttackPlayer", cancellable = true)
	private void shouldAttackPlayer(EntityPlayer player, CallbackInfoReturnable<Boolean> info)
	{
		if (player.isCreative() || player.isPotionActive(ModEffects.END_VEIL) ||
				EnchantmentHelper.getEnchantmentLevel(ModEnchantments.END_VEIL,
						player.getItemStackFromSlot(EntityEquipmentSlot.HEAD)) > 0)
		{
			info.setReturnValue(false);
			info.cancel();
		}
	}
}