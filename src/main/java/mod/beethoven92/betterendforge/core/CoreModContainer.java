package mod.beethoven92.betterendforge.core;

import com.google.common.eventbus.EventBus;
import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class CoreModContainer extends DummyModContainer {
    public CoreModContainer() {
        super(new ModMetadata());
        ModMetadata meta = this.getMetadata();
        meta.modId = BetterEnd.MOD_ID + "-core";
        meta.name = BetterEnd.MOD_NAME + " Core";
        meta.version = BetterEnd.VERSION;
        meta.authorList.add("Enderman");
        meta.description = "Core mod for " + BetterEnd.MOD_NAME;
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller) {
        bus.register(this);
        return true;
    }
}
