package mod.beethoven92.betterendforge.common.init;

import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class ModAttributes {
	public static final IAttribute BLINDNESS_RESISTANCE = new RangedAttribute(null, "attribute.name.generic.blindness_resistance", 0, 0, 1).setShouldWatch(true);

}
