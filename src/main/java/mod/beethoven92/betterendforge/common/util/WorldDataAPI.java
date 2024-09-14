package mod.beethoven92.betterendforge.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WorldDataAPI {
    private static final Map<String, NBTTagCompound> TAGS = Maps.newHashMap();
    private static final List<String> MODS = Lists.newArrayList();
    private static File dataDir;


    public static NBTTagCompound getRootTag(String modID) {
        NBTTagCompound root = TAGS.get(modID);
        if (root == null) {
            root = new NBTTagCompound();
            TAGS.put(modID, root);
        }
        return root;
    }
    public static NBTTagCompound getCompoundTag(String modID, String path) {
        String[] parts = path.split("\\.");
        NBTTagCompound tag = getRootTag(modID);
        for (String part : parts) {
            if (tag.hasKey(part)) {
                tag = tag.getCompoundTag(part);
            }
            else {
                NBTTagCompound t = new NBTTagCompound();
                tag.setTag(part, t);
                tag = t;
            }
        }
        return tag;
    }
}
