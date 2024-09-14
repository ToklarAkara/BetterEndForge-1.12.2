package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.BetterEndBiomeProvider;
import net.minecraft.init.Biomes;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldProviderEnd.class)
public abstract class MixinWorldProviderEnd extends WorldProvider {

    @Inject(method = "init", at=@At("TAIL"))
    public void init(CallbackInfo ci){
        this.biomeProvider = new BetterEndBiomeProvider(ForgeRegistries.BIOMES, getSeed());
    }
}
