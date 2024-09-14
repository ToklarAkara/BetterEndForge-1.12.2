package mod.beethoven92.betterendforge.common.world.feature;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class BiomeNBTStructures extends WorldGenerator {
	private StructureInfo selected;

	private static final HashMap<BetterEndBiome, List<StructureInfo>> nbtStructures = Maps.newHashMap();

	// Called in setupCommon
	public static void loadStructures() {
		for (BetterEndBiome endBiome : ModBiomes.getModBiomes()) {
			if (!endBiome.getNBTStructures().isEmpty()) {
				nbtStructures.put(endBiome, endBiome.getNBTStructures());
			}
		}
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!canSpawn(world, pos, random)) {
			return false;
		}

		Template structure = getStructure(world, pos, random);
		if (structure == null) {
			return false;
		}

		PlacementSettings settings = new PlacementSettings()
				.setRotation(getRotation(world, pos, random))
				.setMirror(getMirror(world, pos, random))
				.setIgnoreEntities(false);

		BlockPos offsetPos = pos.add(0, getYOffset(structure, world, pos, random), 0);
		structure.addBlocksToWorld(world, offsetPos, settings);

		return true;
	}

	protected Template getStructure(World world, BlockPos pos, Random random) {
		Biome biome = world.getBiome(pos);
		BetterEndBiome endBiome = ModBiomes.getFromBiome(biome);
		List<StructureInfo> biomeStructures = nbtStructures.get(endBiome);

		if (biomeStructures == null || biomeStructures.isEmpty()) {
			return null;
		}

		selected = biomeStructures.get(random.nextInt(biomeStructures.size()));
		return selected.getStructure();
	}

	protected boolean canSpawn(World world, BlockPos pos, Random random) {
		if (!nbtStructures.containsKey(ModBiomes.getFromBiome(world.getBiome(pos)))) {
			return false;
		}

		int cx = pos.getX() >> 4;
		int cz = pos.getZ() >> 4;
		return ((cx + cz) & 1) == 0 && pos.getY() > 58 && ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down()).getBlock());
	}

	protected Rotation getRotation(World world, BlockPos pos, Random random) {
		return Rotation.values()[random.nextInt(Rotation.values().length)];
	}

	protected Mirror getMirror(World world, BlockPos pos, Random random) {
		return Mirror.values()[random.nextInt(Mirror.values().length)];
	}

	protected int getYOffset(Template structure, World world, BlockPos pos, Random random) {
		return selected.offsetY;
	}

	protected NBTFeature.TerrainMerge getTerrainMerge(World world, BlockPos pos, Random random) {
		return selected.terrainMerge;
	}

	public static final class StructureInfo {
		public final NBTFeature.TerrainMerge terrainMerge;
		public final String structurePath;
		public final String replacePath;
		public final int offsetY;

		private Template structure;

		public StructureInfo(String structurePath, String replacePath, int offsetY, NBTFeature.TerrainMerge terrainMerge) {
			this.terrainMerge = terrainMerge;
			this.structurePath = structurePath;
			this.replacePath = replacePath;
			this.offsetY = offsetY;
		}

		public Template getStructure() {
			if (structure == null) {
				structure = StructureHelper.readStructure(structurePath);
				// Use this to replace the mod id of the blocks in the structure and save the updated file
				// structure = NBTFeature.readStructure(structurePath, replacePath);
			}
			return structure;
		}
	}
}
