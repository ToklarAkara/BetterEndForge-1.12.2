package mod.beethoven92.betterendforge.common.world.moderngen.placement;

import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Placement<DC extends IPlacementConfig> {
    public static final Placement<ChanceConfig> CHANCE = register("chance", new ChancePlacement());
    public static final Placement<FeatureSpreadConfig> COUNT = register("count", new CountPlacement());
    public static final Placement<NoPlacementConfig> SQUARE = register("square", new SquarePlacement());
    public static final Placement<NoPlacementConfig> TOP_SOLID_HEIGHTMAP = register("top_solid_heightmap", new SimpleHeightmapBasedPlacement());
    public static final Placement<ChanceConfig> WATER_LAKE = register("water_lake", new LakeWater());
    public static final Placement<DecoratedPlacementConfig> DECORATED = register("decorated", new DecoratedPlacement());
    public static final Placement<NoPlacementConfig> HEIGHTMAP = register("heightmap", new SimpleHeightmapBasedPlacement());
    public static final Placement<TopSolidRangeConfig> RANGE = register("range", new RangePlacement());
    private static <T extends IPlacementConfig, G extends Placement<T>> G register(String p_214999_0_, G p_214999_1_) {
        return p_214999_1_;
    }

    public ConfiguredPlacement<DC> configure(DC p_227446_1_) {
        return new ConfiguredPlacement<>(this, p_227446_1_);
    }
    public abstract Stream<BlockPos> getPositions(World world, Random p_241857_2_, DC p_241857_3_, BlockPos p_241857_4_);

    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
    }
}
