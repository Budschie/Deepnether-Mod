package de.budschie.deepnether.biomes;

import de.budschie.deepnether.main.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.NetherBiome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(bus = Bus.MOD)
public class BiomeRegistry
{
	public static final String DEEPNETHER_BIOME_ID = "deepnether_biome";
	public static final String FLOATING_ISLANDS_BIOME_ID = "floating_islands_biome";
	public static final String CRYSTAL_CAVE_BIOME_ID = "crystal_cave_biome";
	
	public static DeepnetherBiome DEEPNETHER_BIOME = null;
	public static FloatingIslandsBiome FLOATING_ISLANDS_BIOME = null;
	public static CrystalCaveBiome CRYSTAL_CAVE_BIOME = null;
	
	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		IForgeRegistry<Biome> registry = event.getRegistry();
		
		DEEPNETHER_BIOME = new DeepnetherBiome(new DeepnetherBiome.DeepnetherBiomeBuilder());
		DEEPNETHER_BIOME.setRegistryName(new ResourceLocation(References.MODID, DEEPNETHER_BIOME_ID));
		registry.register(DEEPNETHER_BIOME);
		BiomeDictionary.addTypes(DEEPNETHER_BIOME, Type.NETHER, Type.SPOOKY, Type.HOT);
		
		FLOATING_ISLANDS_BIOME = new FloatingIslandsBiome(new FloatingIslandsBiome.FloatingIslandsBiomeBuilder());
		FLOATING_ISLANDS_BIOME.setRegistryName(new ResourceLocation(References.MODID, FLOATING_ISLANDS_BIOME_ID));
		registry.register(FLOATING_ISLANDS_BIOME);
		BiomeDictionary.addTypes(FLOATING_ISLANDS_BIOME, Type.NETHER, Type.SPOOKY, Type.HOT);
		
		CRYSTAL_CAVE_BIOME = new CrystalCaveBiome(new CrystalCaveBiome.CrystalCaveBiomeBuilder());
		CRYSTAL_CAVE_BIOME.setRegistryName(new ResourceLocation(References.MODID, CRYSTAL_CAVE_BIOME_ID));
		registry.register(CRYSTAL_CAVE_BIOME);
		BiomeDictionary.addTypes(CRYSTAL_CAVE_BIOME, Type.NETHER, Type.SPOOKY, Type.HOT, Type.WET);
	}
}
