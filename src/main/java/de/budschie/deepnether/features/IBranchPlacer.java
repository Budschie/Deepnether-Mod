package de.budschie.deepnether.features;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;

public interface IBranchPlacer<C extends IFeatureConfig>
{
	<A extends C> void placeBranch(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, ILeavesPlacer<? super A> leavesPlacer, A config);
}
