package de.budschie.deepnether.block;

import java.util.ArrayList;

import de.budschie.deepnether.util.ModItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class NetherCrystalBlockBase extends ModGlassBaseBlock
{
	public static ArrayList<NetherCrystalBlockBase> NETHER_CRYSTALS = new ArrayList<NetherCrystalBlockBase>();
	
	String color = "";
	
	public NetherCrystalBlockBase(String color) 
	{
		super(ModProperties.MOD_CRYSTAL, "nether_crystal_" + color, ModItemGroups.MOD_BLOCKS);
		NETHER_CRYSTALS.add(this);
		this.color = color;
	}
	
	public String getColor() 
	{
		return color;
	}
	
	@Override
	public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) 
	{
		return false;
	}
	
	//Normal cube = false
}
