package mod.beethoven92.betterendforge.core;

import fermiumbooter.FermiumRegistryAPI;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

@IFMLLoadingPlugin.Name("BetterEndCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class CoreModLoader implements IFMLLoadingPlugin {

    public CoreModLoader() {
        MixinBootstrap.init();
        FermiumRegistryAPI.enqueueMixin(false, "mixins.betterendforge.minecraft.json");
    }

    public static final boolean isClient = FMLLaunchHandler.side().isClient();

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
