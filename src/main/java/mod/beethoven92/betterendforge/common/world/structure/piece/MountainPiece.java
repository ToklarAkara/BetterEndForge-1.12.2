package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class MountainPiece extends StructureComponent
{
	private Map<Integer, Integer> heightmap = Maps.newHashMap();
	
	private BlockPos center;
	private float radius;
	private float height;
	private float r2;
	private OpenSimplexNoise noise1;
	private OpenSimplexNoise noise2;
	private IBlockState top;
	private int seed1;
	private int seed2;
	private ResourceLocation biomeID;

	public MountainPiece(BlockPos center, float radius, float height, Random rand, Biome biome) 
	{
		super(rand.nextInt());
		this.center = center;
		this.radius = radius;
		this.height = height;
		this.r2 = radius * radius;
		this.seed1 = rand.nextInt();
		this.seed2 = rand.nextInt();
		this.noise1 = new OpenSimplexNoise(this.seed1);
		this.noise2 = new OpenSimplexNoise(this.seed2);
		top = biome.topBlock;
		this.biomeID = ModBiomes.getBiomeID(biome);
		makeBoundingBox();
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager p_143011_2_) {
		this.center = new BlockPos(nbt.getInteger("centerX"), nbt.getInteger("centerY"), nbt.getInteger("centerZ"));
		this.radius = nbt.getFloat("radius");
		this.height = nbt.getFloat("height");
		this.biomeID = new ResourceLocation(nbt.getString("biomeid"));
		this.r2 = radius * radius;
		seed1 = nbt.getInteger("seed1");
		seed2 = nbt.getInteger("seed2");
		noise1 = new OpenSimplexNoise(seed1);
		noise2 = new OpenSimplexNoise(seed2);
		top = ModBiomes.getBiome(biomeID).getBiome().topBlock;
		makeBoundingBox();
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		// WRITE ADDITIONAL STRUCTURE DATA
		tagCompound.setInteger("centerX", this.center.getX());
		tagCompound.setInteger("centerY", this.center.getY());
		tagCompound.setInteger("centerZ", this.center.getZ());
		tagCompound.setFloat("radius", this.radius);
		tagCompound.setFloat("height", this.height);
		tagCompound.setInteger("seed1", seed1);
		tagCompound.setInteger("seed2", seed2);
		tagCompound.setString("biomeid", this.biomeID.toString());
	}
	
	private void makeBoundingBox() 
	{
		int minX = MathHelper.floor(center.getX() - radius);
		int minZ = MathHelper.floor(center.getZ() - radius);
		int maxX = MathHelper.floor(center.getX() + radius + 1);
		int maxZ = MathHelper.floor(center.getZ() + radius + 1);
		this.boundingBox = new StructureBoundingBox(minX, minZ, maxX, maxZ);
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBoxIn)
	{
		int sx = structureBoundingBoxIn.minX;
		int sz = structureBoundingBoxIn.minZ;
		Mutable pos = new Mutable();
		Chunk chunk = world.getChunk(center.getX() >> 4, center.getZ() >> 4);
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
					dist = 1 - (float) Math.pow(dist / r2, 0.3);
					int minY = chunk.getHeightValue(x, z);
					if (minY < 10) 
					{
						continue;
					}
					pos.setY(minY);
					while (!ModTags.GEN_TERRAIN.contains(chunk.getBlockState(pos).getBlock()) && pos.getY() > 56 && chunk.getBlockState(pos.down()).getBlock()!=(Blocks.AIR))
					{
						pos.setY(pos.getY() - 1);
					}
					minY = pos.getY();
					minY = Math.max(minY, chunk.getHeightValue(x, z));
					//if (minY > 10) 
					//{
						//float maxY = dist * height * getHeightClamp(world, 8, px, pz);
					if (minY > center.getY() - 8) 
					{
						float maxY = dist * height * getHeightClamp(world, 12, px, pz);
						if (maxY > 0) 
						{
							maxY *= (float) noise1.eval(px * 0.05, pz * 0.05) * 0.3F + 0.7F;
							maxY *= (float) noise1.eval(px * 0.1, pz * 0.1) * 0.1F + 0.8F;
							//maxY += 56;
							maxY += center.getY();
							int maxYI = (int) (maxY);
							int cover = maxYI - 1;
							//boolean needCover = (noise1.eval(px * 0.1, pz * 0.1) + MathHelper.nextDouble(random, -0.4, 0.4) - (maxY - 70) * 0.1) > 0;
							boolean needCover = (noise1.eval(px * 0.1, pz * 0.1) + ModMathHelper.randRange(-0.4, 0.4, random) - (center.getY() + 14) * 0.1) > 0;
							for (int y = minY - 1; y < maxYI; y++) 
							{
								pos.setY(y);                                                
								chunk.setBlockState(pos, needCover && y == cover ? top : Blocks.END_STONE.getDefaultState());
							}
						}
					}
				}
			}
		}
		
		// Big crystals
		//int count = (map.getHeight(8, 8) - 80) / 7;
		int count = (chunk.getHeightValue(8, 8) - (center.getY() + 24)) / 7;
		count = MathHelper.clamp(count, 0, 8);
		for (int i = 0; i < count; i++) 
		{
			int radius = AdvMathHelper.nextInt(random, 2, 3);
			float fill = MathHelper.nextFloat(random, 0F, 1F);
			int x = AdvMathHelper.nextInt(random, radius, 15 - radius);
			int z = AdvMathHelper.nextInt(random, radius, 15 - radius);
			int y = chunk.getHeightValue(x, z);
			if (y > 60)
			{
				pos.setPos(x, y, z);
				if (chunk.getBlockState(pos.down()).getBlock() == Blocks.END_STONE) 
				{
					int height = MathHelper.floor(radius * MathHelper.nextFloat(random, 1.5F, 3F) + (y - 60) * 0.3F);
					crystal(chunk, pos, radius, height, fill, random);
				}
			}
		}
		
		// Small crystals
		//count = (map.getHeight(8, 8) - 80) / 2;
		count = (chunk.getHeightValue(8, 8) - (center.getY() + 24)) / 2;
		count = MathHelper.clamp(count, 4, 8);
		for (int i = 0; i < count; i++) 
		{
			int radius = AdvMathHelper.nextInt(random, 1, 2);
			float fill = random.nextBoolean() ? 0 : 1;
			int x = AdvMathHelper.nextInt(random, radius, 15 - radius);
			int z = AdvMathHelper.nextInt(random, radius, 15 - radius);
			int y = chunk.getHeightValue(x, z);
			if (y > 60)
			{
				pos.setPos(x, y, z);
				if (chunk.getBlockState(pos.down()).getBlock() == Blocks.END_STONE) 
				{
					int height = MathHelper.floor(radius * MathHelper.nextFloat(random, 1.5F, 3F) + (y - 60) * 0.3F);
					crystal(chunk, pos, radius, height, fill, random);
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
			heightmap.put(p, -10);
			return -10;
		}
		
		h = world.getHeight(pos.getX(), pos.getZ());
		/*if (h < 57) 
		{
			heightmap.put(p, -4);
			return -4;
		}*/
		h = MathHelper.abs(h - center.getY());
		if (h > 4)
		{
			h = 4 - h;
			heightmap.put(p, h);
			return h;
		}
		
		h = MathHelper.floor(noise2.eval(pos.getX() * 0.01, pos.getZ() * 0.01) * noise2.eval(pos.getX() * 0.002, pos.getZ() * 0.002) * 8 + 8);
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
		int r2 = radius * radius;
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
				if (x2 + z2 < r2) 
				{
					float mult = 1 - (float) Math.sqrt(x2 + z2) / radius;
					max += mult;
					height += getHeight(world, mut) * mult;
				}
			}
		}
		height /= max;
		return MathHelper.clamp(height / radius, 0, 1);
	}

	private void crystal(Chunk chunk, BlockPos pos, int radius, int height, float fill, Random random)
	{
		Mutable mut = new Mutable();
		int max = MathHelper.floor(fill * radius + radius + 0.5F);
		height += pos.getY();
		int coefX = AdvMathHelper.nextInt(random, -1, 1);
		int coefZ = AdvMathHelper.nextInt(random, -1, 1);
		for (int x = -radius; x <= radius; x++) 
		{
			mut.setX(x + pos.getX());
			if (mut.getX() >= 0 && mut.getX() < 16) 
			{
				int ax = Math.abs(x);
				for (int z = -radius; z <= radius; z++) 
				{
					mut.setZ(z + pos.getZ());
					if (mut.getZ() >= 0 && mut.getZ() < 16) 
					{
						int az = Math.abs(z);
						if (ax + az < max) 
						{
							int minY = chunk.getHeight(new BlockPos(mut.getX(), 0, mut.getZ())) - AdvMathHelper.nextInt(random, 3, 7);
							if (pos.getY() - minY > 8) 
							{
								minY = pos.getY() - 8;
							}
							int h = coefX * x + coefZ * z + height;
							for (int y = minY; y < h; y++) 
							{
								mut.setY(y);
								chunk.setBlockState(mut, ModBlocks.AURORA_CRYSTAL.getDefaultState());
							}
						}
					}
				}
			}
		}
	}
}
