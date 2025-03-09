package mod.beethoven92.betterendforge.core;

import com.google.common.collect.ImmutableMap;
import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraftforge.fml.common.Loader;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class MixinLoader implements ILateMixinLoader {
    private static final Map<String, BooleanSupplier> mixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>(){
        {
            put("mixins.betterendforge.json", () -> Loader.isModLoaded(BetterEnd.MOD_ID));
        }
    });

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(mixinConfigs.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(String config) {
        return mixinConfigs.get(config).getAsBoolean();
    }
}