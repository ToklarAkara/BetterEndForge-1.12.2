package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import java.util.Random;

public class ExtendedBiome extends Biome {

    private int grassColor;
    private int foliageColor;

    private ConfiguredSurfaceBuilder<?> surface;
    public ExtendedBiome(BiomeProperties p_i46713_1_) {
        super(p_i46713_1_);
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature) {
        return 0;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public void setGrassColor(int grassColor) {
        this.grassColor = grassColor;
    }

    public int getFoliageColor() {
        return foliageColor;
    }

    public void setFoliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
    }

    public ConfiguredSurfaceBuilder<?> getSurface() {
        return surface;
    }

    public void setSurface(ConfiguredSurfaceBuilder<?> surface) {
        this.surface = surface;
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return grassColor;
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return foliageColor;
    }

    public void buildSurface(Random random, ChunkPrimer chunkIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed) {
        ConfiguredSurfaceBuilder<?> configuredsurfacebuilder = surface;
        if(configuredsurfacebuilder==null) return;
        configuredsurfacebuilder.setSeed(seed);
        configuredsurfacebuilder.buildSurface(random, chunkIn, this, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed);
    }
}
