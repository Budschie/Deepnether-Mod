package de.budschie.deepnether.worldgen.structureSaving;

import java.util.ArrayList;

import de.budschie.deepnether.main.DeepnetherMain;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class StructureData
{
	protected World world;
	protected BlockPos pos;
	protected AxisAlignedBB aabb;
	protected int id;
	IStructureDataProvider<?> provider;
	
	public StructureData(World world, BlockPos pos, AxisAlignedBB aabb, int id, IStructureDataProvider<?> provider)
	{
		this.world = world;
		this.pos = pos;
		this.aabb = aabb;
		this.id = id;
		this.provider = provider;
	}
	
	/** Should only be used when loading structures **/
	public StructureData(int id, IStructureDataProvider<?> provider)
	{
		this.id = id;
		this.provider = provider;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public AxisAlignedBB getAABBBase()
	{
		return aabb;
	}
	
	public AxisAlignedBB getTranslatedAABB()
	{
		return aabb.offset(getPos());
	}
	
	/** Called when the structure is going to be saved on the disk. Please call this via <b>{@code super.save}</b>; there is some critical stuff here **/
	public void save(CompoundNBT compound)
	{
		compound.putInt("internal_id", id);
		compound.putString("world", this.world.getDimension().getType().getRegistryName().toString());
	}
	
	/** Called when the structure will read from the disk **/
	public void read(CompoundNBT compound) 
	{
		this.world = DeepnetherMain.server.getWorld(DimensionType.byName(new ResourceLocation(compound.getString("world"))));
	}
	
	public ArrayList<ChunkPos> getContainingChunks()
	{
		ArrayList<ChunkPos> positions = new ArrayList<ChunkPos>();
		
		AxisAlignedBB translated = getTranslatedAABB();
		
		for(int x = (int) translated.minX; x <= translated.maxX; x++)
		{
			for(int y = (int) translated.minY; y <= translated.maxY; y++)
			{
				for(int z = (int) translated.minZ; z <= translated.maxZ; z++)
				{
					positions.add(new ChunkPos(new BlockPos(x, 0, z)));
				}
			}
		}
		
		return positions;
	}
}
