package de.budschie.deepnether.features;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

public class WitheredTreeFeature extends Feature<WitheredTreeFeatureConfig>
{
	public WitheredTreeFeature()
	{
		super(WitheredTreeFeatureConfig.CODEC);
	}

	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, WitheredTreeFeatureConfig config)
	{
		int size = rand.nextInt(4) + 12;
		
		BlockState logUp = config.getLogUp().getBlockState(rand, pos);
		BlockState rotatedX = config.getRotatedX().getBlockState(rand, pos);
		BlockState rotatedZ = config.getRotatedZ().getBlockState(rand, pos);
		
		for(int i = 0; i < size; i++)
		{
			reader.setBlockState(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), logUp, i);
			
			if(rand.nextInt(3) == 0)
			{
				int branchLength = rand.nextInt(3) + 3;
				
				BlockPos currentPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
				
				BranchDirection currentBranchDirection = BranchDirection.values()[rand.nextInt(BranchDirection.values().length)];
				BlockState currentBlockState = getCurrentBlockState(currentBranchDirection.axis, logUp, rotatedX, rotatedZ);
				
				for(int j = 0; j < branchLength; j++)
				{
					currentPos = currentPos.add(currentBranchDirection.getToAdd());
					
					// We change the block state afterwards, causing the log block to look up before he actually goes up. This is all intentional.
					if(rand.nextInt(3) == 0)
					{
						currentBranchDirection = BranchDirection.values()[rand.nextInt(BranchDirection.values().length)];
						currentBlockState = getCurrentBlockState(currentBranchDirection.axis, logUp, rotatedX, rotatedZ);
					}
					
					reader.setBlockState(currentPos, currentBlockState, 0);
				}
			}
		}
		
		return false;
	}
	
	private BlockState getCurrentBlockState(Direction.Axis axis, BlockState logUp, BlockState rotatedX, BlockState rotatedZ)
	{
		switch (axis)
		{
		case X:
			return rotatedX;
		case Y:
			return logUp;
		default:
			return rotatedZ;
		}
	}
	
	public static enum BranchDirection
	{
		POSITIVE_X(Direction.Axis.X, new BlockPos(1, 0, 0)), NEGATIVE_X(Direction.Axis.X, new BlockPos(-1, 0, 0)), POSITIVE_Y(Direction.Axis.Y, new BlockPos(0, 1, 0)), NEGATIVE_Y(Direction.Axis.Y, new BlockPos(0, -1, 0)), 
		POSITIVE_Z(Direction.Axis.Z, new BlockPos(0, 0, 1)), NEGATIVE_Z(Direction.Axis.Z, new BlockPos(0, 0, -1));
		
		private BlockPos toAdd;
		private Direction.Axis axis;
		
		BranchDirection(Direction.Axis axis, BlockPos toAdd)
		{
			this.axis = axis;
			this.toAdd = toAdd;
		}
		
		public BlockPos getToAdd()
		{
			return toAdd;
		}
	}
}
