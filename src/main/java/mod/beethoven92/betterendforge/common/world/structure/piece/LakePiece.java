package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
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

public class LakePiece extends StructureComponent
{
	private static final IBlockState ENDSTONE = Blocks.END_STONE.getDefaultState();
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private static final IBlockState WATER = Blocks.WATER.getDefaultState();
	
	private Map<Integer, Byte> heightmap = Maps.newHashMap();
	
	private OpenSimplexNoise noise;
	private BlockPos center;
	private float radius;
	private float aspect;
	private float depth;
	private int seed;
	
	private ResourceLocation biomeID;

	
	public LakePiece(BlockPos center, float radius, float depth, Random random, Biome biome) 
	{
		super(random.nextInt());
		this.center = center;
		this.radius = radius;
		this.depth = depth;
		this.seed = random.nextInt();
		this.noise = new OpenSimplexNoise(this.seed);
		this.aspect = radius / depth;
		this.biomeID = ModBiomes.getBiomeID(biome);
		makeBoundingBox();
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager p_i50677_1_) {
		// READ STRUCTURE DATA
		this.center = new BlockPos(nbt.getInteger("centerX"), nbt.getInteger("centerY"), nbt.getInteger("centerZ"));
		this.radius = nbt.getFloat("radius");
		this.depth = nbt.getFloat("depth");
		seed = nbt.getInteger("seed");
		noise = new OpenSimplexNoise(seed);
		aspect = radius / depth;
		this.biomeID = new ResourceLocation(nbt.getString("biomeid"));
		makeBoundingBox();
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger("centerX", this.center.getX());
		tagCompound.setInteger("centerY", this.center.getY());
		tagCompound.setInteger("centerZ", this.center.getZ());
		tagCompound.setFloat("radius", this.radius);
		tagCompound.setFloat("depth", this.depth);
		tagCompound.setInteger("seed", seed);
		tagCompound.setString("biomeid", this.biomeID.toString());
	}
	
	private void makeBoundingBox() 
	{
		int minX = ModMathHelper.floor(center.getX() - radius - 8);
		int minY = ModMathHelper.floor(center.getY() - depth - 8);
		int minZ = ModMathHelper.floor(center.getZ() - radius - 8);
		int maxX = ModMathHelper.floor(center.getX() + radius + 8);
		int maxY = ModMathHelper.floor(center.getY() + depth);
		int maxZ = ModMathHelper.floor(center.getZ() + radius + 8);
		this.boundingBox = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBoxIn)
	{
		int minY = this.boundingBox.minY;
		int maxY = this.boundingBox.maxY;
		int sx = center.getX() << 4;
		int sz = center.getZ() << 4;
		Mutable mut = new Mutable();
		Chunk chunk = world.getChunk(center.getX(), center.getZ());
		for (int x = 0; x < 16; x++) 
		{
			mut.setX(x);
			int wx = x | sx;
			double nx = wx * 0.1;
			int x2 = wx - center.getX();
			for (int z = 0; z < 16; z++) 
			{
				mut.setZ(z);
				int wz = z | sz;
				double nz = wz * 0.1;
				int z2 = wz - center.getZ();
				float clamp = getHeightClamp(world, 8, wx, wz);
				if (clamp < 0.01) continue;
				
				double n = noise.eval(nx, nz) * 1.5 + 1.5;
				double x3 = ModMathHelper.pow2(x2 + noise.eval(nx, nz, 100) * 10);
				double z3 = ModMathHelper.pow2(z2 + noise.eval(nx, nz, -100) * 10);
				
				for (int y = minY; y <= maxY; y++) 
				{
					mut.setY((int) (y + n));
					double y2 = ModMathHelper.pow2((y - center.getY()) * aspect);
					double r2 = radius * clamp;
					double r3 = r2 + 8;
					r2 *= r2;
					r3 = r3 * r3 + 100;
					double dist = x3 + y2 + z3;
					if (dist < r2) 
					{
						IBlockState state = chunk.getBlockState(mut);
						if (canReplace(chunk, mut, state)) 
						{
							state = mut.getY() < center.getY() ? WATER : AIR;
							chunk.setBlockState(mut, state);
							removePlant(chunk, mut.toImmutable().up());
						}
					}
					else if (dist <= r3 && mut.getY() < center.getY()) 
					{
						IBlockState state = chunk.getBlockState(mut);
						BlockPos worldPos = mut.add(sx, 0, sz);
						if (!state.isNormalCube() && !state.isOpaqueCube())
						{
							state = chunk.getBlockState(mut.up());
							if (state.getBlock()==Blocks.AIR)
							{
								state = random.nextBoolean() ? ENDSTONE : world.getBiome(worldPos).topBlock;
							}
							else 
							{
								state = state.getBlock()!= Blocks.WATER ? ENDSTONE : ModBlocks.ENDSTONE_DUST.getDefaultState();
							}
							chunk.setBlockState(mut, state);
						}
					}
				}
			}
		}
		
		fixWater(world, chunk, mut, random, sx, sz);
		return true;
	}
	
