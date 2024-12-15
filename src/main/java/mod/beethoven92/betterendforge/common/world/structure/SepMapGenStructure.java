package mod.beethoven92.betterendforge.common.world.structure;

import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;

import java.util.Random;

public abstract class SepMapGenStructure extends MapGenStructure {
    public int spacing;
    public int separation;
    public int salt;

    private final INetherAPIChunkGenerator endProvider;

    public SepMapGenStructure(INetherAPIChunkGenerator provider, int spacing, int separation, int salt){
        this.spacing = spacing;
        this.separation = separation;
        this.salt = salt;
        this.endProvider = provider;
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

//        if (chunkX < 0)
//        {
//            chunkX -= 19;
//        }
//
//        if (chunkZ < 0)
//        {
//            chunkZ -= 19;
//        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random = this.world.setRandomSeed(k, l, salt);
        k = k * spacing;
        l = l * spacing;
        k = k + (random.nextInt(spacing-separation) + random.nextInt(spacing-separation)) / 2;
        l = l + (random.nextInt(spacing-separation) + random.nextInt(spacing-separation)) / 2;

        if (i == k && j == l)// && this.endProvider.isIslandChunk(i, j))
        {
            int i1 = getYPosForStructure(i, j, this.endProvider);
            return i1 >= 50;
        }
        else
        {
            return false;
        }
    }

    private static int getYPosForStructure(int chunkX, int chunkY, INetherAPIChunkGenerator generatorIn)
    {
        Random random = new Random((long)(chunkX + chunkY * 10387313));
        Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
        ChunkPrimer chunkprimer = new ChunkPrimer();
        generatorIn.setBlocksInPrimer(chunkX, chunkY, chunkprimer);
        int i = 5;
        int j = 5;

        if (rotation == Rotation.CLOCKWISE_90)
        {
            i = -5;
        }
        else if (rotation == Rotation.CLOCKWISE_180)
        {
            i = -5;
            j = -5;
        }
        else if (rotation == Rotation.COUNTERCLOCKWISE_90)
        {
            j = -5;
        }

        int k = chunkprimer.findGroundBlockIdx(7, 7);
        int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
        int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
        int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
        int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
        return k1;
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, spacing, separation, salt, true, 100, findUnexplored);
    }

    public int getSalt() {
        return salt;
    }

    public int getSpacing() {
        return spacing;
    }

    public int getSeparation() {
        return separation;
    }
}
