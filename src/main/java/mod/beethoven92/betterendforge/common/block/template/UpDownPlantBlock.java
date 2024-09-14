package mod.beethoven92.betterendforge.common.block.template;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

public class UpDownPlantBlock extends Block
{
	private static final AxisAlignedBB SHAPE = new AxisAlignedBB(4.0D / 16.0D, 0.0D, 4.0D / 16.0D, 12.0D / 16.0D, 16.0D / 16.0D, 12.0D / 16.0D);

	public UpDownPlantBlock(Material mat)
	{
		super(mat);
		setSoundType(SoundType.PLANT);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return SHAPE;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState down = worldIn.getBlockState(pos.down());
		IBlockState up = worldIn.getBlockState(pos.up());
		return (isTerrain(down) || down.getBlock() == this) && (isSupport(up, worldIn, pos) || up.getBlock() == this);
	}

	protected boolean isTerrain(IBlockState state)
	{
		return ModTags.END_GROUND.contains(state.getBlock());
	}

	protected boolean isSupport(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.isSideSolid(world, pos, EnumFacing.UP);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer)
	{
		return this.getDefaultState();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this);
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canPlaceBlockAt(worldIn, pos))
		{
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, net.minecraft.entity.player.EntityPlayer player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);
		worldIn.notifyNeighborsOfStateChange(pos, Blocks.AIR, false);
	}
}
