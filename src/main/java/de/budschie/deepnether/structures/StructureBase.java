package de.budschie.deepnether.structures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.system.windows.DEVMODE;

import com.google.common.collect.Lists;

import de.budschie.deepnether.util.DebugUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public abstract class StructureBase 
{
	//NBTTagCompound data;
	String fileName;
	StructureMode mode;
	
	
	
	protected short length;
	protected short height;
	protected short width;
	
	protected byte range;
	
	protected byte version;
	
	boolean loaded = false;
	
	ArrayList<BlockObject> preLoadedBlocks = new ArrayList<BlockObject>();
	
	public static final ArrayList<Block> problematicBlocks = Lists.newArrayList(Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.BIRCH_DOOR, Blocks.IRON_DOOR, Blocks.JUNGLE_DOOR, Blocks.OAK_DOOR);
	
	/** World and pos can be null if this should be pre loaded **/
	public void readData(String fileName, World world, BlockPos pos)
	{
		try
		{
			//InputStream stream = StructureBase.class.getResourceAsStream(StructureConst.BASE_PATH + "/" + fileName);
			/*
			InputStream stream = StructureBase.class.getResourceAsStream(fileName);
			NBTTagCompound compound = CompressedStreamTools.readCompressed(stream);
			stream.close();
			*/
			File file = new File(StructureConst.BASE_PATH + "\\" + fileName + ".structure");
			
			//NBTTagCompound compound = CompressedStreamTools.read(file);
			CompoundNBT compound = CompressedStreamTools.readCompressed(new FileInputStream(file));
			
			length = compound.getShort(StructureConst.KEY_LENGTH);
			height = compound.getShort(StructureConst.KEY_HEIGHT);
			width = compound.getShort(StructureConst.KEY_WIDTH);
			
			System.out.println("L: " + length + " H: " + height + " W: " + width);
			
			version = compound.getByte(StructureConst.VERSION);
			
			//CompressedStreamTools.read
			
				ArrayList<BlockObject> blocks = read(compound, world, pos);
				//ArrayList<BlockObject> problematicBlocks = new ArrayList<BlockObject>();
				for(BlockObject block : blocks)
				{
					//System.out.println("Found block with state: " + block.getBlock());
					if(block.getBlock() == Blocks.STRUCTURE_VOID.getDefaultState())
					{
						//continue;
					}
					else
					{
							world.setBlockState(block.getBlockPosWithOffset(pos), block.getBlock(), 2);
							//world.setBlockState(pos, state)
						
					}
				}
				
				int posTE = 0;
				
				boolean isSetTE = false;
				
				if(compound.contains("tileentities"))
				{
					CompoundNBT compoundTEBig = compound.getCompound("tileentities");
					int amount = compoundTEBig.getInt("numbTE");
					for(int i = 0; i < amount; i++)
					{
						CompoundNBT compoundTE = compoundTEBig.getCompound("te_" + i);
						
						
						BlockPos tileEntityBlockPos = new BlockPos(compoundTE.getShort("x"), compoundTE.getShort("y"), compoundTE.getShort("z")).add(pos);
						
						
						compoundTE.remove("x");
						compoundTE.remove("y");
						compoundTE.remove("z");
						
						String ID = compoundTE.getString("id");
						world.setTileEntity(tileEntityBlockPos, TileEntity.readTileEntity(world.getBlockState(tileEntityBlockPos), compoundTE));
						
						/*
						if(te instanceof IUpdatable)
						{
							((IUpdatable)te).onLoadedAsStructure(world);
						}
						*/
					}
				}

			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public StructureBufferObject loadIntoMemory(String name) throws FileNotFoundException
	{
		StructureBufferObject object = null;
		File file = new File(StructureConst.BASE_PATH + "\\" + name + ".structure");
		
		if(!file.exists())
			throw new FileNotFoundException("The file " + file.getAbsolutePath() + " doesn't exist.");
		
	   // ArrayList<BlockObject> util;
		//NBTTagCompound compound = CompressedStreamTools.read(file);
		CompoundNBT compound = null;
		try {
			compound = CompressedStreamTools.readCompressed(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		length = compound.getShort(StructureConst.KEY_LENGTH);
		height = compound.getShort(StructureConst.KEY_HEIGHT);
		width = compound.getShort(StructureConst.KEY_WIDTH);
		
		System.out.println("L: " + length + " H: " + height + " W: " + width);
		
		version = compound.getByte(StructureConst.VERSION);
		
		//preLoadedBlocks = read(compound, null, null);
		
		//util = read(compound, null, null);
		
		ArrayList<BlockObject> blocks = read(compound, null, null);
		ArrayList<CompoundNBT> tileEntities = new ArrayList<CompoundNBT>();
		
		int posTE = 0;
		
		boolean isSetTE = false;
		
		if(compound.contains("tileentities"))
		{
			CompoundNBT compoundTEBig = compound.getCompound("tileentities");
			int amount = compoundTEBig.getInt("numbTE");
			for(int i = 0; i < amount; i++)
			{
				CompoundNBT compoundTE = compoundTEBig.getCompound("te_" + i);
				
				
				//BlockPos tileEntityBlockPos = new BlockPos(compoundTE.getShort("x"), compoundTE.getShort("y"), compoundTE.getShort("z")).add(pos);
				
				/*
				compoundTE.removeTag("x");
				compoundTE.removeTag("y");
				compoundTE.removeTag("z");
				*/
				
				String ID = compoundTE.getString("id");
				//world.setTileEntity(tileEntityBlockPos, TileEntity.create(world, compoundTE));
				tileEntities.add(compoundTE);
			}
		}
		object = new StructureBufferObject(blocks, tileEntities);
		
		return object;
	}
	
	public static void place(StructureBufferObject object, BlockPos pos, World world)
	{
		ArrayList<BlockObject> blocks = object.blocks;
		ArrayList<CompoundNBT> te = object.tileEntities;
		
		//System.out.println("There are " + blocks.size() + " blocks and " + te.size() + " tile entities in the current building.");
		
		//BlockPos pos = new BlockPos(x,y,z);
		for(BlockObject block : blocks)
		{
			//System.out.println("Found block with state: " + block.getBlock());
			if(block.getBlock() != Blocks.STRUCTURE_VOID.getDefaultState())
			{
				world.setBlockState(block.getBlockPosWithOffset(pos), block.getBlock(), 2);
			}
		}
		
		if(te != null)
		{
			for(CompoundNBT compound : te)
			{
				BlockPos tileEntityBlockPos = new BlockPos(compound.getShort("x"), compound.getShort("y"), compound.getShort("z"));
				tileEntityBlockPos = tileEntityBlockPos.add(pos);
				
				TileEntity teInner = world.getTileEntity(tileEntityBlockPos);
				
				if(teInner == null)
				{
					world.setBlockState(tileEntityBlockPos, Blocks.AIR.getDefaultState(), 2);
				}
				else
				{
					teInner.read(null, compound);
					teInner.setPos(tileEntityBlockPos);
					teInner.markDirty();
				}
			}
		}
	}
	
	public static void place(StructureBufferObject object, BlockPos pos, IWorld world)
	{
		ArrayList<BlockObject> blocks = object.blocks;
		ArrayList<CompoundNBT> te = object.tileEntities;
		
		//System.out.println("There are " + blocks.size() + " blocks and " + te.size() + " tile entities in the current building.");
		
		//BlockPos pos = new BlockPos(x,y,z);
		for(final BlockObject block : blocks)
		{
			//System.out.println("Found block with state: " + block.getBlock());
			if(block.getBlock() != Blocks.STRUCTURE_VOID.getDefaultState())
			{
				world.setBlockState(block.getPos().add(pos), block.getBlock(), 2);
			}
		}
		
		if(te != null)
		{
			for(CompoundNBT compound : te)
			{
				BlockPos tileEntityBlockPos = new BlockPos(compound.getShort("x"), compound.getShort("y"), compound.getShort("z"));
				tileEntityBlockPos = tileEntityBlockPos.add(pos);
				
				TileEntity teInner = world.getTileEntity(tileEntityBlockPos);
				
				if(teInner == null)
				{
					world.setBlockState(tileEntityBlockPos, Blocks.AIR.getDefaultState(), 2);
				}
				else
				{
					teInner.read(null, compound);
					teInner.setPos(tileEntityBlockPos);
					teInner.markDirty();
				}
			}
		}
	}
	
	public void writeData(String fileName, ArrayList<BlockObject> blocks, int width, int height, int length, BlockPos pos, World world)
	{
		this.width = (short) width;
		this.height = (short) height;
		this.length = (short) length;
		
		WrittenData compound = write(blocks);
		int posTE = 0;
		
		boolean isSetTE = false;
		
		CompoundNBT compoundTEBig = new CompoundNBT();
		
		for(BlockObject object : blocks)
		{
			if(object.getBlock().getBlock().hasTileEntity(object.getBlock()))
			{
				
					if(!isSetTE)
					{
						isSetTE = true;
						//compound.data.setTag("tileentities", compoundTEBig);
					}
					
					
					TileEntity te = world.getTileEntity(object.getPos());
					

					
					short posStX = (short) object.getPos().getX();
					short posStY = (short) object.getPos().getY();
					short posStZ = (short) object.getPos().getZ();
					
					if(te == null)
					{
						System.out.println("Tile entity was null at " + posStX + " " + posStY + " " + posStZ);
					}
					
					CompoundNBT compoundTE = te.serializeNBT().copy();
					compoundTE.remove("x");
					compoundTE.remove("y");
					compoundTE.remove("z");
					
					compoundTE.putShort("x", (short) (posStX - pos.getX()));
					compoundTE.putShort("y", (short) (posStY - pos.getY()));
					compoundTE.putShort("z", (short) (posStZ - pos.getZ()));
					
					compoundTEBig.put("te_" + posTE, compoundTE);
					
					posTE++;
				
			}
		}
		
		if(isSetTE)
		{
			compoundTEBig.putInt("numbTE", (posTE));
			compound.data.put("tileentities", compoundTEBig);
		}

		//OutputStream.class.
		try
		{
			File file = new File("C:\\Users\\Budschie\\Desktop\\OutputData" + "\\" + fileName + ".structure");
			file.createNewFile();
			//file.
			FileOutputStream stream = new FileOutputStream(file.getAbsoluteFile());
			
			CompressedStreamTools.writeCompressed(compound.data, stream);
			
			stream.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	protected abstract ArrayList<BlockObject> read(CompoundNBT compound, World world, BlockPos pos);
	
	protected abstract WrittenData write(ArrayList<BlockObject> blocks);
	
	public StructureMode getMode()
	{
		return mode;
	}
}

enum StructureMode
{
	READ, WRITE
}
