package de.budschie.deepnether.worldgen.structureSaving;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.Lists;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.SaveHandler;

public class StructureDataHandler
{
	
	private static HashMap<DimensionType, ArrayList<StructureData>> structureData = new HashMap<>();
	private static HashMap<DimensionType, HashMap<ChunkPos, ArrayList<StructureData>>> structureInChunks = new HashMap<>();
	//private static ArrayList<Integer> currentLoadedStructureIDs = new ArrayList<Integer>();
	private static HashMap<IStructureDataProvider<?>, ArrayList<Integer>> currentLoadedStructureIDs = new HashMap<IStructureDataProvider<?>, ArrayList<Integer>>();
	
	public static final String HEADER_KEY = "Header";
	public static final String CURRENT_ID_KEY = "currentID";
	
	public static final String DIM_LIST_KEY = "worlds";
	
	public static void addStructure(IWorld world, StructureData structure)
	{
		System.out.println("Added structure with id " + structure.id + ".");
		
		if(!structureData.containsKey(world.getDimension().getType()))
			structureData.put(world.getDimension().getType(), Lists.newArrayList(structure));
		else
			structureData.get(world.getDimension().getType()).add(structure);
		
		if(!currentLoadedStructureIDs.containsKey(structure.provider))
			currentLoadedStructureIDs.put(structure.provider, Lists.newArrayList(structure.id));
		else
		{
			if(currentLoadedStructureIDs.get(structure.provider).contains(structure.id)) System.out.println("Structure added twice with id: " + structure.id + ".");
			else
			currentLoadedStructureIDs.get(structure.provider).add(structure.id);
		}
		
		remapChunkForStructure(structure);
	}
	
	public static Optional<StructureData> getStructureByID(int id, IStructureDataProvider<?> provider)
	{
		for(DimensionType type : structureData.keySet())
		{
			ArrayList<StructureData> data = structureData.get(type);
			for(StructureData dataObject : data)
			{
				if(dataObject.id == id && provider.getRegistryID().equals(provider.getRegistryID()))
				return Optional.of(dataObject);
			}
		}
		
		return Optional.empty();
	}
	
	static ArrayList<StructureData> getStructures(IWorld world)
	{
		ArrayList<StructureData> data = structureData.get(world.getDimension().getType());
		
		return data == null ? null : Lists.newArrayList(data);
	}
	
	/** Maybe not thread-safe! **/
	public static StructureData getStructureAtPosition(BlockPos pos, IWorld world)
	{
		HashMap<ChunkPos, ArrayList<StructureData>> map = structureInChunks.get(world.getDimension().getType());
		if(map != null)
		{
			ArrayList<StructureData> datas = map.get(new ChunkPos(pos));
			if(datas != null)
			{
				for(StructureData strData : datas)
				{
					if(strData.getTranslatedAABB().contains(new Vec3d(pos)))
						return strData;
				}
			}
		}
		
		return null;
	}
	
	/** Maybe not thread-safe! **/
	public static StructureData getStructureAtPosition(BlockPos pos, IWorld world, Predicate<StructureData> selector)
	{
		HashMap<ChunkPos, ArrayList<StructureData>> map = structureInChunks.get(world.getDimension().getType());
		if(map != null)
		{
			ArrayList<StructureData> datas = new ArrayList<StructureData>();
			if(!map.containsKey(new ChunkPos(pos)))
				return null;
			datas.addAll(map.get(new ChunkPos(pos)));
			if(datas != null)
			{
				for(StructureData strData : datas)
				{
					if(strData.aabb != null && strData.aabb.contains(new Vec3d(pos)) && selector.test(strData))
						return strData;
				}
			}
		}
		
		return null;
	}
	
