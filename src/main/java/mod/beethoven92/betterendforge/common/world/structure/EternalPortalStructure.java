package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Random;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import mod.beethoven92.betterendforge.common.world.structure.piece.NBTPiece;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class EternalPortalStructure extends WorldGenerator {
	private static final ResourceLocation STRUCTURE_ID = new ResourceLocation(BetterEnd.MOD_ID, "portal/eternal_portal");
	private static final Template STRUCTURE = StructureHelper.readStructure(STRUCTURE_ID);

	public EternalPortalStructure() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		int chunkX = pos.getX() >> 4;
		int chunkZ = pos.getZ() >> 4;
		if (!canSpawnStructureAtCoords(world, chunkX, chunkZ)) {
			return false;
		}

		int x = (chunkX << 4) | ModMathHelper.randRange(4, 12, random);
		int z = (chunkZ << 4) | ModMathHelper.randRange(4, 12, random);
		int y = world.getHeight(x, z);
		if (y > 10) {
			BlockPos blockPos = new BlockPos(x, y - 4, z);
			NBTPiece piece = new NBTPiece(STRUCTURE_ID, STRUCTURE, blockPos, random.nextInt(5), true, random);
			piece.addComponentParts(world, random, new StructureBoundingBox(blockPos, blockPos.add(16, 16, 16)));
		}
		return true;
	}

	private boolean canSpawnStructureAtCoords(World world, int chunkX, int chunkZ) {
		long x = chunkX * chunkX;
		long z = chunkZ * chunkZ;
		long d = x * x + z * z;
		if (d < 1024) {
			return false;
		}
		if (world.getHeight((chunkX << 4) | 8, (chunkZ << 4) | 8) < 58) {
			return false;
		}
		return getGenerationHeight(chunkX, chunkZ, world) >= 20;
	}

	private static int getGenerationHeight(int chunkX, int chunkZ, World world) {
		Random random = new Random((long) (chunkX + chunkZ * 10387313));
		Rotation blockRotation = Rotation.values()[random.nextInt(Rotation.values().length)];
		int i = 5;
		int j = 5;
		if (blockRotation == Rotation.CLOCKWISE_90) {
			i = -5;
		} else if (blockRotation == Rotation.CLOCKWISE_180) {
			i = -5;
			j = -5;
		} else if (blockRotation == Rotation.COUNTERCLOCKWISE_90) {
			j = -5;
		}

		int k = (chunkX << 4) + 7;
		int l = (chunkZ << 4) + 7;
		int m = world.getHeight(k, l);
		int n = world.getHeight(k, l + j);
		int o = world.getHeight(k + i, l);
		int p = world.getHeight(k + i, l + j);
		return Math.min(Math.min(m, n), Math.min(o, p));
	}
}
