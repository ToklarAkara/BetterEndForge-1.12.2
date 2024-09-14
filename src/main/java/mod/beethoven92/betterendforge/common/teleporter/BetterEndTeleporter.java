package mod.beethoven92.betterendforge.common.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// Needed when calling ServerPlayerEntity#changeDimension, in order to
// avoid "Player moved wrongly" situations which results in teleporting player position being misplaced
public class BetterEndTeleporter implements net.minecraftforge.common.util.ITeleporter
{
	BlockPos exitPos;
	
	public BetterEndTeleporter(BlockPos exitPos)
	{
		this.exitPos = exitPos;
	}


	@Override
	public void placeEntity(World world, Entity entity, float yaw) {
		entity.moveToBlockPosAndAngles(exitPos, yaw, entity.rotationPitch);
	}
}
