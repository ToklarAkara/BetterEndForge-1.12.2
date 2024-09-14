package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class NBTPiece extends StructureComponent {
	private ResourceLocation structureID;
	private Rotation rotation;
	private Mirror mirror;
	private Template structure;
	private BlockPos pos;
	private int erosion;
	private boolean cover;

	public NBTPiece(ResourceLocation structureID, Template structure, BlockPos pos, int erosion, boolean cover, Random random) {
		super(0);
		this.structureID = structureID;
		this.structure = structure;
		this.rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
		this.mirror = Mirror.values()[random.nextInt(Mirror.values().length)];
		this.pos = StructureHelper.offsetPos(pos, structure, rotation, mirror);
		this.erosion = erosion;
		this.cover = cover;
		makeBoundingBox();
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		tagCompound.setString("Template", structureID.toString());
		tagCompound.setInteger("rotation", rotation.ordinal());
		tagCompound.setInteger("mirror", mirror.ordinal());
		tagCompound.setInteger("erosion", erosion);
		tagCompound.setTag("pos", NBTUtil.createPosTag(pos));
		tagCompound.setBoolean("cover", cover);
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager p_143011_2_) {
		this.structureID = new ResourceLocation(nbt.getString("Template"));
		this.rotation = Rotation.values()[nbt.getInteger("rotation")];
		this.mirror = Mirror.values()[nbt.getInteger("mirror")];
		this.erosion = nbt.getInteger("erosion");
		this.pos = NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
		this.cover = nbt.getBoolean("cover");
		this.structure = StructureHelper.readStructure(structureID);
		makeBoundingBox();
	}

	private void makeBoundingBox() {
		this.boundingBox = StructureHelper.getStructureBounds(pos, structure, rotation, mirror);
	}

	@Override
	public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
		StructureBoundingBox bounds = new StructureBoundingBox(structureBoundingBox);
		bounds.maxY = this.boundingBox.maxY;
		bounds.minY = this.boundingBox.minY;
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
		structure.addBlocksToWorld(world, pos, placementData);
		if (erosion > 0) {
			bounds.maxX = ModMathHelper.min(bounds.maxX, boundingBox.maxX);
			bounds.minX = ModMathHelper.max(bounds.minX, boundingBox.minX);
			bounds.maxZ = ModMathHelper.min(bounds.maxZ, boundingBox.maxZ);
			bounds.minZ = ModMathHelper.max(bounds.minZ, boundingBox.minZ);
			StructureHelper.erode(world, bounds, erosion, random);
		}
		if (cover) {
			StructureHelper.cover(world, bounds, random);
		}
		return true;
	}
}
