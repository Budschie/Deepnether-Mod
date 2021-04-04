package de.budschie.deepnether.features.tree_generation.trunks;

import java.util.Random;
import java.util.function.BiConsumer;

import de.budschie.deepnether.features.tree_generation.ITreePart;
import de.budschie.deepnether.features.tree_generation.TreeTrunkSet;
import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import de.budschie.deepnether.features.tree_generation.branches.BranchEmitionArgs;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class WitheredTreeTrunks implements ITreePart
{
	private TreeTrunkSet blocks;
	
	public WitheredTreeTrunks(TreeTrunkSet blocks)
	{
		this.blocks = blocks;
	}

	@Override
	public void generate(BiConsumer<String, TreeEmitionArgs> emit, TreeEmitionArgs emitionArgs,
			ISeedReader reader, ChunkGenerator generator, Random rand)
	{
		int size = rand.nextInt(4) + 8;
		
		BlockPos pos = emitionArgs.getEmittedFrom();
		
		for(int i = 0; i < size; i++)
		{
			reader.setBlockState(new BlockPos(pos.getX(), pos.getY() + i, pos.getZ()), blocks.getLogUp(), 2);
			
			if(rand.nextInt(1) == 0 && i > 4)
			{
				BranchEmitionArgs branchEmitionArgs = new BranchEmitionArgs(pos, size, i);
				emit.accept("branch", branchEmitionArgs);
			}
		}
	}
}
