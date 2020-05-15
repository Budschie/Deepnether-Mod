package de.budschie.deepnether.structures;

import net.minecraft.nbt.CompoundNBT;

public class WrittenData
{
	String fileName;
	CompoundNBT data;
	
	public WrittenData(String fileName, CompoundNBT data)
	{
		this.fileName = fileName;
		this.data = data;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public CompoundNBT getData()
	{
		return data;
	}
}