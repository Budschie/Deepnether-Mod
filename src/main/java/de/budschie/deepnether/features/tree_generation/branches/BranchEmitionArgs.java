package de.budschie.deepnether.features.tree_generation.branches;

import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import net.minecraft.util.math.BlockPos;

public class BranchEmitionArgs extends TreeEmitionArgs
{
	private int treeSize;
	private int currentPos;
	
	public BranchEmitionArgs(BlockPos emittedFrom, int treeSize, int currentPos)
	{
		super(emittedFrom);
		this.treeSize = treeSize;
		this.currentPos = currentPos;
	}
	
	public int getTreeSize()
	{
		return treeSize;
	}
	
	public int getCurrentPos()
	{
		return currentPos;
	}
}
