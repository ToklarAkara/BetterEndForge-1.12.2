package mod.beethoven92.betterendforge.mixin;

//import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

//@Mixin(targets = "net.minecraft.world.gen.feature.Features$Placements")
@Mixin(MathHelper.class)
public interface FeatureDecoratorsAccessor {
	//@Accessor("HEIGHTMAP_PLACEMENT")
	//ConfiguredPlacement<?> be_getHeightmapSquare();
}
