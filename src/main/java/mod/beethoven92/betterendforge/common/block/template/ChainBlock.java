package mod.beethoven92.betterendforge.common.block.template;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChainBlock extends BlockRotatedPillar {
    protected static final AxisAlignedBB Y_AXIS_SHAPE;
    protected static final AxisAlignedBB Z_AXIS_SHAPE;
    protected static final AxisAlignedBB X_AXIS_SHAPE;

    public ChainBlock() {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        this.setDefaultState(getDefaultState().withProperty(AXIS, EnumFacing.Axis.Y));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(AXIS)) {
            case X:
            default:
                return X_AXIS_SHAPE;
            case Z:
                return Z_AXIS_SHAPE;
            case Y:
                return Y_AXIS_SHAPE;
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

    static {
        Y_AXIS_SHAPE = new AxisAlignedBB(6.5D /16D, 0.0D /16D, 6.5D /16D, 9.5D /16D, 16.0D /16D, 9.5D /16D);
        Z_AXIS_SHAPE = new AxisAlignedBB(6.5D /16D, 6.5D /16D, 0.0D /16D, 9.5D /16D, 9.5D /16D, 16.0D /16D);
        X_AXIS_SHAPE = new AxisAlignedBB(0.0D /16D, 6.5D /16D, 6.5D /16D, 16.0D /16D, 9.5D /16D, 9.5D /16D);
    }
}