package mod.beethoven92.betterendforge.common.world.biome;

import git.jbredwards.nether_api.api.audio.IMusicType;
import git.jbredwards.nether_api.api.audio.impl.VanillaMusicType;
import git.jbredwards.nether_api.api.biome.IAmbienceBiome;
import git.jbredwards.nether_api.api.biome.IEndBiome;
import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import mod.beethoven92.betterendforge.common.particles.FireflyParticle;
import mod.beethoven92.betterendforge.common.particles.SulphurParticle;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Blocks;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class ExtendedBiome extends Biome implements IEndBiome, IAmbienceBiome {

    private int grassColor;
    private int foliageColor;
    private int fogColor;

    private ConfiguredSurfaceBuilder<?> surface;

    private MusicTicker.MusicType musicType;

    private SoundEvent ambientSound;

    public ExtendedBiome(BiomeProperties p_i46713_1_) {
        super(p_i46713_1_);
        musicType = MusicTicker.MusicType.END;
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

    public void setMusicType(MusicTicker.MusicType musicType) {
        this.musicType = musicType;
    }

    public void setAmbientSound(SoundEvent ambientSound) {
        this.ambientSound = ambientSound;
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
        float a = (float)(fogColor >> 24 & 255) / 255.0F;
        float r = (float)(fogColor >> 16 & 255) / 255.0F;
        float g = (float)(fogColor >> 8 & 255) / 255.0F;
        float b = (float)(fogColor & 255) / 255.0F;
        return new Vec3d(r,g,b);
    }

//    @Nullable
//    @Override
//    public IParticleFactory[] getAmbientParticles() {
//        return new IParticleFactory[]{new ParticleFlame.Factory()};
//    }

    @Override
    public void buildSurface(@Nonnull INetherAPIChunkGenerator generator, int i, int i1, @Nonnull ChunkPrimer chunkPrimer, int x, int z, double v) {
        ConfiguredSurfaceBuilder<?> configuredsurfacebuilder = surface;
        if (configuredsurfacebuilder == null) return;
        configuredsurfacebuilder.setSeed(generator.getWorld().getSeed());
        configuredsurfacebuilder.buildSurface(generator.getRand(), chunkPrimer, this, x, z, 127, v, Blocks.END_STONE.getDefaultState(), Blocks.AIR.getDefaultState(), 0, generator.getWorld().getSeed());
    }

    @Nonnull
    @Override
    public IMusicType getMusicType() {
        if(musicType==null)
            return new VanillaMusicType(MusicTicker.MusicType.END);
        return new VanillaMusicType(musicType);
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        return ambientSound;
    }
}
