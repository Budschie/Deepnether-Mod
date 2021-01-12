package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.SimpleBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.StandardHeightmapSupplier;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.StandardTerrainHeightProvider;
import de.budschie.deepnether.block.BlockInit;
import de.budschie.deepnether.dimension.InterpolationChannelBiomeRegistryEvent;
import de.budschie.deepnether.main.References;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE)
public class DeepnetherBiomeData implements IDeepnetherBiomeData
{
	@Override
	public IBiomeGenerator getBiomeGenerator()
	{
		return new SimpleBiomeGenerator(4, BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), Blocks.LAVA.getDefaultState(), BlockInit.SOUL_DUST.getDefaultState());
	}
	
	@SubscribeEvent
	public static void onRegisterBiomeChannels(InterpolationChannelBiomeRegistryEvent event)
	{
		event.getChunkGenerator().<Double>getInterpolationChannel("heightmap").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardHeightmapSupplier(.25, 3));
		event.getChunkGenerator().<Integer>getInterpolationChannel("terrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardTerrainHeightProvider(120));
	}
}
