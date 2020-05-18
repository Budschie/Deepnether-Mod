package de.budschie.deepnether.structures;

import net.minecraft.util.math.BlockPos;

public class BlockPosHelper
{
	public static BlockPos[] sortPos(BlockPos pos1, BlockPos pos2)
	{
		BlockPos[] ret = new BlockPos[2];
		
		ret[0] = new BlockPos(Math.min(pos1.getX(), pos2.getX()), Math.min(pos1.getY(), pos2.getY()), Math.min(pos1.getZ(), pos2.getZ()));
		ret[1] = new BlockPos(Math.max(pos1.getX(), pos2.getX()), Math.max(pos1.getY(), pos2.getY()), Math.max(pos1.getZ(), pos2.getZ()));
		
		return ret;
	}
}
