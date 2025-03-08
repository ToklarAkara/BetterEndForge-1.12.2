package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModLanternBlock extends Block {
	private static final AxisAlignedBB SHAPE_CEIL = new AxisAlignedBB(3D/16D, 1D/16D, 3D/16D, 13D/16D, 16D/16D, 13D/16D);
	private static final AxisAlignedBB SHAPE_FLOOR = new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 15D/16D, 13D/16D);
	private static final Vec3i[] COLORS = AuroraCrystalBlock.COLORS;
	public static final PropertyBool HANGING = PropertyBool.create("hanging");

	public ModLanternBlock() {
		super(Material.IRON);
		setHardness(3.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HANGING, true));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(HANGING) ? SHAPE_CEIL : SHAPE_FLOOR;
	}

	public static int getBlockColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
		long i = (long) pos.getX() + (long) pos.getY() + (long) pos.getZ();
		double delta = i * 0.1;
		int index = ModMathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;

		Vec3i color1 = COLORS[index];
		Vec3i color2 = COLORS[index2];

		int r = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = ModMathHelper.floor(AdvMathHelper.lerp(delta, color1.getZ(), color2.getZ()));

		return ModMathHelper.color(r, g, b);
	}

	public static int getItemColor(ItemStack stack, int tintIndex) {
		return ModMathHelper.color(COLORS[3].getX(), COLORS[3].getY(), COLORS[3].getZ());
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(HANGING, true);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state;
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HANGING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(HANGING) ? 1 : 0);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(HANGING, meta==1);
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
