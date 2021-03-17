package de.budschie.deepnether.features;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;

public interface ILeavesPlacer<C extends IFeatureConfig>
{
	void placeBranchLeaves(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, int currentBranchLength, int maxBranchLength, C config);
	void placeTrunkLeaves(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, int currentTrunkHeight, int maxTrunkHeight, C config);
}
