package mod.beethoven92.betterendforge.common.world.feature;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFDisplacement;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class TenaneaBushFeature extends WorldGenerator
{
	private static final Function<IBlockState, Boolean> REPLACE;
	private static final EnumFacing[] DIRECTIONS = EnumFacing.values();
	
	static 
	{
		REPLACE = (state) -> {
			if (state.getMaterial().equals(Material.PLANTS)) 
			{
				return true;
			}
			return state.getMaterial().isReplaceable();
		};
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		if (!ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) return false;

		Random rand = world.rand;
		float radius = ModMathHelper.randRange(1.8F, 3.5F, rand);
		OpenSimplexNoise noise = new OpenSimplexNoise(rand.nextInt());
		IBlockState leaves = ModBlocks.TENANEA_LEAVES.getDefaultState();
		SDF sphere = new SDFSphere().setRadius(radius).setBlock(leaves);
		sphere = new SDFScale3D().setScale(1, 0.75F, 1).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return (float) noise.eval(vec.getX() * 0.2, vec.getY() * 0.2, vec.getZ() * 0.2) * 3; }).setSource(sphere);
		sphere = new SDFDisplacement().setFunction((vec) -> { return ModMathHelper.randRange(-2F, 2F, rand); }).setSource(sphere);
		sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(new SDFTranslate().setTranslate(0, -radius, 0).setSource(sphere));
		sphere.setReplaceFunction(REPLACE);
		List<BlockPos> support = Lists.newArrayList();
		sphere.addPostProcess((info) -> {
			if (info.getState().getBlock() instanceof BlockLeaves)
			{
				int distance =  Math.abs(info.getPos().getX()-pos.getX())+Math.abs(info.getPos().getZ()-pos.getZ());
				if (distance < 7) 
				{
					if (rand.nextInt(4) == 0 && info.getStateDown().getBlock()==Blocks.AIR)
					{
						BlockPos d = info.getPos().down();
						support.add(d);
					}
					
					ModMathHelper.shuffle(DIRECTIONS, rand);
					for (EnumFacing d: DIRECTIONS)
					{
						if (info.getState(d).getBlock()== Blocks.AIR)
						{
							info.setBlockPos(info.getPos().offset(d), ModBlocks.TENANEA_OUTER_LEAVES.getDefaultState().withProperty(FurBlock.FACING, d));
						}
					}
					
					return info.getState();
				}
				else 
				{
					return Blocks.AIR.getDefaultState();
				}
			}
			return info.getState();
		});
		sphere.fillRecursive(world, pos);
		IBlockState stem = ModBlocks.TENANEA.bark.getDefaultState();
		BlockHelper.setWithoutUpdate(world, pos, stem);
		for (EnumFacing d: EnumFacing.values())
		{
			BlockPos p = pos.offset(d);
			if (world.isAirBlock(p)) 
			{
				BlockHelper.setWithoutUpdate(world, p, leaves);
			}
		}
		
		Mutable mut = new Mutable();
		IBlockState top = ModBlocks.TENANEA_FLOWERS.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.TOP);
		IBlockState middle = ModBlocks.TENANEA_FLOWERS.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.MIDDLE);
		IBlockState bottom = ModBlocks.TENANEA_FLOWERS.getDefaultState().withProperty(BlockProperties.TRIPLE_SHAPE, TripleShape.BOTTOM);
		support.forEach((bpos) -> {
			IBlockState state = world.getBlockState(bpos);
			if (state.getBlock()==Blocks.AIR || state.getBlock()==ModBlocks.TENANEA_OUTER_LEAVES)
			{
				int count = ModMathHelper.randRange(3, 8, rand);
				mut.setPos(bpos);
				if (world.getBlockState(mut.up()).getBlock()==ModBlocks.TENANEA_LEAVES)
				{
					BlockHelper.setWithoutUpdate(world, mut, top);
					for (int i = 1; i < count; i++) 
					{
						mut.setY(mut.getY() - 1);
						if (world.isAirBlock(mut.down())) 
						{
							BlockHelper.setWithoutUpdate(world, mut, middle);
						}
						else {
							break;
						}
					}
					BlockHelper.setWithoutUpdate(world, mut, bottom);
				}
			}
		});
		
		return true;
	}	
}
