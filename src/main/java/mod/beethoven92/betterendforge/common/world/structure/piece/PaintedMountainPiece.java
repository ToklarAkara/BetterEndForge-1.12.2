package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class PaintedMountainPiece extends StructureComponent
{
	private Map<Integer, Integer> heightmap = Maps.newHashMap();
	private OpenSimplexNoise noise1;
	private OpenSimplexNoise noise2;
	private BlockPos center;
	private float radius;
	private float height;
	private float r2;
	private ResourceLocation biomeID;
	private IBlockState[] slises;
	private int seed1;
	private int seed2;
	
	public PaintedMountainPiece(BlockPos center, float radius, float height, Random random, Biome biome, IBlockState[] slises)
	{
		super(random.nextInt());
		this.center = center;
		this.radius = radius;
		this.height = height;
		this.r2 = radius * radius;
		this.seed1 = random.nextInt();
		this.seed2 = random.nextInt();
		this.noise1 = new OpenSimplexNoise(this.seed1);
		this.noise2 = new OpenSimplexNoise(this.seed2);
		this.biomeID = ModBiomes.getBiomeID(biome);
		this.slises = slises;
		makeBoundingBox();
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager p_143011_2_) {
		center = new BlockPos(nbt.getInteger("centerX"), nbt.getInteger("centerY"), nbt.getInteger("centerZ"));
		radius = nbt.getFloat("radius");
		height = nbt.getFloat("height");
		biomeID = new ResourceLocation(nbt.getString("biome"));
		r2 = radius * radius;
		seed1 = nbt.getInteger("seed1");
		seed2 = nbt.getInteger("seed2");
		noise1 = new OpenSimplexNoise(seed1);
		noise2 = new OpenSimplexNoise(seed2);
		NBTTagList slise = nbt.getTagList("slises", 10);
		slises = new IBlockState[slise.tagCount()];
		for (int i = 0; i < slises.length; i++)
		{
			slises[i] = NBTUtil.readBlockState(slise.getCompoundTagAt(i));
		}
		makeBoundingBox();
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("centerX", this.center.getX());
		tagCompound.setInteger("centerY", this.center.getY());
		tagCompound.setInteger("centerZ", this.center.getZ());
		tagCompound.setFloat("radius", radius);
		tagCompound.setFloat("height", height);
		tagCompound.setString("biome", biomeID.toString());
		tagCompound.setInteger("seed1", seed1);
		tagCompound.setInteger("seed2", seed2);

		NBTTagList slise = new NBTTagList();
		for (IBlockState state: slises)
		{
			slise.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), state));
		}
		tagCompound.setTag("slises", slise);
	}

	
	private void makeBoundingBox() 
	{
		int minX = ModMathHelper.floor(center.getX() - radius);
		int minZ = ModMathHelper.floor(center.getZ() - radius);
		int maxX = ModMathHelper.floor(center.getX() + radius + 1);
		int maxZ = ModMathHelper.floor(center.getZ() + radius + 1);
		this.boundingBox = new StructureBoundingBox(minX, minZ, maxX, maxZ);
	}

	@Override
	public boolean addComponentParts(World world, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		int sx = center.getX() << 4;
		int sz = center.getZ() << 4;
		Mutable pos = new Mutable();
		Chunk chunk = world.getChunk(center.getX() >> 4, center.getZ() >> 4);
//		Heightmap map = chunk.getHeightmap(Type.WORLD_SURFACE);
//		Heightmap map2 = chunk.getHeightmap(Type.WORLD_SURFACE_WG);
		for (int x = 0; x < 16; x++)
		{
			int px = x + sx;
			int px2 = px - center.getX();
			px2 *= px2;
			pos.setX(x);
			for (int z = 0; z < 16; z++)
			{
				int pz = z + sz;
				int pz2 = pz - center.getZ();
				pz2 *= pz2;
				float dist = px2 + pz2;
				if (dist < r2) {
					pos.setZ(z);
					dist = 1 - dist / r2;
					int minY = chunk.getHeightValue(x, z);
					pos.setY(minY - 1);
					while (chunk.getBlockState(pos).getBlock()== Blocks.AIR && pos.getY() > 50)
					{
						pos.setY(minY --);
					}
					minY = pos.getY();
					minY = Math.max(minY, chunk.getHeightValue(x, z));
					if (minY > 56)
					{
						float maxY = dist * height * getHeightClamp(world, 8, px, pz);
						if (maxY > 0) {
							maxY *= (float) noise1.eval(px * 0.05, pz * 0.05) * 0.3F + 0.7F;
							maxY *= (float) noise1.eval(px * 0.1, pz * 0.1) * 0.1F + 0.9F;
							maxY += 56;
							float offset = (float) (noise1.eval(px * 0.07, pz * 0.07) * 5 + noise1.eval(px * 0.2, pz * 0.2) * 2 + 7);
							for (int y = minY - 1; y < maxY; y++)
							{
								pos.setY(y);
								int index = ModMathHelper.floor((y + offset) * 0.65F) % slises.length;
								chunk.setBlockState(pos, slises[index]);
							}
						}
					}
				}
			}
		}
		return true;
	}

	private int getHeight(World world, BlockPos pos)
	{
		int p = ((pos.getX() & 2047) << 11) | (pos.getZ() & 2047);
		int h = heightmap.getOrDefault(p, Integer.MIN_VALUE);
		if (h > Integer.MIN_VALUE)
		{
			return h;
		}
		
		if (!ModBiomes.getBiomeID(world.getBiome(pos)).equals(biomeID)) 
		{
			heightmap.put(p, -4);
			return -4;
		}
		
		h = world.getHeight(pos.getX(), pos.getZ());
		if (h < 57) 
		{
			heightmap.put(p, -4);
			return -4;
		}
		h = ModMathHelper.floor(noise2.eval(pos.getX() * 0.005, pos.getZ() * 0.005) * noise2.eval(pos.getX() * 0.001, pos.getZ() * 0.001) * 8 + 8);
		
		if (h < 0) 
		{
			heightmap.put(p, 0);
			return 0;
		}
		
		heightmap.put(p, h);
		
		return h;
	}
	
	private float getHeightClamp(World world, int radius, int posX, int posZ)
	{
		Mutable mut = new Mutable();
		float height = 0;
		float max = 0;
		for (int x = -radius; x <= radius; x++)
		{
			mut.setX(posX + x);
			int x2 = x * x;
			for (int z = -radius; z <= radius; z++) 
			{
				mut.setZ(posZ + z);
				int z2 = z * z;
				float mult = 1 - (float) Math.sqrt(x2 + z2) / radius;
				if (mult > 0) {
					max += mult;
					height += getHeight(world, mut) * mult;
				}
			}
		}
		height /= max;
		return MathHelper.clamp(height / radius, 0, 1);
	}
}