	private static void remapChunkForStructure(StructureData structure)
	{
		// Iterate over every chunk
		ArrayList<ChunkPos> positions = structure.getContainingChunks();
		
		if(!structureInChunks.containsKey(structure.getWorld().getDimension().getType()))
			structureInChunks.put(structure.getWorld().getDimension().getType(), new HashMap<>());
		
		HashMap<ChunkPos, ArrayList<StructureData>> hashMap = structureInChunks.get(structure.getWorld().getDimension().getType());
		
		if(hashMap == null)
		{
			hashMap = new HashMap<>();
			structureInChunks.put(structure.getWorld().getDimension().getType(), hashMap);
		}
		
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
	
	public static void removeChunk(ChunkPos pos, IWorld world)
	{
		int amountStructuresUnloaded = 0;
		if(!structureInChunks.containsKey(world.getDimension().getType()))
			return;
		if(!structureInChunks.get(world.getDimension().getType()).containsKey(pos))
			return;
		
		HashMap<StructureData, Boolean> removalList = new HashMap<StructureData, Boolean>();
		
		ArrayList<StructureData> structuresInChunk = structureInChunks.get(world.getDimension().getType()).remove(pos);
		
		
		/*
		ArrayList<ArrayList<StructureData>> arrayarraylistlist = new ArrayList<ArrayList<StructureData>>();
		
		if(world.chunkExists(pos.x+1, pos.z))
			arrayarraylistlist.add(structureInChunks.get(world.getDimension().getType()).get(new ChunkPos(pos.x+1, pos.z)));
		
		if(world.chunkExists(pos.x-1, pos.z))
			arrayarraylistlist.add(structureInChunks.get(world.getDimension().getType()).get(new ChunkPos(pos.x-1, pos.z)));
		
		if(world.chunkExists(pos.x, pos.z+1))
			arrayarraylistlist.add(structureInChunks.get(world.getDimension().getType()).get(new ChunkPos(pos.x, pos.z+1)));
		
		if(world.chunkExists(pos.x, pos.z-1))
			arrayarraylistlist.add(structureInChunks.get(world.getDimension().getType()).get(new ChunkPos(pos.x, pos.z-1)));
			
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
							if()
							removalList.put(dataToCheck, true);
						}
					}
				}
			}
		}
		*/
		
		structureCheck:
		for(StructureData data : structuresInChunk)
		{
			ArrayList<ChunkPos> containingChunks = data.getContainingChunks();
			
			boolean isLoaded = false;
			
			chunkCheck:
			for(ChunkPos currentContainingPos : containingChunks)
			{
				if(currentContainingPos.x == pos.x && currentContainingPos.z == pos.z)
					continue chunkCheck;
				if(world.chunkExists(currentContainingPos.x, currentContainingPos.z))
				{
					isLoaded = true;
					break chunkCheck;
				}
			}
			
			removalList.put(data, !isLoaded);
		}
		
		//HashMap<IStructureDataProvider<?>, CompoundNBT> save = new HashMap<IStructureDataProvider<?>, CompoundNBT>();
		//ArrayList<Integer> indices = new ArrayList<Integer>();
		//ArrayList<CompoundNBT> savedNBTFromStructures = new ArrayList<CompoundNBT>();
		
		HashMap<IStructureDataProvider<?>, ArrayList<StructureTulpel>> saveMapping = new HashMap<IStructureDataProvider<?>, ArrayList<StructureTulpel>>();
		
		for(StructureData data : removalList.keySet())
		{
			if(removalList.get(data))
			{
				System.out.println("Removed structure!");
				structureData.get(world.getDimension().getType()).remove(data);
				currentLoadedStructureIDs.get(data.provider).remove((Object)data.id);
				
				StructureTulpel tulpel = new StructureTulpel();
				
				tulpel.index = data.id;
				CompoundNBT structureSavedToDisk = new CompoundNBT();
				data.save(structureSavedToDisk);
				
				tulpel.structureNBT = structureSavedToDisk;
				
				if(!saveMapping.containsKey(data.provider))
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
		
		if(saveMapping.isEmpty())
			return;
		
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
			
			CompoundNBT currentDim = dimList.getCompound(world.getDimension().getType().getRegistryName().toString());
			
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
			dimList.put(world.getDimension().getType().getRegistryName().toString(), currentDim);
			
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
			
			System.out.println("Saved " + structures.size() + " structures for provider " + provider.getClass().getName() + ".");
			amountStructuresUnloaded += structures.size();
		}
		
		System.out.println("Saved " + amountStructuresUnloaded + " structures.");
	}
	
	public static void onUnloadWorld(SaveHandler saveHandler, World world)
	{
		onSaveEntireWorld(saveHandler, world, true);
	}
	
	public static boolean shouldSave(IWorld world)
	{
		return structureData.containsKey(world.getDimension().getType()) && structureInChunks.containsKey(world.getDimension().getType());
	}
	
	public static void onSaveEntireWorld(SaveHandler saveHandler, IWorld world, boolean shouldUnload)
	{
		if(!shouldSave(world))
			return;
		
		for(IStructureDataProvider<?> provider : currentLoadedStructureIDs.keySet())
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
			
			CompoundNBT currentDim = dimList.getCompound(world.getDimension().getType().getRegistryName().toString());
			
			CompoundNBT header = new CompoundNBT();
			
			provider.writeHeader(header);
			
			chunk:
			for(ChunkPos pos : structureInChunks.get(world.getDimension().getType()).keySet())
			{
				ArrayList<StructureData> data = structureInChunks.get(world.getDimension().getType()).get(pos);
				if(data == null)
					continue chunk;
				ArrayList<Integer> indices = new ArrayList<>();
				
				for(int i = 0; i < data.size(); i++)
				{
					if(data.get(i).provider == provider)
						indices.add(data.get(i).id);
				}
				
				currentDim.putIntArray(pos.x + " " + pos.z, indices);
			}
			
			loop:
			for(StructureData data : structureData.get(world.getDimension().getType()))
			{
				if(data.provider != provider)
					continue loop;
				
				CompoundNBT dataToSave = new CompoundNBT();
				data.save(dataToSave);
				nbt.put(Integer.valueOf(data.id).toString(), dataToSave);
			}
			
			if(shouldUnload)
			{
				currentLoadedStructureIDs.remove(provider);
			}
			
			// Write current dim
			dimList.put(world.getDimension().getType().getRegistryName().toString(), currentDim);
			
			// Write dimList
			nbt.put(DIM_LIST_KEY, dimList);
			
			nbt.put(HEADER_KEY, header);
			
			try
			{
				saveNBTForStructureProvider(saveHandler.getWorldDirectory().getAbsolutePath(), provider, nbt);
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}
		
		if(shouldUnload)
		{
			structureData.remove(world.getDimension().getType());
			structureInChunks.remove(world.getDimension().getType());
		}
	}
	
	public static void readStructureFromIndex(CompoundNBT nbtBase, int index, IWorld world, IStructureDataProvider<?> provider)
	{
		StructureData data = provider.getDefault(world);
		
		data.id = index;
		
		if(nbtBase.contains(Integer.valueOf(index).toString()))
			data.read(nbtBase.getCompound(Integer.valueOf(index).toString()));
		else
			System.out.println("No data was saved, so no data could be read. This may be a bug.");
		
		addStructure(world, data);
	}
	
	public static void readChunk(ChunkPos pos, IWorld world)
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
						
						CompoundNBT currentDim = dimList.getCompound(world.getDimension().getType().getRegistryName().toString());
						
						int[] indicesInChunk = currentDim.getIntArray(pos.x + " " + pos.z);
						
						if(indicesInChunk == null || indicesInChunk.length <= 0)
							return;
						
						indexLoop:
						for(int index : indicesInChunk)
						{
							if(currentLoadedStructureIDs.containsKey(provider) && currentLoadedStructureIDs.get(provider).contains(index))
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
					
					provider.readHeader(nbt.getCompound(HEADER_KEY));
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
		System.out.println("Saved");
	}
	
	public static boolean existsNBTForStructureProvider(String absoluteWorldDirectory, IStructureDataProvider<?> provider)
	{
		//Construct file
		File nbtFile = getStructureFile(absoluteWorldDirectory, provider.getRegistryID());
		
		return nbtFile.exists();
	}
	
	public static File getStructureFile(String absoluteWorldDirectory, String fileName)
	{
		File pathToNBT = new File(absoluteWorldDirectory + "\\structuresMod");
		
		if(!pathToNBT.exists())
		{
			pathToNBT.mkdirs();
		}
		
		File strFile = new File(absoluteWorldDirectory + "\\structuresMod\\"+fileName+".structurenbt");
		
		return strFile;
	}
	
	public static class StructureTulpel
	{
		public CompoundNBT structureNBT;
		public int index;
	}
}
