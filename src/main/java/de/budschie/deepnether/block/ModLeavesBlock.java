package de.budschie.deepnether.block;

import de.budschie.deepnether.item.ItemInit;
import de.budschie.deepnether.main.References;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.IceBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class ModLeavesBlock extends LeavesBlock
{
	public ModLeavesBlock(Properties props)
	{
		super(props);
	}
	
	@Override
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side)
	{
		return false;
	}
	
	@Override
	public boolean isTransparent(BlockState state)
	{
		return true;
	}
	
	@Override
	public boolean isVariableOpacity()
	{
		return true;
	}
}
