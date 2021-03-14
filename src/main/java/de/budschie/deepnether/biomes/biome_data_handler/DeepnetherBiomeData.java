package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ConfigurableOpenSimplexNoise;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.SimpleBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.StandardIntegerProvider;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ValueProvider;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.WeightedBiomeData;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.InterpolationChannelBiomeRegistryEvent;
import de.budschie.deepnether.main.References;
import de.budschie.deepnether.util.LazyProvider;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class DeepnetherBiomeData implements IDeepnetherBiomeData
{
	private static SimpleBiomeGenerator deepnetherBiome;
	
	@Override
	public IBiomeGenerator getBiomeGenerator()
	{
		return deepnetherBiome;
	}
	
	@SubscribeEvent
	public static void onRegisterBiomeChannels(InterpolationChannelBiomeRegistryEvent event)
	{
		deepnetherBiome = new SimpleBiomeGenerator(seed -> new ConfigurableOpenSimplexNoise(seed, .04, 2, .25, 2), 4, BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), Blocks.LAVA.getDefaultState(), BlockInit.SOUL_DUST.getDefaultState());
		MinecraftForge.EVENT_BUS.register(deepnetherBiome);
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("terrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardIntegerProvider(45));
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("minTerrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardIntegerProvider(25));
		event.getChunkGenerator().<ResourceLocation, WeightedBiomeData>getInterpolationChannel("nearbyBiomes").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new ValueProvider<ResourceLocation>(new ResourceLocation(References.MODID, "deepnether_biome")));
	}
}
