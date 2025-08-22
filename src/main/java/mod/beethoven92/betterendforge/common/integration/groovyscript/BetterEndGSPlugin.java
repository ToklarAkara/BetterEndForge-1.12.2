package mod.beethoven92.betterendforge.common.integration.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import mod.beethoven92.betterendforge.BetterEnd;

import java.util.ArrayList;
import java.util.Collection;

public class BetterEndGSPlugin implements GroovyPlugin {

    public static BetterEndGSContainer get() {
        return new BetterEndGSContainer();
    }

    @Override
    public GroovyPropertyContainer createGroovyPropertyContainer() {
        return get();
    }

    @Override
    public String getModId() {
        return BetterEnd.MOD_ID;
    }

    @Override
    public String getContainerName() {
        return BetterEnd.MOD_NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> groovyContainer) {
    }

    @Override
    public Priority getOverridePriority() {
        return Priority.OVERRIDE;
    }

    @Override
    public Collection<String> getAliases() {
        Collection<String> info = new ArrayList<>();
        info.add(BetterEnd.MOD_ID);
        info.add("betterend");
        return info;
    }
}
