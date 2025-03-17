package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.client.gui.BlockSignEditScreen;
import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

@Mixin(NetHandlerPlayClient.class)
public class ClientPlayNetHandlerMixin {
    @Shadow
    private Minecraft client;

    @Shadow
    private WorldClient world;

    @Inject(method = "handleSignEditorOpen", at = @At(value = "HEAD"), cancellable = true)
    public void be_openSignEditor(SPacketSignEditorOpen packet, CallbackInfo info) {

        PacketThreadUtil.checkThreadAndEnqueue(packet, (NetHandlerPlayClient) (Object) this, client);
        TileEntity blockEntity = this.world.getTileEntity(packet.getSignPosition());
        if (blockEntity instanceof ESignTileEntity) {
            ESignTileEntity sign = (ESignTileEntity) blockEntity;
            client.displayGuiScreen(new BlockSignEditScreen(sign));
            info.cancel();
        }
    }

    @Inject(method = "handleUpdateTileEntity", at = @At(value = "HEAD"), cancellable = true)
    public void be_onEntityUpdate(SPacketUpdateTileEntity packet, CallbackInfo info) {
        PacketThreadUtil.checkThreadAndEnqueue(packet, (NetHandlerPlayClient) (Object) this, client);
        BlockPos blockPos = packet.getPos();
        TileEntity blockEntity = this.client.world.getTileEntity(blockPos);
        if (blockEntity instanceof ESignTileEntity || blockEntity instanceof PedestalTileEntity) {
            blockEntity.readFromNBT(packet.getNbtCompound());
            info.cancel();
        }
    }
}