package mod.beethoven92.betterendforge.common.init;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.potion.EndVeilEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ModEffects 
{

	public static final Potion END_VEIL = new EndVeilEffect();

	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> evt)
	{
		evt.getRegistry().register(END_VEIL.setRegistryName(new ResourceLocation(BetterEnd.MOD_ID, "end_veil")));
	}
}
