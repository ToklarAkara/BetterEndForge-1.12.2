package mod.beethoven92.betterendforge.data;

import net.minecraft.client.renderer.Vector3d;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;


public class AABBAcc {



    public static AxisAlignedBB ofSize(Vec3d vec3, double d, double e, double f) {
        return new AxisAlignedBB(vec3.x - d / 2.0D, vec3.y - e / 2.0D, vec3.z - f / 2.0D, vec3.x + d / 2.0D, vec3.y + e / 2.0D, vec3.z + f / 2.0D);
    }
}
