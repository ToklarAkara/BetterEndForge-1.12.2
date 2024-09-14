package mod.beethoven92.betterendforge.common.block;

import javax.annotation.Nullable;

import mod.beethoven92.betterendforge.common.tileentity.ESignTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EndSignBlock extends BlockSign {
	public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
	public static final PropertyBool FLOOR = PropertyBool.create("floor");
	private static final AxisAlignedBB[] WALL_SHAPES = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D/16D, 4.5D/16D, 14.0D/16D, 16.0D/16D, 12.5D/16D, 16.0D/16D),
			new AxisAlignedBB(0.0D/16D, 4.5D/16D, 0.0D/16D, 2.0D/16D, 12.5D/16D, 16.0D/16D),
			new AxisAlignedBB(0.0D/16D, 4.5D/16D, 0.0D/16D, 16.0D/16D, 12.5D/16D, 2.0D/16D),
			new AxisAlignedBB(14.0D/16D, 4.5D/16D, 0.0D/16D, 16.0D/16D, 12.5D/16D, 16.0D/16D) };

	public EndSignBlock() {
		super();
		this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION, 0).withProperty(FLOOR, true));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { ROTATION, FLOOR });
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0; //TODO META FROM STATE
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(FLOOR) ? SIGN_AABB : WALL_SHAPES[state.getValue(ROTATION) >> 2];
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new ESignTileEntity();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack itemStack = playerIn.getHeldItem(hand);
		boolean bl = itemStack.getItem() instanceof ItemDye && playerIn.capabilities.allowEdit;
		if (worldIn.isRemote) {
			return bl;
		} else {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof ESignTileEntity) {
				ESignTileEntity signTileEntity = (ESignTileEntity) tileentity;
				if (bl) {
					boolean bl2 = signTileEntity.setTextColor(EnumDyeColor.byDyeDamage(itemStack.getMetadata()));
					if (bl2 && !playerIn.capabilities.isCreativeMode) {
						itemStack.shrink(1);
					}
				}
				return signTileEntity.onActivate(playerIn);
			} else {
				return false;
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (placer instanceof EntityPlayer) {
			ESignTileEntity signTileEntity = (ESignTileEntity) worldIn.getTileEntity(pos);
			if (!worldIn.isRemote) {
				signTileEntity.setEditor((EntityPlayer) placer);
				((EntityPlayerMP) placer).connection.sendPacket(new SPacketSignEditorOpen(pos));
			} else {
				signTileEntity.setEditable(true);
			}
		}
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(ROTATION, rot.rotate(state.getValue(ROTATION), 16));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withProperty(ROTATION, mirrorIn.mirrorRotation(state.getValue(ROTATION), 16));
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (facing == EnumFacing.UP) {
			return this.getDefaultState().withProperty(FLOOR, true)
					.withProperty(ROTATION, MathHelper.floor((180.0 + placer.rotationYaw * 16.0 / 360.0) + 0.5 - 12) & 15);
		} else if (facing != EnumFacing.DOWN) {
			IBlockState blockState = this.getDefaultState();
			for (EnumFacing direction : EnumFacing.Plane.HORIZONTAL) {
				if (direction.getAxis().isHorizontal()) {
					EnumFacing direction2 = direction.getOpposite();
					int rot = MathHelper.floor((180.0 + direction2.getHorizontalAngle() * 16.0 / 360.0) + 0.5 + 4) & 15;
					blockState = blockState.withProperty(ROTATION, rot);
					if (blockState.getBlock().canPlaceBlockAt(worldIn, pos)) {
						return blockState.withProperty(FLOOR, false);
					}
				}
			}
		}
		return null;
	}
}
