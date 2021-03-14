package de.budschie.deepnether.features.placements;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import com.mojang.serialization.Codec;

import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

public class ScatteredHeightmapPlacement extends Placement<ScatteredPlacementConfig>
{
	public ScatteredHeightmapPlacement()
	{
		super(ScatteredPlacementConfig.CODEC);
	}

	@Override
	public Stream<BlockPos> getPositions(WorldDecoratingHelper helper, Random rand, ScatteredPlacementConfig config,
			BlockPos pos)
	{
		ArrayList<BlockPos> positions = new ArrayList<>();
		
		ChunkGenerator basicChunkGen = helper.chunkGenerator;
		
		if(basicChunkGen instanceof DeepnetherChunkGenerator)
		{
			DeepnetherChunkGenerator deepneterChunkGen = (DeepnetherChunkGenerator) basicChunkGen;
			int[][] heights = deepneterChunkGen.getDeepnetherHeightmap(config.getChannel(), new ChunkPos(pos));
			
			for(int i = 0; i < config.getAmountPerChunk(); i++)
			{
				// I should remember that nextInt's provided argument is exclusive and stuff.
				int xOff = rand.nextInt(16);
				int zOff = rand.nextInt(16);
				
				positions.add(new BlockPos(pos.getX() + xOff, heights[xOff][zOff] + 1, pos.getZ() + zOff));
			}
		}
		else
			throw new IllegalArgumentException("ScatteredHeightmapPlacement#getPositions may not be invoked from a world which is not an instance of a DeepNetherChunkGenerator.");
		
		return positions.stream();
	}

}
