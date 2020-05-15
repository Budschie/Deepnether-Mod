package de.budschie.deepnether.dimension;

import de.budschie.deepnether.biomes.BiomeRegistry;
import de.budschie.deepnether.biomes.DeepnetherBiomeBase;
import de.budschie.deepnether.main.DeepnetherMain;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.networking.DeepnetherPacketHandler;
import de.budschie.deepnether.networking.PullFogMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.NetherDimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = References.MODID, bus = Bus.FORGE)
public class DimensionDeepnether extends Dimension
{
	private boolean hasPulled = false;
	private long seed = 0;
	DeepnetherBiomeProvider biomeProvider;
	
	// Works JUST with FORGE EVENT BUS and STATIC 
	@SubscribeEvent
	public static void recievedSeed(RecievedSeedEvent event)
	{
		if(Minecraft.getInstance().world.dimension instanceof DimensionDeepnether)
		{
			DimensionDeepnether dimDeepnether = (DimensionDeepnether) Minecraft.getInstance().world.dimension;
			
			dimDeepnether.changedSeed(event.seed);
		}
	}
	
	private void changedSeed(long seed)
	{
		if(this.seed != seed || biomeProvider == null)
		{
			this.seed = seed;
			biomeProvider = new DeepnetherBiomeProvider(seed, 1000.0f, BiomeRegistry.DEEPNETHER_BIOME, BiomeRegistry.FLOATING_ISLANDS_BIOME, BiomeRegistry.CRYSTAL_CAVE_BIOME);
		}
	}
	
	public DimensionDeepnether(World world, DimensionType type)
	{
		super(world, type, 0.1f);
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator()
	{
		biomeProvider = new DeepnetherBiomeProvider(world.getSeed(), 1000.0f, BiomeRegistry.DEEPNETHER_BIOME, BiomeRegistry.FLOATING_ISLANDS_BIOME, BiomeRegistry.CRYSTAL_CAVE_BIOME);
		return new DeepnetherChunkGenerator(world, biomeProvider);
	}

	@Override
	public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid)
	{
		return findSpawn(chunkPosIn.x,  chunkPosIn.z, checkValid);
	}

	@Override
	public BlockPos findSpawn(int posX, int posZ, boolean checkValid)
	{
		return new BlockPos(posX, 60, posZ);
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		return 0;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	@Override
	public Vec3d getFogColor(float celestialAngle, float partialTicks)
	{
		if(biomeProvider == null && !hasPulled)
		{
			hasPulled = true;
			DeepnetherPacketHandler.INSTANCE.sendToServer(new PullFogMessage());
		}
		return biomeProvider == null ? new Vec3d(1.0, 0.25, 0.25) : biomeProvider.getFogColor((int)Minecraft.getInstance().player.getPosX(), (int)Minecraft.getInstance().player.getPosZ(), 10);
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public boolean doesXZShowFog(int x, int z)
	{
		return true;
	}
	
	public void spawnAmbientParticles()
	{
		if(biomeProvider == null)
			return;
    	BlockPos pos = Minecraft.getInstance().player.getPosition();
    	DeepnetherBiomeBase biomeBase = (DeepnetherBiomeBase) biomeProvider.getBiome((int)Minecraft.getInstance().player.getPosX(), (int)Minecraft.getInstance().player.getPosZ());
    	
    	if(biomeBase.hasParticles())
    	{
    		biomeBase.summonParticleAt(world, pos.getX(), pos.getY(), pos.getZ());
    	}
	}
}
