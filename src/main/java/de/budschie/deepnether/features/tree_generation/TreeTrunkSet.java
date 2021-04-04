package de.budschie.deepnether.features.tree_generation;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;

public class TreeTrunkSet
{
	private BlockState logUp, logX, logZ;
	
	public TreeTrunkSet(BlockState up, BlockState logX, BlockState logZ)
	{
		this.logUp = up;
		this.logX = logX;
		this.logZ = logZ;
	}

	public BlockState getLogUp()
	{
		return logUp;
	}
	
	public BlockState getLogX()
	{
		return logX;
	}
	
	public BlockState getLogZ()
	{
		return logZ;
	}
	
	public BlockState getCurrentBlockState(Direction.Axis axis)
	{
		switch (axis)
		{
		case X:
			return logX;
		case Y:
			return logUp;
		default:
			return logZ;
		}
	}
}
