package mod.beethoven92.betterendforge.common.init;

import java.util.function.Supplier;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;


public class ModCreativeTabs 
{
	public static final BetterEndCreativeTab CREATIVE_TAB = 
			new BetterEndCreativeTab(BetterEnd.MOD_ID, () -> new ItemStack(ModBlocks.ETERNAL_PEDESTAL));
	
	private static class BetterEndCreativeTab extends CreativeTabs
    {
    	private final Supplier<ItemStack> icon;
    	
		public BetterEndCreativeTab(String label, Supplier<ItemStack> iconSupplier) 
		{
			super(label);
			icon = iconSupplier;
		}

		@Override
		public ItemStack createIcon() 
		{
			return icon.get();
		}
    }
}
