package mod.beethoven92.betterendforge.client.audio;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BackgroundMusicSelector {
    private final SoundEvent event;
    private final int minDelay;
    private final int maxDelay;
    private final boolean replaceCurrentMusic;

    public BackgroundMusicSelector(SoundEvent p_i231428_1_, int p_i231428_2_, int p_i231428_3_, boolean p_i231428_4_) {
        this.event = p_i231428_1_;
        this.minDelay = p_i231428_2_;
        this.maxDelay = p_i231428_3_;
        this.replaceCurrentMusic = p_i231428_4_;
    }

    @SideOnly(Side.CLIENT)
    public SoundEvent getEvent() {
        return this.event;
    }

    @SideOnly(Side.CLIENT)
    public int getMinDelay() {
        return this.minDelay;
    }

    @SideOnly(Side.CLIENT)
    public int getMaxDelay() {
        return this.maxDelay;
    }

    @SideOnly(Side.CLIENT)
    public boolean replaceCurrentMusic() {
        return this.replaceCurrentMusic;
    }
}