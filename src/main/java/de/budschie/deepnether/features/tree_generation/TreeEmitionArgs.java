package de.budschie.deepnether.features.tree_generation;

import net.minecraft.util.math.BlockPos;

public class TreeEmitionArgs
{
	private BlockPos emittedFrom;
	
	public TreeEmitionArgs(BlockPos emittedFrom)
	{
		this.emittedFrom = emittedFrom;
	}
	
	public BlockPos getEmittedFrom()
	{
		return emittedFrom;
	}
}
