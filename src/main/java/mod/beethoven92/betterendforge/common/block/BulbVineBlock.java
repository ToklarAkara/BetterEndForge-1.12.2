package mod.beethoven92.betterendforge.common.block;

import mod.beethoven92.betterendforge.common.block.template.EndVineBlock;
import net.minecraft.block.material.Material;

public class BulbVineBlock extends EndVineBlock
{
	public BulbVineBlock()
	{
		super(Material.PLANTS);
	}

	// Uncomment and adapt this method if needed
    /*@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        boolean canPlace = super.canPlaceBlockAt(worldIn, pos);
        return (worldIn.getBlockState(pos).getBlock() == this && worldIn.getBlockState(pos).getValue(SHAPE) == TripleShape.BOTTOM) ? canPlace : canPlace && worldIn.getBlockState(pos.down()).getBlock() == this;
    }*/

}
