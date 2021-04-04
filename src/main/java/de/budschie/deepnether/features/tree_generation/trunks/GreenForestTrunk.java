package de.budschie.deepnether.features.tree_generation.trunks;

import java.util.Random;
import java.util.function.BiConsumer;

import de.budschie.deepnether.features.tree_generation.ITreePart;
import de.budschie.deepnether.features.tree_generation.TreeEmitionArgs;
import de.budschie.deepnether.features.tree_generation.TreeTrunkSet;
import de.budschie.deepnether.features.tree_generation.branches.BranchEmitionArgs;
import de.budschie.deepnether.util.MathUtil;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;

public class GreenForestTrunk implements ITreePart
{
	private TreeTrunkSet trunkSet;
	private float branchSparsityBias;
	private int branchSparsityDistance;
	
	public GreenForestTrunk(TreeTrunkSet trunkSet, float branchSparsityBias, int branchSparsityDistance)
	{
		this.trunkSet = trunkSet;
		this.branchSparsityBias = branchSparsityBias;
		this.branchSparsityDistance = branchSparsityDistance;
	}
	
	@Override
	public void generate(BiConsumer<String, TreeEmitionArgs> emit, TreeEmitionArgs emitionArgs, ISeedReader reader,
			ChunkGenerator generator, Random rand)
	{
		int size = rand.nextInt(10) + 5;
		
		int currentX = emitionArgs.getEmittedFrom().getX(), currentZ = emitionArgs.getEmittedFrom().getZ();
		int lastBranch = -69420;
		
		for(int i = 0; i < size; i++)
		{
			float progress = i / (float)size;
			float directionChangeProbability = (float) Math.sqrt(progress);
			float branchProbability = Math.abs((progress * progress) - directionChangeProbability);
			
			if(rand.nextFloat() <= directionChangeProbability)
			{
				boolean dir = rand.nextBoolean();
				int amount = rand.nextBoolean() ? -1 : 1;
				
				if(dir)
					currentX += amount;
				else
					currentZ += amount;
			}
			
			// Let's calculate the distance to the last branch
			int distanceToLastBranch = i - lastBranch;
			
			float biasedDistanceAffection = MathUtil.linearInterpolation(1, Math.min(branchSparsityDistance, distanceToLastBranch) / (float)branchSparsityDistance, branchSparsityBias);
			
			// Then, we have to 
			
			float biasedBranchProbability = branchProbability * biasedDistanceAffection;
			
			if(rand.nextFloat() <= biasedBranchProbability)
			{
				// Spawn in branch
				emit.accept("branch", new BranchEmitionArgs(new BlockPos(currentX, i + emitionArgs.getEmittedFrom().getY(), currentZ), size, i));
			}
			
			reader.setBlockState(new BlockPos(currentX, i + emitionArgs.getEmittedFrom().getY(), currentZ), trunkSet.getLogUp(), 2);
		}
	}
	
}
