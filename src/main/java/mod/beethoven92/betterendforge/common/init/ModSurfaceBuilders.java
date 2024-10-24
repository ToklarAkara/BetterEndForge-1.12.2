package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.SurfaceBuilderConfig;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.DoubleBlockSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.SulphuricSurfaceBuilder;
import mod.beethoven92.betterendforge.common.world.surfacebuilder.UmbraSurfaceBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import org.apache.commons.lang3.tuple.Pair;

// NEEDS RE-WRITE
public class ModSurfaceBuilders 
{
    public static final SurfaceBuilder<SurfaceBuilderConfig> MEGALAKE_SURFACE = new DoubleBlockSurfaceBuilder(ModBlocks.END_MOSS, ModBlocks.ENDSTONE_DUST);

	public static final SurfaceBuilder<SurfaceBuilderConfig> MUSHROOMLAND_SURFACE = new DoubleBlockSurfaceBuilder(ModBlocks.END_MOSS, ModBlocks.END_MYCELIUM);

	public static final  SurfaceBuilder<SurfaceBuilderConfig> NEON_OASIS_SURFACE = new DoubleBlockSurfaceBuilder(ModBlocks.ENDSTONE_DUST, ModBlocks.END_MOSS);


	public static final  SurfaceBuilder<SurfaceBuilderConfig> SULPHURIC_SURFACE = new SulphuricSurfaceBuilder();

	public static final SurfaceBuilder<SurfaceBuilderConfig> UMBRA_SURFACE = new UmbraSurfaceBuilder();


	// Built-in surface builder configurations
	public static class Configs
	{
		public static final SurfaceBuilderConfig CRYSTAL_SURFACE = makeTernaryConfig(ModBlocks.CRYSTAL_MOSS, Blocks.END_STONE, Blocks.END_STONE);

		public static final SurfaceBuilderConfig DUMMY = makeSimpleConfig(Blocks.END_STONE);

		public static final SurfaceBuilderConfig DEFAULT_END_CONFIG = makeSimpleConfig(Blocks.END_STONE);
		public static final SurfaceBuilderConfig FLAVOLITE_CONFIG = makeSimpleConfig(ModBlocks.FLAVOLITE.stone);
		public static final SurfaceBuilderConfig BRIMSTONE_CONFIG = makeSimpleConfig(ModBlocks.BRIMSTONE);
		public static final SurfaceBuilderConfig SULFURIC_ROCK_CONFIG = makeSimpleConfig(ModBlocks.SULPHURIC_ROCK.stone);
		public static final SurfaceBuilderConfig UMBRA_SURFACE_CONFIG = makeSimpleConfig(ModBlocks.UMBRALITH.stone);

		public static final SurfaceBuilderConfig PALLIDIUM_FULL_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_FULL, ModBlocks.UMBRALITH.stone);
		public static final SurfaceBuilderConfig PALLIDIUM_HEAVY_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_HEAVY, ModBlocks.UMBRALITH.stone);
		public static final SurfaceBuilderConfig PALLIDIUM_THIN_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_THIN, ModBlocks.UMBRALITH.stone);
		public static final SurfaceBuilderConfig PALLIDIUM_TINY_SURFACE_CONFIG = makeSurfaceConfig(ModBlocks.PALLIDIUM_TINY, ModBlocks.UMBRALITH.stone);

		private static SurfaceBuilderConfig makeSimpleConfig(Block block)
		{
			IBlockState state = block.getDefaultState();
			return new SurfaceBuilderConfig(state, state, state);
		}

		private static SurfaceBuilderConfig makeTernaryConfig(Block block1, Block block2, Block block3)
		{
			IBlockState state1 = block1.getDefaultState();
			IBlockState state2 = block2.getDefaultState();
			IBlockState state3 = block3.getDefaultState();
			return new SurfaceBuilderConfig(state1, state2, state3);
		}

		private static SurfaceBuilderConfig makeSurfaceConfig(Block surface, Block under) {
			return new SurfaceBuilderConfig(
					surface.getDefaultState(),
					under.getDefaultState(),
					under.getDefaultState()
			);
		}
	}
}
