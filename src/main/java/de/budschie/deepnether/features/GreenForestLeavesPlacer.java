package de.budschie.deepnether.features;

import java.awt.geom.Ellipse2D;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class GreenForestLeavesPlacer implements ILeavesPlacer<BasicLeavesLogFeatureConfig>
{

	@Override
	public void placeBranchLeaves(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			int currentBranchLength, int maxBranchLength, BasicLeavesLogFeatureConfig config)
	{
	}

	@Override
	public void placeTrunkLeaves(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos,
			int currentTrunkHeight, int maxTrunkHeight, BasicLeavesLogFeatureConfig config)
	{
		int maxWidth = (maxTrunkHeight - rand.nextInt(4));
		float inverseProgress = 1 - (currentTrunkHeight / (float)maxTrunkHeight);
		int currentWidth = (int) (maxWidth * inverseProgress);
		
		Ellipse2D.Float floatEllipse = new Ellipse2D.Float(currentWidth, currentWidth, currentWidth, currentWidth);
		
		for(int x = -currentWidth; x < currentWidth; x++)
		{
			for(int z = -currentWidth; z < currentWidth; z++)
			{
				if(floatEllipse.contains(x, z))
				{
					reader.setBlockState(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z), config.getLeavesBlockStateProvider().getBlockState(rand, pos), 2);
				}
			}
		}
	}	
}
