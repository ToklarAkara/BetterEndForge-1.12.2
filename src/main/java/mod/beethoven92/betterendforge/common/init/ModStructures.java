package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.common.world.structure.*;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures 
{

	public static final MapGenStructure MOUNTAIN = new MountainStructure();
	public static final MapGenStructure MEGALAKE = new MegaLakeStructure();
	public static final MapGenStructure MEGALAKE_SMALL = new MegaLakeStructure();
	public static final MapGenStructure GIANT_MOSSY_GLOWSHROOM = new GiantMossyGlowshroomStructure();
	public static final MapGenStructure PAINTED_MOUNTAIN = new PaintedMountainStructure();
	public static final WorldGenerator ETERNAL_PORTAL = new EternalPortalStructure();
	public static final MapGenStructure GIANT_ICE_STAR = new GiantIceStarStructure();

	public static void registerStructures()
    {
		MapGenStructureIO.registerStructure(MountainStructure.Start.class, "mountain_structure");
		MapGenStructureIO.registerStructure(MountainStructure.Start.class, "megalake_structure");
		MapGenStructureIO.registerStructure(MountainStructure.Start.class,  "megalake_small_structure");
		MapGenStructureIO.registerStructure(MountainStructure.Start.class,  "giant_mossy_glowshroom_structure");
		MapGenStructureIO.registerStructure(MountainStructure.Start.class,  "painted_mountain_structure");
		MapGenStructureIO.registerStructure(MountainStructure.Start.class,  "eternal_portal_structure");
		MapGenStructureIO.registerStructure(MountainStructure.Start.class,  "giant_ice_star_structure");

//    	setupStructure(MOUNTAIN, new StructureSeparationSettings(3, 2, 1234567890));
//    	setupStructure(MEGALAKE, new StructureSeparationSettings(4, 1, 1237890));
//    	setupStructure(MEGALAKE_SMALL, new StructureSeparationSettings(4, 1, 1223462190));
//    	setupStructure(GIANT_MOSSY_GLOWSHROOM, new StructureSeparationSettings(16, 8, 1234560));
//    	setupStructure(PAINTED_MOUNTAIN, new StructureSeparationSettings(3, 2, 12890));
//    	setupStructure(ETERNAL_PORTAL, new StructureSeparationSettings(16, 6, 1289052454));
//    	setupStructure(GIANT_ICE_STAR, new StructureSeparationSettings(16, 8, 128954));

        ModStructurePieces.registerAllPieces();
    }
    
//    public static <F extends Structure<?>> void setupStructure(F structure,
//    		StructureSeparationSettings structureSeparationSettings)
//    {
//        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);
//
//        DimensionStructuresSettings.field_236191_b_ =
//                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
//                        .putAll(DimensionStructuresSettings.field_236191_b_)
//                        .put(structure, structureSeparationSettings)
//                        .build();
//    }

}
