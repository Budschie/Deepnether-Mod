package de.budschie.deepnether.worldgen.structureSaving;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class StructureDataProviderRegistry
{
	private static HashMap<String, IStructureDataProvider<?>> hashMap = new HashMap<>();
	
	public static void addEntry(IStructureDataProvider<?> entry)
	{
		hashMap.put(entry.getRegistryID(), entry);
	}
	
	public static IStructureDataProvider<?> removeEntry(String id)
	{
		return hashMap.remove(id);
	}
	
	public static <A> IStructureDataProvider<A> getEntry(String id)
	{
		return (IStructureDataProvider<A>) hashMap.get(id);
	}
	
	public static Collection<IStructureDataProvider<?>> getEntries()
	{
		return hashMap.values();
	}
	
	public static void addEntriesFromRegistry()
	{
		addEntry(new TestStructureDataProvider());
	}
}
