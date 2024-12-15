package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.BlockChorusPlant;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(value = BlockChorusPlant.class, priority = 100)
public abstract class ChorusPlantBlockMixin extends Block {

	public ChorusPlantBlockMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState plant = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		if (Blocks.CHORUS_PLANT.canPlaceBlockAt(world,pos) && plant.getBlock()==(Blocks.CHORUS_PLANT) && ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) {
			return plant.withProperty(BlockChorusPlant.DOWN, true);
		}
		return plant;
	}

//	@Inject(method = "makeConnections", at = @At("RETURN"), cancellable = true)
//	private void beConnectionProperties(IBlockReader blockGetter, BlockPos blockPos, CallbackInfoReturnable<BlockState> info)
//	{
//		BlockState plant = info.getReturnValue();
//		if (plant.isIn(Blocks.CHORUS_PLANT) && blockGetter.getBlockState(blockPos.down()).isIn(ModTags.END_GROUND)) {
//			info.setReturnValue(plant.with(BlockStateProperties.DOWN, true));
//		}
//
//	}

	@Inject(method = "canPlaceBlockAt", at = @At("HEAD"), cancellable = true)
	private void isValidPosition(World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
	{
		IBlockState down = world.getBlockState(pos.down());
		if (down.getBlock()==(ModBlocks.CHORUS_NYLIUM) || down.getBlock()==(Blocks.END_STONE)) {
			cir.setReturnValue(true);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		IBlockState plant = worldIn.getBlockState(pos);
		if (plant.getBlock()==(Blocks.CHORUS_PLANT) && ModTags.END_GROUND.contains(worldIn.getBlockState(pos.down()).getBlock())) {
			plant = plant.withProperty(BlockChorusPlant.DOWN, true);
			worldIn.setBlockState(pos, plant);

		}
	}
}
