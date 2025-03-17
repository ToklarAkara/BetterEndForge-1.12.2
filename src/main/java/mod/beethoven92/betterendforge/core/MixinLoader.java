package mod.beethoven92.betterendforge.core;

import com.google.common.collect.ImmutableMap;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class MixinLoader implements ILateMixinLoader {
    /**
     * This is a list of all the mixin configs that should be loaded.
     * The key is the name of the mixin config file, and the value is a boolean supplier that returns whether the mixin config should be loaded.
     * This list contains all mixins configs that don't target any minecraft or forge classes.
     */
    private static final Map<String, BooleanSupplier> mixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>(){});

    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<>(mixinConfigs.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(String config) {
        return mixinConfigs.get(config).getAsBoolean();
    }
}