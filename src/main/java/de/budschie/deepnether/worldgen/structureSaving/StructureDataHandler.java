package de.budschie.deepnether.worldgen.structureSaving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.SaveHandler;

public class StructureDataHandler
{
	private static HashMap<World, ArrayList<StructureData>> structureData = new HashMap<>();
	private static HashMap<World, HashMap<ChunkPos, ArrayList<StructureData>>> structureInChunks = new HashMap<>();
	//private static ArrayList<Integer> currentLoadedStructureIDs = new ArrayList<Integer>();
	private static HashMap<IStructureDataProvider<?>, ArrayList<Integer>> currentLoadedStructureIDs = new HashMap<IStructureDataProvider<?>, ArrayList<Integer>>();
	
	public static final String HEADER_KEY = "Header";
	public static final String CURRENT_ID_KEY = "currentID";
	
	public static final String DIM_LIST_KEY = "worlds";
	
	public static void addStructure(World world, StructureData structure)
	{
		if(!structureData.containsKey(world))
			structureData.put(world, Lists.newArrayList(structure));
		else
			structureData.get(world).add(structure);
		
		if(!currentLoadedStructureIDs.containsKey(structure.provider))
			currentLoadedStructureIDs.put(structure.provider, Lists.newArrayList(structure.id));
		else
			currentLoadedStructureIDs.get(structure.provider).add(structure.id);
		
		remapChunkForStructure(structure);
	}
	
	private static void remapChunkForStructure(StructureData structure)
	{
		// Iterate over every chunk
		ArrayList<ChunkPos> positions = structure.getContainingChunks();
		
		if(!structureInChunks.containsKey(structure.getWorld()))
			structureInChunks.put(structure.getWorld(), new HashMap<>());
		
		HashMap<ChunkPos, ArrayList<StructureData>> hashMap = structureInChunks.get(structure.getWorld());
		
		for(ChunkPos pos : positions)
		{
			ArrayList<StructureData> list = hashMap.get(pos);
			
			// Add to chunkpos
			if(list == null)
			{
				hashMap.put(pos, Lists.newArrayList(structure));
			}
			else
			{
				list.add(structure);
			}
		}
	}
	
