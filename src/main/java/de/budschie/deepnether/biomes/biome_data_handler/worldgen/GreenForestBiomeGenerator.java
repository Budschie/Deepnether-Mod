package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import de.budschie.deepnether.biomes.biome_data_handler.BiomeDataHandler;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.GreenForestBiomeGenerator.WeightedBlockState;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerationInitEvent;
import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.HeightmapChannels;
import de.budschie.deepnether.dimension.IHeightmapProvider;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import de.budschie.deepnether.util.MathUtil;
import de.budschie.deepnether.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class GreenForestBiomeGenerator implements IBiomeGenerator, IHeightmapProvider
{
	int seaLevel;
	int nearLavaBlockRange;
	BlockState grassBlock;
	BlockState groundBlock;
	BlockState lavaBlock;
	BlockState nearLavaBlock;
	INoiseProvider noiseProvider;
	Function<Long, INoiseProvider> noiseProviderSupplier;
	
	public GreenForestBiomeGenerator(Function<Long, INoiseProvider> noiseProviderSupplier, int seaLevel, int nearLavaBlockRange, BlockState grassBlock, BlockState groundBlock,
			BlockState lavaBlock, BlockState nearLavaBlock)
	{
		super();
		this.seaLevel = seaLevel;
		this.nearLavaBlockRange = nearLavaBlockRange;
		this.grassBlock = grassBlock;
		this.groundBlock = groundBlock;
		this.lavaBlock = lavaBlock;
		this.nearLavaBlock = nearLavaBlock;
		this.noiseProviderSupplier = noiseProviderSupplier;
	}
	
	public GreenForestBiomeGenerator(Function<Long, INoiseProvider> noiseProviderSupplier, int nearLavaBlockRange, BlockState grassBlock, BlockState groundBlock,
			BlockState lavaBlock, BlockState nearLavaBlock)
	{
		this(noiseProviderSupplier, WorldGenConsts.SEA_LEVEL, nearLavaBlockRange, grassBlock, groundBlock, lavaBlock, nearLavaBlock);
	}
	
	@SubscribeEvent
	public void onChunkGenInit(DeepnetherChunkGenerationInitEvent event)
	{
		noiseProvider = noiseProviderSupplier.apply(event.getSeed());
	}

	@Override
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk,
			DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
		int minTerrainHeight = interpolationChannelBuffer.<Integer>getValue("minTerrainHeight")[localX][localZ];
		// double currentHeightValue = interpolationChannelBuffer.<Double>getValue("heightmap")[localX][localZ];
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
		
		// System.out.println("DATA: terrainHeight:" + currentTerrainHeight + "; currentHeightValue: " + currentHeightValue + "; definedTerrainHeight: " + definedTerrainHeight);
		
		Pair<Float, List<WeightedBlockState>> lavaInterpolationData = getLaveInterpolationData(biomeData, definedTerrainHeight);
		Random xyRandom = new Random((localX * 2) ^ localZ);
		int totalWeight = WeightedRandom.getTotalWeight(lavaInterpolationData.getSecond());
				
		for(int y = 0; y <= Math.max(definedTerrainHeight, lavaInterpolationData.getFirst()); y++)
		{

			BlockState chosenBlockState = Blocks.AIR.getDefaultState();
			
			if(y > definedTerrainHeight && y <= lavaInterpolationData.getFirst())
			{
				chosenBlockState = WeightedRandom.getRandomItem(xyRandom, lavaInterpolationData.getSecond(), totalWeight).blockState;
			}
			else if(((y - nearLavaBlockRange) < lavaInterpolationData.getFirst() && y >= lavaInterpolationData.getFirst()) || (y < lavaInterpolationData.getFirst() && y == definedTerrainHeight))
			{
				chosenBlockState = nearLavaBlock;
			}
			else if(y < definedTerrainHeight || (y == definedTerrainHeight && y < lavaInterpolationData.getFirst()))
			{
				chosenBlockState = groundBlock;
			}
			else if(y == definedTerrainHeight)
			{
				chosenBlockState = grassBlock;
			}
			
			chunk.setBlockState(new BlockPos(localX + chunkStartX, y, localZ + chunkStartZ), chosenBlockState, false);
		}
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
			}				
			currentHeightValue = MathUtil.linearInterpolation(minTerrainHeight / (double)currentTerrainHeight, 1, currentHeightValue);
			
			return (int) (currentHeightValue * currentTerrainHeight);
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
	
	public static class WeightedBlockState extends WeightedRandom.Item
	{
		private BlockState blockState;
		
		public WeightedBlockState(int itemWeightIn, BlockState blockState)
		{
			super(itemWeightIn);
			this.blockState = blockState;
		}
		
		public BlockState getBlockState()
		{
			return blockState;
		}
	}

	@Override
	public BlockState getFillerBlock()
	{
		return lavaBlock;
	}

	@Override
	public Optional<Integer> getSeaLevelHeight()
	{
		return Optional.of(seaLevel);
	}
}
