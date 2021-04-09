package de.budschie.deepnether.features.tree_generation.branches;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import de.budschie.deepnether.features.WitheredTreeFeature.BranchDirection;
import de.budschie.deepnether.features.tree_generation.ITreePart;
import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import de.budschie.deepnether.features.tree_generation.TreeTrunkSet;
import de.budschie.deepnether.features.tree_generation.leaves.LeavesEmitionArgs;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class WitheredTreeBranch implements ITreePart
{	
	private TreeTrunkSet blocks;
	private float leavesChance;
	
	public WitheredTreeBranch(TreeTrunkSet blocks, float leavesChance)
	{
		this.blocks = blocks;
		this.leavesChance = leavesChance;
	}

	@Override
	public void generate(BiConsumer<String, TreeEmitionArgs> emit, TreeEmitionArgs emitionArgs,
			ISeedReader reader, ChunkGenerator generator, Random rand)
	{
		if(!(emitionArgs instanceof BranchEmitionArgs))
			throw new IllegalArgumentException("The emition args of " + emitionArgs.getClass().getName() + " and " + BranchEmitionArgs.class.getName() + " are not compatible.");
		
		BlockPos pos = emitionArgs.getEmittedFrom();
		int size = ((BranchEmitionArgs)emitionArgs).getTreeSize();
		int currentTreeHeight = ((BranchEmitionArgs)emitionArgs).getCurrentPos();
		
		final BranchDirection[] initialBranches = Stream.of(BranchDirection.values()).filter(element -> 
		{
			if(element == BranchDirection.NEGATIVEXY || element == BranchDirection.POSITIVEXY || element == BranchDirection.NEGATIVEZY || element == BranchDirection.POSITIVEZY || element == BranchDirection.POSITIVE_Y || element == BranchDirection.NEGATIVE_Y)
				return false;
			else
				return true;
		}).toArray(arraySize -> new BranchDirection[arraySize]);
		
		int randDir = rand.nextInt(initialBranches.length);
		final BranchDirection initialBranchDir = initialBranches[randDir];
		BranchDirection currentBranchDir = initialBranchDir;
		
		int length = rand.nextInt(Math.max(size - 4, 1)) + 3;
		
		BlockPos currentBlockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ()).add(currentBranchDir.getToAdd());
		BlockState currentBlockState = blocks.getCurrentBlockState(currentBranchDir.getAxis());
		
		//currentBlockPos = currentBlockPos.add(currentBranchDir.getToAdd());
		
		for(int j = 0; j < length; j++)
		{
			reader.setBlockState(currentBlockPos, currentBlockState, 2);
			
			if(rand.nextFloat() < leavesChance)
				emit.accept("leaves", new LeavesEmitionArgs(currentBlockPos, currentTreeHeight, size));
			
			if(rand.nextInt(2) == 0)
			{
				currentBranchDir = BranchDirection.values()[rand.nextInt(BranchDirection.values().length)];
				currentBlockState = blocks.getCurrentBlockState(currentBranchDir.getAxis());
			}
			
			currentBlockPos = currentBlockPos.add(currentBranchDir.getToAdd());
		}
	}
}
