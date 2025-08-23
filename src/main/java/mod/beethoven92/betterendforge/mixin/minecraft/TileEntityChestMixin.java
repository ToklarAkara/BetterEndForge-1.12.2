package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityChest.class)
public abstract class TileEntityChestMixin extends TileEntityLockable {

    @Inject(method = "isChestAt", at = @At("RETURN"), cancellable = true)
    private void isChestAt(BlockPos p_174912_1_, CallbackInfoReturnable<Boolean> cir) {
        if (this.world == null) {
            cir.setReturnValue(false);
        } else {
            TileEntity tile = this.world.getTileEntity(p_174912_1_);
            cir.setReturnValue(!(tile instanceof EChestTileEntity));
        }
    }
}
