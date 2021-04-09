package de.budschie.deepnether.features;

import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BambooFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;

public class WitheredTreeFeature extends Feature<BasicLogFeatureConfig>
{
	public WitheredTreeFeature()
	{
		super(BasicLogFeatureConfig.CODEC);
	}
	
	// Branches are much shorter than expected. Is this maybe a bug?
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BasicLogFeatureConfig config)
	{		
		if(pos.getY() < 0)
			return false;
		
		int size = rand.nextInt(4) + 8;
		
		BlockState logUp = config.getLogUp().getBlockState(rand, pos);
		BlockState rotatedX = config.getRotatedX().getBlockState(rand, pos);
		BlockState rotatedZ = config.getRotatedZ().getBlockState(rand, pos);
		
		for(int i = 0; i < size; i++)
		{
			reader.setBlockState(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), logUp, 2);
			
			if(rand.nextInt(1) == 0 && i > 4)
			{
				int randDir = rand.nextInt(BranchDirection.values().length);
				//final BranchDirection initialBranchDir = randDir < 2 ? BranchDirection.values()[randDir] : BranchDirection.values()[randDir + 2];
				final BranchDirection initialBranchDir = BranchDirection.values()[randDir];
				BranchDirection currentBranchDir = initialBranchDir;
//				BranchDirection[] branches = Stream.of(BranchDirection.values()).filter(dir -> dir != initialBranchDir.getOpposite()).toArray((length) -> 
//				{ 
//					return new BranchDirection[length];
//				});
				
				int length = rand.nextInt(Math.max(size - 4, 1)) + 3;
				
				BlockPos currentBlockPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()).add(currentBranchDir.getToAdd());
				BlockState currentBlockState = getCurrentBlockState(currentBranchDir.getAxis(), logUp, rotatedX, rotatedZ);
				
				for(int j = 0; j < length; j++)
				{
					reader.setBlockState(currentBlockPos, currentBlockState, 2);
					
					if(rand.nextInt(2) == 0)
					{
						currentBranchDir = BranchDirection.values()[rand.nextInt(BranchDirection.values().length)];
						currentBlockState = getCurrentBlockState(currentBranchDir.getAxis(), logUp, rotatedX, rotatedZ);
					}
					
					currentBlockPos = currentBlockPos.add(currentBranchDir.getToAdd());
				}
			}
		}
		
		return true;
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
		POSITIVE_X(Direction.Axis.X, "NEGATIVE_X", new BlockPos(1, 0, 0)), NEGATIVE_X(Direction.Axis.X, "POSITIVE_X", new BlockPos(-1, 0, 0)), POSITIVE_Y(Direction.Axis.Y,"NEGATIVE_Y",  new BlockPos(0, 1, 0)), 
		NEGATIVE_Y(Direction.Axis.Y,"POSITIVE_Y",  new BlockPos(0, -1, 0)), POSITIVE_Z(Direction.Axis.Z,"NEGATIVE_Z",  new BlockPos(0, 0, 1)), NEGATIVE_Z(Direction.Axis.Z,"POSITIVE_Z",  new BlockPos(0, 0, -1)), 
		POSITIVEXY(Direction.Axis.X, "NEGATIVEXY", new BlockPos(1, 1, 0)), NEGATIVEXY(Direction.Axis.X, "POSITIVEXY", new BlockPos(-1, -1, 0)), POSITIVE_XZ(Direction.Axis.Z, "NEGATIVE_XZ", new BlockPos(1, 0, 1)), NEGATIVE_XZ(Direction.Axis.X, "POSITIVE_XZ", new BlockPos(-1, 0, -1)),
		POSITIVEZY(Direction.Axis.X, "NEGATIVEZY", new BlockPos(0, 1, 1)), NEGATIVEZY(Direction.Axis.X, "POSITIVEZY", new BlockPos(0, -1, -1));
		
		private BlockPos toAdd;
		private Direction.Axis axis;
		private String oppositeName;
		
		BranchDirection(Direction.Axis axis, String oppositeName, BlockPos toAdd)
		{
			this.axis = axis;
			this.toAdd = toAdd;
			this.oppositeName = oppositeName;
		}
		
		public BlockPos getToAdd()
		{
			return toAdd;
		}
		
		public Direction.Axis getAxis()
		{
			return axis;
		}
		
		public BranchDirection getOpposite()
		{
			return BranchDirection.valueOf(oppositeName);
		}
	}
}
