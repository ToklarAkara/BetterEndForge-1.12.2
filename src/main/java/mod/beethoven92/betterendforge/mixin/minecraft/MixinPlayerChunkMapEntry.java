package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.common.capability.EndData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(PlayerChunkMapEntry.class)
public class MixinPlayerChunkMapEntry {

    @Shadow
    @Nullable
    private Chunk chunk;

    @Inject(method = "sendToPlayer", at = @At(value = "HEAD"))
    private void sendToPlayerMixin(EntityPlayerMP p_187278_1_, CallbackInfo ci) {
        betterEndForge$doDecoration();
    }

    @Inject(method = "sendToPlayers", at = @At(value = "HEAD"))
    private void sendToPlayersMixin(CallbackInfoReturnable<Boolean> cir) {
        betterEndForge$doDecoration();
    }

    @Inject(method = "update", at = @At(value = "HEAD"))
    private void update(CallbackInfo ci) {
        betterEndForge$doDecoration();
    }

    @Unique
    private void betterEndForge$doDecoration() {
        if (chunk == null) {
            return;
        }
        if (chunk.getWorld().provider.getDimension() != 1) {
            return;
        }
        EndData data = EndData.getInstance();
        BlockPos pos = new BlockPos(chunk.x * 16, 0, chunk.z * 16);
        if (data != null && data.hasPass(pos)) {
            data.removePass(pos);
            chunk.getWorld().getBiome(pos.add(16, 0, 16)).decorate(chunk.getWorld(), chunk.getWorld().rand, pos);
        }
    }
}
