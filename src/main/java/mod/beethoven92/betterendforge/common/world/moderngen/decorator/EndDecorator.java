package mod.beethoven92.betterendforge.common.world.moderngen.decorator;

import mod.beethoven92.betterendforge.common.capability.EndData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.event.entity.EntityEvent;

import java.util.List;
import java.util.Random;

public class EndDecorator extends BiomeDecorator {
    public List<List<ConfiguredFeature<?, ?>>> list;

    public static boolean decorating = false;

    public EndDecorator(List<List<ConfiguredFeature<?, ?>>> list){
        this.list = list;
    }

    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos){
        if(decorating){
            worldIn.getCapability(EndData.CAPABILITY, null).storePass(pos);
            return;
        }
        decorating = true;
        int len = Decoration.values().length;
        for(int i = 0; i < len; ++i) {
            for(ConfiguredFeature<?, ?> feature : list.get(i)){
                try {
                    feature.place(worldIn, random, pos);
                }catch (Exception e){ }
            }
        }
        decorating = false;
    }
}
