package mod.beethoven92.betterendforge.common.item;

import java.util.List;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.block.template.EndAnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EndAnvilItem extends ItemAnvilBlock {

	public EndAnvilItem(Block block) {
		super(block);
	}

//	@Override
//	public IBlockState getStateForPlacement(net.minecraft.util.math.BlockPos pos, net.minecraft.util.EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
//		IBlockState blockState = super.getStateForPlacement(pos, facing, hitX, hitY, hitZ, meta, placer, hand);
//		if (blockState == null) return null;
//		ItemStack stack = placer.getHeldItem(hand);
//		NBTTagCompound tag = stack.getTagCompound();
//		int level = tag != null ? tag.getInteger("level") : 0;
//		blockState = blockState.withProperty(((EndAnvilBlock) blockState.getBlock()).getDestructionProperty(), level);
//		return blockState;
//	} TODO ANVIL DAMAGE

	@Override
	public void addInformation(ItemStack itemStack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		super.addInformation(itemStack, world, tooltip, flag);
		NBTTagCompound tag = itemStack.getTagCompound();
		int level = tag != null ? tag.getInteger("level") : 0;
		if (level > 0) {
			tooltip.add(new TextComponentTranslation("message." + BetterEnd.MOD_ID + ".anvil_damage").appendText(": " + level).getFormattedText());
		}
	}
}
