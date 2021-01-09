package de.budschie.deepnether.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomeUtil
{
	public static ResourceLocation getBiomeRS(Biome biome, MinecraftServer server)
	{
		return server.func_244267_aX().getRegistry(Registry.BIOME_KEY).getKey(biome);
	}
}
