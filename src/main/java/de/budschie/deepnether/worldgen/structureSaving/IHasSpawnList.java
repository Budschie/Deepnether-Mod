package de.budschie.deepnether.worldgen.structureSaving;

import java.util.List;

import net.minecraft.world.biome.Biome;

public interface IHasSpawnList
{
	/** Determines if the biomespecific spawn list should be replaced **/
	public boolean replaceOldList();
	
	public List<Biome.SpawnListEntry> getSpawnables();
}
