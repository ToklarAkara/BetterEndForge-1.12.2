package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.entity.CubozoaEntity;
import mod.beethoven92.betterendforge.common.entity.DragonflyEntity;
import mod.beethoven92.betterendforge.common.entity.EndFishEntity;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.entity.ShadowWalkerEntity;
import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ModEntityTypes
{
	public static void registryEntities(){
		EntityRegistry.registerModEntity(new ResourceLocation(BetterEnd.MOD_ID, "end_fish"), EndFishEntity.class, "end_fish", 0, BetterEnd.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(BetterEnd.MOD_ID, "dragonfly"), DragonflyEntity.class, "dragonfly", 1, BetterEnd.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(BetterEnd.MOD_ID, "shadow_walker"), ShadowWalkerEntity.class, "shadow_walker", 2, BetterEnd.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(BetterEnd.MOD_ID, "end_slime"), EndSlimeEntity.class, "end_slime", 3, BetterEnd.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(BetterEnd.MOD_ID, "cubozoa"), CubozoaEntity.class,"cubozoa", 4, BetterEnd.instance, 64, 3, true);
		EntityRegistry.registerModEntity(new ResourceLocation(BetterEnd.MOD_ID, "silk_moth"), SilkMothEntity.class, "silk_moth", 5, BetterEnd.instance, 64, 3, true);
	}

//	@SubscribeEvent
//	public static void registerGlobalEntityAttributes(EntityAttributeCreationEvent event) {
//		event.put(ModEntityTypes.END_FISH.get(), EndFishEntity.registerAttributes().create());
//		event.put(ModEntityTypes.DRAGONFLY.get(), DragonflyEntity.registerAttributes().create());
//		event.put(ModEntityTypes.SHADOW_WALKER.get(), ShadowWalkerEntity.registerAttributes().create());
//		event.put(ModEntityTypes.END_SLIME.get(), EndSlimeEntity.registerAttributes().create());
//		event.put(ModEntityTypes.CUBOZOA.get(), CubozoaEntity.registerAttributes().create());
//		event.put(ModEntityTypes.SILK_MOTH.get(), SilkMothEntity.registerAttributes().create());
//	}
	
	public static void registerEntitySpawns()
	{
		EntitySpawnPlacementRegistry.setPlacementType(EndFishEntity.class, EntityLiving.SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(DragonflyEntity.class, EntityLiving.SpawnPlacementType.IN_AIR);
		EntitySpawnPlacementRegistry.setPlacementType(ShadowWalkerEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
		EntitySpawnPlacementRegistry.setPlacementType(EndSlimeEntity.class, EntityLiving.SpawnPlacementType.ON_GROUND);
		EntitySpawnPlacementRegistry.setPlacementType(CubozoaEntity.class, EntityLiving.SpawnPlacementType.IN_WATER);
		EntitySpawnPlacementRegistry.setPlacementType(SilkMothEntity.class, EntityLiving.SpawnPlacementType.IN_AIR);
	}
}
