package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlime;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class JellyshroomCapBlock extends BlockSlime
{
	public static final PropertyInteger COLOR = PropertyInteger.create("color", 0, 7);
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(0);
	private final Vec3i colorStart;
	private final Vec3i colorEnd;
	private final int coloritem;

	public JellyshroomCapBlock(int r1, int g1, int b1, int r2, int g2, int b2)
	{
		super();
		colorStart = new Vec3i(r1, g1, b1);
		colorEnd = new Vec3i(r2, g2, b2);
		coloritem = ModMathHelper.color((r1 + r2) >> 1, (g1 + g2) >> 1, (b1 + b2) >> 1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, 0));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		double px = pos.getX() * 0.1;
		double py = pos.getY() * 0.1;
		double pz = pos.getZ() * 0.1;

		return this.getDefaultState().withProperty(COLOR, ModMathHelper.floor(NOISE.eval(px, py, pz) * 3.5 + 4));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, COLOR);
	}

	public int getBlockColor(IBlockState state)
	{
		float delta = (float) state.getValue(COLOR) / 7F;
		int r = MathHelper.floor(AdvMathHelper.lerp(delta, colorStart.getX() / 255F, colorEnd.getX() / 255F) * 255F);
		int g = MathHelper.floor(AdvMathHelper.lerp(delta, colorStart.getY() / 255F, colorEnd.getY() / 255F) * 255F);
		int b = MathHelper.floor(AdvMathHelper.lerp(delta, colorStart.getZ() / 255F, colorEnd.getZ() / 255F) * 255F);
		return ModMathHelper.color(r, g, b);
	}

	public static int getItemColor(ItemStack stack)
	{
		if (stack.getItem() instanceof ItemBlock)
		{
			ItemBlock itemBlock = (ItemBlock) stack.getItem();
			if (itemBlock.getBlock() instanceof JellyshroomCapBlock)
			{
				JellyshroomCapBlock block = (JellyshroomCapBlock) itemBlock.getBlock();
				return block.coloritem;
			}
		}
		return 0;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(COLOR);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(COLOR, meta);
	}
}
