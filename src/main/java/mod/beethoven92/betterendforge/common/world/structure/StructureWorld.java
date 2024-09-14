package mod.beethoven92.betterendforge.common.world.structure;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class StructureWorld
{
	private Map<ChunkPos, Part> parts = Maps.newHashMap();
	private ChunkPos lastPos;
	private Part lastPart;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int minZ = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int maxZ = Integer.MIN_VALUE;

	public StructureWorld() {}

	public StructureWorld(NBTTagCompound tag)
	{
		minX = tag.getInteger("minX");
		maxX = tag.getInteger("maxX");
		minY = tag.getInteger("minY");
		maxY = tag.getInteger("maxY");
		minZ = tag.getInteger("minZ");
		maxZ = tag.getInteger("maxZ");

		NBTTagList map = tag.getTagList("parts", 10);
		for (int i = 0; i < map.tagCount(); i++) {
			NBTTagCompound compound = map.getCompoundTagAt(i);
			Part part = new Part(compound);
			int x = compound.getInteger("x");
			int z = compound.getInteger("z");
			parts.put(new ChunkPos(x, z), part);
		}
	}

	public void setBlock(BlockPos pos, IBlockState state)
	{
		ChunkPos cPos = new ChunkPos(pos);

		if (cPos.equals(lastPos))
		{
			lastPart.addBlock(pos, state);
			return;
		}

		Part part = parts.get(cPos);
		if (part == null)
		{
			part = new Part();
			parts.put(cPos, part);

			if (cPos.x < minX) minX = cPos.x;
			if (cPos.x > maxX) maxX = cPos.x;
			if (cPos.z < minZ) minZ = cPos.z;
			if (cPos.z > maxZ) maxZ = cPos.z;
		}

		if (pos.getY() < minY) minY = pos.getY();
		if (pos.getY() > maxY) maxY = pos.getY();
		part.addBlock(pos, state);

		lastPos = cPos;
		lastPart = part;
	}

	public boolean placeChunk(World world, ChunkPos chunkPos)
	{
		Part part = parts.get(chunkPos);
		if (part != null) {
			Chunk chunk = world.getChunk(chunkPos.x, chunkPos.z);
			part.placeChunk(chunk);
			return true;
		}
		return false;
	}

	public NBTTagCompound toNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("minX", minX);
		tag.setInteger("maxX", maxX);
		tag.setInteger("minY", minY);
		tag.setInteger("maxY", maxY);
		tag.setInteger("minZ", minZ);
		tag.setInteger("maxZ", maxZ);
		NBTTagList map = new NBTTagList();
		tag.setTag("parts", map);
		parts.forEach((pos, part) -> {
			map.appendTag(part.toNBT(pos.x, pos.z));
		});
		return tag;
	}

	public StructureBoundingBox getBounds()
	{
		if (minX == Integer.MAX_VALUE || maxX == Integer.MIN_VALUE || minZ == Integer.MAX_VALUE || maxZ == Integer.MIN_VALUE)
		{
			return new StructureBoundingBox();
		}
		return new StructureBoundingBox(minX << 4, minY, minZ << 4, (maxX << 4) | 15, maxY, (maxZ << 4) | 15);
	}

	private static final class Part
	{
		Map<BlockPos, IBlockState> blocks = Maps.newHashMap();

		public Part() {}

		public Part(NBTTagCompound tag)
		{
			NBTTagList map = tag.getTagList("blocks", 10);
			NBTTagList map2 = tag.getTagList("states", 10);
			IBlockState[] states = new IBlockState[map2.tagCount()];
			for (int i = 0; i < states.length; i++)
			{
				states[i] = NBTUtil.readBlockState(map2.getCompoundTagAt(i));
			}

			for (int i = 0; i < map.tagCount(); i++) {
				NBTTagCompound block = map.getCompoundTagAt(i);
				BlockPos pos = NBTUtil.getPosFromTag(block.getCompoundTag("pos"));
				int stateID = block.getInteger("state");
				IBlockState state = stateID < states.length ? states[stateID] : Block.getStateById(stateID);
				blocks.put(pos, state);
			}
		}

		void addBlock(BlockPos pos, IBlockState state)
		{
			BlockPos inner = new BlockPos(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
			blocks.put(inner, state);
		}

		void placeChunk(Chunk chunk)
		{
			blocks.forEach((pos, state) -> {
				chunk.setBlockState(pos, state);
			});
		}

		NBTTagCompound toNBT(int x, int z)
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("x", x);
			tag.setInteger("z", z);
			NBTTagList map = new NBTTagList();
			tag.setTag("blocks", map);
			NBTTagList stateMap = new NBTTagList();
			tag.setTag("states", stateMap);

			int[] id = new int[1];
			Map<IBlockState, Integer> states = Maps.newHashMap();

			blocks.forEach((pos, state) -> {
				int stateID = states.getOrDefault(state, -1);
				if (stateID < 0) {
					stateID = id[0]++;
					states.put(state, stateID);
					stateMap.appendTag(NBTUtil.writeBlockState(new NBTTagCompound(), state));
				}

				NBTTagCompound block = new NBTTagCompound();
				block.setTag("pos", NBTUtil.createPosTag(pos));
				block.setInteger("state", stateID);
				map.appendTag(block);
			});

			return tag;
		}
	}
}
