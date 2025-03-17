package mod.beethoven92.betterendforge.mixin.minecraft;

import java.util.Random;

import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// TO DO: consider replacing the mixin with events and block behaviours(see grass block grow method for example)
@Mixin(ItemDye.class)
public abstract class BoneMealItemMixin {
    @Unique
    private static final EnumFacing[] betterEndForge_1_12_2$DIR = BlockHelper.makeHorizontal();
    @Unique
    private static final Mutable betterEndForge_1_12_2$POS = new Mutable();

    @Inject(method = "onItemUse", at = @At("HEAD"), cancellable = true)
    private void onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> info) {
        ItemStack itemstack = player.getHeldItem(hand);
        EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());

        if (enumdyecolor != EnumDyeColor.WHITE) {
            return;
        }
        // FIX underwater seeds not being able to grow when using bonemeal on them
        if (ItemDye.applyBonemeal(itemstack, world, blockPos, player, hand)) {
            if (!world.isRemote) {
                world.playEvent(2005, blockPos, 0);
            }

            info.setReturnValue(world.isRemote ? EnumActionResult.SUCCESS : EnumActionResult.FAIL);
            info.cancel();
        } else if (!world.isRemote) {
            BlockPos offseted = blockPos.offset(facing);
            boolean endBiome = world.provider.getDimension() == 1; //world.getBiome(offseted).getCategory() == Category.THEEND;

            if (ModTags.END_GROUND.contains(world.getBlockState(blockPos).getBlock())) {
                boolean consume = false;
                if (world.getBlockState(blockPos).getBlock() == (Blocks.END_STONE)) {
                    IBlockState nylium = betterEndForge_1_12_2$getNylium(world, blockPos);
                    if (nylium != null) {
                        BlockHelper.setWithoutUpdate(world, blockPos, nylium);
                        consume = true;
                    }
                    // Cannot grow underwater plants on end stone
                    if (world.getBlockState(offseted).getBlock() == Blocks.WATER && endBiome) {
                        info.setReturnValue(EnumActionResult.FAIL);
                        info.cancel();
                    }
                } else {
                    if (world.getBlockState(offseted).getBlock() == Blocks.WATER && endBiome) {
                        // FIX being able to use bone meal underwater on a block where plants already grew
                        if (world.getBlockState(offseted).getBlock() == (Blocks.WATER)) {
                            consume = betterEndForge_1_12_2$growWaterGrass(world, blockPos);
                        }
                    } else if (world.getBlockState(offseted).getBlock() == Blocks.AIR) // FIX being able to use bone meal on a block where plants already grew
                    {
                        consume = betterEndForge_1_12_2$growGrass(world, blockPos);
                    }
                }
                if (consume) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    world.playEvent(2005, blockPos, 0);
                    info.setReturnValue(EnumActionResult.SUCCESS);
                    info.cancel();
                }
            }
            // Prevents bonemeal generating sea grass underwater in end biomes
            else if (world.getBlockState(offseted).getBlock() == Blocks.WATER && endBiome) {
                info.setReturnValue(EnumActionResult.FAIL);
                info.cancel();
            }
        }
    }

    @Unique
    private boolean betterEndForge_1_12_2$growGrass(World world, BlockPos pos) {
        int y1 = pos.getY() + 3;
        int y2 = pos.getY() - 3;
        boolean result = false;
        for (int i = 0; i < 64; i++) {
            int x = (int) (pos.getX() + world.rand.nextGaussian() * 2);
            int z = (int) (pos.getZ() + world.rand.nextGaussian() * 2);
            betterEndForge_1_12_2$POS.setX(x);
            betterEndForge_1_12_2$POS.setZ(z);
            for (int y = y1; y >= y2; y--) {
                betterEndForge_1_12_2$POS.setY(y);
                BlockPos down = betterEndForge_1_12_2$POS.down();
                if (world.isAirBlock(betterEndForge_1_12_2$POS) && !world.isAirBlock(down)) {
                    IBlockState grass = betterEndForge_1_12_2$getGrassState(world, down);
                    if (grass != null) {
                        BlockHelper.setWithoutUpdate(world, betterEndForge_1_12_2$POS, grass);
                        result = true;
                    }
                    break;
                }
            }
        }
        return result;
    }

    @Unique
    private boolean betterEndForge_1_12_2$growWaterGrass(World world, BlockPos pos) {
        int y1 = pos.getY() + 3;
        int y2 = pos.getY() - 3;
        boolean result = false;
        for (int i = 0; i < 64; i++) {
            int x = (int) (pos.getX() + world.rand.nextGaussian() * 2);
            int z = (int) (pos.getZ() + world.rand.nextGaussian() * 2);
            betterEndForge_1_12_2$POS.setX(x);
            betterEndForge_1_12_2$POS.setZ(z);
            for (int y = y1; y >= y2; y--) {
                betterEndForge_1_12_2$POS.setY(y);
                BlockPos down = betterEndForge_1_12_2$POS.down();
                if (world.getBlockState(betterEndForge_1_12_2$POS).getBlock() == (Blocks.WATER) && ModTags.END_GROUND.contains(world.getBlockState(down).getBlock())) {
                    IBlockState grass = betterEndForge_1_12_2$getWaterGrassState(world, down);
                    if (grass != null) {
                        BlockHelper.setWithoutUpdate(world, betterEndForge_1_12_2$POS, grass);
                        result = true;
                    }
                    break;
                }
            }
        }
        return result;
    }

    @Unique
    private Block[] betterEndForge_1_12_2$glowingGrasslandsGrass() {
        return new Block[]{ModBlocks.BLOOMING_COOKSONIA, ModBlocks.VAIOLUSH_FERN,
                ModBlocks.FRACTURN, ModBlocks.SALTEAGO, ModBlocks.CREEPING_MOSS,
                ModBlocks.UMBRELLA_MOSS, ModBlocks.TWISTED_UMBRELLA_MOSS};
    }

    @Unique
    private IBlockState betterEndForge_1_12_2$getGrassState(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == ModBlocks.END_MOSS || block == ModBlocks.END_MYCELIUM) {
            if (world.getBiome(pos).getRegistryName().equals(ModBiomes.GLOWING_GRASSLANDS.getID())) {
                Block[] grasses = betterEndForge_1_12_2$glowingGrasslandsGrass();
                return grasses[world.rand.nextInt(grasses.length)].getDefaultState();
            } else {
                return world.rand.nextBoolean() ? ModBlocks.CREEPING_MOSS.getDefaultState() : ModBlocks.UMBRELLA_MOSS.getDefaultState();
            }
        } else if (block == ModBlocks.CAVE_MOSS) {
            return ModBlocks.CAVE_GRASS.getDefaultState();
        } else if (block == ModBlocks.CHORUS_NYLIUM) {
            return ModBlocks.CHORUS_GRASS.getDefaultState();
        } else if (block == ModBlocks.CRYSTAL_MOSS) {
            return ModBlocks.CRYSTAL_GRASS.getDefaultState();
        } else if (block == ModBlocks.AMBER_MOSS) {
            return ModBlocks.AMBER_GRASS.getDefaultState();
        } else if (block == ModBlocks.SHADOW_GRASS) {
            return ModBlocks.SHADOW_PLANT.getDefaultState();
        } else if (block == ModBlocks.PINK_MOSS) {
            return ModBlocks.BUSHY_GRASS.getDefaultState();
        } else if (block == ModBlocks.JUNGLE_MOSS) {
            return betterEndForge_1_12_2$getRandomGrassState(world.rand, ModBlocks.TWISTED_UMBRELLA_MOSS.getDefaultState(),
                    ModBlocks.SMALL_JELLYSHROOM.getDefaultState(), ModBlocks.JUNGLE_GRASS.getDefaultState());
        } else if (block == ModBlocks.SANGNUM || block == ModBlocks.MOSSY_DRAGON_BONE || block == ModBlocks.MOSSY_OBSIDIAN) {
            return betterEndForge_1_12_2$getRandomGrassState(world.rand, ModBlocks.GLOBULAGUS.getDefaultState(),
                    ModBlocks.CLAWFERN.getDefaultState(), ModBlocks.SMALL_AMARANITA_MUSHROOM.getDefaultState());
        } else if (block == ModBlocks.RUTISCUS) {
            return betterEndForge_1_12_2$getRandomGrassState(world.rand, ModBlocks.AERIDIUM.getDefaultState(),
                    ModBlocks.LAMELLARIUM.getDefaultState(), ModBlocks.BOLUX_MUSHROOM.getDefaultState(), ModBlocks.ORANGO.getDefaultState(), ModBlocks.LUTEBUS.getDefaultState());
        }
        return null;
    }

    @Unique
    private IBlockState betterEndForge_1_12_2$getWaterGrassState(World world, BlockPos pos) {
        BetterEndBiome biome = ModBiomes.getFromBiome(world.getBiome(pos));

        if (world.rand.nextInt(16) == 0) {
            return ModBlocks.CHARNIA_RED.getDefaultState();
        } else if (biome == ModBiomes.FOGGY_MUSHROOMLAND || biome == ModBiomes.MEGALAKE || biome == ModBiomes.MEGALAKE_GROVE) {
            return world.rand.nextBoolean() ? ModBlocks.CHARNIA_CYAN.getDefaultState() : ModBlocks.CHARNIA_LIGHT_BLUE.getDefaultState();
        } else if (biome == ModBiomes.AMBER_LAND) {
            return world.rand.nextBoolean() ? ModBlocks.CHARNIA_ORANGE.getDefaultState() : ModBlocks.CHARNIA_RED.getDefaultState();
        } else if (biome == ModBiomes.CHORUS_FOREST || biome == ModBiomes.SHADOW_FOREST) {
            return ModBlocks.CHARNIA_PURPLE.getDefaultState();
        } else if (biome == ModBiomes.SULPHUR_SPRINGS) {
            return world.rand.nextBoolean() ? ModBlocks.CHARNIA_ORANGE.getDefaultState() : ModBlocks.CHARNIA_GREEN.getDefaultState();
        } else if (biome == ModBiomes.UMBRELLA_JUNGLE) {
            return betterEndForge_1_12_2$getRandomGrassState(world.rand, ModBlocks.CHARNIA_CYAN.getDefaultState(),
                    ModBlocks.CHARNIA_GREEN.getDefaultState(), ModBlocks.CHARNIA_LIGHT_BLUE.getDefaultState());
        } else if (biome == ModBiomes.GLOWING_GRASSLANDS) {
            return betterEndForge_1_12_2$getRandomGrassState(world.rand, ModBlocks.CHARNIA_CYAN.getDefaultState(),
                    ModBlocks.CHARNIA_GREEN.getDefaultState(), ModBlocks.CHARNIA_LIGHT_BLUE.getDefaultState());
        }
        return ModBlocks.CHARNIA_RED.getDefaultState();
        //return null;
    }

    @Unique
    private IBlockState betterEndForge_1_12_2$getRandomGrassState(Random rand, IBlockState... states) {
        int index = rand.nextInt(states.length);
        return states[index];
    }

    @Unique
    private void betterEndForge_1_12_2$shuffle(Random random) {
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(4);
            EnumFacing d = betterEndForge_1_12_2$DIR[i];
            betterEndForge_1_12_2$DIR[i] = betterEndForge_1_12_2$DIR[j];
            betterEndForge_1_12_2$DIR[j] = d;
        }
    }

    @Unique
    private IBlockState betterEndForge_1_12_2$getNylium(World world, BlockPos pos) {
        betterEndForge_1_12_2$shuffle(world.rand);
        for (EnumFacing dir : betterEndForge_1_12_2$DIR) {
            IBlockState state = world.getBlockState(pos.offset(dir));
            if (BlockHelper.isEndNylium(state)) {
                return state;
            }
        }
        return null;
    }
}
