package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HelixTreeLeavesBlock extends Block {
	public static final PropertyInteger COLOR = PropertyInteger.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);

	public HelixTreeLeavesBlock() {
		super(Material.LEAVES);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, 0));
	}

    public HelixTreeLeavesBlock(Material leaves, MapColor adobe) {
        super(leaves, adobe);
    }

    @Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, COLOR);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOR, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR);
	}

	public static int getBlockColor(IBlockState state) {
		return ModMathHelper.color(237, getGreen(state.getValue(COLOR)), 20);
	}

	public static int getItemColor() {
		return ModMathHelper.color(237, getGreen(4), 20);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer) {
		double px = pos.getX() * 0.1;
		double py = pos.getY() * 0.1;
		double pz = pos.getZ() * 0.1;
		return this.getDefaultState().withProperty(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}

	private static int getGreen(int color) {
		float delta = color / 7F;
		return (int) MathHelper.clamp(delta * 78 + 80, 80, 158);
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
		return false;
	}
}