	private boolean canReplace(Chunk chunk, BlockPos pos, IBlockState state) {
		if (ModTags.GEN_TERRAIN.contains(state.getBlock()) || state.getBlock()==Blocks.AIR)
			return true;
		Block b = state.getBlock();
		if (b instanceof PlantBlock) {
			removePlant(chunk, pos);
			return true;
		}
		return false;
	}
	
	private void removePlant(Chunk chunk, BlockPos pos) {
		BlockPos p = new BlockPos(pos);
		while (chunk.getBlockState(p).getBlock() instanceof PlantBlock) {
			chunk.setBlockState(p, AIR);
			p = p.up();
		}
		p = new BlockPos(pos).down();
		while (chunk.getBlockState(p).getBlock() instanceof PlantBlock) {
			chunk.setBlockState(p, AIR);
			p = p.down();
		}
	}

	private void fixWater(World world, Chunk chunk, Mutable mut, Random random, int sx, int sz)
	{
		int minY = this.boundingBox.minY;
		int maxY = this.boundingBox.maxY;
		for (int x = 0; x < 16; x++) 
		{
			mut.setX(x);
			for (int z = 0; z < 16; z++) 
			{
				mut.setZ(z);
				for (int y = minY; y <= maxY; y++) 
				{
					mut.setY(y);
					IBlockState state = chunk.getBlockState(mut);
					if (state.getBlock()==Blocks.WATER)
					{
						mut.setY(y - 1);
						if (chunk.getBlockState(mut).getBlock()==Blocks.AIR)
						{
							mut.setY(y + 1);
							
							IBlockState bState = chunk.getBlockState(mut);
							if (bState.getBlock()==Blocks.AIR)
							{
								bState = random.nextBoolean() ? ENDSTONE : world.getBiome(mut.add(sx, 0, sz)).topBlock;
							}
							else 
							{
								bState = bState.getBlock()!=Blocks.WATER ? ENDSTONE : ModBlocks.ENDSTONE_DUST.getDefaultState();
							}
							
							mut.setY(y);
							
							makeEndstonePillar(chunk, mut, bState);
						}
						else if (x > 1 && x < 15 && z > 1 && z < 15) 
						{
							mut.setY(y);
							for (EnumFacing dir: BlockHelper.HORIZONTAL_DIRECTIONS)
							{
								BlockPos wPos = mut.add(dir.getXOffset(), 0, dir.getZOffset());
								if (chunk.getBlockState(wPos).getBlock()==Blocks.AIR)
								{
									mut.setY(y + 1);
									IBlockState bState = chunk.getBlockState(mut);
									if (bState.getBlock()==Blocks.AIR)
									{
										bState = random.nextBoolean() ? ENDSTONE : world.getBiome(mut.add(sx, 0, sz)).topBlock;
									}
									else {
										bState = bState.getBlock()!=Blocks.WATER ? ENDSTONE : ModBlocks.ENDSTONE_DUST.getDefaultState();
									}
									mut.setY(y);
									makeEndstonePillar(chunk, mut, bState);
									break;
								}
							}
						}
						else if (chunk.getBlockState(mut.move(EnumFacing.UP)).getBlock()==Blocks.AIR)
						{
							world.updateBlockTick(mut.move(EnumFacing.DOWN), state.getBlock(), 0, 0);
						}
					}
				}
			}
		}
	}
	
	private void makeEndstonePillar(Chunk chunk, Mutable mut, IBlockState terrain)
	{
		chunk.setBlockState(mut, terrain);
		mut.setY(mut.getY() - 1);
		while (chunk.getBlockState(mut).getBlock()==Blocks.WATER)
		{
			chunk.setBlockState(mut, ENDSTONE);
			mut.setY(mut.getY() - 1);
		}
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
		return MathHelper.clamp(height, 0, 1);
	}
	
	private int getHeight(World world, BlockPos pos)
	{
		int p = ((pos.getX() & 2047) << 11) | (pos.getZ() & 2047);
		int h = heightmap.getOrDefault(p, Byte.MIN_VALUE);
		if (h > Byte.MIN_VALUE) 
		{
			return h;
		}
		
		if (!ModBiomes.getBiomeID(world.getBiome(pos)).equals(biomeID)) 
		{
			heightmap.put(p, (byte) 0);
			return 0;
		}
		
		h = world.getHeight(pos.getX(), pos.getZ());
		h = MathHelper.abs(h - center.getY());
		h = h < 8 ? 1 : 0;
		
		heightmap.put(p, (byte) h);
		return h;
	}
}


