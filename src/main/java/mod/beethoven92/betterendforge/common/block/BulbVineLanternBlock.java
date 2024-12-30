package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.material.IColoredBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BulbVineLanternBlock extends Block implements IDyedBlock, IColoredBlock
{
	private static final AxisAlignedBB SHAPE_CEIL = new AxisAlignedBB(4.0D / 16.0D, 4.0D / 16.0D, 4.0D / 16.0D, 12.0D / 16.0D, 16.0D / 16.0D, 12.0D / 16.0D);
	private static final AxisAlignedBB SHAPE_FLOOR = new AxisAlignedBB(4.0D / 16.0D, 0.0D, 4.0D / 16.0D, 12.0D / 16.0D, 12.0D / 16.0D, 12.0D / 16.0D);
	public static final PropertyBool HANGING = PropertyBool.create("hanging");
	public EnumDyeColor color;

	public BulbVineLanternBlock()
	{
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setHardness(1.0F);
		this.setLightLevel(1.0F); // Equivalent to light level 15
		this.setDefaultState(this.blockState.getBaseState().withProperty(HANGING, false));
	}

	public BulbVineLanternBlock(EnumDyeColor color)
	{
		this();
		this.color = color;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return state.getValue(HANGING) ? SHAPE_CEIL : SHAPE_FLOOR;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, HANGING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(HANGING, (meta & 1) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(HANGING) ? 1 : 0;
	}

	@Override
	public Block createFromColor(EnumDyeColor color) {
		return new BulbVineLanternBlock(color);
	}

	public EnumDyeColor getColor(){
		return color;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState superState = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		if(facing==EnumFacing.DOWN){
			superState = superState.withProperty(HANGING, true);
		}
		return superState;
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
}
