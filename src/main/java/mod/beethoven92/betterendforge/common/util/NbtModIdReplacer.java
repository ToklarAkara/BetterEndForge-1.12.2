package mod.beethoven92.betterendforge.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

public class NbtModIdReplacer 
{
	// Helper method to replace mod ids of blocks inside nbt structures with my mod id
	// Saves the updated file in the specified location
	public static void readAndReplace(NBTTagCompound compound, String replacePath)
	{
		NBTTagList palette = compound.getTagList("palette", 10);

	    for(int i = 0; i < palette.tagCount(); ++i)
	    {
	    	if (palette.getCompoundTagAt(i).hasKey("Name", 8))
	    	{
	    		ResourceLocation registryName = new ResourceLocation(palette.getCompoundTagAt(i).getString("Name"));
	    		String namespace = registryName.getNamespace();
	    		String path = registryName.getPath();
	    		if (namespace.equals("betterend"))
	    		{
	    			ResourceLocation newRegistryName = new ResourceLocation("betterendforge", path);
	    			palette.getCompoundTagAt(i).setString("Name", newRegistryName.toString());
	    		}
	    	}
	    }
	    compound.setTag("palette", palette);

	    // Insert your file path here
	    String filePath = "";
	    
        String fileToSave = filePath + replacePath + ".nbt";  
       
        try (OutputStream outputstream = new FileOutputStream(fileToSave)) 
        {
            CompressedStreamTools.writeCompressed(compound, outputstream);
            BetterEnd.LOGGER.debug("STRUCTURE: " + replacePath + " WROTE TO FILE");
        } 
        catch (IOException exception)
        {
        	exception.printStackTrace();
        }
	}
}
