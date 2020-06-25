package de.budschie.deepnether.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

public class DebugUtils
{
	public static String getShortBlockPosAsString(BlockPos pos)
	{
		return pos.getX() + " " + pos.getY() + " " + pos.getZ();
	}
	
	public static String getLongBlockPosAsString(BlockPos pos)
	{
		return "X: " + pos.getX() + "; Y: " + pos.getY() + "; Z: " + pos.getZ();
	}
	
	public static String printComponentsofNBT(CompoundNBT nbt)
	{
		String str = "";
		for(String key : nbt.keySet())
		{
			str += key + "; ";
		}
		
		return str;
	}
}
