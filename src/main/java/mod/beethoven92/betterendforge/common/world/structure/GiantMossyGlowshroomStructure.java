package mod.beethoven92.betterendforge.common.world.structure;

import java.util.List;
import java.util.Random;


import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.MossyGlowshroomCapBlock;
import mod.beethoven92.betterendforge.common.block.template.FurBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.util.ModMathHelper;
import mod.beethoven92.betterendforge.common.util.SplineHelper;
import mod.beethoven92.betterendforge.common.util.sdf.SDF;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFBinary;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFCoordModify;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFFlatWave;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFRound;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFScale3D;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSmoothUnion;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFSubtraction;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFTranslate;
import mod.beethoven92.betterendforge.common.util.sdf.operator.SDFUnion;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFCappedCone;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFPrimitive;
import mod.beethoven92.betterendforge.common.util.sdf.primitive.SDFSphere;
import mod.beethoven92.betterendforge.common.util.sdf.vector.Vector3f;
import mod.beethoven92.betterendforge.common.world.generator.OpenSimplexNoise;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GiantMossyGlowshroomStructure extends SDFStructure
{
	
	public Decoration getDecorationStage() 
	{
		return Decoration.SURFACE_STRUCTURES;
	}
	
	@Override
	public String getStructureName() 
	{
		return BetterEnd.MOD_ID + ":giant_mossy_glowshroom_structure";
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		return BlockPos.ORIGIN;
	}

	@Override
	protected SDF getSDF(BlockPos center, Random random) 
	{
		SDFCappedCone cone1 = new SDFCappedCone().setHeight(2.5F).setRadius1(1.5F).setRadius2(2.5F);
		SDFCappedCone cone2 = new SDFCappedCone().setHeight(3F).setRadius1(2.5F).setRadius2(13F);
		SDF posedCone2 = new SDFTranslate().setTranslate(0, 5, 0).setSource(cone2);
		SDF posedCone3 = new SDFTranslate().setTranslate(0, 12F, 0).setSource(new SDFScale().setScale(2).setSource(cone2));
		SDF upCone = new SDFSubtraction().setSourceA(posedCone2).setSourceB(posedCone3);
		SDF wave = new SDFFlatWave().setRaysCount(12).setIntensity(1.3F).setSource(upCone);
		SDF cones = new SDFSmoothUnion().setRadius(3).setSourceA(cone1).setSourceB(wave);
		
		SDF innerCone = new SDFTranslate().setTranslate(0, 1.25F, 0).setSource(upCone);
		innerCone = new SDFScale3D().setScale(1.2F, 1F, 1.2F).setSource(innerCone);
		cones = new SDFUnion().setSourceA(cones).setSourceB(innerCone);
		
		SDF glowCone = new SDFCappedCone().setHeight(3F).setRadius1(2F).setRadius2(12.5F);
		SDFPrimitive priGlowCone = (SDFPrimitive) glowCone;
		glowCone = new SDFTranslate().setTranslate(0, 4.25F, 0).setSource(glowCone);
		glowCone = new SDFSubtraction().setSourceA(glowCone).setSourceB(posedCone3);
		
		cones = new SDFUnion().setSourceA(cones).setSourceB(glowCone);
		
		OpenSimplexNoise noise = new OpenSimplexNoise(1234);
		cones = new SDFCoordModify().setFunction((pos) -> {
			float dist = ModMathHelper.length(pos.getX(), pos.getZ());
			float y = pos.getY() + (float) noise.eval(pos.getX() * 0.1 + center.getX(), pos.getZ() * 0.1 + center.getZ()) * dist * 0.3F - dist * 0.15F;
			pos.set(pos.getX(), y, pos.getZ());
		}).setSource(cones);
		
		SDFTranslate HEAD_POS = (SDFTranslate) new SDFTranslate().setSource(new SDFTranslate().setTranslate(0, 2.5F, 0).setSource(cones));
		
		SDF roots = new SDFSphere().setRadius(4F);
		SDFPrimitive primRoots = (SDFPrimitive) roots;
		roots = new SDFScale3D().setScale(1, 0.7F, 1).setSource(roots);
		SDFFlatWave rotRoots = (SDFFlatWave) new SDFFlatWave().setRaysCount(5).setIntensity(1.5F).setSource(roots);
		
		SDFBinary function = new SDFSmoothUnion().setRadius(4).setSourceB(new SDFUnion().setSourceA(HEAD_POS).setSourceB(rotRoots));
		
		cone1.setBlock(ModBlocks.MOSSY_GLOWSHROOM_CAP);
		cone2.setBlock(ModBlocks.MOSSY_GLOWSHROOM_CAP);
		priGlowCone.setBlock(ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE);
		primRoots.setBlock(ModBlocks.MOSSY_GLOWSHROOM.bark);
		
		float height = MathHelper.nextFloat(random, 10F, 25F);
		int count = MathHelper.floor(height / 4);
		List<Vector3f> spline = SplineHelper.makeSpline(0, 0, 0, 0, height, 0, count);
		SplineHelper.offsetParts(spline, random, 1F, 0, 1F);
		SDF sdf = SplineHelper.buildSDF(spline, 2.1F, 1.5F, (pos) -> {
			return ModBlocks.MOSSY_GLOWSHROOM.log.getDefaultState();
		});
		Vector3f pos = spline.get(spline.size() - 1);
		float scale = MathHelper.nextFloat(random, 2F, 3.5F);
		
		HEAD_POS.setTranslate(pos.getX(), pos.getY(), pos.getZ());
		rotRoots.setAngle(random.nextFloat() * ((float)Math.PI * 2));
		function.setSourceA(sdf);
		
		return new SDFRound().setRadius(1.5F).setSource(new SDFScale()
				.setScale(scale)
				.setSource(function))
				.addPostProcess((info) -> {
					if (ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getState()))
					{
						if (random.nextBoolean() && info.getStateUp().getBlock() == ModBlocks.MOSSY_GLOWSHROOM_CAP) 
						{
							info.setState(ModBlocks.MOSSY_GLOWSHROOM_CAP.getDefaultState().withProperty(MossyGlowshroomCapBlock.TRANSITION, true));
							return info.getState();
						}
						else if (!ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getStateUp()) || !ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getStateDown()))
						{
							info.setState(ModBlocks.MOSSY_GLOWSHROOM.bark.getDefaultState());
							return info.getState();
						}
					}
					else if (info.getState().getBlock() == ModBlocks.MOSSY_GLOWSHROOM_CAP) 
					{
						if (ModBlocks.MOSSY_GLOWSHROOM.isTreeLog(info.getStateDown().getBlock())) 
						{
							info.setState(ModBlocks.MOSSY_GLOWSHROOM_CAP.getDefaultState().withProperty(MossyGlowshroomCapBlock.TRANSITION, true));
							return info.getState();
						}
						
						info.setState(ModBlocks.MOSSY_GLOWSHROOM_CAP.getDefaultState());
						return info.getState();
					}
					else if (info.getState().getBlock() == ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE) 
					{
						for (EnumFacing dir: BlockHelper.HORIZONTAL_DIRECTIONS) 
						{
							if (info.getState(dir) == Blocks.AIR.getDefaultState()) 
							{
								info.setBlockPos(info.getPos().offset(dir), ModBlocks.MOSSY_GLOWSHROOM_FUR.getDefaultState().withProperty(FurBlock.FACING, dir));
							}
						}
						
						if (info.getStateDown().getBlock() != ModBlocks.MOSSY_GLOWSHROOM_HYMENOPHORE) 
						{
							info.setBlockPos(info.getPos().down(), ModBlocks.MOSSY_GLOWSHROOM_FUR.getDefaultState().withProperty(FurBlock.FACING, EnumFacing.DOWN));
						}
					}
					return info.getState();
				});
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return true;
	}
}
