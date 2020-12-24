package de.budschie.deepnether.structures;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockObject 
{
	private BlockState blockState;
	private BlockPos pos;
	
	public BlockObject(BlockState state, BlockPos blockPos)
	{
		this.blockState = state;
		this.pos = blockPos;
	}
	
	public BlockState getBlock()
	{
		return blockState;
	}
	
	public BlockPos getPos()
	{
		return pos;
	}
	
	public BlockPos getBlockPosWithOffset(int x, int y, int z)
	{
		return new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
	}
	
	public BlockPos getBlockPosWithOffset(BlockPos blockPos)
	{
		return new BlockPos(pos.getX() + blockPos.getX(), pos.getY() + blockPos.getY(), pos.getZ() + blockPos.getZ());
	}
}
