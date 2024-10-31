package mod.beethoven92.betterendforge.common.capability;

import com.google.common.collect.ImmutableList;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EndData implements INBTSerializable<NBTTagCompound> {
	//public static final Capability<EndData> CAPABILITY = null;
	private static EndData INSTANCE;

	public static EndData getInstance(){
		if(INSTANCE==null){
			INSTANCE = new EndData();
		}
		return INSTANCE;
	}

	private Set<UUID> players;
	private BlockPos spawn;
	private Set<Long> decoratorPasses;

	private EndData() {
		players = new HashSet<>();
		decoratorPasses = new HashSet<>();
	}

	private void login(EntityPlayerMP player) {
		if (players.contains(player.getUniqueID()))
			return;
		players.add(player.getUniqueID());

		teleportToSpawn(player);
	}

	private void teleportToSpawn(EntityPlayerMP player) {
		// If custom spawn point is set or config not set, get out of here
		if (player.getBedLocation() != null || !GeneratorOptions.swapOverworldToEnd())
			return;

		World world = player.getServerWorld();
		World end = player.getServer().getWorld(1);
		if (end == null)
			return;

		if (spawn == null)
			spawn = findSpawn(end, player);
		if (spawn == null)
			return;

		if (world == end) {
			player.setPositionAndUpdate(spawn.getX(), spawn.getY(), spawn.getZ());
		} else {
			player.changeDimension(end.provider.getDimension(), (world1, entity, yaw) -> entity.moveToBlockPosAndAngles(spawn, yaw, entity.rotationPitch));
		}
	}

	private BlockPos findSpawn(World end, EntityPlayer player) {
		ImmutableList<Biome> biomes = ImmutableList.of(ModBiomes.AMBER_LAND.getActualBiome(),
				ModBiomes.BLOSSOMING_SPIRES.getActualBiome(), ModBiomes.CHORUS_FOREST.getActualBiome(),
				ModBiomes.CRYSTAL_MOUNTAINS.getActualBiome(), ModBiomes.DRY_SHRUBLAND.getActualBiome(),
				ModBiomes.DUST_WASTELANDS.getActualBiome(), ModBiomes.FOGGY_MUSHROOMLAND.getActualBiome(),
				ModBiomes.GLOWING_GRASSLANDS.getActualBiome(), ModBiomes.LANTERN_WOODS.getActualBiome(),
				ModBiomes.MEGALAKE.getActualBiome(), ModBiomes.SULPHUR_SPRINGS.getActualBiome(),
				ModBiomes.UMBRELLA_JUNGLE.getActualBiome());
		for (Biome biome : biomes) {
			BlockPos pos = end.getBiomeProvider().findBiomePosition(0, 40, 6400, biomes, end.rand);
			if (pos == null)
				continue;

			for (int i = 0; i < 40; i++) {
				BlockPos p = pos.add(end.rand.nextInt(40) - 20, 0, end.rand.nextInt(40) - 20);
				for (int k = 0; k < 150; k++) {
					if (!end.isAirBlock(p.add(0, k, 0)) && end.isAirBlock(p.add(0, k + 1, 0))
							&& end.isAirBlock(p.add(0, k + 2, 0)))
						return p.add(0, k + 1, 0);
				}
			}
		}
		return null;
	}

	public static void playerLogin(EntityPlayerMP player) {
		EndData.getInstance().login(player);
	}

	public static void playerRespawn(EntityPlayerMP player) {
		EndData.getInstance().teleportToSpawn(player);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (spawn != null)
			nbt.setTag("spawn", NBTUtil.createPosTag(spawn));
		NBTTagList list = new NBTTagList();
		for (UUID id : players)
			list.appendTag(NBTUtil.createUUIDTag(id));
		nbt.setTag("players", list);

		NBTTagList listDec = new NBTTagList();
		for (Long id : decoratorPasses)
			listDec.appendTag(new NBTTagLong(id));
		nbt.setTag("decoratorPasses", listDec);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("spawn"))
			spawn = NBTUtil.getPosFromTag(nbt.getCompoundTag("spawn"));

		NBTTagList list = nbt.getTagList("players", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++)
			players.add(NBTUtil.getUUIDFromTag(list.getCompoundTagAt(i)));

		NBTTagList listDec = nbt.getTagList("decoratorPasses", Constants.NBT.TAG_LONG);
		for (int i = 0; i < listDec.tagCount(); i++)
			decoratorPasses.add(((NBTTagLong)listDec.get(i)).getLong());
	}

	public void storePass(BlockPos pos){
		decoratorPasses.add(pos.toLong());
	}

	public boolean hasPass(BlockPos pos){
		return decoratorPasses.contains(pos.toLong());
	}

	public boolean removePass(BlockPos pos){
		return decoratorPasses.remove(pos.toLong());
	}

}
