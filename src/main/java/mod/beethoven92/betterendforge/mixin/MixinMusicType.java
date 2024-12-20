package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MusicTicker.MusicType.class)
public class MixinMusicType {
    @Shadow(remap=false)
    @Final
    @Mutable
    private static MusicTicker.MusicType[] $VALUES;

    private static final MusicTicker.MusicType MUSIC_FOREST = betterEnd$addVariant("MUSIC_FOREST", ModSoundEvents.MUSIC_FOREST, 6000, 24000);
    private static final MusicTicker.MusicType MUSIC_WATER = betterEnd$addVariant("MUSIC_WATER", ModSoundEvents.MUSIC_WATER, 6000, 24000);
    private static final MusicTicker.MusicType MUSIC_DARK = betterEnd$addVariant("MUSIC_DARK", ModSoundEvents.MUSIC_DARK, 6000, 24000);
    private static final MusicTicker.MusicType MUSIC_OPENSPACE = betterEnd$addVariant("MUSIC_OPENSPACE", ModSoundEvents.MUSIC_OPENSPACE, 6000, 24000);
    private static final MusicTicker.MusicType MUSIC_CAVES = betterEnd$addVariant("MUSIC_CAVES", ModSoundEvents.MUSIC_CAVES, 6000, 24000);
    private static final MusicTicker.MusicType MUSIC_UMBRA_VALLEY = betterEnd$addVariant("MUSIC_UMBRA_VALLEY", ModSoundEvents.AMBIENT_UMBRA_VALLEY, 6000, 24000);
    @Invoker("<init>")
    private static MusicTicker.MusicType betterEnd$invokeInit(String internalName, int internalId, SoundEvent musicLocationIn, int minDelayIn, int maxDelayIn) {
        throw new AssertionError();
    }

    private static MusicTicker.MusicType betterEnd$addVariant(String internalName, SoundEvent musicLocationIn, int minDelayIn, int maxDelayIn) {
        MusicTicker.MusicType instrument = betterEnd$invokeInit(internalName, $VALUES[$VALUES.length - 1].ordinal() + 1, musicLocationIn, minDelayIn, maxDelayIn);
        $VALUES = addToArray($VALUES, instrument);
        return instrument;
    }

    private static MusicTicker.MusicType[] addToArray(MusicTicker.MusicType[] array, MusicTicker.MusicType type){
        MusicTicker.MusicType[] newArray = new MusicTicker.MusicType[array.length+1];
        newArray[array.length] = type;
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }
}
