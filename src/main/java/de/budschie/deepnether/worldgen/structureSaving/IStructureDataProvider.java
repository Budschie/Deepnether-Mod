package de.budschie.deepnether.worldgen.structureSaving;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public interface IStructureDataProvider<T>
{
	public StructureData get(T generatorData);
	public StructureData getDefault(World world);
	
	public String getRegistryID();
	
	public void writeHeader(CompoundNBT compound);
	public void readHeader(CompoundNBT compound);
}
