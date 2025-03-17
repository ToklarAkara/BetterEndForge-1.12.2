package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayServer.class)
public class ServerPlayNetHandlerMixin {
    @Shadow
    private static final Logger LOGGER = LogManager.getLogger();

    @Shadow
    public EntityPlayerMP player;

    @Inject(method = "processUpdateSign", at = @At(value = "HEAD"), cancellable = true)
    private void be_signUpdate(CPacketUpdateSign packet, CallbackInfo ci) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, NetHandlerPlayServer.class.cast(this), this.player.getServerWorld());
        this.player.markPlayerActive();
        WorldServer serverWorld = this.player.getServerWorld();
        BlockPos blockPos = packet.getPosition();
        if (serverWorld.isBlockLoaded(blockPos)) {
            IBlockState blockState = serverWorld.getBlockState(blockPos);
            TileEntity blockEntity = serverWorld.getTileEntity(blockPos);
            if (blockEntity instanceof ESignTileEntity) {
                ESignTileEntity signBlockEntity = (ESignTileEntity) blockEntity;
                if (!signBlockEntity.isEditable() || signBlockEntity.getEditor() != this.player) {
                    LOGGER.warn("Player {} just tried to change non-editable sign", this.player.getName());
                    return;
                }

                String[] strings = packet.getLines();

                for (int i = 0; i < strings.length; ++i) {
                    String blankText = TextFormatting.getTextWithoutFormattingCodes(strings[i]);
                    assert blankText != null;
                    signBlockEntity.setTextOnRow(i, new TextComponentString(blankText));
                }

                signBlockEntity.markDirty();
                serverWorld.notifyBlockUpdate(blockPos, blockState, blockState, 3);

                ci.cancel();
            }
        }
    }
}
