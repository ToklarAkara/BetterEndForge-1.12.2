package mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

public class DefaultSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public DefaultSurfaceBuilder() {
        super();
    }

    public void buildSurface(Random random, ChunkPrimer chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        this.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    protected void buildSurface(Random random, ChunkPrimer chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, IBlockState defaultBlock, IBlockState defaultFluid, IBlockState top, IBlockState middle, IBlockState bottom, int sealevel) {
        IBlockState blockState = top;
        IBlockState blockState1 = middle;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        int i = -1;
        int j = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int k = x & 15;
        int l = z & 15;

        for(int i1 = startHeight; i1 >= 0; --i1) {
            blockpos$mutable.setPos(k, i1, l);
            IBlockState blockState2 = chunkIn.getBlockState(blockpos$mutable.getX(), blockpos$mutable.getY(), blockpos$mutable.getZ());
            if (blockState2.getBlock()==Blocks.AIR) {
                i = -1;
            } else if (blockState2.getBlock()==(defaultBlock.getBlock())) {
                if (i == -1) {
                    if (j <= 0) {
                        blockState = Blocks.AIR.getDefaultState();
                        blockState1 = defaultBlock;
                    } else if (i1 >= sealevel - 4 && i1 <= sealevel + 1) {
                        blockState = top;
                        blockState1 = middle;
                    }

                    if (i1 < sealevel && (blockState == null || blockState.getBlock()==Blocks.AIR)) {
                        if (biomeIn.getTemperature(blockpos$mutable.setPos(x, i1, z)) < 0.15F) {
                            blockState = Blocks.ICE.getDefaultState();
                        } else {
                            blockState = defaultFluid;
                        }

                        blockpos$mutable.setPos(k, i1, l);
                    }

                    i = j;
                    if (i1 >= sealevel - 1) {
                        chunkIn.setBlockState(blockpos$mutable.getX(), blockpos$mutable.getY(), blockpos$mutable.getZ(), blockState);
                    } else if (i1 < sealevel - 7 - j) {
                        blockState = Blocks.AIR.getDefaultState();
                        blockState1 = defaultBlock;
                        chunkIn.setBlockState(blockpos$mutable.getX(), blockpos$mutable.getY(), blockpos$mutable.getZ(), bottom);
                    } else {
                        chunkIn.setBlockState(blockpos$mutable.getX(), blockpos$mutable.getY(), blockpos$mutable.getZ(), blockState1);
                    }
                } else if (i > 0) {
                    --i;
                    chunkIn.setBlockState(blockpos$mutable.getX(), blockpos$mutable.getY(), blockpos$mutable.getZ(), blockState1);
                    if (i == 0 && blockState1.getBlock()==(Blocks.SAND) && j > 1) {
                        i = random.nextInt(4) + Math.max(0, i1 - 63);
                        blockState1 =  Blocks.SANDSTONE.getDefaultState();
                    }
                }
            }
        }

    }
}