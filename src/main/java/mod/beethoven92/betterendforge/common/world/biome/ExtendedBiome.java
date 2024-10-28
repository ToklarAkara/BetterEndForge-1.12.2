package mod.beethoven92.betterendforge.common.world.biome;

import git.jbredwards.nether_api.api.biome.IEndBiome;
import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class ExtendedBiome extends Biome implements IEndBiome {

    private int grassColor;
    private int foliageColor;
    private int fogColor;

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

    public void setFogColor(int fogColor) {
        this.fogColor = fogColor;
    }

    public int getFogColor() {
        return fogColor;
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(0.09411766, 0.07529412, 0.09411766);
    }


    @Override
    public void buildSurface(@Nonnull INetherAPIChunkGenerator generator, int i, int i1, @Nonnull ChunkPrimer chunkPrimer, int x, int z, double v) {
        ConfiguredSurfaceBuilder<?> configuredsurfacebuilder = surface;
        if(configuredsurfacebuilder==null) return;
        configuredsurfacebuilder.setSeed(generator.getWorld().getSeed());
        configuredsurfacebuilder.buildSurface(generator.getRand(), chunkPrimer, this, x, z, 127, v,  Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 0, generator.getWorld().getSeed());
    }
}
