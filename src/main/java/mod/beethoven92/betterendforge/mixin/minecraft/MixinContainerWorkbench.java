package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerWorkbench.class)
public class MixinContainerWorkbench {
    @Shadow
    @Final
    private World world;

    @Shadow
    @Final
    private BlockPos pos;

    @Inject(method = "canInteractWith", at = @At("HEAD"), cancellable = true)
    public void canInteractWith(EntityPlayer playerIn, CallbackInfoReturnable<Boolean> cir) {
        if (this.world.getBlockState(this.pos).getBlock() instanceof BlockWorkbench) {
            cir.setReturnValue(playerIn.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D);
        }
    }
}
