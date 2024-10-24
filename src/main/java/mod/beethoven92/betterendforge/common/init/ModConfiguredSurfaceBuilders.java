package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.ConfiguredSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilder;

// NEEDS RE-WRITE
public class ModConfiguredSurfaceBuilders 
{
	public static ConfiguredSurfaceBuilder<?> END_SURFACE = new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, ModSurfaceBuilders.Configs.DEFAULT_END_CONFIG);
	public static ConfiguredSurfaceBuilder<?> CRYSTAL_SURFACE = new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, ModSurfaceBuilders.Configs.CRYSTAL_SURFACE);
	public static ConfiguredSurfaceBuilder<?> MEGALAKE_SURFACE = new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.MEGALAKE_SURFACE, ModSurfaceBuilders.Configs.DUMMY);
	public static ConfiguredSurfaceBuilder<?> MUSHROOMLAND_SURFACE = new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.MUSHROOMLAND_SURFACE, ModSurfaceBuilders.Configs.DUMMY);
	public static ConfiguredSurfaceBuilder<?> SULPHURIC_SURFACE = new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.SULPHURIC_SURFACE, ModSurfaceBuilders.Configs.DUMMY);
	public static ConfiguredSurfaceBuilder<?> NEON_OASUS_SURFACE = new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.NEON_OASIS_SURFACE, ModSurfaceBuilders.Configs.DUMMY);
	public static ConfiguredSurfaceBuilder<?> UMBRA_SURFACE = new ConfiguredSurfaceBuilder<>(ModSurfaceBuilders.UMBRA_SURFACE, ModSurfaceBuilders.Configs.DUMMY);
}
