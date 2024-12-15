package mod.beethoven92.betterendforge.common.world.structure;

import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import mod.beethoven92.betterendforge.common.world.structure.piece.PaintedMountainPiece;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class PaintedMountainStructure extends SepMapGenStructure
{
	private static final IBlockState[] VARIANTS;
	
	static 
	{
		VARIANTS = new IBlockState[] {
				Blocks.END_STONE.getDefaultState(),
				ModBlocks.FLAVOLITE.stone.getDefaultState(),
				ModBlocks.VIOLECITE.stone.getDefaultState(),
		};
	}

	public PaintedMountainStructure(INetherAPIChunkGenerator endProviderIn, int spacing, int separation, int salt) {
		super(endProviderIn, spacing, separation, salt);
	}

	public Decoration getDecorationStage()
	{
		return Decoration.RAW_GENERATION;
	}

	@Override
	public String getStructureName()
	{
		return BetterEnd.MOD_ID + ":painted_mountain_structure";
	}


	protected StructureStart getStructureStart(int chunkX, int chunkZ)
	{
		return new Start(this, this.world, this.rand, chunkX, chunkZ);
	}


	public static class Start extends StructureStart
	{
		MapGenStructure structure;
		public Start()
		{
		}

		public Start(MapGenStructure structure, World worldIn, Random random, int chunkX, int chunkZ)
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
			if (y > 50) 
			{
				float radius = ModMathHelper.randRange(50, 100, rand);
				float height = radius * ModMathHelper.randRange(0.4F, 0.6F, rand);
				int count = ModMathHelper.floor(height * ModMathHelper.randRange(0.1F, 0.35F, rand) + 1);
				IBlockState[] slises = new IBlockState[count];
				for (int i = 0; i < count; i++) 
				{
					slises[i] = VARIANTS[rand.nextInt(VARIANTS.length)];
				}
				this.components.add(new PaintedMountainPiece(new BlockPos(x, y, z), radius, height, rand, worldIn.getBiome(new BlockPos(x,y,z)), slises));
			}
			this.updateBoundingBox();
		}		
	}
}
