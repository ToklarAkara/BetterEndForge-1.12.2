package mod.beethoven92.betterendforge.common.world.moderngen.surfacebuilders;


import net.minecraft.block.state.IBlockState;

public class SurfaceBuilderConfig implements ISurfaceBuilderConfig {

    private final IBlockState topMaterial;
    private final IBlockState underMaterial;
    private final IBlockState underWaterMaterial;

    public SurfaceBuilderConfig(IBlockState topMaterial, IBlockState underMaterial, IBlockState underWaterMaterial) {
        this.topMaterial = topMaterial;
        this.underMaterial = underMaterial;
        this.underWaterMaterial = underWaterMaterial;
    }

    public IBlockState getTop() {
        return this.topMaterial;
    }

    public IBlockState getUnder() {
        return this.underMaterial;
    }

    public IBlockState getUnderWaterMaterial() {
        return this.underWaterMaterial;
    }
}