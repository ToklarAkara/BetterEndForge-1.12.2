package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class AuroraCrystalBlock extends Block
{
	public static final Vec3i[] COLORS;

	static
	{
		COLORS = new Vec3i[]
				{
						new Vec3i(247,  77, 161),
						new Vec3i(120, 184, 255),
						new Vec3i(120, 255, 168),
						new Vec3i(243,  58, 255)
				};
	}

	public AuroraCrystalBlock()
	{
		super(Material.GLASS);
		setSoundType(SoundType.GLASS);
	}

	public static int getBlockColor(BlockPos pos)
	{
		long i = (long) pos.getX() + (long) pos.getY() + (long) pos.getZ();
		double delta = i * 0.1;
		int index = MathHelper.floor(delta);
		int index2 = (index + 1) & 3;
		delta -= index;
		index &= 3;

		Vec3i color1 = COLORS[index];
		Vec3i color2 = COLORS[index2];

		int r = MathHelper.floor(AdvMathHelper.lerp(delta, color1.getX(), color2.getX()));
		int g = MathHelper.floor(AdvMathHelper.lerp(delta, color1.getY(), color2.getY()));
		int b = MathHelper.floor(AdvMathHelper.lerp(delta, color1.getZ(), color2.getZ()));

		return ModMathHelper.color(r, g, b);
	}

	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		return this.quantityDropped(random) + random.nextInt(fortune + 1);
	}

	protected ItemStack getSilkTouchDrop(IBlockState state)
	{
		return new ItemStack(this, 1, 0);
	}

	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ModItems.CRYSTAL_SHARDS;
	}

	public static int getItemColor()
	{
		return ModMathHelper.color(COLORS[3].getX(), COLORS[3].getY(), COLORS[3].getZ());
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
