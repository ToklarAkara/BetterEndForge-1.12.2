package mod.beethoven92.betterendforge.common.world.feature;

import net.minecraft.util.math.BlockPos;

public class Mutable extends BlockPos.MutableBlockPos {
    public Mutable()
    {
        super(0, 0, 0);
    }

    public Mutable(BlockPos pos)
    {
        super(pos.getX(), pos.getY(), pos.getZ());
    }

    public Mutable(int x_, int y_, int z_)
    {
        super(0, 0, 0);
        this.x = x_;
        this.y = y_;
        this.z = z_;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setZ(int z){
        this.z = z;
    }
}
