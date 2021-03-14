package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ConfigurableOpenSimplexNoise;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.GreenForestBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.InterpolatedIntegerSupplier;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ValueProvider;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.WeightedBiomeData;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.InterpolationChannelBiomeRegistryEvent;
import de.budschie.deepnether.main.References;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class GreenForestBiomeData implements IDeepnetherBiomeData
{
	private static GreenForestBiomeGenerator greenForest;
	
	@Override
	public IBiomeGenerator getBiomeGenerator()
	{
		return greenForest;
	}
	
	@SubscribeEvent
	public static void onRegisterBiomeChannels(InterpolationChannelBiomeRegistryEvent event)
	{
		greenForest = new GreenForestBiomeGenerator(seed -> new ConfigurableOpenSimplexNoise(seed, .03, 2, 1.7), 8, BlockInit.GREEN_FOREST_FERTILIUM_GRASS_BLOCK.getDefaultState(), BlockInit.FERTILIUM.getDefaultState(), Blocks.LAVA.getDefaultState(), BlockInit.FERTILIUM.getDefaultState());
		MinecraftForge.EVENT_BUS.register(greenForest);

		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("terrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "green_forest_biome"), new InterpolatedIntegerSupplier(30, 57));
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("minTerrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "green_forest_biome"), new InterpolatedIntegerSupplier(0, 38));
		event.getChunkGenerator().<ResourceLocation, WeightedBiomeData>getInterpolationChannel("nearbyBiomes").addMappingFunction(new ResourceLocation(References.MODID, "green_forest_biome"), new ValueProvider<ResourceLocation>(new ResourceLocation(References.MODID, "green_forest_biome")));
	}
}
