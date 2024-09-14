package mod.beethoven92.betterendforge.common.world.structure.piece;

import java.util.Random;
import java.util.function.Consumer;

import mod.beethoven92.betterendforge.common.init.ModStructurePieces;
import mod.beethoven92.betterendforge.common.world.structure.StructureWorld;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class VoxelPiece extends StructureComponent
{
	private StructureWorld world;
	
	public VoxelPiece(Consumer<StructureWorld> function, int id) 
	{
		super(id);
		world = new StructureWorld();
		function.accept(world);
		this.boundingBox = world.getBounds();
	}

	@Override
	protected void readStructureFromNBT(NBTTagCompound nbt, TemplateManager p_143011_2_) {
		world = new StructureWorld(nbt.getCompoundTag("world"));
		this.boundingBox = world.getBounds();
	}

	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		this.world.placeChunk(worldIn, new ChunkPos( (structureBoundingBoxIn.minX+structureBoundingBoxIn.maxX)/2 << 4,
				(structureBoundingBoxIn.minZ+structureBoundingBoxIn.maxZ)/2 << 4));
		return true;
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound) {
		tagCompound.setTag("world", world.toNBT());
	}


}
