package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class DecoratedFeature extends WorldGenerator {
    DecoratedFeatureConfig config;

    public DecoratedFeature(DecoratedFeatureConfig config){
        this.config = config;
    }

    public boolean generate(World p_241855_1_, Random p_241855_3_, BlockPos p_241855_4_) {
        MutableBoolean mutableboolean = new MutableBoolean();
        config.decorator.getPositions(p_241855_1_, p_241855_3_, p_241855_4_).forEach((p_242772_5_) -> {
            if (config.feature.get().place(p_241855_1_, p_241855_3_, p_242772_5_)) {
                mutableboolean.setTrue();
            }

        });
        return mutableboolean.isTrue();
    }

}