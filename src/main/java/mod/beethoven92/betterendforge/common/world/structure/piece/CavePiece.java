package mod.beethoven92.betterendforge.common.world.structure.piece;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.init.ModStructures;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class CavePiece extends BasePiece {
	private OpenSimplexNoise noise;
	private BlockPos center;
	private float radius;
	
	public CavePiece(BlockPos center, float radius, Random random) {
		super(random.nextInt());
		this.center = center;
		this.radius = radius;
		this.noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, center.getX(), center.getZ()));
		makeBoundingBox();
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tag, TemplateManager p_143011_2_) {
		makeBoundingBox();
		center = NBTUtil.getPosFromTag(tag.getCompoundTag("center"));
		radius = tag.getFloat("radius");
	}


	@Override
	protected void writeStructureToNBT(NBTTagCompound tag) {
		tag.setTag("center", NBTUtil.createPosTag(center));
		tag.setFloat("radius", radius);
		noise = new OpenSimplexNoise(ModMathHelper.getSeed(534, center.getX(), center.getZ()));
	}

	@Override
	public boolean addComponentParts(World world, Random randomIn, StructureBoundingBox blockBox) {
		int x1 = ModMathHelper.max(this.getBoundingBox().minX, blockBox.minX);
		int z1 = ModMathHelper.max(this.getBoundingBox().minZ, blockBox.minZ);
		int x2 = ModMathHelper.min(this.getBoundingBox().maxX, blockBox.maxX);
		int z2 = ModMathHelper.min(this.getBoundingBox().maxZ, blockBox.maxZ);
		int y1 = this.getBoundingBox().minY;
		int y2 = this.getBoundingBox().maxY;

		double hr = radius * 0.75;
		double nr = radius * 0.25;
		Mutable pos = new Mutable();
		for (int x = x1; x <= x2; x++) {
			int xsq = x - center.getX();
			xsq *= xsq;
			pos.setX(x);
			for (int z = z1; z <= z2; z++) {
				int zsq = z - center.getZ();
				zsq *= zsq;
				pos.setZ(z);
				for (int y = y1; y <= y2; y++) {
					int ysq = y - center.getY();
					ysq *= 1.6;
					ysq *= ysq;
					pos.setY(y);
					double r = noise.eval(x * 0.1, y * 0.1, z * 0.1) * nr + hr;
					double r2 = r - 4.5;
					double dist = xsq + ysq + zsq;
					if (dist < r2 * r2) {
						if (ModTags.END_GROUND.contains(world.getBlockState(pos).getBlock())) {
							BlockHelper.setWithoutUpdate(world, pos, Blocks.AIR);
						}
					}
					else if (dist < r * r) {
						if (world.getBlockState(pos).getMaterial().isReplaceable()) {
							BlockHelper.setWithoutUpdate(world, pos, Blocks.END_STONE);
						}
					}
				}
			}
		}

		return true;
	}

	private void makeBoundingBox() {
		int minX = ModMathHelper.floor(center.getX() - radius);
		int minY = ModMathHelper.floor(center.getY() - radius);
		int minZ = ModMathHelper.floor(center.getZ() - radius);
		int maxX = ModMathHelper.floor(center.getX() + radius + 1);
		int maxY = ModMathHelper.floor(center.getY() + radius + 1);
		int maxZ = ModMathHelper.floor(center.getZ() + radius + 1);
		this.boundingBox = new StructureBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}




	/**
	 * (abstract) Helper method to read subclass data from NBT
	 *
	 * @param tagCompound
	 */


}
