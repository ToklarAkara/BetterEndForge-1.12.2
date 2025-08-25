package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.UpDownPlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class GlowingPillarRootsBlock extends UpDownPlantBlock {
	public static final PropertyEnum<TripleShape> SHAPE = PropertyEnum.create("shape", TripleShape.class);

	public GlowingPillarRootsBlock() {
		super(Material.PLANTS);
		setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, TripleShape.BOTTOM));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, TripleShape.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock(), 1, 0));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
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

	@Override
	public int quantityDropped(Random p_149745_1_) {
		return 2+p_149745_1_.nextInt(3);
	}

	@Override
	public Item getItemDropped(IBlockState p_180660_1_, Random p_180660_2_, int p_180660_3_) {
		if(p_180660_1_.getValue(SHAPE)== TripleShape.BOTTOM){
			return Item.getItemFromBlock(ModBlocks.GLOWING_PILLAR_SEED);
		}
		return super.getItemDropped(p_180660_1_, p_180660_2_, p_180660_3_);
	}
}
