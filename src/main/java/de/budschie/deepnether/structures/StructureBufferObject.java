package de.budschie.deepnether.structures;

import java.util.ArrayList;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class StructureBufferObject 
{
	ArrayList<BlockObject> blocks = new ArrayList<BlockObject>();
	ArrayList<CompoundNBT> tileEntities = new ArrayList<CompoundNBT>();
	
	public StructureBufferObject(ArrayList<BlockObject> blocks, ArrayList<CompoundNBT> tileEntities)
	{
		this.blocks = (ArrayList<BlockObject>) blocks.clone();
		this.tileEntities = (ArrayList<CompoundNBT>) tileEntities.clone();
	}
	
	public ArrayList<BlockObject> getBlocks() {
		return blocks;
	}
	
	public ArrayList<CompoundNBT> getTileEntities() {
		return tileEntities;
	}
}
