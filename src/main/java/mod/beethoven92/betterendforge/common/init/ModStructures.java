package mod.beethoven92.betterendforge.common.init;

import git.jbredwards.nether_api.api.registry.INetherAPIRegistry;
import git.jbredwards.nether_api.mod.common.registry.NetherAPIRegistry;
import mod.beethoven92.betterendforge.common.world.structure.*;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures 
{

//	public static final MapGenStructure MOUNTAIN = new MountainStructure(3, 2, 1234567890);
//	public static final MapGenStructure MEGALAKE = new MegaLakeStructure(4, 1, 1237890);
//	public static final MapGenStructure MEGALAKE_SMALL = new MegaLakeStructure(4, 1, 1223462190);
//	public static final MapGenStructure GIANT_MOSSY_GLOWSHROOM = new GiantMossyGlowshroomStructure(16, 8, 1234560);
//	public static final MapGenStructure PAINTED_MOUNTAIN = new PaintedMountainStructure(3, 2, 12890);
//	//public static final WorldGenerator ETERNAL_PORTAL = new EternalPortalStructure(16, 6, 1289052454);
//	public static final MapGenStructure GIANT_ICE_STAR = new GiantIceStarStructure(16, 8, 128954);

	public static void registerStructures(INetherAPIRegistry registry)
    {
		registry.registerStructure("mountain_structure", (provider)->new MountainStructure(provider, 3, 2, 1234567890));
//		registry.registerStructure("megalake_structure", (provider)->new MegaLakeStructure(provider, 4, 1, 1237890));
//		registry.registerStructure("megalake_small_structure", (provider)->new MegaLakeStructure(provider, 4, 1, 1223462190));
//		registry.registerStructure("giant_mossy_glowshroom_structure", (provider)->new GiantMossyGlowshroomStructure(provider, 16, 8, 1234560));
//		registry.registerStructure("painted_mountain_structure", (provider)->new PaintedMountainStructure(provider, 3, 2, 12890));
//		//NetherAPIRegistry.THE_END.registerStructure("eternal_portal_structure", (provider)->new EternalPortalStructure(16, 6, 1289052454));
//		registry.registerStructure("giant_ice_star_structure", (provider)->new GiantIceStarStructure(provider, 16, 8, 128954));

		MapGenStructureIO.registerStructure(MountainStructure.Start.class, "mountain_structure");
		MapGenStructureIO.registerStructure(MegaLakeStructure.SDFStructureStart.class, "megalake_structure");
		MapGenStructureIO.registerStructure(MegaLakeSmallStructure.SDFStructureStart.class,  "megalake_small_structure");
		MapGenStructureIO.registerStructure(GiantMossyGlowshroomStructure.SDFStructureStart.class,  "giant_mossy_glowshroom_structure");
		MapGenStructureIO.registerStructure(PaintedMountainStructure.Start.class,  "painted_mountain_structure");
		//MapGenStructureIO.registerStructure(MountainStructure.Start.class,  "eternal_portal_structure");
		MapGenStructureIO.registerStructure(GiantIceStarStructure.Start.class,  "giant_ice_star_structure");

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
