package mod.beethoven92.betterendforge.common.integration.tconstruct;


import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModItems;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialStats;
import slimeknights.tconstruct.library.materials.Material;

public class TinkersIntegration {
    private static Material thallasium = new Material("betterend_thallasium", 0x60959A, false);
    private static Material terminite = new Material("betterend_terminite", 0x82D499, false);
    private static Material aeternium = new Material("betterend_aeternium", 0xC59AFB, false);

    public static void setupMaterialStats(FMLPreInitializationEvent event) {
        TinkerRegistry.addMaterial(thallasium);
        TinkerRegistry.addMaterial(terminite);
        TinkerRegistry.addMaterial(aeternium);
        registerThallasium();
        registerTerminite();
        registerAeternium();
    }

    public static void setupMaterials(FMLInitializationEvent event) {
        thallasium.setCraftable(true);
        thallasium.addItem(ModBlocks.THALLASIUM.ingot, 1, Material.VALUE_Ingot);
        thallasium.setRepresentativeItem(ModBlocks.THALLASIUM.ingot);
        terminite.setCraftable(true);
        terminite.addItem(ModBlocks.TERMINITE.ingot, 1, Material.VALUE_Ingot);
        terminite.setRepresentativeItem(ModBlocks.TERMINITE.ingot);
        aeternium.setCraftable(true);
        aeternium.addItem(ModItems.AETERNIUM_INGOT, 1, Material.VALUE_Ingot);
        aeternium.setRepresentativeItem(ModItems.AETERNIUM_INGOT);
    }

    private static void registerThallasium() {
        TinkerRegistry.addMaterialStats(thallasium, new HeadMaterialStats(200, 5.0f, 5.0f, 2));
        TinkerRegistry.addMaterialStats(thallasium, new HandleMaterialStats(1.0f, 37));
        TinkerRegistry.addMaterialStats(thallasium, new ExtraMaterialStats(37));
        TinkerRegistry.addMaterialStats(thallasium, new BowMaterialStats(1.20f, 2.0f, 5.0f));

        if (Loader.isModLoaded("conarm")) {
            try{
                TinkerRegistry.addMaterialStats(thallasium, (IMaterialStats) Class.forName("c4.conarm.lib.materials.CoreMaterialStats").getConstructor(float.class, float.class).newInstance(5, 13.0f));
                TinkerRegistry.addMaterialStats(thallasium, (IMaterialStats) Class.forName("c4.conarm.lib.materials.PlatesMaterialStats").getConstructor(float.class, float.class, float.class).newInstance(0.5f, 10, 0.0f));
                TinkerRegistry.addMaterialStats(thallasium, (IMaterialStats) Class.forName("c4.conarm.lib.materials.TrimMaterialStats").getConstructor(float.class).newInstance(3));
            }catch (Exception e){}
        }
    }

    private static void registerTerminite() {
        TinkerRegistry.addMaterialStats(terminite, new HeadMaterialStats(600, 7.0f, 7.0f, 3));
        TinkerRegistry.addMaterialStats(terminite, new HandleMaterialStats(1.25f, 150));
        TinkerRegistry.addMaterialStats(terminite, new ExtraMaterialStats(100));
        TinkerRegistry.addMaterialStats(terminite, new BowMaterialStats(0.6f, 0.7f, 7.0f));

        if (Loader.isModLoaded("conarm")) {
            try{
                TinkerRegistry.addMaterialStats(terminite, (IMaterialStats) Class.forName("c4.conarm.lib.materials.CoreMaterialStats").getConstructor(float.class, float.class).newInstance(45, 18.0f));
                TinkerRegistry.addMaterialStats(terminite, (IMaterialStats) Class.forName("c4.conarm.lib.materials.PlatesMaterialStats").getConstructor(float.class, float.class, float.class).newInstance(1.0f, 13, 1.0f));
                TinkerRegistry.addMaterialStats(terminite, (IMaterialStats) Class.forName("c4.conarm.lib.materials.TrimMaterialStats").getConstructor(float.class).newInstance(10));
            }catch (Exception e){}
        }
    }

    private static void registerAeternium() {
        TinkerRegistry.addMaterialStats(aeternium, new HeadMaterialStats(1300, 9.0f, 9.0f, 5));
        TinkerRegistry.addMaterialStats(aeternium, new HandleMaterialStats(1.5f, 112));
        TinkerRegistry.addMaterialStats(aeternium, new ExtraMaterialStats(112));
        TinkerRegistry.addMaterialStats(aeternium, new BowMaterialStats(1.0f, 1.0f, 9.0f));

        if (Loader.isModLoaded("conarm")) {
            try{
                TinkerRegistry.addMaterialStats(aeternium, (IMaterialStats) Class.forName("c4.conarm.lib.materials.CoreMaterialStats").getConstructor(float.class, float.class).newInstance(90, 24.0f));
                TinkerRegistry.addMaterialStats(aeternium, (IMaterialStats) Class.forName("c4.conarm.lib.materials.PlatesMaterialStats").getConstructor(float.class, float.class, float.class).newInstance(1.5f, 15, 2.0f));
                TinkerRegistry.addMaterialStats(aeternium, (IMaterialStats) Class.forName("c4.conarm.lib.materials.TrimMaterialStats").getConstructor(float.class).newInstance(20));
            }catch (Exception e){}
        }
    }
}