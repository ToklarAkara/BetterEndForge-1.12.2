package mod.beethoven92.betterendforge.common.event.forge;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.capability.EndData;
import mod.beethoven92.betterendforge.common.init.ModAttributes;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(modid = BetterEnd.MOD_ID)
public class ForgeEvents {
	@SubscribeEvent
	public static void giveGuideBookToPlayer(AdvancementEvent event) {
		ResourceLocation id = event.getAdvancement().getId();
	}

	@SubscribeEvent
	public static void loginEvent(PlayerEvent.PlayerLoggedInEvent event) {
		EndData.playerLogin((EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public static void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
		EndData.playerRespawn((EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public static void removeBlindness(PotionApplicableEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (event.getPotionEffect().getPotion() == Potion.REGISTRY.getObject( new ResourceLocation("blindness"))
				&& entity.getAttributeMap().getAttributeInstance(ModAttributes.BLINDNESS_RESISTANCE) != null
				&& entity.getAttributeMap().getAttributeInstance(ModAttributes.BLINDNESS_RESISTANCE).getAttributeValue() > 0)
			event.setResult(Event.Result.DENY);
	}

	@SubscribeEvent
	public static void onDragonBreakBlocks(LivingDestroyBlockEvent event) {
		if(!(event.getEntityLiving() instanceof EntityDragon)) return;
		Block[] forbidden = new Block[]{ModBlocks.FLAVOLITE.wall, ModBlocks.FLAVOLITE.stairs, ModBlocks.FLAVOLITE.slab, ModBlocks.THALLASIUM.bars};
		for(Block block: forbidden) {
			if (event.getState().getBlock() == block) {
				event.setCanceled(true);
				return;
			}
		}
	}

}
