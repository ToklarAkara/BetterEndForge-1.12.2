package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.multiplayer.WorldClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public GuiScreen currentScreen;

    @Shadow
    @Final
    public GuiIngame ingameGUI;

    @Shadow
    public EntityPlayerSP player;

    @Shadow
    public WorldClient world;

    @Inject(method = "getAmbientMusicType", at = @At("HEAD"), cancellable = true)
    private void be_getEndMusic(CallbackInfoReturnable<MusicTicker.MusicType> info) {
//        if (!(this.currentScreen instanceof GuiWinGame) && this.player != null) {
//            if (this.player.world.provider.getDimension()==1) {
//                if (this.ingameGUI.getBossOverlay().shouldPlayEndBossMusic() && ModMathHelper.lengthSqr(this.player.posX, this.player.posZ) < 250000) {
//                    info.setReturnValue(MusicTicker.MusicType.END_BOSS);
//                } else {
//                    //TODO MUSIC
//                    //MusicTicker.MusicType sound = (BackgroundMusicSelector) this.world.getBiome(this.player.getPosition())..getBackgroundMusic().orElse(MusicTicker.MusicType.END);
//                    //info.setReturnValue(sound);
//                }
//                info.cancel();
//            }
//        } TODO MUSIC
    }
}
