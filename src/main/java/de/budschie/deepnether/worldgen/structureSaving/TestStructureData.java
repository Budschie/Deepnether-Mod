package de.budschie.deepnether.worldgen.structureSaving;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TestStructureData extends StructureData
{

	public TestStructureData(net.minecraft.world.IWorld world, BlockPos pos, AxisAlignedBB aabb, int id, IStructureDataProvider<?> provider)
	{
		super(world, pos, aabb, id, provider);
	}
	
	@Override
	public void save(CompoundNBT compound)
	{
		compound.putString("thisis", "a short test.!!!");
		
		compound.putInt("x", pos.getX());
		compound.putInt("y", pos.getY());
		compound.putInt("z", pos.getZ());
		
		super.save(compound);
	}
	
	@Override
	public void read(CompoundNBT compound)
	{
		System.out.println(compound.getString("thisis"));
		
		pos = new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z"));
	}
}
