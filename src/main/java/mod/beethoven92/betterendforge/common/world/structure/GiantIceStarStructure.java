package mod.beethoven92.betterendforge.common.world.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import git.jbredwards.nether_api.api.world.INetherAPIChunkGenerator;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRotation;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import mod.beethoven92.betterendforge.common.world.structure.piece.VoxelPiece;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGeneratorEnd;
import net.minecraft.world.gen.structure.MapGenEndCity;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;

public class GiantIceStarStructure extends SDFStructure
{
	private final float minSize = 20;
	private final float maxSize = 35;
	private final int minCount = 25;
	private final int maxCount = 40;

	public GiantIceStarStructure(INetherAPIChunkGenerator endProviderIn, int spacing, int separation, int salt) {
		super(endProviderIn, spacing, separation, salt);
	}


	public Decoration getDecorationStage()
	{
		return Decoration.SURFACE_STRUCTURES;
	}
	
	@Override
	public String getStructureName() 
	{
		return BetterEnd.MOD_ID + ":giant_ice_star_structure";
	}

	@Override
	protected SDF getSDF(BlockPos pos, Random random)
	{
		float size = ModMathHelper.randRange(minSize, maxSize, random);
		int count = ModMathHelper.randRange(minCount, maxCount, random);
		List<Vector3f> points = getFibonacciPoints(count);
		SDF sdf = null;
		SDF spike = new SDFCappedCone().setRadius1(3 + (size - 5) * 0.2F).setRadius2(0).setHeight(size).setBlock(ModBlocks.DENSE_SNOW);
		spike = new SDFTranslate().setTranslate(0, size - 0.5F, 0).setSource(spike);
		for (Vector3f point: points) 
		{
			SDF rotated = spike;
			point = ModMathHelper.normalize(point);
			float angle = ModMathHelper.angle(Vector3f.YP, point);
			if (angle > 0.01F && angle < 3.14F) 
			{
				Vector3f axis = ModMathHelper.normalize(ModMathHelper.cross(Vector3f.YP, point));
				rotated = new SDFRotation().setRotation(axis, angle).setSource(spike);
			}
			else if (angle > 1) 
			{
				rotated = new SDFRotation().setRotation(Vector3f.YP, (float) Math.PI).setSource(spike);
			}
			sdf = (sdf == null) ? rotated : new SDFUnion().setSourceA(sdf).setSourceB(rotated);
		}
		
		final float ancientRadius = size * 0.7F;
		final float denseRadius = size * 0.9F;
		final float iceRadius = size < 7 ? size * 5 : size * 1.3F;
		final float randScale = size * 0.3F;
		
		final BlockPos center = pos;
		final IBlockState ice = ModBlocks.EMERALD_ICE.getDefaultState();
		final IBlockState dense = ModBlocks.DENSE_EMERALD_ICE.getDefaultState();
		final IBlockState ancient = ModBlocks.ANCIENT_EMERALD_ICE.getDefaultState();
		final SDF sdfCopy = sdf;
		
		return sdf.addPostProcess((info) -> {
			BlockPos bpos = info.getPos();
			float px = bpos.getX() - center.getX();
			float py = bpos.getY() - center.getY();
			float pz = bpos.getZ() - center.getZ();
			float distance = ModMathHelper.length(px, py, pz) + sdfCopy.getDistance(px, py, pz) * 0.4F + random.nextFloat() * randScale;
			if (distance < ancientRadius) 
			{
				return ancient;
			}
			else if (distance < denseRadius) 
			{
				return dense;
			}
			else if (distance < iceRadius) 
			{
				return ice;
			}
			return info.getState();
		});
	}

	private List<Vector3f> getFibonacciPoints(int count) 
	{
		float max = count - 1;
		List<Vector3f> result = new ArrayList<>(count);
		for (int i = 0; i < count; i++) 
		{
			float y = 1F - (i / max) * 2F;
			float radius = (float) Math.sqrt(1F - y * y);
			float theta = ModMathHelper.PHI * i;
			float x = (float) Math.cos(theta) * radius;
			float z = (float) Math.sin(theta) * radius;
			result.add(new Vector3f(x, y, z));
		}
		return result;
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
			BlockPos start = new BlockPos(x, ModMathHelper.randRange(32, 128, rand), z);
			VoxelPiece piece = new VoxelPiece((world) -> { ((SDFStructure) this.structure).getSDF(start, rand).fillRecursive(world, start); }, rand.nextInt());
			this.components.add(piece);
			this.updateBoundingBox();
		}

	}
}
