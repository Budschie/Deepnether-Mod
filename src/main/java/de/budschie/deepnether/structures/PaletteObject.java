package de.budschie.deepnether.structures;

import net.minecraft.block.BlockState;

public class PaletteObject
{
	BlockState state;
	
	public PaletteObject(BlockState state)
	{
		this.state = state;
	}
	
	public BlockState getState() {
		return state;
	}
	
	public boolean compareWithBlockState(BlockState state)
	{
		return state == this.state;
	}
}
