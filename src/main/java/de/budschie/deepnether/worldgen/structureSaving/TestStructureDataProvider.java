package de.budschie.deepnether.worldgen.structureSaving;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IWorld;

public class TestStructureDataProvider implements IStructureDataProvider<TestArguments>
{
	public int id = 0;
	
	@Override
	public StructureData get(TestArguments generatorData)
	{
		return new TestStructureData(generatorData.world, generatorData.pos, new AxisAlignedBB(0, 0, 0, 6, 5, 3), id++, this);
	}

	@Override
	public StructureData getDefault(IWorld world)
	{
		return new TestStructureData(id, this);
	}

	@Override
	public String getRegistryID()
	{
		return "test";
	}

	@Override
	public void writeHeader(CompoundNBT compound)
	{
		compound.putInt("id", id);
	}

	@Override
	public void readHeader(CompoundNBT compound)
	{
		id = compound.getInt("id");
	}
}
