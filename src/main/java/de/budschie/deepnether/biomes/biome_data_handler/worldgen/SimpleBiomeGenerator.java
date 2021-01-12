package de.budschie.deepnether.biomes.biome_data_handler.worldgen;

import de.budschie.deepnether.dimension.DeepnetherChunkGenerator;
import de.budschie.deepnether.dimension.InterpolationChannelBuffer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

public class SimpleBiomeGenerator implements IBiomeGenerator
{
	int seaLevel;
	int nearLavaBlockRange;
	BlockState grassBlock;
	BlockState groundBlock;
	BlockState lavaBlock;
	BlockState nearLavaBlock;
	
	public SimpleBiomeGenerator(int seaLevel, int nearLavaBlockRange, BlockState grassBlock, BlockState groundBlock,
			BlockState lavaBlock, BlockState nearLavaBlock)
	{
		super();
		this.seaLevel = seaLevel;
		this.nearLavaBlockRange = nearLavaBlockRange;
		this.grassBlock = grassBlock;
		this.groundBlock = groundBlock;
		this.lavaBlock = lavaBlock;
		this.nearLavaBlock = nearLavaBlock;
	}
	
	public SimpleBiomeGenerator(int nearLavaBlockRange, BlockState grassBlock, BlockState groundBlock,
			BlockState lavaBlock, BlockState nearLavaBlock)
	{
		this(WorldGenConsts.SEA_LEVEL, nearLavaBlockRange, grassBlock, groundBlock, lavaBlock, nearLavaBlock);
	}

	@Override
	public void generate(int chunkStartX, int chunkStartZ, int localX, int localZ, IChunk chunk,
			DeepnetherChunkGenerator chunkGenerator, InterpolationChannelBuffer interpolationChannelBuffer)
	{
		int currentTerrainHeight = interpolationChannelBuffer.<Integer>getValue("terrainHeight")[localX][localZ];
		double currentHeightValue = interpolationChannelBuffer.<Double>getValue("heightmap")[localX][localZ];
		
		int definedTerrainHeight = (int) (currentHeightValue * currentTerrainHeight);
		
		for(int y = 0; y <= currentTerrainHeight; y++)
		{
			BlockState chosenBlockState = Blocks.AIR.getDefaultState();
			
			if(y == definedTerrainHeight)
			{
				chosenBlockState = grassBlock;
			}
			else if(y < definedTerrainHeight)
			{
				if(y > seaLevel && y < (seaLevel + nearLavaBlockRange))
				{
					chosenBlockState = nearLavaBlock;
				}
				else
				{
					chosenBlockState = groundBlock;
				}
			}
			else if(y >= seaLevel)
			{
				chosenBlockState = lavaBlock;
			}
			
			chunk.setBlockState(new BlockPos(localX + chunkStartX, y, localZ + chunkStartZ), chosenBlockState, false);
		}
	}	
}
