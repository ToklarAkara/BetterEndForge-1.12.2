package mod.beethoven92.betterendforge.mixin.minecraft;

import java.util.Random;

import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockChorusPlant;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(value = BlockChorusFlower.class, priority = 100)
public abstract class ChorusFlowerBlockMixin extends Block {
    @Unique
    private static final AxisAlignedBB SHAPE_FULL = new AxisAlignedBB(0, 0, 0, 1.0, 1.0, 16 / 16d);
    @Unique
    private static final AxisAlignedBB SHAPE_HALF = new AxisAlignedBB(0, 0, 0, 1.0, 4d / 16d, 1.0);

    public ChorusFlowerBlockMixin(Material blockMaterialIn, MapColor blockMapColorIn) {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(method = "canPlaceBlockAt", at = @At("HEAD"), cancellable = true)
    private void isValidPosition(World world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (world.getBlockState(pos.down()).getBlock() == (ModBlocks.CHORUS_NYLIUM)) {
            info.setReturnValue(true);
            info.cancel();
        }
    }

    @Inject(method = "updateTick", at = @At("HEAD"), cancellable = true)
    private void randomTick(World world, BlockPos pos, IBlockState state, Random rand, CallbackInfo ci) {
        if (ModTags.END_GROUND.contains(world.getBlockState(pos.down()).getBlock())) {
            BlockPos up = pos.up();
            if (world.isAirBlock(up) && up.getY() < 256) {
                int i = state.getValue(BlockChorusFlower.AGE);
                if (i < 5) {
                    this.placeGrownFlower(world, up, i + 1);
                    BlockHelper.setWithoutUpdate(world, pos, Blocks.CHORUS_PLANT.getDefaultState().withProperty(BlockChorusPlant.UP, true).withProperty(BlockChorusPlant.DOWN, true));
                    ci.cancel();
                }
            }
        }
    }

    @Shadow
    private void placeGrownFlower(World world, BlockPos pos, int age) {
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (GeneratorOptions.changeChorusPlant()) {
            return state.getValue(BlockChorusFlower.AGE) == 5 ? SHAPE_HALF : SHAPE_FULL;
        } else {
            return super.getBoundingBox(state, source, pos);
        }
    }

    @Inject(method = "placeDeadFlower", at = @At("HEAD"), cancellable = true)
    private void beOnDie(World world, BlockPos pos, CallbackInfo info) {
        IBlockState down = world.getBlockState(pos.down());
        if (down.getBlock() == (Blocks.CHORUS_PLANT) || ModTags.GEN_TERRAIN.contains(down.getBlock())) {
            world.setBlockState(pos, this.getDefaultState().withProperty(BlockChorusFlower.AGE, 5), 2);
            world.playEvent(1034, pos, 0);
        }
        info.cancel();
    }
}
