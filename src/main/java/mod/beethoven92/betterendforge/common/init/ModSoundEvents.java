package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ModSoundEvents 
{
	public static ArrayList<SoundEvent> registry = new ArrayList<>();

	// MUSIC TRACKS
	public static final SoundEvent MUSIC_FOREST = 
			registerSoundEvent("betterendforge.music.forest");
	public static final SoundEvent MUSIC_WATER = 
			registerSoundEvent("betterendforge.music.water");
	public static final SoundEvent MUSIC_DARK = 
			registerSoundEvent("betterendforge.music.dark");
	public static final SoundEvent MUSIC_OPENSPACE = 
			registerSoundEvent("betterendforge.music.openspace");
	public static final SoundEvent MUSIC_CAVES =
			registerSoundEvent("betterendforge.music.caves");

	
	// AMBIENT SOUNDS
	public static final SoundEvent AMBIENT_AMBER_LAND =
			registerSoundEvent("betterendforge.ambient.amber_land");
	public static final SoundEvent AMBIENT_FOGGY_MUSHROOMLAND = 
			registerSoundEvent("betterendforge.ambient.foggy_mushroomland");
	public static final SoundEvent AMBIENT_CHORUS_FOREST = 
			registerSoundEvent("betterendforge.ambient.chorus_forest");
	public static final SoundEvent AMBIENT_MEGALAKE = 
			registerSoundEvent("betterendforge.ambient.megalake");
	public static final SoundEvent AMBIENT_DUST_WASTELANDS = 
			registerSoundEvent("betterendforge.ambient.dust_wastelands");
	public static final SoundEvent AMBIENT_MEGALAKE_GROVE = 
			registerSoundEvent("betterendforge.ambient.megalake_grove");
	public static final SoundEvent AMBIENT_BLOSSOMING_SPIRES = 
			registerSoundEvent("betterendforge.ambient.blossoming_spires");
	public static final SoundEvent AMBIENT_SULPHUR_SPRINGS = 
			registerSoundEvent("betterendforge.ambient.sulphur_springs");
	public static final SoundEvent AMBIENT_UMBRELLA_JUNGLE = 
			registerSoundEvent("betterendforge.ambient.umbrella_jungle");
	public static final SoundEvent AMBIENT_GLOWING_GRASSLANDS = 
			registerSoundEvent("betterendforge.ambient.glowing_grasslands");
	public static final SoundEvent AMBIENT_UMBRA_VALLEY =
			registerSoundEvent("betterendforge.ambient.umbra_valley");
	public static final SoundEvent AMBIENT_CAVES =
			registerSoundEvent("betterendforge.ambient.caves");
	
	// ENTITY SOUNDS
	public static final SoundEvent ENTITY_DRAGONFLY = 
			registerSoundEvent("betterendforge.entity.dragonfly");
	public static final SoundEvent ENTITY_SHADOW_WALKER = 
			registerSoundEvent("betterendforge.entity.shadow_walker");
	public static final SoundEvent ENTITY_SHADOW_WALKER_DAMAGE = 
			registerSoundEvent("betterendforge.entity.shadow_walker_damage");
	public static final SoundEvent ENTITY_SHADOW_WALKER_DEATH = 
			registerSoundEvent("betterendforge.entity.shadow_walker_death");
	
	// RECORDS
	public static final SoundEvent RECORD_STRANGE_AND_ALIEN = 
			registerSoundEvent("betterendforge.record.strange_and_alien");
	public static final SoundEvent RECORD_GRASPING_AT_STARS = 
			registerSoundEvent("betterendforge.record.grasping_at_stars");
	public static final SoundEvent RECORD_ENDSEEKER = 
			registerSoundEvent("betterendforge.record.endseeker");
	public static final SoundEvent RECORD_EO_DRACONA = 
			registerSoundEvent("betterendforge.record.eo_dracona");

	private static SoundEvent registerSoundEvent(String name)
	{
		SoundEvent event = new SoundEvent(new ResourceLocation(BetterEnd.MOD_ID, name)).setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, name));
		registry.add(event);
		//ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt)
	{
		evt.getRegistry().registerAll(registry.toArray(new SoundEvent[0]));
	}
}
