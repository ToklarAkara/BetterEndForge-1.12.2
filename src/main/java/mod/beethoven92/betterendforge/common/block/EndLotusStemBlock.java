package mod.beethoven92.betterendforge.common.block;

import java.util.Map;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EndLotusStemBlock extends Block implements IShearable
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool LEAF = PropertyBool.create("leaf");
	public static final PropertyEnum<TripleShape> SHAPE = PropertyEnum.create("shape", TripleShape.class);
	private static final Map<Axis, AxisAlignedBB> SHAPES = Maps.newEnumMap(Axis.class);

	static
	{
		SHAPES.put(Axis.X, new AxisAlignedBB(0D/16D, 6D/16D, 6D/16D, 16D/16D, 10D/16D, 10D/16D));
		SHAPES.put(Axis.Y, new AxisAlignedBB(6D/16D, 0D/16D, 6D/16D, 10D/16D, 16D/16D, 10D/16D));
		SHAPES.put(Axis.Z, new AxisAlignedBB(6D/16D, 6D/16D, 0D/16D, 10D/16D, 10D/16D, 16D/16D));
	}

	public EndLotusStemBlock(Material materialIn)
	{
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, TripleShape.MIDDLE).withProperty(LEAF, false).withProperty(FACING, EnumFacing.UP));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING, LEAF, SHAPE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(LEAF) ? SHAPES.get(Axis.Y) : SHAPES.get(state.getValue(FACING).getAxis());
	}


	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
//		if (state.getValue(WATERLOGGED))
//		{
//			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
//		}
	}

	@Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) { return true; }
	@Override
	public java.util.List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return com.google.common.collect.Lists.newArrayList(new ItemStack(Item.getItemFromBlock(this)));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}//TODO META

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
}
