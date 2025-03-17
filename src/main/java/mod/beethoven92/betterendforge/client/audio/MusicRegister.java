package mod.beethoven92.betterendforge.client.audio;

import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraftforge.client.EnumHelperClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MusicRegister {
    private MusicRegister () {
    }

    public static final MusicRegister INSTANCE = new MusicRegister();

    public void registerMusic() {
        EnumHelperClient.addMusicType("MUSIC_FOREST", ModSoundEvents.MUSIC_FOREST, 6000, 24000);
        EnumHelperClient.addMusicType("MUSIC_WATER", ModSoundEvents.MUSIC_WATER, 6000, 24000);
        EnumHelperClient.addMusicType("MUSIC_DARK", ModSoundEvents.MUSIC_DARK, 6000, 24000);
        EnumHelperClient.addMusicType("MUSIC_OPENSPACE", ModSoundEvents.MUSIC_OPENSPACE, 6000, 24000);
        EnumHelperClient.addMusicType("MUSIC_CAVES", ModSoundEvents.MUSIC_CAVES, 6000, 24000);
        EnumHelperClient.addMusicType("MUSIC_UMBRA_VALLEY", ModSoundEvents.AMBIENT_UMBRA_VALLEY, 6000, 24000);
    }
}
