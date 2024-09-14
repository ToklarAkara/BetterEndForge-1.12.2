package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GuideBookItem extends Item {
	private final static ResourceLocation BOOK_ID = new ResourceLocation(BetterEnd.MOD_ID, "guidebook");

	public GuideBookItem() {
		super();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote && playerIn instanceof EntityPlayerMP) {
//			if (Loader.isModLoaded("patchouli")) {
//				PatchouliAPI.instance.openBookGUI((EntityPlayerMP) playerIn, BOOK_ID);
//				return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
//			} else
			{
				playerIn.sendStatusMessage(new TextComponentTranslation("message.betterendforge.patchouli_missing"), true);
				return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
			}
		}
		return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.DARK_PURPLE +  new TextComponentTranslation("book.betterendforge.subtitle").toString());
	}
}
