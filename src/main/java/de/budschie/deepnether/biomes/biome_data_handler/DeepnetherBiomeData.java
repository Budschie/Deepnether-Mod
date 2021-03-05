package de.budschie.deepnether.biomes.biome_data_handler;

import de.budschie.deepnether.biomes.biome_data_handler.worldgen.IBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.SimpleBiomeGenerator;
import de.budschie.deepnether.biomes.biome_data_handler.worldgen.StandardHeightmapSupplier;
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
	private static final LazyProvider<SimpleBiomeGenerator> DEEPNETHER_BIOME = new LazyProvider<SimpleBiomeGenerator>(() -> 
	{
		SimpleBiomeGenerator biomeGenerator = new SimpleBiomeGenerator(4, BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), BlockInit.COMPRESSED_NETHERRACK.getDefaultState(), Blocks.LAVA.getDefaultState(), BlockInit.SOUL_DUST.getDefaultState());
		MinecraftForge.EVENT_BUS.register(biomeGenerator);
		return biomeGenerator;
	});
	
	@Override
	public IBiomeGenerator getBiomeGenerator()
	{
		return DEEPNETHER_BIOME.get();
	}
	
	@SubscribeEvent
	public static void onRegisterBiomeChannels(InterpolationChannelBiomeRegistryEvent event)
	{
		event.getChunkGenerator().<Double, Double>getInterpolationChannel("heightmap").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardHeightmapSupplier(.04, 2, .25, 2));
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("terrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardIntegerProvider(45));
		event.getChunkGenerator().<Integer, Integer>getInterpolationChannel("minTerrainHeight").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new StandardIntegerProvider(25));
		event.getChunkGenerator().<ResourceLocation, WeightedBiomeData>getInterpolationChannel("nearbyBiomes").addMappingFunction(new ResourceLocation(References.MODID, "deepnether_biome"), new ValueProvider<ResourceLocation>(new ResourceLocation(References.MODID, "deepnether_biome")));
	}
}
