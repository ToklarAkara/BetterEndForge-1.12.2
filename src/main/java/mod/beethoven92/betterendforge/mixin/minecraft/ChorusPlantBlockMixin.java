package mod.beethoven92.betterendforge.mixin.minecraft;

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
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//Allowing chorus plants to spawn on chorus nylium and end ground blocks
@Mixin(value = BlockChorusPlant.class, priority = 100)
public abstract class ChorusPlantBlockMixin extends Block {

    public ChorusPlantBlockMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState plant = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
        if (Blocks.CHORUS_PLANT.canPlaceBlockAt(world, pos) && plant.getBlock() == (Blocks.CHORUS_PLANT) && ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) {
            return plant.withProperty(BlockChorusPlant.DOWN, true);
        }
        return plant;
    }

    @Inject(method = "canPlaceBlockAt", at = @At("HEAD"), cancellable = true)
    private void isValidPosition(World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        IBlockState down = world.getBlockState(pos.down());
        if (down.getBlock() == (ModBlocks.CHORUS_NYLIUM) || down.getBlock() == (Blocks.END_STONE)) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        IBlockState plant = worldIn.getBlockState(pos);
        if (plant.getBlock() == (Blocks.CHORUS_PLANT) && ModTags.END_GROUND.contains(worldIn.getBlockState(pos.down()).getBlock())) {
            plant = plant.withProperty(BlockChorusPlant.DOWN, true);
            worldIn.setBlockState(pos, plant);
        }
    }

    @Redirect(method = "canSurviveAt", at= @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"))
    public Block canSurviveAt(IBlockState instance) {
        if(ModTags.END_GROUND.contains(instance.getBlock())){
            return Blocks.END_STONE;
        }
        return instance.getBlock();
    }

    @Redirect(method = "getActualState", at= @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"))
    public Block getActualState(IBlockState instance) {
        if(ModTags.END_GROUND.contains(instance.getBlock())){
            return Blocks.END_STONE;
        }
        return instance.getBlock();
    }
}
