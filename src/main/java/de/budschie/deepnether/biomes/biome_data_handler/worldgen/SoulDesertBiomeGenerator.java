package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.GreenForestBiomeGenerator.WeightedBlockState;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerationInitEvent;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.HeightmapChannels;
import de.budschie.deepnether.dimension.IHeightmapProvider;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.noise.VoronoiNoise;
import de.budschie.deepnether.util.MathUtil;
import de.budschie.deepnether.util.NoiseUtils;
import de.budschie.deepnether.util.Pair;
import net.kdotjpg.opensimplexnoise.OpenSimplexNoise;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class SoulDesertBiomeGenerator implements IBiomeGenerator, IHeightmapProvider
{
	public static final int MAX_ISLAND_HEIGHT = 120;
	public static final int MIN_ISLAND_HEIGHT = 90;
	public static final double ISLAND_SCALING = .05;
	public static final double ISLAND_SIZE_LAYER1 = .02;
	public static final double ISLAND_SIZE_LAYER2 = .001;
	public static final double ISLAND_SIZE_LAYER3 = .001;
	public static final double ISLAND_THRESHOLD_LAYER1 = .35;
	public static final double ISLAND_THRESHOLD_LAYER2 = .3;
	public static final double ISLAND_THRESHOLD_LAYER3 = .5;
	INoiseProvider noiseProvider;
	Function<Long, INoiseProvider> noiseProviderSupplier;

	private OpenSimplexNoise osn = new OpenSimplexNoise(696969);
	
	public SoulDesertBiomeGenerator(Function<Long, INoiseProvider> noiseProviderSupplier)
	{
		this.noiseProviderSupplier = noiseProviderSupplier;
	}
	
	@SubscribeEvent
	public void onChunkGenInit(DeepnetherChunkGenerationInitEvent event)
	{
		osn = new OpenSimplexNoise(event.getSeed());
		noiseProvider = noiseProviderSupplier.apply(event.getSeed());
	}
	
	@Override
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk,
			DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
		int minTerrainHeight = interpolationChannelBuffer.<Integer>getValue("minTerrainHeight")[localX][localZ];
		double currentHeightValue = 0;
		
		WeightedBiomeData biomeData = interpolationChannelBuffer.<WeightedBiomeData>getValue("nearbyBiomes")[localX][localZ];
		for(ResourceLocation rs : biomeData.getWeights().keySet())
		{
			currentHeightValue += (BiomeDataHandler.getBiomeData(rs).getBiomeGenerator().getParamValue(Double.class, "heightmapGround", new HeightmapTuple(chunkGenerator.getBiomeProvider(), interpolationChannelBuffer.getBiomeFunction(), chunkStartX + localX, chunkStartZ + localZ)) * biomeData.getWeights().get(rs));
		}
		
		// Modify so that the minTerrainHeight applies
		// Remapping from 0 to 1 to x to 1
		currentHeightValue = MathUtil.linearInterpolation(minTerrainHeight / (double)currentTerrainHeight, 1, currentHeightValue);
				
		int definedTerrainHeight = (int) (currentHeightValue * currentTerrainHeight);
		
		Pair<Float, List<WeightedBlockState>> lavaInterpolationData = getLaveInterpolationData(biomeData, definedTerrainHeight);
		Random xyRandom = new Random((localX * 2) ^ localZ);
		int totalWeight = WeightedRandom.getTotalWeight(lavaInterpolationData.getSecond());
		
		for(int y = 0; y <= Math.max(definedTerrainHeight, lavaInterpolationData.getFirst()); y++)
		{
			if(y <= definedTerrainHeight)
				chunk.setBlockState(new BlockPos(localX, y, localZ), BlockInit.SOUL_DUST.getDefaultState(), false);
			else
			{
				chunk.setBlockState(new BlockPos(localX, y, localZ), WeightedRandom.getRandomItem(xyRandom, lavaInterpolationData.getSecond(), totalWeight).getBlockState(), false);
			}
		}
		
		int biomeId = chunkGenerator.getBiomeProvider().getBiomeId(chunkStartX + localX, 0, chunkStartZ + localZ);
				
		// VoronoiNoise noise = new VoronoiNoise(biomeId + 5);
		
		boolean hasIsland = hasIsland(localX, localZ, chunkStartX, chunkStartZ);
		
		if(hasIsland)
		{
			//double sampledHeightmap = noise.voronoiNoise(chunkStartX + localX, chunkStartZ + localZ, ISLAND_SCALING, false);
			double sampledHeightmap = NoiseUtils.sampleNormNoiseAdvanced((sampleX, sampleY) -> osn.eval(sampleX, sampleY), localX + chunkStartX, localZ + chunkStartZ, ISLAND_SIZE_LAYER1, 3, 2, 1);
			
			sampledHeightmap *= biomeData.getWeights().get(new ResourceLocation(References.MODID, "soul_desert_biome"));
			
			int totalValue = (int) ((Math.floor(sampledHeightmap * (MAX_ISLAND_HEIGHT - MIN_ISLAND_HEIGHT))));
			
			for(int y = 0; y <= totalValue; y++)
			{
				int currentY = y + MIN_ISLAND_HEIGHT;
				
				if(y == totalValue)
				{
					chunk.setBlockState(new BlockPos(localX, currentY, localZ), BlockInit.NETHER_DUST_GRASS_BLOCK.getDefaultState(), false);
					
					boolean placeGrass = new Random(currentY * localX ^ localZ).nextInt(6) == 0;
					
					if(placeGrass)
						chunk.setBlockState(new BlockPos(localX, currentY + 1, localZ), BlockInit.NETHER_DUST_GRASS.getDefaultState(), false);
				}
				else if((y + 3) >= totalValue)
				{
					chunk.setBlockState(new BlockPos(localX, currentY, localZ), BlockInit.SOUL_DUST.getDefaultState(), false);
				}
				else
				{
					chunk.setBlockState(new BlockPos(localX, currentY, localZ), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), false);
				}
			}
		}
	}
	
	protected boolean hasIsland(int localX, int localZ, int chunkStartX, int chunkStartZ)
	{
		return (NoiseUtils.sampleNormNoiseAdvanced((sampleX, sampleY) -> osn.eval(sampleX, sampleY), localX + chunkStartX, localZ + chunkStartZ, ISLAND_SIZE_LAYER1, 2, 2, 1) > ISLAND_THRESHOLD_LAYER1 
				&& NoiseUtils.sampleNormNoiseAdvanced((sampleX, sampleY) -> osn.eval(sampleX, sampleY), localX + chunkStartX, localZ + chunkStartZ, ISLAND_SIZE_LAYER2, 4, 2, 1) > ISLAND_THRESHOLD_LAYER2
				&& NoiseUtils.sampleNormNoiseAdvanced((sampleX, sampleY) -> osn.eval(sampleX + 696969, sampleY + 420420), localX + chunkStartX, localZ + chunkStartZ, ISLAND_SIZE_LAYER3, 4, 2, 1) > ISLAND_THRESHOLD_LAYER3);
	}

	@Override
	public int getHeight(String channel, int chunkStartX, int chunkStartZ, int localX, int localZ, DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		if(channel.equals(HeightmapChannels.GROUND))
		{
			int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
			int minTerrainHeight = interpolationChannelBuffer.<Integer>getValue("minTerrainHeight")[localX][localZ];
			double currentHeightValue = 0;
			
			WeightedBiomeData biomeData = interpolationChannelBuffer.<WeightedBiomeData>getValue("nearbyBiomes")[localX][localZ];
			for(ResourceLocation rs : biomeData.getWeights().keySet())
			{
				currentHeightValue += (BiomeDataHandler.getBiomeData(rs).getBiomeGenerator().getParamValue(Double.class, "heightmapGround", new HeightmapTuple(chunkGenerator.getBiomeProvider(), interpolationChannelBuffer.getBiomeFunction(), chunkStartX + localX, chunkStartZ + localZ)) * biomeData.getWeights().get(rs));
			}				currentHeightValue = MathUtil.linearInterpolation(minTerrainHeight / (double)currentTerrainHeight, 1, currentHeightValue);
					
			return (int) (currentHeightValue * currentTerrainHeight);

		}
		else if(channel.equals(HeightmapChannels.SOUL_DESERT_ISLANDS))
		{
			WeightedBiomeData biomeData = interpolationChannelBuffer.<WeightedBiomeData>getValue("nearbyBiomes")[localX][localZ];
			
			// This copy-pasta is getting annoying...
			
			if(hasIsland(localX, localZ, chunkStartX, chunkStartZ))
			{
				double sampledHeightmap = NoiseUtils.sampleNormNoiseAdvanced((sampleX, sampleY) -> osn.eval(sampleX, sampleY), localX + chunkStartX, localZ + chunkStartZ, ISLAND_SIZE_LAYER1, 3, 2, 1);
				
				sampledHeightmap *= biomeData.getWeights().get(new ResourceLocation(References.MODID, "soul_desert_biome"));
				
				return (int) ((Math.floor(sampledHeightmap * (MAX_ISLAND_HEIGHT - MIN_ISLAND_HEIGHT)))) + MIN_ISLAND_HEIGHT;
			}
			else 
				return NOOP_INT;
		}
		
		return NOOP_INT;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T, A> T getParamValue(Class<T> clazz, String name, A args)
	{
		if(clazz == Double.class && args.getClass() == HeightmapTuple.class)
		{
			HeightmapTuple tuple = (HeightmapTuple) args;
			return (T) noiseProvider.getNoise(tuple.biomeProvider, tuple.biomeSupplier, tuple.x, tuple.z);
		}
		
		return null;
	}

	@Override
	public BlockState getFillerBlock()
	{
		return BlockInit.SOUL_DUST.getDefaultState();
	}

	@Override
	public Optional<Integer> getSeaLevelHeight()
	{
		return Optional.empty();
	}
}
