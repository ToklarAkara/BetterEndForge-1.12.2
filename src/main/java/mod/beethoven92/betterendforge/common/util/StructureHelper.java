package mod.beethoven92.betterendforge.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.collect.Sets;

import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModTags;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class StructureHelper {
    private static final EnumFacing[] DIR = BlockHelper.makeHorizontal();

    public static Template readStructure(ResourceLocation resource) {
        String ns = resource.getNamespace();
        String nm = resource.getPath();
        return readStructure("/data/" + ns + "/structures/" + nm + ".nbt");
    }

    public static Template readStructure(File datapack, String path) {
        if (datapack.isDirectory()) {
            return readStructure(datapack.toString() + "/" + path);
        } else if (datapack.isFile() && datapack.getName().endsWith(".zip")) {
            try {
                ZipFile zipFile = new ZipFile(datapack);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    String name = entry.getName();
                    long compressedSize = entry.getCompressedSize();
                    long normalSize = entry.getSize();
                    String type = entry.isDirectory() ? "DIR" : "FILE";

                    System.out.println(name);
                    System.out.format("\t %s - %d - %d\n", type, compressedSize, normalSize);
                }
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Template readStructure(String path) {
        try {
            InputStream inputstream = StructureHelper.class.getResourceAsStream(path);
            return readStructureFromStream(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Template readStructureFromStream(InputStream stream) throws IOException {
        NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(stream);

        Template template = new Template();
        template.read(nbttagcompound);

        return template;
    }

    public static BlockPos offsetPos(BlockPos pos, Template structure, Rotation rotation, Mirror mirror) {
        BlockPos offset = Template.transformedBlockPos(new PlacementSettings().setRotation(rotation).setMirror(mirror), structure.getSize());
        return pos.add(-offset.getX() * 0.5, 0, -offset.getZ() * 0.5);
    }

    public static void placeCenteredBottom(World world, BlockPos pos, Template structure, Rotation rotation, Mirror mirror, Random random) {
        placeCenteredBottom(world, pos, structure, rotation, mirror, makeBox(pos), random);
    }

    public static void placeCenteredBottom(World world, BlockPos pos, Template structure, Rotation rotation, Mirror mirror, StructureBoundingBox bounds, Random random) {
        BlockPos offset = offsetPos(pos, structure, rotation, mirror);
        PlacementSettings placementData = new PlacementSettings().setRotation(rotation).setMirror(mirror).setBoundingBox(bounds);
        structure.addBlocksToWorld(world, offset, placementData, 2);
    }

    private static StructureBoundingBox makeBox(BlockPos pos) {
        int sx = ((pos.getX() >> 4) << 4) - 16;
        int sz = ((pos.getZ() >> 4) << 4) - 16;
        int ex = sx + 47;
        int ez = sz + 47;
        return new StructureBoundingBox(sx, 0, sz, ex, 255, ez);
    }

    public static StructureBoundingBox getStructureBounds(BlockPos pos, Template structure, Rotation rotation, Mirror mirror) {
        BlockPos max = structure.getSize();
        BlockPos min = Template.transformedBlockPos(new PlacementSettings().setRotation(rotation).setMirror(mirror), structure.getSize());
        max = max.subtract(min);
        return new StructureBoundingBox(min.add(pos), max.add(pos));
    }

    public static StructureBoundingBox intersectBoxes(StructureBoundingBox box1, StructureBoundingBox box2) {
        int x1 = MathHelper.clamp(box1.minX, box2.minX, box2.maxX);
        int y1 = MathHelper.clamp(box1.minY, box2.minY, box2.maxY);
        int z1 = MathHelper.clamp(box1.minZ, box2.minZ, box2.maxZ);

        int x2 = MathHelper.clamp(box1.maxX, box2.minX, box2.maxX);
        int y2 = MathHelper.clamp(box1.maxY, box2.minY, box2.maxY);
        int z2 = MathHelper.clamp(box1.maxZ, box2.minZ, box2.maxZ);

        return new StructureBoundingBox(x1, y1, z1, x2, y2, z2);
    }

    public static void erode(World world, StructureBoundingBox bounds, int iterations, Random random)
    {
        Mutable mut = new Mutable();
        boolean canDestruct = true;
        for (int i = 0; i < iterations; i++) {
            for (int x = bounds.minX; x <= bounds.maxX; x++)
            {
                mut.setX(x);
                for (int z = bounds.minZ; z <= bounds.maxZ; z++)
                {
                    mut.setZ(z);
                    for (int y = bounds.maxY; y >= bounds.minY; y--)
                    {
                        mut.setY(y);
                        IBlockState state = world.getBlockState(mut);
                        if (canDestruct && state.getBlock()==(ModBlocks.FLAVOLITE_RUNED_ETERNAL) && random.nextInt(8) == 0 && world.isAirBlock(mut.down(2)))
                        {
                            int r = ModMathHelper.randRange(1, 4, random);
                            int cx = mut.getX();
                            int cy = mut.getY();
                            int cz = mut.getZ();
                            int x1 = cx - r;
                            int y1 = cy - r;
                            int z1 = cz - r;
                            int x2 = cx + r;
                            int y2 = cy + r;
                            int z2 = cz + r;
                            for (int px = x1; px <= x2; px++)
                            {
                                int dx = px - cx;
                                dx *= dx;
                                mut.setX(px);
                                for (int py = y1; py <= y2; py++)
                                {
                                    int dy = py - cy;
                                    dy *= dy;
                                    mut.setY(py);
                                    for (int pz = z1; pz <= z2; pz++)
                                    {
                                        int dz = pz - cz;
                                        dz *= dz;
                                        mut.setZ(pz);
                                        if (dx + dy + dz <= r && world.getBlockState(mut).getBlock()==(ModBlocks.FLAVOLITE_RUNED_ETERNAL))
                                        {
                                            BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
                                        }
                                    }
                                }
                            }
                            mut.setX(cx);
                            mut.setY(cy);
                            mut.setZ(cz);
                            canDestruct = false;
                            continue;
                        }
                        else if (ignore(state))
                        {
                            continue;
                        }
                        if (state.getBlock()!=Blocks.AIR && random.nextBoolean())
                        {
                            shuffle(random);
                            for (EnumFacing dir: DIR)
                            {
                                if (world.isAirBlock(mut.offset(dir)) && world.isAirBlock(mut.down().offset(dir)))
                                {
                                    BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
                                    mut.move(dir).move(EnumFacing.DOWN);
                                    for (int py = mut.getY(); y >= bounds.minY - 10; y--)
                                    {
                                        mut.setY(py - 1);
                                        if (!world.isAirBlock(mut)) {
                                            mut.setY(py);
                                            BlockHelper.setWithoutUpdate(world, mut, state);
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                        else if (random.nextInt(8) == 0 && world.getBlockState(mut.up()).getBlock()!=(ModBlocks.ETERNAL_PEDESTAL))
                        {
                            BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
                        }
                    }
                }
            }
        }
        for (int x = bounds.minX; x <= bounds.maxX; x++)
        {
            mut.setX(x);
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {

                mut.setZ(z);
                for (int y = bounds.maxY; y >= bounds.minY; y--)
                {
                    mut.setY(y);
                    IBlockState state = world.getBlockState(mut);
                    if (!ignore(state) && world.isAirBlock(mut.down()))
                    {
                        BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR);
                        for (int py = mut.getY(); py >= bounds.minY - 10; py--)
                        {
                            mut.setY(py - 1);
                            if (!world.isAirBlock(mut))
                            {
                                mut.setY(py);
                                BlockHelper.setWithoutUpdate(world, mut, state);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void erodeIntense(World world, StructureBoundingBox bounds, Random random) {
        MutableBlockPos mut = new MutableBlockPos();
        MutableBlockPos mut2 = new MutableBlockPos();
        int minY = bounds.minY - 10;
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            mut.setPos(x, 0, 0);
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
                mut.setPos(x, 0, z);
                for (int y = bounds.maxY; y >= bounds.minY; y--) {
                    mut.setPos(x, y, z);
                    IBlockState state = world.getBlockState(mut);
                    if (!ignore(state)) {
                        if (random.nextInt(6) == 0) {
                            BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR.getDefaultState());
                            if (random.nextBoolean()) {
                                int px = MathHelper.floor(random.nextGaussian() * 2 + x + 0.5);
                                int pz = MathHelper.floor(random.nextGaussian() * 2 + z + 0.5);
                                mut2.setPos(px, y, pz);
                                while (world.getBlockState(mut2).getMaterial().isReplaceable() && mut2.getY() > minY) {
                                    mut2.setY(mut2.getY() - 1);
                                }
                                if (world.getBlockState(mut2).getBlock()!=Blocks.AIR && state.getBlock().canPlaceBlockAt(world, mut2)) {
                                    mut2.setY(mut2.getY() + 1);
                                    BlockHelper.setWithoutUpdate(world, mut2, state);
                                }
                            }
                        } else if (random.nextInt(8) == 0 && world.getBlockState(mut.up()).getBlock() != ModBlocks.ETERNAL_PEDESTAL) {
                            BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }

        drop(world, bounds);
    }

    private static boolean isTerrainNear(World world, BlockPos pos) {
        for (EnumFacing dir : BlockHelper.HORIZONTAL_DIRECTIONS) {
            if (ModTags.GEN_TERRAIN.contains(world.getBlockState(pos.offset(dir)).getBlock())) {
                return true;
            }
        }
        return false;
    }

    private static void drop(World world, StructureBoundingBox bounds) {
        MutableBlockPos mut = new MutableBlockPos();

        Set<BlockPos> blocks = Sets.newHashSet();
        Set<BlockPos> edge = Sets.newHashSet();
        Set<BlockPos> add = Sets.newHashSet();

        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            mut.setPos(x, 0, 0);
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
                mut.setPos(x, 0, z);
                for (int y = bounds.minY; y <= bounds.maxY; y++) {
                    mut.setPos(x, y, z);
                    IBlockState state = world.getBlockState(mut);
                    if (!ignore(state) && isTerrainNear(world, mut)) {
                        edge.add(mut.toImmutable());
                    }
                }
            }
        }

        if (edge.isEmpty()) {
            return;
        }

        while (!edge.isEmpty()) {
            for (BlockPos center : edge) {
                for (EnumFacing dir : BlockHelper.HORIZONTAL_DIRECTIONS) {
                    IBlockState state = world.getBlockState(center);
                    if (state.isOpaqueCube()) {
                        mut.setPos(center).move(dir);
                        if (bounds.isVecInside(mut)) {
                            state = world.getBlockState(mut);
                            if (!ignore(state) && !blocks.contains(mut)) {
                                add.add(mut.toImmutable());
                            }
                        }
                    }
                }
            }

            blocks.addAll(edge);
            edge.clear();
            edge.addAll(add);
            add.clear();
        }

        int minY = bounds.minY - 10;
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            mut.setPos(x, 0, 0);
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
                mut.setPos(x, 0, z);
                for (int y = bounds.minY; y <= bounds.maxY; y++) {
                    mut.setPos(x, y, z);
                    IBlockState state = world.getBlockState(mut);
                    if (!ignore(state) && !blocks.contains(mut)) {
                        BlockHelper.setWithoutUpdate(world, mut, Blocks.AIR.getDefaultState());
                        while (world.getBlockState(mut).getMaterial().isReplaceable() && mut.getY() > minY) {
                            mut.setY(mut.getY() - 1);
                        }
                        if (mut.getY() > minY) {
                            mut.setY(mut.getY() + 1);
                            BlockHelper.setWithoutUpdate(world, mut, state);
                        }
                    }
                }
            }
        }
    }

    private static boolean ignore(IBlockState state) {
        return state.getMaterial().isReplaceable()
                || state.getBlock()==Blocks.WATER
                || state.getBlock()==Blocks.FLOWING_WATER
                || ModTags.END_GROUND.contains(state.getBlock())
                || state.getBlock() == ModBlocks.ETERNAL_PEDESTAL
                || state.getBlock() == ModBlocks.FLAVOLITE_RUNED_ETERNAL
                || state.getBlock() instanceof BlockLog
                || state.getBlock() instanceof BlockLeaves
                || state.getMaterial() == Material.PLANTS
                || state.getMaterial() == Material.LEAVES;
    }

    private static void shuffle(Random random) {
        for (int i = 0; i < 4; i++) {
            int j = random.nextInt(4);
            EnumFacing d = DIR[i];
            DIR[i] = DIR[j];
            DIR[j] = d;
        }
    }

    public static void cover(World world, StructureBoundingBox bounds, Random random) {
        MutableBlockPos mut = new MutableBlockPos();
        for (int x = bounds.minX; x <= bounds.maxX; x++) {
            mut.setPos(x, 0, 0);
            for (int z = bounds.minZ; z <= bounds.maxZ; z++) {
                mut.setPos(x, 0, z);
                IBlockState top = world.getBiome(mut).topBlock;
                for (int y = bounds.maxY; y >= bounds.minY; y--) {
                    mut.setPos(x, y, z);
                    IBlockState state = world.getBlockState(mut);
                    if (ModTags.END_GROUND.contains(state.getBlock()) && !world.getBlockState(mut.up()).isOpaqueCube()) {
                        BlockHelper.setWithoutUpdate(world, mut, top);
                    }
                }
            }
        }
    }
}
