package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.WorldRendererHelper;
import mod.beethoven92.betterendforge.client.ClientOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public abstract class WorldRendererMixin {
    @Shadow
    private int cloudTickCounter;

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void onInit(Minecraft mcIn, CallbackInfo ci) {

        WorldRendererHelper.init();
        //directOpenGL = ModList.get().isLoaded("optifine") || ModList.get().isLoaded("immersive_portals");
    }

    //Used to render custom end skybox
    @Inject(method = "renderSkyEnd", at = @At("HEAD"), cancellable = true)
    private void renderSkyEnd(CallbackInfo ci) {
        WorldRendererHelper.renderSkyEnd(cloudTickCounter);
        if (ClientOptions.isCustomSky()) {
            ci.cancel();
        }
    }
}
