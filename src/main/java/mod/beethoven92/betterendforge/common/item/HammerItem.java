package mod.beethoven92.betterendforge.common.item;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HammerItem extends ItemTool {
	public final static UUID ATTACK_KNOCKBACK_MODIFIER = MathHelper.getRandomUUID(ThreadLocalRandom.current());

	private final Multimap<String, AttributeModifier> attributeModifiers;

	public HammerItem(ToolMaterial material, float attackDamage, float attackSpeed, double knockback) {
		super(attackDamage, attackSpeed, material, Sets.newHashSet());

		ImmutableMultimap.Builder<String, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage + material.getAttackDamage(), 0));
		builder.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", attackSpeed, 0));
		builder.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(ATTACK_KNOCKBACK_MODIFIER, "Weapon modifier", knockback, 0));
		this.attributeModifiers = builder.build();
	}

	public int getHarvestLevel() {
		return toolMaterial.getHarvestLevel();
	}

	@Override
	public boolean canHarvestBlock(IBlockState state) {
		Block block = state.getBlock();
		return block == Blocks.DIAMOND_BLOCK || block == Blocks.EMERALD_BLOCK || block == Blocks.LAPIS_BLOCK || block == Blocks.REDSTONE_BLOCK || state.getMaterial() == net.minecraft.block.material.Material.ROCK || state.getMaterial() == net.minecraft.block.material.Material.GLASS;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (state.getBlockHardness(worldIn, pos) != 0.0F) {
			stack.damageItem(1, entityLiving);
		}
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		return true;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		if (state.getMaterial() == net.minecraft.block.material.Material.GLASS) {
			return this.toolMaterial.getEfficiency() * 2.0F;
		}
		if (this.canHarvestBlock(state)) {
			float mult = 1.0F;
			if (state.getBlock() == Blocks.DIAMOND_BLOCK || state.getBlock() == Blocks.EMERALD_BLOCK || state.getBlock() == Blocks.LAPIS_BLOCK || state.getBlock() == Blocks.REDSTONE_BLOCK) {
				mult = this.toolMaterial.getEfficiency();
			} else {
				mult = this.toolMaterial.getEfficiency() / 2.0F;
			}
			return mult > 1.0F ? mult : 1.0F;
		}
		return 1.0F;
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		return equipmentSlot == EntityEquipmentSlot.MAINHAND ? this.attributeModifiers : super.getItemAttributeModifiers(equipmentSlot);
	}
}
