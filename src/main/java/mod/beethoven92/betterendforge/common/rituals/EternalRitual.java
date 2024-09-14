package mod.beethoven92.betterendforge.common.rituals;

import com.google.common.collect.Sets;
import mod.beethoven92.betterendforge.common.block.BlockProperties;
import mod.beethoven92.betterendforge.common.block.EndPortalBlock;
import mod.beethoven92.betterendforge.common.block.RunedFlavoliteBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.teleporter.EndPortals;
import mod.beethoven92.betterendforge.common.tileentity.EternalPedestalTileEntity;
import mod.beethoven92.betterendforge.common.world.feature.Mutable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.awt.*;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class EternalRitual 
{
	private final static Set<Point> STRUCTURE_MAP = Sets.newHashSet(
			new Point(-4, -5), new Point(-4, 5), new Point(-6, 0),
			new Point(4, -5), new Point(4, 5), new Point(6, 0));
	private final static Set<Point> FRAME_MAP = Sets.newHashSet(
			new Point(0, 0), new Point(0, 6), new Point(1, 0),
			new Point(1, 6), new Point(2, 1), new Point(2, 5),
			new Point(3, 2), new Point(3, 3), new Point(3, 4));
	private final static Set<Point> PORTAL_MAP = Sets.newHashSet(
			new Point(0, 0), new Point(0, 1), new Point(0, 2),
			new Point(0, 3), new Point(0, 4), new Point(1, 0),
			new Point(1, 1), new Point(1, 2), new Point(1, 3),
			new Point(1, 4), new Point(2, 1), new Point(2, 2),
			new Point(2, 3));
	private final static Set<Point> BASE_MAP = Sets.newHashSet(
			new Point(3, 0), new Point(2, 0), new Point(2, 1), new Point(1, 1),
			new Point(1, 2), new Point(0, 1), new Point(0, 2));
	
	private final static Block BASE = ModBlocks.FLAVOLITE.tiles;
	private final static Block PEDESTAL = ModBlocks.ETERNAL_PEDESTAL;
	private final static Block FRAME = ModBlocks.FLAVOLITE_RUNED_ETERNAL;
	private final static Block PORTAL = ModBlocks.END_PORTAL_BLOCK;
	private final static PropertyBool ACTIVE = BlockProperties.ACTIVATED;
	
	private World world;
	private EnumFacing.Axis axis;
	private BlockPos center;
	private BlockPos exit;
	private boolean active = false;
	
	public EternalRitual(World world) 
	{
		this.world = world;
	}
	
	public EternalRitual(World world, BlockPos initial) 
	{
		this(world);
		this.configure(initial);
	}
	
	public void setWorld(World world) 
	{
		this.world = world;
	}
	
	private boolean isInvalid() 
	{
		return world == null || world.isRemote ||
				center == null || axis == null ||
				world.provider.getDimension()==-1;
	}
	
	public void checkStructure() 
	{
		if (isInvalid()) return;
		EnumFacing moveX, moveY;
		if (EnumFacing.Axis.X == axis) 
		{
			moveX = EnumFacing.EAST;
			moveY = EnumFacing.NORTH;
		} 
		else 
		{
			moveX = EnumFacing.SOUTH;
			moveY = EnumFacing.EAST;
		}
		boolean valid = this.checkFrame();
		
		Item item = null;
		
		for (Point pos : STRUCTURE_MAP) 
		{
			Mutable checkPos = new Mutable(center);
			checkPos.move(moveX, pos.x).move(moveY, pos.y);
			valid &= this.isActive(checkPos);
			
			if (valid) 
			{
				EternalPedestalTileEntity pedestal = (EternalPedestalTileEntity) world.getTileEntity(checkPos);
				Item pItem = pedestal.getStack().getItem();
				if (item == null)
				{
					item = pItem;
				}
				else if (!item.equals(pItem)) 
				{
					valid = false;
				}
			}
		}
		/*if (valid)
		{
			this.activatePortal();
		}*/
		if (valid && item != null) 
		{
			this.activatePortal(item);
		}
	}
	
	private boolean checkFrame() 
	{
		BlockPos framePos = center.down();
		EnumFacing moveDir = EnumFacing.Axis.X == axis ? EnumFacing.NORTH: EnumFacing.EAST;
		boolean valid = true;
		for (Point point : FRAME_MAP)
		{
			BlockPos pos = new Mutable(framePos).move(moveDir, point.x).move(EnumFacing.UP, point.y);
			IBlockState state = world.getBlockState(pos);
			valid &= state.getBlock() instanceof RunedFlavoliteBlock;
			pos = new Mutable(framePos).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
			state = world.getBlockState(pos);
			valid &= state.getBlock() instanceof RunedFlavoliteBlock;
		}
		return valid;
	}
	
	public boolean isActive() 
	{
		return this.active;
	}
	
	private void activatePortal(Item item) 
	{
		if (active) return;
		//this.activatePortal(world, center);
		int state = EndPortals.getPortalState(ForgeRegistries.ITEMS.getKey(item));
		this.activatePortal(world, center, state);
		
		this.doEffects((WorldServer) world, center);
		if (exit == null) 
		{
			//this.exit = this.findPortalPos();
			this.exit = this.findPortalPos(state);
		} 
		else 
		{
			//World targetWorld = this.getTargetWorld();
			World targetWorld = this.getTargetWorld(state);
			if (targetWorld.getBlockState(exit.up()).getBlock()==(ModBlocks.END_PORTAL_BLOCK)) 
			{
				//this.exit = this.findPortalPos();
				this.exit = this.findPortalPos(state);
			}
			else 
			{
				//this.activatePortal(targetWorld, exit);
				this.activatePortal(targetWorld, exit, state);
			}
		}
		this.active = true;
	}

	private void doEffects(WorldServer serverWorld, BlockPos center) 
	{
		EnumFacing moveX, moveY;
		if (EnumFacing.Axis.X == axis) 
		{
			moveX = EnumFacing.EAST;
			moveY = EnumFacing.NORTH;
		} 
		else 
		{
			moveX = EnumFacing.SOUTH;
			moveY = EnumFacing.EAST;
		}
		for (Point pos : STRUCTURE_MAP) 
		{
			Mutable p = new Mutable(center);
			p.move(moveX, pos.x).move(moveY, pos.y);
			//serverWorld.spawnParticle(EnumParticleTypes.PORTAL, p.getX() + 0.5, p.getY() + 1.5, p.getZ() + 0.5, 20, 0, 0, 0, 1);
			//serverWorld.spawnParticle(EnumParticleTypes.REVERSE_PORTAL, p.getX() + 0.5, p.getY() + 1.5, p.getZ() + 0.5, 20, 0, 0, 0, 0.3);
		}
		serverWorld.playSound(null, center, SoundEvents.BLOCK_END_PORTAL_SPAWN, SoundCategory.NEUTRAL, 16, 1);
	}
	
	private void activatePortal(World world, BlockPos center, int dim) 
	{
		BlockPos framePos = center.down();
		EnumFacing moveDir = EnumFacing.Axis.X == axis ? EnumFacing.NORTH: EnumFacing.EAST;
		IBlockState frame = FRAME.getDefaultState().withProperty(ACTIVE, true);
		FRAME_MAP.forEach(point -> {
			BlockPos pos = new Mutable(framePos).move(moveDir, point.x).move(EnumFacing.UP, point.y);
			IBlockState state = world.getBlockState(pos);
			if (state.getProperties().containsKey(ACTIVE) && !state.getValue(ACTIVE)) 
			{
				world.setBlockState(pos, frame);
			}
			pos = new Mutable(framePos).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
			state = world.getBlockState(pos);
			if (state.getProperties().containsKey(ACTIVE) && !state.getValue(ACTIVE)) {
				world.setBlockState(pos, frame);
			}
		});
		EnumFacing.Axis portalAxis = EnumFacing.Axis.X == axis ? EnumFacing.Axis.Z : EnumFacing.Axis.X;
		
		//BlockState portal = PORTAL.getDefaultState().with(EndPortalBlock.AXIS, portalAxis);
		IBlockState portal = PORTAL.getDefaultState().withProperty(EndPortalBlock.AXIS, portalAxis).withProperty(EndPortalBlock.PORTAL, dim);
		
//		IParticleData effect = new BlockParticleData(ParticleTypes.BLOCK, portal);
//		ServerWorld serverWorld = (ServerWorld) world;
//		
//		PORTAL_MAP.forEach(point -> {
//			BlockPos pos = new Mutable(center).move(moveDir, point.x).move(EnumFacing.UP, point.y);
//			if (!world.getBlockState(pos).isIn(PORTAL)) {
//				world.setBlockState(pos, portal);
//				serverWorld.spawnParticle(effect, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.1);
//				serverWorld.spawnParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.3);
//			}
//			pos = new Mutable(center).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
//			if (!world.getBlockState(pos).isIn(PORTAL)) 
//			{
//				world.setBlockState(pos, portal);
//				serverWorld.spawnParticle(effect, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.1);
//				serverWorld.spawnParticle(ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.3);
//			}
//		});
	}
	
	public void removePortal(int state) 
	{
		if (!active || isInvalid()) return;
		//World targetWorld = this.getTargetWorld();
		World targetWorld = this.getTargetWorld(state);
		this.removePortal(world, center);
		this.removePortal(targetWorld, exit);
	}
	
	private void removePortal(World world, BlockPos center) 
	{
		BlockPos framePos = center.down();
		EnumFacing moveDir = EnumFacing.Axis.X == axis ? EnumFacing.NORTH: EnumFacing.EAST;
		FRAME_MAP.forEach(point -> {
			BlockPos pos = new Mutable(framePos).move(moveDir, point.x).move(EnumFacing.UP, point.y);
			IBlockState state = world.getBlockState(pos);
			if (state.getBlock()==(FRAME) && state.getValue(ACTIVE)) 
			{
				world.setBlockState(pos, state.withProperty(ACTIVE, false));
			}
			pos = new Mutable(framePos).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
			state = world.getBlockState(pos);
			if (state.getBlock()==(FRAME) && state.getValue(ACTIVE)) 
			{
				world.setBlockState(pos, state.withProperty(ACTIVE, false));
			}
		});
		PORTAL_MAP.forEach(point -> {
			BlockPos pos = new Mutable(center).move(moveDir, point.x).move(EnumFacing.UP, point.y);
			if (world.getBlockState(pos).getBlock()==(PORTAL))
			{
				world.setBlockToAir(pos);
			}
			pos = new Mutable(center).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
			if (world.getBlockState(pos).getBlock()==(PORTAL))
			{
				world.setBlockToAir(pos);
			}
		});
		this.active = false;
	}
	
	private BlockPos findPortalPos(int state) 
	{
		//ServerWorld targetWorld = (ServerWorld) this.getTargetWorld();
		WorldServer targetWorld = (WorldServer) this.getTargetWorld(state);

		double mult = targetWorld.provider.getMovementFactor();
		
		BlockPos.MutableBlockPos basePos = new Mutable(center).setPos(center.getX() / mult, center.getY(), center.getZ() / mult);
		EnumFacing.Axis portalAxis = EnumFacing.Axis.X == axis ? EnumFacing.Axis.Z : EnumFacing.Axis.X;
		if (checkIsAreaValid(targetWorld, basePos, portalAxis)) 
		{
			EternalRitual.generatePortal(targetWorld, basePos, portalAxis);
			return basePos.toImmutable();
		} 
		else 
		{
			EnumFacing enumFacing = EnumFacing.EAST;
			Mutable checkPos = new Mutable(basePos);

			for (int step = 1; step < 128; step++)
			{
				for (int i = 0; i < (step >> 1); i++)
				{
					Chunk chunk = world.getChunk(checkPos);
					if (chunk != null)
					{
						int ceil = chunk.getHeight(new BlockPos(checkPos.getX() & 15, 0, checkPos.getZ() & 15)) + 1;
						if (ceil < 2) continue;
						checkPos.setY(ceil);
						while (checkPos.getY() > 2) 
						{
							if(checkIsAreaValid(targetWorld, checkPos, portalAxis)) 
							{
								EternalRitual.generatePortal(targetWorld, checkPos, portalAxis);
								return checkPos.toImmutable();
							}
							checkPos.move(EnumFacing.DOWN);
						}
					}
					checkPos.move(enumFacing);
				}
				enumFacing = enumFacing.rotateY();
			}
		}
		
		if (targetWorld.provider.getDimension() == 1)
		{
			(new WorldGenEndIsland()).generate(targetWorld, new Random(basePos.toLong()), basePos.down());
			//Features.END_ISLAND.generate(targetWorld, targetWorld.getChunkProvider().getChunkGenerator(), new Random(basePos.toLong()), basePos.down());
		} 
		else 
		{
			basePos.setY(targetWorld.getChunk(basePos).getHeight(new BlockPos(basePos.getX(), 0, basePos.getZ())) + 1);
			ModConfiguredFeatures.OVERWORLD_ISLAND.place(targetWorld, new Random(basePos.toLong()), basePos.down());
		}
		
		EternalRitual.generatePortal(targetWorld, basePos, portalAxis);
		
		return basePos.toImmutable();
	}
	
	private World getTargetWorld(int state) 
	{
		//RegistryKey<World> target = world.getDimensionKey() == World.THE_END ? World.OVERWORLD : World.THE_END;
		//return Objects.requireNonNull(world.getServer()).getWorld(target);
		if (world.provider.getDimension() == 1)
		{
			return EndPortals.getWorld(world.getMinecraftServer(), state);
		}
		return Objects.requireNonNull(world.getMinecraftServer()).getWorld(1);
	}
	
	private boolean checkIsAreaValid(World world, BlockPos pos, EnumFacing.Axis axis) 
	{
		if (!isBaseValid(world, pos, axis)) return false;
		return EternalRitual.checkArea(world, pos, axis);
	}
	
	private boolean isBaseValid(World world, BlockPos pos, EnumFacing.Axis axis) 
	{
		boolean solid = true;
		if (axis.equals(EnumFacing.Axis.X)) 
		{
			pos = pos.down().add(0, 0, -3);
			for (int i = 0; i < 7; i++) 
			{
				BlockPos checkPos = pos.add(0, 0, i);
				IBlockState state = world.getBlockState(checkPos);
				solid &= this.validBlock(world, checkPos, state);
			}
		} 
		else 
		{
			pos = pos.down().add(-3, 0, 0);
			for (int i = 0; i < 7; i++) 
			{
				BlockPos checkPos = pos.add(i, 0, 0);
				IBlockState state = world.getBlockState(checkPos);
				solid &= this.validBlock(world, checkPos, state);
			}
		}
		return solid;
	}
	
	private boolean validBlock(World world, BlockPos pos, IBlockState state) 
	{
		return state.isNormalCube() && state.isOpaqueCube();
	}
	
	public static void generatePortal(World world, BlockPos center, EnumFacing.Axis axis) 
	{
		BlockPos framePos = center.down();
		EnumFacing moveDir = EnumFacing.Axis.X == axis ? EnumFacing.EAST: EnumFacing.NORTH;
		IBlockState frame = FRAME.getDefaultState().withProperty(ACTIVE, true);
		FRAME_MAP.forEach(point -> {
			BlockPos pos = new Mutable(framePos).move(moveDir, point.x).move(EnumFacing.UP, point.y);
			world.setBlockState(pos, frame);
			pos = new Mutable(framePos).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
			world.setBlockState(pos, frame);
		});
		IBlockState portal = PORTAL.getDefaultState().withProperty(EndPortalBlock.AXIS, axis);
		PORTAL_MAP.forEach(point -> {
			BlockPos pos = new Mutable(center).move(moveDir, point.x).move(EnumFacing.UP, point.y);
			world.setBlockState(pos, portal);
			pos = new Mutable(center).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
			world.setBlockState(pos, portal);
		});
		generateBase(world, framePos, moveDir);
	}
	
	private static void generateBase(World world, BlockPos center, EnumFacing moveX) 
	{
		IBlockState base = BASE.getDefaultState();
		EnumFacing moveY = moveX.rotateY();
		BASE_MAP.forEach(point -> {
			BlockPos pos = new Mutable(center).move(moveX, point.x).move(moveY, point.y);
			world.setBlockState(pos, base);
			pos = new Mutable(center).move(moveX, -point.x).move(moveY, point.y);
			world.setBlockState(pos, base);
			pos = new Mutable(center).move(moveX, point.x).move(moveY, -point.y);
			world.setBlockState(pos, base);
			pos = new Mutable(center).move(moveX, -point.x).move(moveY, -point.y);
			world.setBlockState(pos, base);
		});
	}
	
	public static boolean checkArea(World world, BlockPos center, EnumFacing.Axis axis) 
	{
		EnumFacing moveDir = EnumFacing.Axis.X == axis ? EnumFacing.NORTH: EnumFacing.EAST;
		for (BlockPos checkPos : BlockPos.getAllInBoxMutable(center.offset(moveDir.rotateY()), center.offset(moveDir.rotateYCCW()))) 
		{
			for (Point point : PORTAL_MAP) 
			{
				BlockPos pos = new Mutable(checkPos).move(moveDir, point.x).move(EnumFacing.UP, point.y);
				IBlockState state = world.getBlockState(pos);

				if (isStateInvalid(state)) return false;
				
				pos = new Mutable(checkPos).move(moveDir, -point.x).move(EnumFacing.UP, point.y);
				state = world.getBlockState(pos);
				
				if (isStateInvalid(state)) return false;
			}
		}
		return true;
	}
	
	private static boolean isStateInvalid(IBlockState state) 
	{
		if (state.getBlock()== Blocks.WATER) return true;
		Material material = state.getMaterial();
		return !material.isReplaceable() && !material.equals(Material.PLANTS);
	}
	
	public void configure(BlockPos initial) 
	{
		BlockPos checkPos = initial.east(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.X;
			this.center = initial.east(6);
			return;
		}
		checkPos = initial.west(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.X;
			this.center = initial.west(6);
			return;
		}
		checkPos = initial.south(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.Z;
			this.center = initial.south(6);
			return;
		}
		checkPos = initial.north(12);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.Z;
			this.center = initial.north(6);
			return;
		}
		checkPos = initial.north(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.X;
			checkPos = checkPos.east(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.north(5).east(4);
			} 
			else 
			{
				this.center = initial.north(5).west(4);
			}
			return;
		}
		checkPos = initial.south(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.X;
			checkPos = checkPos.east(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.south(5).east(4);
			} 
			else
			{
				this.center = initial.south(5).west(4);
			}
			return;
		}
		checkPos = initial.east(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.Z;
			checkPos = checkPos.south(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.east(5).south(4);
			} 
			else
			{
				this.center = initial.east(5).north(4);
			}
			return;
		}
		checkPos = initial.west(10);
		if (this.hasPedestal(checkPos)) 
		{
			this.axis = EnumFacing.Axis.Z;
			checkPos = checkPos.south(8);
			if (this.hasPedestal(checkPos)) 
			{
				this.center = initial.west(5).south(4);
			} 
			else
			{
				this.center = initial.west(5).north(4);
			}
			return;
		}
	}
	
	private boolean hasPedestal(BlockPos pos) 
	{
		return world.getBlockState(pos).getBlock()==(PEDESTAL);
	}
	
	private boolean isActive(BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock()==(PEDESTAL)) 
		{
			EternalPedestalTileEntity pedestal = (EternalPedestalTileEntity) world.getTileEntity(pos);
			
			assert pedestal != null;
			
			if (!pedestal.hasRitual()) 
			{
				pedestal.linkRitual(this);
			}
			return state.getValue(ACTIVE);
		}
		return false;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		compound.setTag("center", NBTUtil.createPosTag((center)));
		if (exit != null) {
			compound.setTag("exit", NBTUtil.createPosTag(exit));
		}
		compound.setString("axis", axis.getName2());
		compound.setBoolean("active", active);
		return compound;
	}
	
	public void readFromNBT(NBTTagCompound nbt) 
	{
		this.axis = EnumFacing.Axis.byName(nbt.getString("axis"));
		this.center = NBTUtil.getPosFromTag(nbt.getCompoundTag("center"));
		this.active = nbt.getBoolean("active");
		if (nbt.hasKey("exit")) {
			this.exit = NBTUtil.getPosFromTag(nbt.getCompoundTag("exit"));
		}
	}
}
