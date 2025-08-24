package mod.beethoven92.betterendforge.common.world.feature;

import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class ChorusPlantFeature extends WorldGenerator {
    @Override
    public boolean generate(World world, Random random, BlockPos blockPos) {
        if (world.isAirBlock(blockPos) && ModTags.END_GROUND.contains(world.getBlockState(blockPos.down()).getBlock())) {
            BlockChorusFlower.generatePlant(world, blockPos, random, 8);
            return true;
        } else {
            return false;
        }
    }
}
