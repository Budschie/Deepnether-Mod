package de.budschie.deepnether.features;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class GreenForestTrunkPlacer implements ITrunkPlacer<BasicLogFeatureConfig>
{
	@Override
	public <A extends BasicLogFeatureConfig> void placeTrunk(ISeedReader reader, ChunkGenerator generator, Random rand,
			BlockPos pos, ILeavesPlacer<? super A> leavesPlacer, IBranchPlacer<? super A> branchPlacer, A config)
	{
		int treeSize = rand.nextInt(4) + 4;
				
		for(int i = 0; i < treeSize; i++)
		{
			BlockPos currentTrunkPos = new BlockPos(pos.getX(), pos.getY() + i, pos.getZ());
			leavesPlacer.placeTrunkLeaves(reader, generator, rand, currentTrunkPos, i, treeSize, config);
			reader.setBlockState(currentTrunkPos, config.getLogUp().getBlockState(rand, currentTrunkPos), 2);
		}
	}
}
