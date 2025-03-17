package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockAnvil.class)
public class AnvilBlockMixin {
    @Inject(method = "onEndFalling", at = @At("HEAD"), cancellable = true)
    private void be_onEndFalling(World worldIn, BlockPos pos, IBlockState fallingState, IBlockState hitState, CallbackInfo info) {
        if (fallingState.getBlock() instanceof EndAnvilBlock) {
            PropertyInteger destruction = BlockAnvil.DAMAGE;
            int destructionLevel = fallingState.getValue(destruction);
            try {
                IBlockState state = fallingState.withProperty(destruction, destructionLevel + 1);
                worldIn.setBlockState(pos, state);
                info.cancel();
            } catch (Exception ex) {
                worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
                info.cancel();
            }
        }
    }
}
