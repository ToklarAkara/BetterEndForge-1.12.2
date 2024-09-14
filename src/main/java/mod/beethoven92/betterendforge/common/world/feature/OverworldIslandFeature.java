package mod.beethoven92.betterendforge.common.world.feature;

import java.util.Random;

import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class OverworldIslandFeature extends WorldGenerator
{
	private static final OpenSimplexNoise NOISE = new OpenSimplexNoise(412);
	private static final Mutable CENTER = new Mutable();
	private static final SDF ISLAND;
	
	static 
	{
		SDF cone = new SDFCappedCone().setRadius1(0).setRadius2(6).setHeight(4).setBlock((pos) -> {
			if (pos.getY() == CENTER.getY()) return Blocks.GRASS.getDefaultState();
			if (pos.getY() == CENTER.getY() - 1) 
			{
				return Blocks.DIRT.getDefaultState();
			} 
			else if (pos.getY() == CENTER.getY() - Math.round(2.0 * Math.random())) 
			{
				return Blocks.DIRT.getDefaultState();
			}
			return Blocks.STONE.getDefaultState();
		});
		cone = new SDFTranslate().setTranslate(0, -3, 0).setSource(cone);
		cone = new SDFDisplacement().setFunction((pos) -> {
			return (float) NOISE.eval(CENTER.getX() + pos.getX(), CENTER.getY() + pos.getY(), CENTER.getZ() + pos.getZ());
		}).setSource(cone).setReplaceFunction(state -> {
			return state.getBlock()==(Blocks.WATER) || state.getMaterial().isReplaceable();
		});
		ISLAND = cone;
	}
	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		CENTER.setPos(pos);
		ISLAND.fillRecursive(world, pos.down());
		return true;
	}

}
