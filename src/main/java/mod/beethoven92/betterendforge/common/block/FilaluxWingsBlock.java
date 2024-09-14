package mod.beethoven92.betterendforge.common.block;

import java.util.EnumMap;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.template.AttachedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FilaluxWingsBlock extends AttachedBlock {
	private static final EnumMap<EnumFacing, AxisAlignedBB> BOUNDING_SHAPES = Maps.newEnumMap(EnumFacing.class);

	public FilaluxWingsBlock() {
		super(Material.VINE);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDING_SHAPES.get(state.getValue(FACING));
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}


	static {
		BOUNDING_SHAPES.put(EnumFacing.UP, new AxisAlignedBB(0.0D/16D, 0.0D/16D, 0.0D/16D, 1.0D/16D, 0.5D/16D, 1.0D/16D));
		BOUNDING_SHAPES.put(EnumFacing.DOWN, new AxisAlignedBB(0.0D/16D, 0.5D/16D, 0.0D/16D, 1.0D/16D, 1.0D/16D, 1.0D/16D));
		BOUNDING_SHAPES.put(EnumFacing.NORTH, new AxisAlignedBB(0.0D/16D, 0.0D/16D, 0.5D/16D, 1.0D/16D, 1.0D/16D, 1.0D/16D));
		BOUNDING_SHAPES.put(EnumFacing.SOUTH, new AxisAlignedBB(0.0D/16D, 0.0D/16D, 0.0D/16D, 1.0D/16D, 1.0D/16D, 0.5D/16D));
		BOUNDING_SHAPES.put(EnumFacing.WEST, new AxisAlignedBB(0.5D/16D, 0.0D/16D, 0.0D/16D, 1.0D/16D, 1.0D/16D, 1.0D/16D));
		BOUNDING_SHAPES.put(EnumFacing.EAST, new AxisAlignedBB(0.0D/16D, 0.0D/16D, 0.0D/16D, 0.5D/16D, 1.0D/16D, 1.0D/16D));
	}
}
