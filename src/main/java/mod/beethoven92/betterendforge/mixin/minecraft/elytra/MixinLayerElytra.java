package mod.beethoven92.betterendforge.mixin.minecraft.elytra;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.item.CustomElytraItem;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LayerElytra.class)
public class MixinLayerElytra {
    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    private Item getItemEqualsElytraRedirect(ItemStack instance){
        if(instance.getItem() instanceof CustomElytraItem) return Items.ELYTRA;
        return instance.getItem();
    }
    
    @Redirect(method = "doRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;getLocationElytra()Lnet/minecraft/util/ResourceLocation;"))
    public ResourceLocation getLocationElytra(AbstractClientPlayer instance){
        if(instance.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem()== ModItems.ARMORED_ELYTRA){
            return new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/elytra_armored.png");
        }
        if(instance.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem()== ModItems.CRYSTAL_ELYTRA){
            return new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/elytra_crystalite.png");
        }
        return instance.getLocationElytra();
    }
}
