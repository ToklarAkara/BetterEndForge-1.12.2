package mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

public abstract class SurfaceBuilder<C extends ISurfaceBuilderConfig> {

    public static final SurfaceBuilder<SurfaceBuilderConfig> DEFAULT = new DefaultSurfaceBuilder();



    public SurfaceBuilder() {
    }


    public ConfiguredSurfaceBuilder<C> func_242929_a(C p_242929_1_) {
        return new ConfiguredSurfaceBuilder<>(this, p_242929_1_);
    }

    public abstract void buildSurface(Random random, ChunkPrimer chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, C config);

    public void setSeed(long seed) {
    }
}
