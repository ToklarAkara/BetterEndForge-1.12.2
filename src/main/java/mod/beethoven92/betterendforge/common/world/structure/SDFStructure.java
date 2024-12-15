package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Random;

import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.world.structure.piece.VoxelPiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class SDFStructure extends SepMapGenStructure
{
	public SDFStructure(INetherAPIChunkGenerator endProviderIn, int spacing, int separation, int salt) {
		super(endProviderIn, spacing, separation, salt);
	}
	
	/*public SDFStructure() 
	{
		super(NoFeatureConfig.field_236558_a_);
	}*/

	protected abstract SDF getSDF(BlockPos pos, Random random);

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ)
	{
		return new SDFStructureStart(this, this.world, this.rand, chunkX, chunkZ);
	}

	public static class SDFStructureStart extends StructureStart
	{
		MapGenStructure structure;
		public SDFStructureStart()
		{
		}

		public SDFStructureStart(MapGenStructure structure, World worldIn, Random random, int chunkX, int chunkZ)
		{
			super(chunkX, chunkZ);
			this.structure = structure;
			this.create(worldIn, random, chunkX, chunkZ);
		}

		private void create(World worldIn, Random rand, int chunkX, int chunkZ)
		{
			int x = (chunkX << 4) | AdvMathHelper.nextInt(rand, 4, 12);
			int z = (chunkZ << 4) | AdvMathHelper.nextInt(rand, 4, 12);
			int y = worldIn.getHeight(x, z);
			if (y > 5) 
			{
				BlockPos start = new BlockPos(x, y, z);
				VoxelPiece piece = new VoxelPiece((world) -> { ((SDFStructure) structure).getSDF(start, rand).fillRecursive(world, start); }, rand.nextInt());
				this.components.add(piece);
			}
			this.updateBoundingBox();
		}

	}
}
