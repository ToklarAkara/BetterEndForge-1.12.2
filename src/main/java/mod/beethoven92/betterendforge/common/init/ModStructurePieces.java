package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.world.structure.piece.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;

public class ModStructurePieces
{

    public static void registerAllPieces() 
    {
        registerStructurePiece(CavePiece.class, new ResourceLocation(BetterEnd.MOD_ID, "cave_piece"));
    	registerStructurePiece(VoxelPiece.class, new ResourceLocation(BetterEnd.MOD_ID, "voxel_piece"));
        registerStructurePiece(MountainPiece.class, new ResourceLocation(BetterEnd.MOD_ID, "mountain_piece"));
        registerStructurePiece(LakePiece.class, new ResourceLocation(BetterEnd.MOD_ID, "lake_piece"));
        registerStructurePiece(PaintedMountainPiece.class, new ResourceLocation(BetterEnd.MOD_ID, "painted_mountain_piece"));
        registerStructurePiece(NBTPiece.class, new ResourceLocation(BetterEnd.MOD_ID, "nbt_piece"));
    }

    static void registerStructurePiece(Class<? extends StructureComponent> cl, ResourceLocation rl)
    {
        MapGenStructureIO.registerStructureComponent(cl, rl.toString());
    }
}
