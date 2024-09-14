package mod.beethoven92.betterendforge.common.world.structure;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import mod.beethoven92.betterendforge.common.world.structure.piece.LakePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;
import java.util.Random;

public class MegaLakeSmallStructure extends MapGenStructure
{


	public Decoration getDecorationStage() 
	{
		return Decoration.RAW_GENERATION;
	}
	
	@Override
	public String getStructureName() 
	{
		return BetterEnd.MOD_ID + ":megalake_small_structure";
	}

	protected StructureStart getStructureStart(int chunkX, int chunkZ)
	{
		return new SDFStructureStart(this, this.world, this.rand, chunkX, chunkZ);
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return true;
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		return BlockPos.ORIGIN;
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
			int x = (chunkX << 4) | ModMathHelper.randRange(4, 12, rand);
			int z = (chunkZ << 4) | ModMathHelper.randRange(4, 12, rand);
			int y = worldIn.getHeight(x, z);
			if (y > 5) 
			{
				float radius = ModMathHelper.randRange(20, 40, rand);
				float depth = ModMathHelper.randRange(5, 10, rand);
				LakePiece piece = new LakePiece(new BlockPos(x, y, z), radius, depth, rand, worldIn.getBiome(new BlockPos(x,y,z)));
				this.components.add(piece);
			}
			this.updateBoundingBox();
		}
	}
}
