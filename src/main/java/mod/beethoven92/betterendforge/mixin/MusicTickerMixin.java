package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.client.ClientOptions;
import mod.beethoven92.betterendforge.common.util.AdvMathHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MusicTicker.class)
public class MusicTickerMixin 
{
	@Shadow
	@Final
	private Minecraft mc;
	
	@Shadow
	@Final
	private Random rand;
	
	@Shadow
	private ISound currentMusic;
	
	@Shadow
	private int timeUntilNextMusic;
	
	private static float volume = 1;
	private static float srcVolume = 0;
	private static long time;

	@Inject(method = "update", at = @At("HEAD"), cancellable = true)
	public void be_onTick(CallbackInfo info) 
	{
//		if (ClientOptions.blendBiomeMusic()) TODO SOUNDS
//		{
//			MusicTicker.MusicType musicSound = mc.getAmbientMusicType();
//			if (volume > 0 && beIsInEnd() && beShouldChangeSound(musicSound))
//			{
//				if (volume > 0)
//				{
//					if (srcVolume < 0)
//					{
//						srcVolume = currentMusic.getVolume();
//					}
//					if (currentMusic instanceof PositionedSound)
//					{
//						((SoundVolumeAccessor)currentMusic).setVolume(volume);
//					}
//					mc.getSoundHandler().setSoundLevel(currentMusic.getCategory(), currentMusic.getVolume() * volume);
//					long t = System.currentTimeMillis();
//					if (volume == 1 && time == 0)
//					{
//						time = t;
//					}
//					float delta = (t - time) * 0.0005F;
//					time = t;
//					volume -= delta;
//					if (volume < 0)
//					{
//						volume = 0;
//					}
//				}
//				if (volume == 0)
//				{
//					volume = 1;
//					time = 0;
//					srcVolume = -1;
//					mc.getSoundHandler().stopSound(this.currentMusic);
//					this.timeUntilNextMusic = AdvMathHelper.nextInt(this.rand, 0, musicSound.getMinDelay() / 2);
//					this.currentMusic = null;
//				}
//				if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0)
//				{
//					this.playMusic(musicSound);
//				}
//				info.cancel();
//			}
//			else
//			{
//				volume = 1;
//			}
//		}
	}
	
	private boolean beIsInEnd() 
	{
		return mc.world != null && mc.world.provider.getDimension()==1;
	}
	
	private boolean beShouldChangeSound(MusicTicker.MusicType musicSound)
	{
		return currentMusic != null && !musicSound.getMusicLocation().getSoundName().equals(this.currentMusic.getSoundLocation()) && shouldReplace(musicSound);
	}

	private boolean shouldReplace(MusicTicker.MusicType type){
		return type != MusicTicker.MusicType.CREATIVE && type != MusicTicker.MusicType.GAME;
	}

	@Shadow
	public void playMusic(MusicTicker.MusicType requestedMusicType) {}
}
