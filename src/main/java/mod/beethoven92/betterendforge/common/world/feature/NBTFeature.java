package mod.beethoven92.betterendforge.common.world.feature;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.FeatureHelper;
import mod.beethoven92.betterendforge.common.util.NbtModIdReplacer;
import mod.beethoven92.betterendforge.common.world.biome.ExtendedBiome;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public abstract class NBTFeature extends WorldGenerator
{
	
	//protected static final DestructionStructureProcessor DESTRUCTION = new DestructionStructureProcessor();
	
	protected abstract Template getStructure(World world, BlockPos pos, Random random);
	
	protected abstract boolean canSpawn(World world, BlockPos pos, Random random);
	
	protected abstract Rotation getRotation(World world, BlockPos pos, Random random);
	
	protected abstract Mirror getMirror(World world, BlockPos pos, Random random);
	
	protected abstract int getYOffset(Template structure, World world, BlockPos pos, Random random);
	
	protected abstract TerrainMerge getTerrainMerge(World world, BlockPos pos, Random random);
	
	protected abstract void addStructureData(PlacementSettings data);
	
	protected BlockPos getGround(World world, BlockPos center) 
	{
		Biome biome = world.getBiome(center);
		ResourceLocation id = ModBiomes.getBiomeID(biome);
		if (id.getNamespace().contains("moutain") || id.getNamespace().contains("lake")) 
		{
			int y = getAverageY(world, center);
			return new BlockPos(center.getX(), y, center.getZ());
		}
		else 
		{
			int y = getAverageYWG(world, center);
			return new BlockPos(center.getX(), y, center.getZ());
		}
	}
	
	protected int getAverageY(World world, BlockPos center) {
		int y = FeatureHelper.getYOnSurface(world, center.getX(), center.getZ());
		y += FeatureHelper.getYOnSurface(world, center.getX() - 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurface(world, center.getX() + 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurface(world, center.getX() - 2, center.getZ() + 2);
		y += FeatureHelper.getYOnSurface(world, center.getX() + 2, center.getZ() + 2);
		return y / 5;
	}
	
	protected int getAverageYWG(World world, BlockPos center) {
		int y = FeatureHelper.getYOnSurfaceWG(world, center.getX(), center.getZ());
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() - 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() + 2, center.getZ() - 2);
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() - 2, center.getZ() + 2);
		y += FeatureHelper.getYOnSurfaceWG(world, center.getX() + 2, center.getZ() + 2);
		return y / 5;
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos center) {
		center = new BlockPos(((center.getX() >> 4) << 4) | 8, 128, ((center.getZ() >> 4) << 4) | 8);
		center = getGround(world, center);

		if (!canSpawn(world, center, rand)) {
			return false;
		}

		int posY = center.getY() + 1;

		Template structure = getStructure(world, center, rand);
		Rotation rotation = getRotation(world, center, rand);
		Mirror mirror = getMirror(world, center, rand);
		BlockPos offset = Template.transformedBlockPos(new PlacementSettings().setRotation(rotation).setMirror(mirror), structure.getSize());
		center = center.add(0, getYOffset(structure, world, center, rand) + 0.5, 0);

		StructureBoundingBox bounds = makeBox(center);
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
		addStructureData(placementData);
		center = center.add(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);
		structure.addBlocksToWorld(world, center, placementData);

		TerrainMerge merge = getTerrainMerge(world, center, rand);
		int x1 = center.getX();
		int z1 = center.getZ();
		int x2 = x1 + offset.getX();
		int z2 = z1 + offset.getZ();
		if (merge != TerrainMerge.NONE) {
			BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();

			if (x2 < x1) {
				int a = x1;
				x1 = x2;
				x2 = a;
			}

			if (z2 < z1) {
				int a = z1;
				z1 = z2;
				z2 = a;
			}

			int surfMax = posY - 1;
			for (int x = x1; x <= x2; x++) {
				mut.setPos(x, mut.getY(), mut.getZ());
				for (int z = z1; z <= z2; z++) {
					mut.setPos(mut.getX(), mut.getY(), z);
					mut.setY(surfMax);
					IBlockState state = world.getBlockState(mut);
					if (!ModTags.GEN_TERRAIN.contains(state.getBlock()) && state.isSideSolid(world, mut, EnumFacing.DOWN)) {
						for (int i = 0; i < 10; i++) {
							mut.setY(mut.getY() - 1);
							IBlockState stateSt = world.getBlockState(mut);
							if (!ModTags.GEN_TERRAIN.contains(stateSt.getBlock())) {
								if (merge == TerrainMerge.SURFACE) {
									IBlockState top = (world.getBiome(mut) instanceof ExtendedBiome)?((ExtendedBiome)world.getBiome(mut)).getSurface().config.getTop() :world.getBiome(mut).topBlock;
									boolean isTop = mut.getY() == surfMax && state.getMaterial().isOpaque();
									IBlockState surfaceState = isTop ? top : world.getBiome(mut).fillerBlock;
									BlockHelper.setWithoutUpdate(world, mut, surfaceState);
								} else {
									BlockHelper.setWithoutUpdate(world, mut, state);
								}
							} else {
								if (ModTags.END_GROUND.contains(stateSt.getBlock()) && state.getMaterial().isOpaque()) {
									if (merge == TerrainMerge.SURFACE) {
										IBlockState surfaceState = world.getBiome(mut).fillerBlock;
										BlockHelper.setWithoutUpdate(world, mut, surfaceState);
									} else {
										BlockHelper.setWithoutUpdate(world, mut, state);
									}
								}
								break;
							}
						}
					}
				}
			}
		}
		BlockHelper.fixBlocks(world, new BlockPos(x1, center.getY(), z1), new BlockPos(x2, center.getY() + offset.getY(), z2));

		return true;
	}

	protected StructureBoundingBox makeBox(BlockPos pos) {
		int sx = ((pos.getX() >> 4) << 4) - 16;
		int sz = ((pos.getZ() >> 4) << 4) - 16;
		int ex = sx + 47;
		int ez = sz + 47;
		return new StructureBoundingBox(sx, 0, sz, ex, 255, ez);
	}

	public static Template readStructure(String path, String replacePath) {
		try {
			InputStream inputstream = MinecraftServer.class.getResourceAsStream(path);
			return readStructureFromStream(inputstream, replacePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static Template readStructure(ResourceLocation resource) {
		String ns = resource.getNamespace();
		String nm = resource.getPath();

		try {
			InputStream inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + ns + "/structures/" + nm + ".nbt");
			return readStructureFromStream(inputstream, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Template readStructureFromStream(InputStream stream, String replacePath) throws IOException {
		NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(stream);

		NbtModIdReplacer.readAndReplace(nbttagcompound, replacePath);

		Template template = new Template();
		template.read(nbttagcompound);

		return template;
	}
	
	public static enum TerrainMerge 
	{
		NONE,
		SURFACE,
		OBJECT;
		
		public static TerrainMerge getFromString(String type) 
		{
			if (type.equals("surface")) 
			{
				return SURFACE;
			}
			else if (type.equals("object")) 
			{
				return OBJECT;
			}
			else 
			{
				return NONE;
			}
		}
	}
}
