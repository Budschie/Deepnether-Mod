package de.budschie.deepnether.features.tree_generation.leaves;

import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import net.minecraft.util.math.BlockPos;

public class LeavesEmitionArgs extends TreeEmitionArgs
{
	private int currentTreeHeight;
	private int fullTreeSize;
	
	public LeavesEmitionArgs(BlockPos emittedFrom, int currentTreeHeight, int fullTreeSize)
	{
		super(emittedFrom);
		
		this.currentTreeHeight = currentTreeHeight;
		this.fullTreeSize = fullTreeSize;
	}
	
	public int getCurrentTreeHeight()
	{
		return currentTreeHeight;
	}
	
	public int getFullTreeSize()
	{
		return fullTreeSize;
	}
}
