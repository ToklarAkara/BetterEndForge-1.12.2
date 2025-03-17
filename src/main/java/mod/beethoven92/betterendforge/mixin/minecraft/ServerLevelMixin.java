package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldServer.class)
public abstract class ServerLevelMixin extends World {
    @Unique
    private static String be_lastWorld = null;

    protected ServerLevelMixin(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }


    @Inject(method = "<init>*", at = @At("TAIL"))
    private void be_onServerWorldInit(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn, CallbackInfo ci) {
        if (be_lastWorld != null && be_lastWorld.equals(saveHandlerIn.loadWorldInfo().getWorldName())) {
            return;
        }

        be_lastWorld = saveHandlerIn.loadWorldInfo().getWorldName();
        WorldServer world = WorldServer.class.cast(this);
        ModBiomes.onWorldLoad(world.getSeed(), ForgeRegistries.BIOMES);
    }

    @Inject(method = "getSpawnCoordinate", at = @At("HEAD"), cancellable = true)
    private void be_getSharedSpawnPos(CallbackInfoReturnable<BlockPos> info) {
        if (GeneratorOptions.changeSpawn()) {
            if (WorldServer.class.cast(this).provider.getDimension() == 1) {
                BlockPos pos = GeneratorOptions.getSpawn();
                info.setReturnValue(pos);
            }
        }
    }

//	@Inject(method = "func_241121_a_", at = @At("HEAD"), cancellable = true)
//	private static void be_createObsidianPlatform(WorldServer serverLevel, CallbackInfo info) {
//		if (!GeneratorOptions.generateObsidianPlatform()) {
//			info.cancel();
//		}
//		else if (GeneratorOptions.changeSpawn()) {
//			BlockPos blockPos = GeneratorOptions.getSpawn();
//			int i = blockPos.getX();
//			int j = blockPos.getY() - 2;
//			int k = blockPos.getZ();
//			BlockPos.getAllInBoxMutable(i - 2, j + 1, k - 2, i + 2, j + 3, k + 2).forEach((blockPosx) -> {
//				serverLevel.setBlockState(blockPosx, Blocks.AIR.getDefaultState());
//			});
//			BlockPos.getAllInBoxMutable(i - 2, j, k - 2, i + 2, j, k + 2).forEach((blockPosx) -> {
//				serverLevel.setBlockState(blockPosx, Blocks.OBSIDIAN.getDefaultState());
//			});
//			info.cancel();
//		}
//	}

//	@ModifyArg(method = "tickEnvironment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/server/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
//	private IBlockState be_modifyTickState(BlockPos pos, IBlockState state) {
//		if (state.getBlock()==(Blocks.ICE)) {
//			ResourceLocation biome = ModBiomes.getBiomeID(getBiome(pos));
//			if (biome.getNamespace().equals(BetterEnd.MOD_ID)) {
//				state = ModBlocks.EMERALD_ICE.getDefaultState();
//			}
//		}
//		return state;
//	}
}
