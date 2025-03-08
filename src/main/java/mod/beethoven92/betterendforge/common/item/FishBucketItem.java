package mod.beethoven92.betterendforge.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FishBucketItem<T extends Entity> extends ItemBucket {
    private Class<T> fishClass;

    public FishBucketItem(Class<T> fishClass) {
        super(Blocks.FLOWING_WATER);
        this.fishClass = fishClass;
    }

    @Override
    public boolean tryPlaceContainedLiquid(@Nullable EntityPlayer player, World worldIn, BlockPos posIn) {
        if (!worldIn.isRemote) {
            try {
                T fishEntity = fishClass.getConstructor(World.class).newInstance(worldIn);
                fishEntity.setPosition(posIn.getX()+0.5f, posIn.getY(), posIn.getZ()+0.5f);
                worldIn.spawnEntity(fishEntity);
            } catch (Exception ignored) {

            }
        }
        return super.tryPlaceContainedLiquid(player, worldIn, posIn);
    }
}