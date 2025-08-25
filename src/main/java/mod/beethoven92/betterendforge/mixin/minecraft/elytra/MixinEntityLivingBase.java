package mod.beethoven92.betterendforge.mixin.minecraft.elytra;

import mod.beethoven92.betterendforge.common.item.CustomElytraItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityLivingBase.class)
public class MixinEntityLivingBase {
  @Redirect(method = "updateElytra", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
  private Item getItemEqualsElytraRedirect(ItemStack instance){
    if(instance.getItem() instanceof CustomElytraItem) return Items.ELYTRA;
    return instance.getItem();
  }

  @Redirect(method = "playEquipSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
  private Item getItemEqualsElytraRedirect2(ItemStack instance){
    if(instance.getItem() instanceof CustomElytraItem) return Items.ELYTRA;
    return instance.getItem();
  }
}
