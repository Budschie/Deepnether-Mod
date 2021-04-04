package de.budschie.deepnether.features.tree_generation.branches;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import de.budschie.deepnether.features.WitheredTreeFeature.BranchDirection;
import de.budschie.deepnether.features.tree_generation.ITreePart;
import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import de.budschie.deepnether.features.tree_generation.TreeTrunkSet;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class WitheredTreeBranch implements ITreePart
{	
	private TreeTrunkSet blocks;
	
	public WitheredTreeBranch(TreeTrunkSet blocks)
	{
		this.blocks = blocks;
	}

	@Override
	public void generate(BiConsumer<String, TreeEmitionArgs> emit, TreeEmitionArgs emitionArgs,
			ISeedReader reader, ChunkGenerator generator, Random rand)
	{
		if(!(emitionArgs instanceof BranchEmitionArgs))
			throw new IllegalArgumentException("The emition args of " + emitionArgs.getClass().getName() + " and " + BranchEmitionArgs.class.getName() + " are not compatible.");
		
		BlockPos pos = emitionArgs.getEmittedFrom();
		int size = ((BranchEmitionArgs)emitionArgs).getTreeSize();
		int i = ((BranchEmitionArgs)emitionArgs).getCurrentPos();
		
		int randDir = rand.nextInt(4);
		final BranchDirection initialBranchDir = randDir < 2 ? BranchDirection.values()[randDir] : BranchDirection.values()[randDir + 2];
		BranchDirection currentBranchDir = initialBranchDir;
		BranchDirection[] branches = Stream.of(BranchDirection.values()).filter(dir -> dir != initialBranchDir.getOpposite()).toArray((length) -> 
		{ 
			return new BranchDirection[length];
		});
		
		int length = rand.nextInt(Math.max(size - 4, 1)) + 3;
		
		BlockPos currentBlockPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()).add(currentBranchDir.getToAdd());
		BlockState currentBlockState = blocks.getCurrentBlockState(currentBranchDir.getAxis());
		
		for(int j = 0; j < length; j++)
		{
			reader.setBlockState(currentBlockPos, currentBlockState, 2);
			
			if(rand.nextInt(2) == 0)
			{
				currentBranchDir = branches[rand.nextInt(branches.length)];
				currentBlockState = blocks.getCurrentBlockState(currentBranchDir.getAxis());
			}
			
			currentBlockPos = currentBlockPos.add(currentBranchDir.getToAdd());
		}
	}
}
