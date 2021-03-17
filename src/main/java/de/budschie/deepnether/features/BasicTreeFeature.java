package de.budschie.deepnether.features;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class BasicTreeFeature<C extends IFeatureConfig> extends Feature<C>
{
	ITrunkPlacer<? super C> trunkPlacer;
	ILeavesPlacer<? super C> leavesPlacer;
	IBranchPlacer<? super C> branchPlacer;
	
	public BasicTreeFeature(Codec<C> codec, ITrunkPlacer<? super C> trunkPlacer, ILeavesPlacer<? super C> leavesPlacer, IBranchPlacer<? super C> branchPlacer)
	{
		super(codec);
		
		this.trunkPlacer = trunkPlacer;
		this.leavesPlacer = leavesPlacer;
		this.branchPlacer = branchPlacer;
	}
	
	@Override
	public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, C config)
	{
		trunkPlacer.<C>placeTrunk(reader, generator, rand, pos, leavesPlacer, branchPlacer, config);
		
		return false;
	}
	
	//public void placeLeaves(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, C config)

	protected BlockState getCurrentBlockState(Direction.Axis axis, BlockState logUp, BlockState rotatedX, BlockState rotatedZ)
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
		POSITIVEXY(Direction.Axis.X, "NEGATIVEXY", new BlockPos(1, 1, 0)), NEGATIVEXY(Direction.Axis.X, "POSITIVEXY", new BlockPos(-1, -1, 0)), 
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
