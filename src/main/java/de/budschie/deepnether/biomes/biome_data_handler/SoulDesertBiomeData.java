package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.SimpleBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.SoulDesertBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.StandardHeightmapSupplier;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.StandardIntegerProvider;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.ValueProvider;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.WeightedBiomeData;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.InterpolationChannelBiomeRegistryEvent;
import de.budschie.deepnether.main.References;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class SoulDesertBiomeData implements IDeepnetherBiomeData
{
	@Override
	public IBiomeGenerator getBiomeGenerator()
	{
		return new SoulDesertBiomeGenerator();
	}
	
	@SubscribeEvent
	public static void onRegisterBiomeChannels(InterpolationChannelBiomeRegistryEvent event)
	{
		event.getChunkGenerator().<Double, Double>getInterpolationChannel("heightmap").addMappingFunction(new ResourceLocation(References.MODID, "soul_desert_biome"), new StandardHeightmapSupplier(.03, 2, 1.7));
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("terrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "soul_desert_biome"), new StandardIntegerProvider(40));
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("minTerrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "soul_desert_biome"), new StandardIntegerProvider(15));
		event.getChunkGenerator().<ResourceLocation, WeightedBiomeData>getInterpolationChannel("nearbyBiomes").addMappingFunction(new ResourceLocation(References.MODID, "soul_desert_biome"), new ValueProvider<ResourceLocation>(new ResourceLocation(References.MODID, "soul_desert_biome")));
	}
}
