package mod.beethoven92.betterendforge.mixin.minecraft.elytra;

import com.mojang.authlib.GameProfile;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractPlayer extends EntityPlayer {

    public MixinAbstractPlayer(World p_i45324_1_, GameProfile p_i45324_2_) {
        super(p_i45324_1_, p_i45324_2_);
    }

    @Inject(method = "getLocationElytra", at = @At("HEAD"), cancellable = true)
    public void getLocationElytra(CallbackInfoReturnable<ResourceLocation> cir){
        if(getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem()== ModItems.ARMORED_ELYTRA){
            cir.setReturnValue(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/elytra_armored.png"));
        }
        if(getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem()== ModItems.CRYSTAL_ELYTRA){
            cir.setReturnValue(new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/elytra_crystalite.png"));
        }
    }
}
