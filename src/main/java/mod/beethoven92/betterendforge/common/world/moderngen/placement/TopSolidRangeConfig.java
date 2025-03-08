package mod.beethoven92.betterendforge.common.world.moderngen.placement;

public class TopSolidRangeConfig implements IPlacementConfig {

    public final int bottomOffset;
    public final int topOffset;
    public final int maximum;

    public TopSolidRangeConfig(int bottomOffset, int topOffset, int maximum) {
        this.bottomOffset = bottomOffset;
        this.topOffset = topOffset;
        this.maximum = maximum;
    }
}