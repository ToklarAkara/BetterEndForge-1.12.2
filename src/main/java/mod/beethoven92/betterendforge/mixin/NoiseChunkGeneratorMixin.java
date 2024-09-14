package mod.beethoven92.betterendforge.mixin;

import java.util.function.Supplier;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.beethoven92.betterendforge.common.world.generator.TerrainGenerator;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorEnd.class)
public abstract class NoiseChunkGeneratorMixin
{


	@Shadow @Final private World world;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void beOnInit(World p_i47241_1_, boolean p_i47241_2_, long seed, BlockPos p_i47241_5_, CallbackInfo ci)
	{
		TerrainGenerator.initNoise(seed);
	}
	
	@Inject(method = "getHeights", at = @At("HEAD"), cancellable = true, allow = 2)
	private void beFillNoiseColumn(double[] buffer, int x, int p_185963_3_, int z, int p_185963_5_, int p_185963_6_, int p_185963_7_, CallbackInfoReturnable<double[]> cir)
	{
//		if (GeneratorOptions.useNewGenerator()) // End settings
//		{
//			if (buffer == null)
//			{
//				buffer = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
//			}
//			TerrainGenerator.fillTerrainDensity(buffer, x, z, world.getBiomeProvider());
//			cir.setReturnValue(buffer);
//			cir.cancel();
//		}
	}
}
