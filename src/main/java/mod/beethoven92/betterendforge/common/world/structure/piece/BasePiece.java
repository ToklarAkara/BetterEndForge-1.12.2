package mod.beethoven92.betterendforge.common.world.structure.piece;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class BasePiece extends StructureComponent {
	protected BasePiece() {
		super(0);
	}

	protected BasePiece(int i) {
		super(i);
	}
}
