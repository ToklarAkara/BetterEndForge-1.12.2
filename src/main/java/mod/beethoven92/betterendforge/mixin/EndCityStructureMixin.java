package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenEndCity;
import net.minecraft.world.gen.structure.MapGenStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MapGenEndCity.class)
public abstract class EndCityStructureMixin extends MapGenStructure
{
	@Shadow @Final private ChunkGeneratorEnd endProvider;

	@Inject(method = "canSpawnStructureAtCoords", at = @At("HEAD"), cancellable = true)
	protected void canSpawnStructureAtCoords(int i, int j, CallbackInfoReturnable<Boolean> info)
	{
//		if (GeneratorOptions.useNewGenerator())
//		{
//			Random random = this.world.setRandomSeed(i, j, 10387313);
//			int chance = GeneratorOptions.getEndCityFailChance();
//			if (chance == 0)
//			{
//				info.setReturnValue(getYPosForStructure(i, j, endProvider) >= 60);
//				info.cancel();
//			}
//			else if (random.nextInt(chance) == 0)
//			{
//				info.setReturnValue(getYPosForStructure(i, j, endProvider) >= 60);
//				info.cancel();
//			}
//			else
//			{
//				info.setReturnValue(false);
//				info.cancel();
//			}
//		}
	}
	
	@Shadow
	private static int getYPosForStructure(int chunkX, int chunkY, ChunkGeneratorEnd generatorIn)
	{
		return 0;
	}
}
