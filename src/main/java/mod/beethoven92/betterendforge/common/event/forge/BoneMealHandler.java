package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import mod.beethoven92.betterendforge.common.world.biome.BetterEndBiome;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class BoneMealHandler {
    @Unique
    private static final EnumFacing[] DIR = BlockHelper.makeHorizontal();
    @Unique
    private static final Mutable POS = new Mutable();
    
    @SubscribeEvent
    public static void onBonemealApplied(BonemealEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        EnumHand hand = event.getHand();
        ItemStack itemstack = event.getStack();

        if (!world.isRemote) {
            BlockPos offseted = blockPos.offset(EnumFacing.UP);
            boolean endBiome = world.provider.getDimension() == 1; //world.getBiome(offseted).getCategory() == Category.THEEND;

            if (ModTags.END_GROUND.contains(world.getBlockState(blockPos).getBlock())) {
                boolean consume = false;
                if (world.getBlockState(blockPos).getBlock() == (Blocks.END_STONE)) {
                    IBlockState nylium = getNylium(world, blockPos);
                    if (nylium != null) {
                        BlockHelper.setWithoutUpdate(world, blockPos, nylium);
                        consume = true;
                    }
                    // Cannot grow underwater plants on end stone
                    if (world.getBlockState(offseted).getBlock() == Blocks.WATER && endBiome) {
                        event.setResult(Event.Result.DENY);
                    }
                } else {
                    if (world.getBlockState(offseted).getBlock() == Blocks.WATER && endBiome) {
                        // FIX being able to use bone meal underwater on a block where plants already grew
                        if (world.getBlockState(offseted).getBlock() == (Blocks.WATER)) {
                            consume = growWaterGrass(world, blockPos);
                        }
                    } else if (world.getBlockState(offseted).getBlock() == Blocks.AIR) // FIX being able to use bone meal on a block where plants already grew
                    {
                        consume = growGrass(world, blockPos);
                    }
                }
                if (consume) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    world.playEvent(2005, blockPos, 0);
                    event.setResult(Event.Result.ALLOW);
                }
            }
            // Prevents bonemeal generating sea grass underwater in end biomes
            else if (world.getBlockState(offseted).getBlock() == Blocks.WATER && endBiome) {
                event.setResult(Event.Result.DENY);
            }
        }
        
    }


    private static boolean growGrass(World world, BlockPos pos) {
        int y1 = pos.getY() + 3;
        int y2 = pos.getY() - 3;
        boolean result = false;
        for (int i = 0; i < 64; i++) {
            int x = (int) (pos.getX() + world.rand.nextGaussian() * 2);
            int z = (int) (pos.getZ() + world.rand.nextGaussian() * 2);
            POS.setX(x);
            POS.setZ(z);
            for (int y = y1; y >= y2; y--) {
                POS.setY(y);
                BlockPos down = POS.down();
                if (world.isAirBlock(POS) && !world.isAirBlock(down)) {
                    IBlockState grass = getGrassState(world, down);
                    if (grass != null) {
                        BlockHelper.setWithoutUpdate(world, POS, grass);
                        result = true;
                    }
                    break;
                }
            }
        }
        return result;
    }

    @Unique
    private static boolean growWaterGrass(World world, BlockPos pos) {
        int y1 = pos.getY() + 3;
        int y2 = pos.getY() - 3;
        boolean result = false;
        for (int i = 0; i < 64; i++) {
            int x = (int) (pos.getX() + world.rand.nextGaussian() * 2);
            int z = (int) (pos.getZ() + world.rand.nextGaussian() * 2);
            POS.setX(x);
            POS.setZ(z);
            for (int y = y1; y >= y2; y--) {
                POS.setY(y);
                BlockPos down = POS.down();
                if (world.getBlockState(POS).getBlock() == (Blocks.WATER) && ModTags.END_GROUND.contains(world.getBlockState(down).getBlock())) {
                    IBlockState grass = getWaterGrassState(world, down);
                    if (grass != null) {
                        BlockHelper.setWithoutUpdate(world, POS, grass);
                        result = true;
                    }
                    break;
                }
            }
        }
        return result;
    }

    @Unique
    private static Block[] glowingGrasslandsGrass() {
        return new Block[]{ModBlocks.BLOOMING_COOKSONIA, ModBlocks.VAIOLUSH_FERN,
                ModBlocks.FRACTURN, ModBlocks.SALTEAGO, ModBlocks.CREEPING_MOSS,
                ModBlocks.UMBRELLA_MOSS, ModBlocks.TWISTED_UMBRELLA_MOSS};
    }

    @Unique
    private static IBlockState getGrassState(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if (block == ModBlocks.END_MOSS || block == ModBlocks.END_MYCELIUM) {
            if (world.getBiome(pos).getRegistryName().equals(ModBiomes.GLOWING_GRASSLANDS.getID())) {
                Block[] grasses = glowingGrasslandsGrass();
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
            return getRandomGrassState(world.rand, ModBlocks.TWISTED_UMBRELLA_MOSS.getDefaultState(),
                    ModBlocks.SMALL_JELLYSHROOM.getDefaultState(), ModBlocks.JUNGLE_GRASS.getDefaultState());
        } else if (block == ModBlocks.SANGNUM || block == ModBlocks.MOSSY_DRAGON_BONE || block == ModBlocks.MOSSY_OBSIDIAN) {
            return getRandomGrassState(world.rand, ModBlocks.GLOBULAGUS.getDefaultState(),
                    ModBlocks.CLAWFERN.getDefaultState(), ModBlocks.SMALL_AMARANITA_MUSHROOM.getDefaultState());
        } else if (block == ModBlocks.RUTISCUS) {
            return getRandomGrassState(world.rand, ModBlocks.AERIDIUM.getDefaultState(),
                    ModBlocks.LAMELLARIUM.getDefaultState(), ModBlocks.BOLUX_MUSHROOM.getDefaultState(), ModBlocks.ORANGO.getDefaultState(), ModBlocks.LUTEBUS.getDefaultState());
        }
        return null;
    }

    @Unique
    private static IBlockState getWaterGrassState(World world, BlockPos pos) {
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
            return getRandomGrassState(world.rand, ModBlocks.CHARNIA_CYAN.getDefaultState(),
                    ModBlocks.CHARNIA_GREEN.getDefaultState(), ModBlocks.CHARNIA_LIGHT_BLUE.getDefaultState());
        } else if (biome == ModBiomes.GLOWING_GRASSLANDS) {
            return getRandomGrassState(world.rand, ModBlocks.CHARNIA_CYAN.getDefaultState(),
                    ModBlocks.CHARNIA_GREEN.getDefaultState(), ModBlocks.CHARNIA_LIGHT_BLUE.getDefaultState());
        }
        return ModBlocks.CHARNIA_RED.getDefaultState();
        //return null;
    }

    @Unique
    private static IBlockState getRandomGrassState(Random rand, IBlockState... states) {
        int index = rand.nextInt(states.length);
        return states[index];
    }

    @Unique
    private static void shuffle(Random random) {
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(4);
            EnumFacing d = DIR[i];
            DIR[i] = DIR[j];
            DIR[j] = d;
        }
    }

    @Unique
    private static IBlockState getNylium(World world, BlockPos pos) {
        shuffle(world.rand);
        for (EnumFacing dir : DIR) {
            IBlockState state = world.getBlockState(pos.offset(dir));
            if (BlockHelper.isEndNylium(state)) {
                return state;
            }
        }
        return null;
    }
}
