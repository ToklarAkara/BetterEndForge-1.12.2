package mod.beethoven92.betterendforge.mixin.minecraft;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPlayer.class)
public abstract class PlayerEntityMixin {
//	private static EnumFacing[] HORIZONTAL; TODO RESPAWN OBELISK
//
//	@Inject(method = "func_242374_a", at = @At(value = "HEAD"), cancellable = true)
//	private static void statueRespawn(WorldServer world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vector3d>> info) {
//		IBlockState blockState = world.getBlockState(pos);
//		if (blockState.getBlock()==(ModBlocks.RESPAWN_OBELISK)) {
//			info.setReturnValue(beObeliskRespawnPosition(world, pos, blockState));
//			info.cancel();
//		}
//	}
//
//	private static Optional<Vector3d> beObeliskRespawnPosition(WorldServer world, BlockPos pos, IBlockState state) {
//		if (state.getValue(BlockProperties.TRIPLE_SHAPE) == TripleShape.TOP) {
//			pos = pos.down(2);
//		}
//		else if (state.getValue(BlockProperties.TRIPLE_SHAPE) == TripleShape.MIDDLE) {
//			pos = pos.down();
//		}
//		if (HORIZONTAL == null) {
//			HORIZONTAL = BlockHelper.makeHorizontal();
//		}
//		ModMathHelper.shuffle(HORIZONTAL, world.rand);
//		for (EnumFacing dir: HORIZONTAL) {
//			BlockPos p = pos.offset(dir);
//			IBlockState state2 = world.getBlockState(p);
//			if (!state2.getMaterial().blocksMovement() && state2.getBoundingBox(world, pos).isEmpty()) {
//				return Optional.of(new Vector3d(p.getX(), p.getY(), p.getZ()).add(0.5, 0, 0.5));
//			}
//		}
//		return Optional.empty();
//	}
}