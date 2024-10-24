package mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

public class ConfiguredSurfaceBuilder<SC extends ISurfaceBuilderConfig> {
    public final SurfaceBuilder<SC> builder;
    public final SC config;

    public ConfiguredSurfaceBuilder(SurfaceBuilder<SC> builder, SC config) {
        this.builder = builder;
        this.config = config;
    }

    public void buildSurface(Random random, ChunkPrimer chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed) {
        this.builder.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, this.config);
    }

    public void setSeed(long seed) {
        this.builder.setSeed(seed);
    }

    public SC getConfig() {
        return this.config;
    }
}