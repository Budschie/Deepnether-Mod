package de.budschie.deepnether.features;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;

public interface ITrunkPlacer<C extends IFeatureConfig>
{
	/** This method is invoked from {@link BasicTreeFeature#generate(ISeedReader, ChunkGenerator, Random, BlockPos, net.minecraft.world.gen.feature.IFeatureConfig)}.
	 * **/
	<A extends C> void placeTrunk(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ILeavesPlacer<? super A> leavesPlacer, IBranchPlacer<? super A> branchPlacer, A config);
}
