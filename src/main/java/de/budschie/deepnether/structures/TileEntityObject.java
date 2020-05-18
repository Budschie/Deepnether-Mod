package de.budschie.deepnether.structures;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityObject 
{
	TileEntity te;
	int x;
	int y;
	int z;
	
	public TileEntityObject(TileEntity entity, int x, int y, int z)
	{
		this.te = entity;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public TileEntityObject(int x, int y, int z, World world)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.te = world.getTileEntity(new BlockPos(x, y, z));
	}
	
	public int getX() 
	{
		return x;
	}
	
	public int getY() 
	{
		return y;
	}
	
	public int getZ() 
	{
		return z;
	}
	
	public TileEntity getTileEntity() 
	{
		return te;
	}
}