	public static void removeChunk(ChunkPos pos, World world)
	{
		if(!structureInChunks.containsKey(world) || !structureInChunks.get(world).containsKey(pos))
			return;
		
		HashMap<StructureData, Boolean> removalList = new HashMap<StructureData, Boolean>();
		
		ArrayList<StructureData> structuresInChunk = structureInChunks.get(world).remove(pos);
		

		ArrayList<ArrayList<StructureData>> arrayarraylistlist = new ArrayList<ArrayList<StructureData>>();
			
		arrayarraylistlist.add(structureInChunks.get(world).get(new ChunkPos(pos.x+1, pos.z)));
		arrayarraylistlist.add(structureInChunks.get(world).get(new ChunkPos(pos.x-1, pos.z)));
		arrayarraylistlist.add(structureInChunks.get(world).get(new ChunkPos(pos.x, pos.z+1)));
		arrayarraylistlist.add(structureInChunks.get(world).get(new ChunkPos(pos.x, pos.z-1)));
			
		chunkNeighbours:
		for(ArrayList<StructureData> arraylist : arrayarraylistlist)
		{
			if(arraylist == null || arraylist.size() <= 0)
				continue chunkNeighbours;
			else
			{
				neighbourCheck:
				for(StructureData dataToCheck : structuresInChunk)
				{
					for(StructureData dataNeighbour : arraylist)
					{
						if(dataNeighbour == dataToCheck)
						{
							removalList.put(dataToCheck, false);
							continue neighbourCheck;
						}
						else
						{
							removalList.put(dataToCheck, true);
						}
					}
				}
			}
		}
		
		//HashMap<IStructureDataProvider<?>, CompoundNBT> save = new HashMap<IStructureDataProvider<?>, CompoundNBT>();
		//ArrayList<Integer> indices = new ArrayList<Integer>();
		//ArrayList<CompoundNBT> savedNBTFromStructures = new ArrayList<CompoundNBT>();
		
		HashMap<IStructureDataProvider<?>, ArrayList<StructureTulpel>> saveMapping = new HashMap<IStructureDataProvider<?>, ArrayList<StructureTulpel>>();
		
		for(StructureData data : removalList.keySet())
		{
			if(removalList.get(data))
			{
				structureData.get(world).remove(data);
				currentLoadedStructureIDs.get(data.provider).remove(data.id);
				
				StructureTulpel tulpel = new StructureTulpel();
				
				tulpel.index = data.id;
				CompoundNBT structureSavedToDisk = new CompoundNBT();
				data.save(structureSavedToDisk);
				
				tulpel.structureNBT = structureSavedToDisk;
				
				if(!saveMapping.containsKey(data))
				{
					saveMapping.put(data.provider, Lists.newArrayList(tulpel));
				}
				else
				{
					saveMapping.get(data.provider).add(tulpel);
				}
				/*
				CompoundNBT nbt = null;
				
				if(!save.containsKey(data.provider))
				{
					try
					{
						nbt = loadNBTForStructureProvider(((ServerWorld)world).getSaveHandler().getWorldDirectory().getAbsolutePath(), data.provider);
					} 
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}
					
					if(nbt == null)
						nbt = new CompoundNBT();
				}
				else
				{
					nbt = save.get(data.provider);
				}
				
				CompoundNBT dimList = nbt.getCompound(DIM_LIST_KEY);
				
				CompoundNBT currentDim = nbt.getCompound(data.getWorld().dimension.getType().getRegistryName().toString());
				
				Com
				*/
			}
		}
		
		structureInChunks.get(world).remove(pos);
		
		for(IStructureDataProvider<?> provider : saveMapping.keySet())
		{
			CompoundNBT nbt = null; 
			try
			{
				nbt = loadNBTForStructureProvider(((ServerWorld)world).getSaveHandler().getWorldDirectory().getAbsolutePath(), provider);
			} 
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			if(nbt == null)
				nbt = new CompoundNBT();
			
			CompoundNBT dimList = nbt.getCompound(DIM_LIST_KEY);
			
			CompoundNBT currentDim = nbt.getCompound(world.dimension.getType().getRegistryName().toString());
			
			ArrayList<StructureTulpel> structures = saveMapping.get(provider);
			
			ArrayList<Integer> indices = new ArrayList<Integer>();
			
			for(StructureTulpel tulpel : structures)
			{
				indices.add(tulpel.index);
				
				nbt.put(Integer.valueOf(tulpel.index).toString(), tulpel.structureNBT);
			}
			
			currentDim.putIntArray(pos.x + " " + pos.z, indices);
			
			CompoundNBT header = new CompoundNBT();
			
			provider.writeHeader(header);
			
			// Write current dim
			dimList.put(world.dimension.getType().getRegistryName().toString(), currentDim);
			
			// Write dimList
			nbt.put(DIM_LIST_KEY, dimList);
			
			nbt.put(HEADER_KEY, header);
			
			try
			{
				saveNBTForStructureProvider(((ServerWorld)world).getSaveHandler().getWorldDirectory().getAbsolutePath(), provider, nbt);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void onUnloadWorld(SaveHandler saveHandler, World world)
	{
		onSaveEntireWorld(saveHandler, world, true);
		
		structureData.remove(world);
		structureInChunks.remove(world);
	}
	
	public static void onSaveEntireWorld(SaveHandler saveHandler, World world, boolean removeIndicesFromLoadedList)
	{
		HashMap<IStructureDataProvider<?>, HashMap<ChunkPos, ArrayList<Integer>>> idMap = new HashMap<IStructureDataProvider<?>, HashMap<ChunkPos, ArrayList<Integer>>>();
		
		HashMap<IStructureDataProvider<?>, HashMap<Integer, CompoundNBT>> savedStructuresList = new HashMap<IStructureDataProvider<?>, HashMap<Integer,CompoundNBT>>(); 
		
		HashMap<StructureData, Boolean> savedMap = new HashMap<StructureData, Boolean>();
		
		if(!structureInChunks.containsKey(world))
			return;
		
		for(ChunkPos pos : structureInChunks.get(world).keySet())
		{
			ArrayList<StructureData> structuresInChunk = structureInChunks.get(world).get(pos);
			for(StructureData data : structuresInChunk)
			{
				if(!idMap.containsKey(data.provider))
				{
					idMap.put(data.provider, new HashMap<>());
				}
				
				if(!idMap.get(data.provider).containsKey(pos))
					idMap.get(data.provider).put(pos, Lists.newArrayList(data.id));
				else
					idMap.get(data.provider).get(pos).add(data.id);
				
				if(!(savedMap.containsKey(data) || savedMap.get(data)))
				{
					savedMap.put(data, true);
					
					if(!savedStructuresList.containsKey(data.provider))
						savedStructuresList.put(data.provider, new HashMap<>());
					CompoundNBT nbt = new CompoundNBT();
					data.save(nbt);
					
					savedStructuresList.get(data.provider).put(data.id, nbt);
					
					if(removeIndicesFromLoadedList)
						currentLoadedStructureIDs.get(data.provider).remove(data.id);
				}
			}
		}
		
		for(IStructureDataProvider<?> provider : idMap.keySet())
		{			
			CompoundNBT nbt = null;
			
			try
			{
				nbt = loadNBTForStructureProvider(saveHandler.getWorldDirectory().getAbsolutePath().toString(), provider);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
			if(nbt == null)
				nbt = new CompoundNBT();
			
			CompoundNBT dimList = nbt.getCompound(DIM_LIST_KEY);
			
			CompoundNBT currentDim = nbt.getCompound(world.dimension.getType().getRegistryName().toString());
			
			for(int i : savedStructuresList.get(provider).keySet())
			{
				nbt.put(Integer.valueOf(i).toString(), savedStructuresList.get(provider).get(i));
			}
			
			for(ChunkPos pos : idMap.get(provider).keySet())
			{
				currentDim.putIntArray(pos.x + " " + pos.z, idMap.get(provider).get(pos));
			}
			
			CompoundNBT header = new CompoundNBT();
			
			provider.writeHeader(header);
			
			// Write current dim
			dimList.put(world.dimension.getType().getRegistryName().toString(), currentDim);
			
			// Write dimList
			nbt.put(DIM_LIST_KEY, dimList);
			
			nbt.put(HEADER_KEY, header);
			
			try
			{
				saveNBTForStructureProvider(((ServerWorld)world).getSaveHandler().getWorldDirectory().getAbsolutePath(), provider, nbt);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("DONE!");
	}
	
	public static void readStructureFromIndex(CompoundNBT nbtBase, int index, World world, IStructureDataProvider<?> provider)
	{
		StructureData data = provider.getDefault(world);
		
		data.id = index;
		
		if(nbtBase.contains(Integer.valueOf(index).toString()))
			data.read(nbtBase.getCompound(Integer.valueOf(index).toString()));
		else
			System.out.println("No data was saved, so no data could be read. This may be a bug.");
		
		addStructure(world, data);
	}
	
	public static void readChunk(ChunkPos pos, World world)
	{
		for(IStructureDataProvider<?> provider : StructureDataProviderRegistry.getEntries())
		{
			if(existsNBTForStructureProvider(((ServerWorld)world).getSaveHandler().getWorldDirectory().getAbsolutePath(), provider))
			{
				try
				{
					CompoundNBT compound = loadNBTForStructureProvider(((ServerWorld)world).getSaveHandler().getWorldDirectory().getAbsolutePath(), provider);
					
					if(!compound.contains(HEADER_KEY))
						return;
					else
					{
						CompoundNBT dimList = compound.getCompound(DIM_LIST_KEY);
						
						CompoundNBT currentDim = compound.getCompound(world.dimension.getType().getRegistryName().toString());
						
						int[] indicesInChunk = currentDim.getIntArray(pos.x + " " + pos.z);
						
						if(indicesInChunk == null || indicesInChunk.length <= 0)
							return;
						
						indexLoop:
						for(int index : indicesInChunk)
						{
							if(currentLoadedStructureIDs.get(provider).contains(index))
								continue indexLoop;
							
							readStructureFromIndex(compound, index, world, provider);
						}
					}
				} 
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void loadHeaders(String absoluteWorldDirectory)
	{
		for(IStructureDataProvider<?> provider : StructureDataProviderRegistry.getEntries())
		{
			if(existsNBTForStructureProvider(absoluteWorldDirectory, provider))
			{
				try
				{
					CompoundNBT nbt = loadNBTForStructureProvider(absoluteWorldDirectory, provider);
					
					provider.readHeader(nbt);
				} 
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static CompoundNBT loadNBTForStructureProvider(String absoluteWorldDirectory, IStructureDataProvider<?> provider) throws FileNotFoundException, IOException
	{
		CompoundNBT nbt = null;
		
		//Construct file
		File nbtFile = getStructureFile(absoluteWorldDirectory, provider.getRegistryID());
		
		nbt = CompressedStreamTools.readCompressed(new FileInputStream(nbtFile));
		
		return nbt;
	}
	
	public static void saveNBTForStructureProvider(String absoluteWorldDirectory, IStructureDataProvider<?> provider, CompoundNBT nbt) throws FileNotFoundException, IOException
	{
		//Construct file
		File nbtFile = getStructureFile(absoluteWorldDirectory, provider.getRegistryID());
		
		CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(nbtFile));
	}
	
	public static boolean existsNBTForStructureProvider(String absoluteWorldDirectory, IStructureDataProvider<?> provider)
	{
		//Construct file
		File nbtFile = getStructureFile(absoluteWorldDirectory, provider.getRegistryID());
		
		return nbtFile.exists();
	}
	
	public static File getStructureFile(String absoluteWorldDirectory, String fileName)
	{
		return new File(absoluteWorldDirectory + "\\structuresMod\\"+fileName+".structurenbt");
	}
	
	public static class StructureTulpel
	{
		public CompoundNBT structureNBT;
		public int index;
	}
}
