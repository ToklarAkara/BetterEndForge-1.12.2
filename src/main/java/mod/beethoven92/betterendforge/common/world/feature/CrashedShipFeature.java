package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.StructureHelper;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class CrashedShipFeature extends NBTFeature
{
//	private static final StructureProcessor REPLACER; TODO CHECK
	private static final String STRUCTURE_PATH = "/data/minecraft/structures/end_city/ship.nbt";
	private Template structure;
	
//	static
//	{
//		REPLACER = new StructureProcessor()
//		{
//			@Override
//			public Template.BlockInfo process(IWorldReader worldView, BlockPos pos, BlockPos blockPos,
//											  Template.BlockInfo structureBlockInfo, Template.BlockInfo structureBlockInfo2,
//											  PlacementSettings structurePlacementData, Template template)
//			{
//				BlockState state = structureBlockInfo2.state;
//				if (state.isIn(Blocks.SPAWNER) || state.getMaterial().equals(Material.WOOL))
//				{
//					return new Template.BlockInfo(structureBlockInfo2.pos, Blocks.AIR.getDefaultState(), null);
//				}
//				return structureBlockInfo2;
//			}
//
//			@Override
//			protected IStructureProcessorType<?> getType()
//			{
//				return IStructureProcessorType.NOP;
//			}
//		};
//	}
	
	@Override
	protected Template getStructure(World world, BlockPos pos, Random random) 
	{
		if (structure == null && world instanceof WorldServer)
		{
			structure = ((WorldServer)world).getStructureTemplateManager().getTemplate(world.getMinecraftServer(), new ResourceLocation("end_city/ship"));
			if (structure == null) 
			{
				structure = StructureHelper.readStructure(STRUCTURE_PATH);
			}
		}
		return structure;
	}

	@Override
	protected boolean canSpawn(World world, BlockPos pos, Random random)
	{
		return pos.getY() > 58 && ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.down()));
	}

	protected Rotation getRotation(World world, BlockPos pos, Random random) {
		return Rotation.values()[random.nextInt(Rotation.values().length)];
	}

	@Override
	protected Mirror getMirror(World world, BlockPos pos, Random random) 
	{
		return Mirror.values()[random.nextInt(3)];
	}

	@Override
	protected int getYOffset(Template structure, World world, BlockPos pos, Random random) 
	{
		int min = structure.getSize().getY() >> 3;
		int max = structure.getSize().getY() >> 2;
		return -ModMathHelper.randRange(min, max, random);
	}

	@Override
	protected TerrainMerge getTerrainMerge(World world, BlockPos pos, Random random) 
	{
		return TerrainMerge.NONE;
	}

	@Override
	protected void addStructureData(PlacementSettings data) 
	{
		//data.addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK).addProcessor(REPLACER).setIgnoreEntities(true);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos center) {
		center = new BlockPos(((center.getX() >> 4) << 4) | 8, 128, ((center.getZ() >> 4) << 4) | 8);
		center = getGround(world, center);
		StructureBoundingBox bounds = makeBox(center);

		if (!canSpawn(world, center, rand)) {
			return false;
		}

		Template structure = getStructure(world, center, rand);
		Rotation rotation = getRotation(world, center, rand);
		Mirror mirror = getMirror(world, center, rand);
		BlockPos offset = Template.transformedBlockPos(new PlacementSettings().setRotation(rotation).setMirror(mirror), structure.getSize());
		center = center.add(0, getYOffset(structure, world, center, rand) + 0.5, 0);
		PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror);
		center = center.add(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);

		StructureBoundingBox structB = getBoundingBox(structure, placementData, center);
		bounds = StructureHelper.intersectBoxes(bounds, structB);

		addStructureData(placementData);
		structure.addBlocksToWorld(world, center, placementData.setBoundingBox(bounds), 2);

		StructureHelper.erodeIntense(world, bounds, rand);
		BlockHelper.fixBlocks(world, new BlockPos(bounds.minX, bounds.minY, bounds.minZ), new BlockPos(bounds.maxX, bounds.maxY, bounds.maxZ));

		return true;
	}

	public BlockPos getSize(Template template, Rotation p_186257_1_) {
		BlockPos size = template.getSize();
		switch(p_186257_1_) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				return new BlockPos(size.getZ(), size.getY(), size.getX());
			default:
				return size;
		}
	}

	public StructureBoundingBox getBoundingBox(Template structure, PlacementSettings p_215388_1_, BlockPos p_215388_2_) {
		return getBoundingBox(structure, p_215388_2_, p_215388_1_.getRotation(), BlockPos.ORIGIN, p_215388_1_.getMirror());
	}

	public StructureBoundingBox getBoundingBox(Template structure, BlockPos p_237150_1_, Rotation p_237150_2_, BlockPos p_237150_3_, Mirror p_237150_4_) {
		BlockPos blockpos = getSize(structure, p_237150_2_);
		int i = p_237150_3_.getX();
		int j = p_237150_3_.getZ();
		int k = blockpos.getX() - 1;
		int l = blockpos.getY() - 1;
		int i1 = blockpos.getZ() - 1;
		StructureBoundingBox mutableboundingbox = new StructureBoundingBox(0, 0, 0, 0, 0, 0);
		switch(p_237150_2_) {
			case COUNTERCLOCKWISE_90:
				mutableboundingbox = new StructureBoundingBox(i - j, 0, i + j - i1, i - j + k, l, i + j);
				break;
			case CLOCKWISE_90:
				mutableboundingbox = new StructureBoundingBox(i + j - k, 0, j - i, i + j, l, j - i + i1);
				break;
			case CLOCKWISE_180:
				mutableboundingbox = new StructureBoundingBox(i + i - k, 0, j + j - i1, i + i, l, j + j);
				break;
			case NONE:
				mutableboundingbox = new StructureBoundingBox(0, 0, 0, k, l, i1);
		}

		switch(p_237150_4_) {
			case LEFT_RIGHT:
				this.mirrorAABB(p_237150_2_, i1, k, mutableboundingbox, EnumFacing.NORTH, EnumFacing.SOUTH);
				break;
			case FRONT_BACK:
				this.mirrorAABB(p_237150_2_, k, i1, mutableboundingbox, EnumFacing.WEST, EnumFacing.EAST);
			case NONE:
		}

		mutableboundingbox.offset(p_237150_1_.getX(), p_237150_1_.getY(), p_237150_1_.getZ());
		return mutableboundingbox;
	}

	private void mirrorAABB(Rotation p_215385_1_, int p_215385_2_, int p_215385_3_, StructureBoundingBox p_215385_4_, EnumFacing p_215385_5_, EnumFacing p_215385_6_) {
		BlockPos blockpos = BlockPos.ORIGIN;
		if (p_215385_1_ != Rotation.CLOCKWISE_90 && p_215385_1_ != Rotation.COUNTERCLOCKWISE_90) {
			if (p_215385_1_ == Rotation.CLOCKWISE_180) {
				blockpos = relative(blockpos, p_215385_6_, p_215385_2_);
			} else {
				blockpos = relative(blockpos, p_215385_5_, p_215385_2_);
			}
		} else {
			blockpos = relative(blockpos, p_215385_1_.rotate(p_215385_5_), p_215385_3_);
		}

		p_215385_4_.offset(blockpos.getX(), 0, blockpos.getZ());
	}

	public BlockPos relative(BlockPos cur, EnumFacing p_177967_1_, int p_177967_2_) {
		return p_177967_2_ == 0 ? cur : new BlockPos(cur.getX() + p_177967_1_.getDirectionVec().getX() * p_177967_2_, cur.getY() + p_177967_1_.getDirectionVec().getY() * p_177967_2_, cur.getZ() + p_177967_1_.getDirectionVec().getZ() * p_177967_2_);
	}
}
