package mod.beethoven92.betterendforge.common.block.template;

import java.util.function.ToIntFunction;

import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.PedestalState;
import mod.beethoven92.betterendforge.common.init.ModTileEntityTypes;
import mod.beethoven92.betterendforge.common.tileentity.PedestalTileEntity;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PedestalBlock extends Block
{
	public static final PropertyEnum<PedestalState> STATE = BlockProperties.PEDESTAL_STATE;
	public static final PropertyBool HAS_ITEM = BlockProperties.HAS_ITEM;
	public static final PropertyBool HAS_LIGHT = BlockProperties.HAS_LIGHT;

	private static final AxisAlignedBB SHAPE_DEFAULT;
	private static final AxisAlignedBB SHAPE_COLUMN;
	private static final AxisAlignedBB SHAPE_PILLAR;
	private static final AxisAlignedBB SHAPE_PEDESTAL_TOP;
	private static final AxisAlignedBB SHAPE_COLUMN_TOP;
	private static final AxisAlignedBB SHAPE_BOTTOM;

	static
	{
		AxisAlignedBB basinUp = new AxisAlignedBB(2D/16D, 3D/16D, 2D/16D, 14D/16D, 4D/16D, 14D/16D);
		AxisAlignedBB basinDown = new AxisAlignedBB(0D/16D, 0D/16D, 0D/16D, 16D/16D, 3D/16D, 16D/16D);
		AxisAlignedBB columnTopUp = new AxisAlignedBB(1D/16D, 14D/16D, 1D/16D, 15D/16D, 16D/16D, 15D/16D);
		AxisAlignedBB columnTopDown = new AxisAlignedBB(2D/16D, 13D/16D, 2D/16D, 14D/16D, 14D/16D, 14D/16D);
		AxisAlignedBB pedestalTop = new AxisAlignedBB(1D/16D, 8D/16D, 1D/16D, 15D/16D, 10D/16D, 15D/16D);
		AxisAlignedBB pedestalDefault = new AxisAlignedBB(1D/16D, 12D/16D, 1D/16D, 15D/16D, 14D/16D, 15D/16D);
		AxisAlignedBB pillar = new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 8D/16D, 13D/16D);
		AxisAlignedBB pillarDefault = new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 12D/16D, 13D/16D);
		AxisAlignedBB columnTop = columnTopDown.union(columnTopUp);
		AxisAlignedBB basin = basinDown.union(basinUp);
		SHAPE_PILLAR = new AxisAlignedBB(3D/16D, 0D/16D, 3D/16D, 13D/16D, 16D/16D, 13D/16D);
		SHAPE_DEFAULT = basin.union(pillarDefault).union(pedestalDefault);
		SHAPE_PEDESTAL_TOP = pillar.union(pedestalTop);
		SHAPE_COLUMN_TOP = SHAPE_PILLAR.union(columnTop);
		SHAPE_COLUMN = basin.union(SHAPE_PILLAR).union(columnTop);
		SHAPE_BOTTOM = basin.union(SHAPE_PILLAR);
	}

	protected float height = 1.0F;

	public PedestalBlock()
	{
		super(Material.GROUND);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(STATE, PedestalState.DEFAULT).withProperty(HAS_ITEM, false).withProperty(HAS_LIGHT, false));
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(HAS_LIGHT) ? 12 : 0;
	}

	public float getHeight(IBlockState state)
	{
		if (state.getBlock() instanceof PedestalBlock && state.getValue(STATE) == PedestalState.PEDESTAL_TOP)
		{
			return this.height - 0.2F;
		}
		return this.height;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getBlock()==(this))
		{
			switch(state.getValue(STATE))
			{
				case BOTTOM:
				{
					return SHAPE_BOTTOM;
				}
				case PEDESTAL_TOP:
				{
					return SHAPE_PEDESTAL_TOP;
				}
				case COLUMN_TOP:
				{
					return SHAPE_COLUMN_TOP;
				}
				case PILLAR:
				{
					return SHAPE_PILLAR;
				}
				case COLUMN:
				{
					return SHAPE_COLUMN;
				}
				default:
				{
					return SHAPE_DEFAULT;
				}
			}
		}
		return new AxisAlignedBB(0,0,0,1,1,1);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new PedestalTileEntity();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote|| state.getBlock()!=(this)){
			return true; //TODO CHECK CONSUME
		}
		if (!this.isPlaceable(state))
		{
			return false;
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof PedestalTileEntity)
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) tileEntity;
			if (pedestal.isEmpty())
			{
				ItemStack itemStack = player.getHeldItem(hand);
				if (itemStack.isEmpty()) return true; //TODO CHECK CONSUME
				pedestal.setStack(player.capabilities.isCreativeMode ? itemStack.copy().splitStack(1) : itemStack.splitStack(1));
				//this.checkRitual(worldIn, pos);
				return true;
			}
			else
			{
				ItemStack itemStack = pedestal.getStack();
				if (player.addItemStackToInventory(itemStack))
				{
					pedestal.removeStack(worldIn, state);
					return true;
				}
				return false;
			}
		}
		return false;
	}

    /*public void checkRitual(World world, BlockPos pos)
    {
        Mutable mut = new Mutable();
        Point[] points = InfusionRitual.getMap();
        for (Point p: points)
        {
            mut.setPos(pos).move(p.x, 0, p.y);
            IBlockState state = world.getBlockState(mut);
            if (state.getBlock() instanceof InfusionPedestal)
            {
                ((InfusionPedestal) state.getBlock()).checkRitual(world, mut);
                break;
            }
        }
    }*/

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof PedestalTileEntity)
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) tileentity;
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), pedestal.getStack());
			worldIn.updateComparatorOutputLevel(pos, this);
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState upState = world.getBlockState(pos.up());
		IBlockState downState = world.getBlockState(pos.down());
		boolean upSideSolid = upState.getBlock().isSideSolid(upState, world, pos.up(), EnumFacing.DOWN) || upState.getBlock() instanceof BlockWall;
		boolean hasPedestalOver = upState.getBlock() instanceof PedestalBlock;
		boolean hasPedestalUnder = downState.getBlock() instanceof PedestalBlock;
		if (!hasPedestalOver && hasPedestalUnder && upSideSolid)
		{
			return this.getDefaultState().withProperty(STATE, PedestalState.COLUMN_TOP);
		}
		else if (!hasPedestalOver && !hasPedestalUnder && upSideSolid)
		{
			return this.getDefaultState().withProperty(STATE, PedestalState.COLUMN);
		}
		else if (hasPedestalUnder && hasPedestalOver)
		{
			return this.getDefaultState().withProperty(STATE, PedestalState.PILLAR);
		}
		else if (hasPedestalUnder)
		{
			return this.getDefaultState().withProperty(STATE, PedestalState.PEDESTAL_TOP);
		}
		else if (hasPedestalOver)        {
			return this.getDefaultState().withProperty(STATE, PedestalState.BOTTOM);
		}
		return this.getDefaultState();
	}


	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos currentPos, IBlockState stateIn, EntityLivingBase placer, ItemStack stack) {
		IBlockState updated = this.getUpdatedState(stateIn, EnumFacing.DOWN, stateIn, worldIn, currentPos, currentPos); //TODO CHECK
		if (updated.getBlock()!=(this)) {
			worldIn.setBlockState(currentPos, updated);
		}
		if (!this.isPlaceable(updated))
		{
			this.moveStoredStack(worldIn, updated, currentPos);
		}
		worldIn.setBlockState(currentPos, updated);
	}

	private IBlockState getUpdatedState(IBlockState state, EnumFacing direction, IBlockState newState, World world, BlockPos pos, BlockPos posFrom)
	{
		if (state.getBlock()!=(this)){
			return world.getBlockState(pos); //TODO CHECK
		}
		if (direction != EnumFacing.UP && direction != EnumFacing.DOWN) return state;
		IBlockState upState = world.getBlockState(pos.up());
		IBlockState downState = world.getBlockState(pos.down());
		boolean upSideSolid = upState.getBlock().isSideSolid(upState, world, pos.up(), EnumFacing.DOWN) || upState.getBlock() instanceof BlockWall;
		boolean hasPedestalOver = upState.getBlock() instanceof PedestalBlock;
		boolean hasPedestalUnder = downState.getBlock() instanceof PedestalBlock;
		if (direction == EnumFacing.UP)
		{
			upSideSolid = newState.getBlock().isSideSolid( newState, world, posFrom, EnumFacing.DOWN) || newState.getBlock() instanceof BlockWall;
			hasPedestalOver = newState.getBlock() instanceof PedestalBlock;
		}
		else if (direction == EnumFacing.DOWN)
		{
			hasPedestalUnder = newState.getBlock() instanceof PedestalBlock;
		}
		if (!hasPedestalOver && hasPedestalUnder && upSideSolid)
		{
			return state.withProperty(STATE, PedestalState.COLUMN_TOP);
		}
		else if (!hasPedestalOver && !hasPedestalUnder && upSideSolid)
		{
			return state.withProperty(STATE, PedestalState.COLUMN);
		}
		else if (hasPedestalUnder && hasPedestalOver)
		{
			return state.withProperty(STATE, PedestalState.PILLAR);
		}
		else if (hasPedestalUnder)
		{
			return state.withProperty(STATE, PedestalState.PEDESTAL_TOP);
		}
		else if (hasPedestalOver)
		{
			return state.withProperty(STATE, PedestalState.BOTTOM);
		}
		return state.withProperty(STATE, PedestalState.DEFAULT);
	}

	private void moveStoredStack(World world, IBlockState state, BlockPos pos)
	{
		ItemStack stack = ItemStack.EMPTY;
		TileEntity blockEntity = world.getTileEntity(pos);
		if (blockEntity instanceof PedestalTileEntity && state.getBlock()==(this))
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) blockEntity;
			stack = pedestal.getStack();
			pedestal.clear();
			BlockHelper.setWithoutUpdate(world, pos, state.withProperty(HAS_ITEM, false));
		}
		if (!stack.isEmpty()) {
			BlockPos upPos = pos.up();
			this.moveStoredStack(world, stack, world.getBlockState(upPos), upPos);
		}
	}

	private void moveStoredStack(World world, ItemStack stack, IBlockState state, BlockPos pos)
	{
		TileEntity blockEntity = world.getTileEntity(pos);
		if (state.getBlock()!=(this))
		{
			this.dropStoredStack(blockEntity, stack, pos);
		}
		else if (state.getValue(STATE).equals(PedestalState.PILLAR))
		{
			BlockPos upPos = pos.up();
			this.moveStoredStack(world, stack, world.getBlockState(upPos), upPos);
		}
		else if (!this.isPlaceable(state))
		{
			this.dropStoredStack(blockEntity, stack, pos);
		}
		else if (blockEntity instanceof PedestalTileEntity)
		{
			PedestalTileEntity pedestal = (PedestalTileEntity) blockEntity;
			if (pedestal.isEmpty())
			{
				pedestal.setStack(stack);
			}
			else
			{
				this.dropStoredStack(blockEntity, stack, pos);
			}
		}
		else
		{
			this.dropStoredStack(blockEntity, stack, pos);
		}
	}

	private void dropStoredStack(TileEntity tileEntity, ItemStack stack, BlockPos pos)
	{
		if (tileEntity != null && tileEntity.getWorld() != null)
		{
			World world = tileEntity.getWorld();
			Block.spawnAsEntity(world, this.getDropPos(world, pos), stack);
		}
	}

	private BlockPos getDropPos(World world, BlockPos pos)
	{
		BlockPos dropPos;
		for(int i = 2; i < EnumFacing.values().length; i++)
		{
			dropPos = pos.offset(EnumFacing.byIndex(i));
			if (world.getBlockState(dropPos).getBlock()== Blocks.AIR)
			{
				return dropPos.toImmutable();
			}
		}
		if (world.getBlockState(pos.up()).getBlock()== Blocks.AIR)
		{
			return pos.up();
		}
		return this.getDropPos(world, pos.up());
	}

	protected boolean isPlaceable(IBlockState state)
	{
		if (state.getBlock()!=(this)) return false;
		PedestalState currentState = state.getValue(STATE);
		return currentState != PedestalState.BOTTOM &&
				currentState != PedestalState.COLUMN &&
				currentState != PedestalState.PILLAR &&
				currentState != PedestalState.COLUMN_TOP;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, STATE, HAS_ITEM, HAS_LIGHT);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return state.getBlock() instanceof PedestalBlock;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return blockState.getValue(HAS_ITEM) ? 15 : 0;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	} //TODO META

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState p_149662_1_) {
		return false;
	}
}

