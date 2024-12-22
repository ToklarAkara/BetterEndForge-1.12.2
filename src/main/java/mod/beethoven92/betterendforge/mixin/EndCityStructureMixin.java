package mod.beethoven92.betterendforge.mixin;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureEndCityPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(StructureEndCityPieces.CityTemplate.class)
public abstract class EndCityStructureMixin
{
	@Inject(method = "handleDataMarker", at=@At("HEAD"), cancellable = true)
	protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb, CallbackInfo ci){
		if (function.startsWith("Sentry"))
		{
			if(!worldIn.getEntitiesWithinAABB(EntityShulker.class, new AxisAlignedBB(pos).expand(1, 1, 1)).isEmpty())
				ci.cancel();
		}
		else if (function.startsWith("Elytra"))
		{
			if(!worldIn.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(pos).expand(1, 1, 1)).isEmpty())
				ci.cancel();
		}
	}
}
