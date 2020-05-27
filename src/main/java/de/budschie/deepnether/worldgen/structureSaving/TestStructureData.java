package de.budschie.deepnether.worldgen.structureSaving;

import java.util.List;

import com.google.common.collect.Lists;

import de.budschie.deepnether.entity.EntityInit;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome.SpawnListEntry;

public class TestStructureData extends StructureData implements IHasSpawnList
{

	public TestStructureData(net.minecraft.world.IWorld world, BlockPos pos, AxisAlignedBB aabb, int id, IStructureDataProvider<?> provider)
	{
		super(world, pos, aabb, id, provider);
		if(id == 824)
			System.out.println("FOUND!!!!!");
	}
	
	/** Should only be used when loading structures **/
	public TestStructureData(int id, IStructureDataProvider<?> provider)
	{
		super(id, provider);
	}
	
	@Override
	public void save(CompoundNBT compound)
	{
		compound.putInt("x", pos.getX());
		compound.putInt("y", pos.getY());
		compound.putInt("z", pos.getZ());
		
		compound.putInt("x1", (int) aabb.minX);
		compound.putInt("y1", (int) aabb.minY);
		compound.putInt("z1", (int) aabb.minZ);
		compound.putInt("x2", (int) aabb.maxX);
		compound.putInt("y2", (int) aabb.maxY);
		compound.putInt("z2", (int) aabb.maxZ);
		
		super.save(compound);
	}
	
	@Override
	public void read(CompoundNBT compound)
	{
		System.out.println(compound.getString("thisis"));
		
		pos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
		aabb = new AxisAlignedBB(compound.getInt("x1"), compound.getInt("y1"), compound.getInt("z1"), compound.getInt("x2"), compound.getInt("y2"), compound.getInt("z2"));
		
		super.read(compound);
	}

	@Override
	public boolean replaceOldList()
	{
		return true;
	}

	@Override
	public List<SpawnListEntry> getSpawnables()
	{
		return Lists.newArrayList(new SpawnListEntry(EntityInit.HELL_DEVIL, 1, 1, 1));
	}
}
